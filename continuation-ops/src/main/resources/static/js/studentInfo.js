$(function () {


    /**
     * 学生信息查询
     */
    function onSearchStudentInfo() {
        var query = {
            classId: $('#search_classId').val(),
            studentNumber: $('#student_number').val()
        };
        var Apply = Backbone.Model.extend({});

        /** @namespace Backbone.PageableCollection */
        var PageableTerritories = Backbone.PageableCollection.extend({
            model: Apply,
            url: "/student/search",
            state: {
                pageSize: 10
            },
            queryParams: {
                currentPage: "page",
                pageSize: "size",
                totalPages: null,
                totalRecords: null,
                query: JSON.stringify(query)

            },
            parseState: function (resp) {
                return {
                    totalRecords: resp.totalElements
                };
            },
            parseRecords: function (resp) {
                return resp.content;
            }
        });

        var pageableTerritories = new PageableTerritories();

        //绑定查询表格行的事件
        var FocusableRow = Backgrid.Row.extend({
            highlightColor: "lightYellow",
            events: {
                "click": "onclick"
            },
            onclick: function () {
                this.$el.parent().children().each(function () {
                    delete $(this).removeAttr("style");
                });
                this.el.style.backgroundColor = this.highlightColor;
                // if (currentRow !== this) {
                //     currentRowChanged(this);
                //     currentRow = this;
                // }
            }
        });

        //绑定查询结果单元格中button的事件
        Backgrid.ButtonCell = Backgrid.Cell.extend({
            className: "button-cell",
            render: function () {
                this.$el.empty();
                var model = this.model, column = this.column;
                this.$el.append($("<button>", {
                    class: "btn btn-success btn-xs"
                }).on("click", function () {
                    onDetailButtonClick(model, column.attributes.name);
                }).text(this.formatter.fromRaw(model.get(this.column.get("name")), model)));
                this.delegateEvents();
                return this;
            }
        });

        // button绑定的点击方法
        function onDetailButtonClick(model, name) {
            if (name === "detail") {
                $('.modal-title').text("学员信息");
                $('#modalExt').modal('show');
                initBaseJson(model.attributes.id);
            } else if (name === "update") {
                state = 1;
                $('#updateModalTitle').text("更新学员信息");
                $("#infoForm")[0].reset();
                var $updateModal = $('#updateModal');
                $updateModal.data('id', model.attributes.id);
                $updateModal.find("#studentNumber").val(model.attributes.studentNumber);
                $updateModal.find("#password").val(model.attributes.password);
                if (clazz) {
                    $.each(clazz, function () {
                        if (this.id === parseInt(model.attributes.classId)) {
                            $updateModal.find("select[name='year']").val(this.year).trigger('change');
                            $updateModal.find("#classId").val(model.attributes.classId).trigger('change');
                        }
                    })
                }
                $updateModal.find("#name").val(model.attributes.name);
                $updateModal.find("#identityNumber").val(model.attributes.identityNumber);
                $updateModal.find("#contactNumber").val(model.attributes.contactNumber);
                $updateModal.find("#homeAddress").val(model.attributes.homeAddress);
                $updateModal.find("#contactName").val(model.attributes.contactName);
                $updateModal.find("#contactTelephone").val(model.attributes.contactTelephone);
                $updateModal.find("#contactRelationship").val(model.attributes.contactRelationship);
                $updateModal.find("#standbyContactName").val(model.attributes.standbyContactName);
                $updateModal.find("#standbyContactTelephone").val(model.attributes.standbyContactTelephone);
                $updateModal.find("#standbyContactRelationship").val(model.attributes.standbyContactRelationship);
                $updateModal.find('#voided').attr('checked', !model.attributes.voided);
                $updateModal.modal('show');
            } else {
                alert("点击事件不存在");
            }
        }

        // 查询申请用户基本信息
        function initBaseJson(id) {
            $.get('/student/searchById', {
                id: id
            }, function (data, status) {
                if (status === "success") {
                    var $json_renderer = $('#json-renderer');
                    $json_renderer.children().remove();
                    $json_renderer.jsonViewer(data);
                }
            });
        }


        // 创建查询申请信息列表
        function createBackslid(collection) {
            var columns = [{
                editable: false,
                name: "studentNumber",
                label: "学号",
                cell: "string"
            }, {
                editable: false,
                name: "name",
                label: "姓名",
                cell: "string"
            }, {
                editable: false,
                name: "classId",
                label: "班级",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        if (clazz) {
                            $.each(clazz, function () {
                                if (this.id === rawValue) {
                                    return this.classDesc;
                                }
                            })
                        }
                    }
                })
            }, {
                editable: false,
                name: "sex",
                label: "性别",
                cell: "string"
            }, {
                editable: false,
                name: "homeAddress",
                label: "住址",
                cell: "string"
            }, {
                editable: false,
                name: "voided",
                label: "是否在读",
                cell: "boolean",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return !rawValue;
                    }
                })
            }, {
                editable: false,
                cell: "button",
                label: "详情",
                name: "detail",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return '查看';
                    }
                })
            }, {
                editable: false,
                cell: "button",
                label: "更新",
                name: "update",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return '更新';
                    }
                })
            }];
            if (LightBlue.isScreen('xs')) {
                columns.splice(3, 1)
            }
            var pageableGrid = new Backgrid.Grid({
                row: FocusableRow,
                columns: columns,
                collection: collection,
                className: 'table table-striped table-editable no-margin mb-sm'
            });

            var paginator = new Backgrid.Extension.Paginator({
                slideScale: 0.25, // Default is 0.5
                // Whether sorting should go back to the first page
                goBackFirstOnSort: false, // Default is true
                collection: collection,
                controls: {
                    rewind: {
                        label: '<i class="fa fa-angle-double-left fa-lg"></i>',
                        title: "First"
                    },
                    back: {
                        label: '<i class="fa fa-angle-left fa-lg"></i>',
                        title: "Previous"
                    },
                    forward: {
                        label: '<i class="fa fa-angle-right fa-lg"></i>',
                        title: "Next"
                    },
                    fastForward: {
                        label: '<i class="fa fa-angle-double-right fa-lg"></i>',
                        title: "Last"
                    }
                }
            });
            $("#table-apply").html('').append(pageableGrid.render().$el).append(paginator.render().$el);
        }

        createBackslid(pageableTerritories);
        pageableTerritories.fetch();
    }

    var state = 0;

    /**
     * 初始化添加学员信息表单
     */
    function initStudentForm() {
        state = 0;
        var $updateModal = $('#updateModal');
        $('#updateModalTitle').text("添加学员");
        $("#infoForm")[0].reset();
        var $studentNumber = $("#studentNumber");
        $studentNumber.parent().parent().hide();
        $studentNumber.removeAttr("required");
        $("#name").removeAttr("readonly");
        $("#password").removeAttr("readonly");
        var $level = $('#level');
        $level.select2($level.data());
        var $subject = $('#subject');
        $subject.select2($subject.data());
        $updateModal.modal('show');
    }

    /**
     * 保存学生信息
     */
    function onSaveStudentInfo() {
        var $infoForm = $("#infoForm");
        if (!$infoForm.parsley().validate()) {
            return false;
        }
        var data = {};
        var dataArray = $infoForm.serializeArray();
        $.each(dataArray, function (i, field) {
            data[field.name] = field.value;
        });
        data["voided"] = data["voided"] !== "on";
        if (state !== 0) {
            data["id"] = $("#updateModal").data('id');
        }
        $.ajax({
            url: '/student/update',
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (result) {
                if (state === 0) {
                    alert("添加成功！");
                } else {
                    alert("更新成功！");
                }
                onSearchStudentInfo();
                $("#updateModal").modal('hide');
            },
            error: function (result) {
                alert(result.responseJSON.message);

            }
        });
    }

    /**
     *  获取所有配置：级别/科目
     */
    function initConfigInfo() {
        var $year = $("select[name='year']");
        initSelect($year);
        var year = new Date().getFullYear();
        for (var i = 0; i < 10; i++) {
            $year.append($("<option>", {value: year}).text(year));
            year--;
        }
        $year.select2($year.data());
        var $classId = $("select[name='classId']");
        initSelect($classId);
        $classId.select2($classId.data());
        $.get('/class/findAll', {}, function (data, status) {
            if (status === "success") {
                clazz = data;
            } else {
                alert(data.message);
                return false;
            }
        });
    }

    var clazz;
    function changeYear(year) {
        var $classId = $("select[name='classId']");
        initSelect($classId);
        if (clazz) {
            $.each(clazz, function () {
                if (this.year === year) {
                    $classId.append($("<option>", {value: this.id}).text(this.classDesc));
                }
            })
        }
        $classId.select2($classId.data());
        // $.get('/class/findByYear', {
        //     year: year
        // }, function (data, status) {
        //     if (status === "success") {
        //         clazz = data;
        //         var $classId = $("select[name='classId']");
        //         initSelect($classId);
        //         $.each(data,function (index, value) {
        //             $classId.append($("<option>", {value: this.id}).text(this.classDesc));
        //         });
        //         $classId.select2($classId.data());
        //     }else{
        //         alert("系统异常");
        //         return false;
        //     }
        // });
    }

    /**
     * 页面加载
     */
    function pageLoad() {
        initConfigInfo();
        // 绑定change点击事件
        var $year = $("select[name='year']");
        $year.on('change', function () {
            changeYear(this.value);
        });

        // 绑定button点击事件
        $('#insertButton').on('click', function () {
            initStudentForm();
        });
        $('#commitButton').on('click', function () {
            onSaveStudentInfo();
        });
        $('#searchButton').on('click', function () {
            onSearchStudentInfo();
        });


    }

    pageLoad();
    PjaxApp.onPageLoad(pageLoad);

});