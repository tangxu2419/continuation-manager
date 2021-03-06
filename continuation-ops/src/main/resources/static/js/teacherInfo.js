$(function () {


    var onSearchTeacherInfo = function () {
        var query = {
            level: $('#teacherLevel').val(),
            subject: $('#search_subject').val(),
            workNumber: $('#work_number').val()
        };
        var Apply = Backbone.Model.extend({});

        /** @namespace Backbone.PageableCollection */
        var PageableTerritories = Backbone.PageableCollection.extend({
            model: Apply,
            url: "/teacher/search",
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
                $('.modal-title').text("教职工信息");
                $('#modalExt').modal('show');
                initBaseJson(model.attributes.id);
            } else if (name === "update") {
                state = 1;
                $('#updateModalTitle').text("更新教职工信息");
                $("#teacherInfoForm")[0].reset();
                var $updateModal = $('#updateModal');
                $updateModal.data('id', model.attributes.id);
                $updateModal.find("#workNumber").val(model.attributes.workNumber);
                $updateModal.find("#password").val(model.attributes.password);
                $updateModal.find("#level").val(model.attributes.level).trigger('change');
                $updateModal.find("#subject").val(model.attributes.subject).trigger('change');
                $updateModal.find("#name").val(model.attributes.name);
                $updateModal.find("#identityNumber").val(model.attributes.identityNumber);
                $updateModal.find("#homePhone").val(model.attributes.homePhone);
                $updateModal.find("#telephone").val(model.attributes.telephone);
                $updateModal.find("#homeAddress").val(model.attributes.homeAddress);
                $updateModal.find("#contactName").val(model.attributes.contactName);
                $updateModal.find("#contactTelephone").val(model.attributes.contactTelephone);
                $updateModal.find("#contactRelationship").val(model.attributes.contactRelationship);
                $updateModal.find('#voided').attr('checked', !model.attributes.voided);
                $updateModal.modal('show');
            } else {
                alert("点击事件不存在");
            }
        }

        // 查询申请用户基本信息
        function initBaseJson(id) {
            $.get('/teacher/searchById', {
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
                name: "workNumber",
                label: "工号",
                cell: "string"
            }, {
                editable: false,
                name: "name",
                label: "姓名",
                cell: "string"
            }, {
                editable: false,
                name: "subject",
                label: "科目",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return _SUBJECT_MAP[rawValue];
                    }
                })
            }, {
                editable: false,
                name: "sex",
                label: "性别",
                cell: "string"
            }, {
                editable: false,
                name: "telephone",
                label: "手机号码",
                cell: "string"
            }, {
                editable: false,
                name: "homeAddress",
                label: "住址",
                cell: "string"
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
    };

    var state = 0;

    /**
     * 初始化添加教师信息表单
     */
    function initTeacherForm() {
        state = 0;
        var $updateModal = $('#updateModal');
        $('#updateModalTitle').text("添加教师");
        $("#teacherInfoForm")[0].reset();
        $("#workNumber").removeAttr("readonly");
        $("#password").removeAttr("readonly");
        var $level = $('#level');
        $level.select2($level.data());
        var $subject = $('#subject');
        $subject.select2($subject.data());
        $updateModal.modal('show');
    }

    /**
     * 保存教师信息
     */
    function onSaveTeacherInfo() {
        var $teacherInfoForm = $("#teacherInfoForm");
        if (!$teacherInfoForm.parsley().validate()) {
            return false;
        }
        var data = {};
        var dataArray = $teacherInfoForm.serializeArray();
        $.each(dataArray, function (i, field) {
            data[field.name] = field.value;
        });
        data["voided"] = data["voided"] !== "on";
        if (state !== 0) {
            data["id"] = $("#updateModal").data('id');
        }
        $.ajax({
            url: '/teacher/update',
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (result) {
                if (state === 0) {
                    alert("添加成功！");
                } else {
                    alert("更新成功！");
                }
                onSearchTeacherInfo();
                $("#updateModal").modal('hide');
            },
            error: function (result) {
                alert("系统异常");

            }
        });
    }

    /**
     *  获取所有配置：级别/科目
     */
    function initTeacherConfigInfo() {
        var $level = $("select[name='level']");
        initSelect($level);
        $level.append($("<option>", {value: "1"}).text("1"));
        $level.append($("<option>", {value: "2"}).text("2"));
        $level.select2($level.data());
        var $subject = $("select[name='subject']");
        initSelect($subject);
        $subject.append($("<option>", {value: "MATH"}).text("数学"));
        $subject.append($("<option>", {value: "CHINESE"}).text("语文"));
        $subject.append($("<option>", {value: "ENGLISH"}).text("英语"));
        $subject.select2($subject.data());
    }

    /**
     * 页面加载
     */
    function pageLoad() {
        var $isVoided = $('#isVoided');
        $isVoided.select2($isVoided.data());

        initTeacherConfigInfo();

        // 绑定button点击事件
        $('#insertButton').on('click', function () {
            initTeacherForm();
        });
        $('#commitButton').on('click', function () {
            onSaveTeacherInfo();
        });
        $('#searchButton').on('click', function () {
            onSearchTeacherInfo();
        });


    }

    pageLoad();
    PjaxApp.onPageLoad(pageLoad);

});