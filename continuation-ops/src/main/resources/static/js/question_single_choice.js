$(function () {

    /**
     * 题目答案
     * @type {*|jQuery|HTMLElement}
     */
    var $options = $('.input-group-add');
    $options.initInputGroup({
        'widget': 'input'
    });

    var c = $.extend({
        'widget': 'input',
        'add': "<span class=\"glyphicon glyphicon-plus\"></span>",
        'del': "<span class=\"glyphicon glyphicon-minus\"></span>"
    });

    function initSelect(element){
        element.empty();
        element.append($("<option>", {value: ""}).text("-"));
    }

    function changeAnswer() {
        var answer = [];
        var answerDesc = [];
        var i = 0;
        $('.input-group-add').children("div").each(function () {
            var desc = $(this).children('input')[0].value;
            if (desc) {
                answer[i] = $(this).children('span').text();
                answerDesc[i] = $(this).children('span').text() + "." + desc;
                i++;
            }
        });
        if (answer.length < 1) return;
        var $answer = $("#answer");
        initSelect($answer);
        $.each(answer, function (index, value) {
            $answer.append($("<option>", {value: value.trim()}).text(value.trim()));
        });
        $answer.select2($answer.data());
        var $answerDesc = $("#answerDesc");
        $answerDesc.empty();
        $answerDesc.append($("<option>", {value: ""}).text("-"));
        $.each(answerDesc, function (index, value) {
            $answerDesc.append($("<option>", {value: value.trim()}).text(value.trim()));
        });
        $answerDesc.select2($answerDesc.data());
    }

    /**
     * 条件查询单选问题
     */
    function onSearchQuestionInfo() {
        var query = {
            level: $('#search_level').val(),
            subject: $('#search_subject').val(),
            unit: $('#search_unit').val()
        };
        var Apply = Backbone.Model.extend({});

        /** @namespace Backbone.PageableCollection */
        var PageableTerritories = Backbone.PageableCollection.extend({
            model: Apply,
            url: "/question/search/singleChoice",
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
                $('.modal-title').text("题目详情");
                $('#modalExt').modal('show');
                initBaseJson(model.attributes.id);
            } else if (name === "update") {
                state = 1;
                var $options = $('.input-group-add');
                $options.empty();
                $('#updateModalTitle').text("更新题目");
                $("#questionInfoForm")[0].reset();
                var $updateModal = $('#updateModal');
                $updateModal.data('id', model.attributes.id);
                $updateModal.find("#level").val(model.attributes.level).trigger('change');
                $updateModal.find("#subject").val(model.attributes.subject).trigger('change');
                $updateModal.find("#unit").val(model.attributes.unit).trigger('change');
                $updateModal.find("#score").val(model.attributes.score);
                $updateModal.find("#answerTime").val(model.attributes.answerTime.replace(/[^0-9]/ig, ""));
                $updateModal.find("#number").val(model.attributes.number);
                $updateModal.find("#title").val(model.attributes.question.title);
                var $answer = $('#answer');
                $answer.empty();
                $answer.append($("<option>", {value: ""}).text("-"));
                var $answerDesc = $('#answerDesc');
                $answerDesc.empty();
                $answerDesc.append($("<option>", {value: ""}).text("-"));
                var i = 65;
                $.each(model.attributes.question.options, function (index, value) {
                    addInputGroup($options, c, $.trim(this.split(".")[0]),$.trim(this.split(".")[1]));
                    i++;
                    $answer.append($("<option>", {value: this.split(".")[0].trim()}).text($.trim(this.split(".")[0])));
                    $answerDesc.append($("<option>", {value: $.trim(this)}).text($.trim(this)));
                });
                $answer.select2($answer.data());
                $answerDesc.select2($answerDesc.data());
                $answer.val(model.attributes.answer).trigger('change');
                $answerDesc.val(model.attributes.answerDesc).trigger('change');
                $updateModal.modal('show');
            } else {
                alert("点击事件不存在");
            }
        }

        // 查询申请用户基本信息
        function initBaseJson(id) {
            $.get('/question/search/singleChoiceById', {
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
                name: "unit",
                label: "单元",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return _UNIT_MAP[rawValue];
                    }
                })
            }, {
                editable: false,
                name: "level",
                label: "问题级别",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return _LEVEL_MAP[rawValue];
                    }
                })
            }, {
                editable: false,
                name: "question",
                label: "问题详情",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return rawValue.title;
                    }
                })
            }, {
                editable: false,
                name: "answer",
                label: "答案",
                cell: "string"
            }, {
                editable: false,
                name: "answerDesc",
                label: "答案明细",
                cell: "string"
            }, {
                editable: false,
                name: "score",
                label: "分值",
                cell: "number"
            }, {
                editable: false,
                name: "answerTime",
                label: "答题时间/分钟",
                cell: "string",
                formatter: _.extend({}, Backgrid.CellFormatter.prototype, {
                    fromRaw: function (rawValue, model) {
                        return rawValue.replace(/[^0-9]/ig, "");
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

    /**
     * 保存信息
     */
    function onSaveQuestionInfo() {
        var $questionInfoForm = $("#questionInfoForm");
        if (!$questionInfoForm.parsley().validate()) {
            return false;
        }
        var data = {};
        var dataArray = $questionInfoForm.serializeArray();
        $.each(dataArray, function (i, field) {
            data[field.name] = field.value;
        });
        data["voided"] = data["voided"] !== "on";
        if (state !== 0) {
            data["id"] = $("#updateModal").data('id');
        }
        var options = [];
        var i = 0;
        $('.input-group-add>div').each(function () {
            options[i] = $(this).children('span').text().trim() + "." + $(this).children('input')[0].value;
            i++;
        });
        data['question'] = {
            title: $("#title").val(),
            options: options
        };
        data["answerTime"] = "PT" + data["answerTime"] + "M";
        $.ajax({
            url: '/question/update/singleChoice',
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (result) {
                if (state === 0) {
                    alert("添加成功！");
                } else {
                    alert("更新成功！");
                }
                $("#updateModal").modal('hide');
                onSearchQuestionInfo();
            },
            error: function (result) {
                alert("系统异常");

            }
        });
    }

    var state;

    /**
     * 初始化添加教师信息表单
     */
    function initQuestionForm() {
        state = 0;
        var $options = $('.input-group-add');
        $options.empty();
        $options.initInputGroup({
            'widget': 'input'
        });
        var $updateModal = $('#updateModal');
        $('#updateModalTitle').text("添加题目");
        $("#questionInfoForm")[0].reset();
        $("#workNumber").removeAttr("readonly");
        $("#password").removeAttr("readonly");
        $updateModal.find("#level").val("").trigger('change');
        $updateModal.find("#subject").val("").trigger('change');
        $updateModal.find("#unit").val("").trigger('change');
        var $answer = $('#answer');
        $answer.empty();
        $answer.append($("<option>", {value: ""}).text("-"));
        $answer.select2($answer.data());
        var $answerDesc = $('#answerDesc');
        $answerDesc.empty();
        $answerDesc.append($("<option>", {value: ""}).text("-"));
        $answerDesc.select2($answerDesc.data());
        $updateModal.modal('show');
    }

    /**
     *  获取所有配置：级别/科目
     */
    function initConfigInfo() {
        $.ajax({
            url: '/config/question',
            type: 'GET',
            success: function (result) {
                var levels = result.levels;
                subjects = result.subject;
                // 添加问题级别信息
                var $level = $("select[name='level']");
                $.each(levels, function (i, value) {
                    $level.append($("<option>", {value: value}).text(_LEVEL_MAP[value]));
                });
                $level.select2($level.data());
                // 添加科目级别
                var $subject = $("select[name='subject']");
                $.each(subjects, function (i, subject) {
                    $.each(subject,function (name,units) {
                        $subject.append($("<option>", {value: name}).text(_SUBJECT_MAP[name]));
                    });
                });
                $subject.select2($subject.data());
            },
            error: function () {
                alert("系统异常");
                return false;
            }
        });

        var $unit = $("select[name='unit']");
        $unit.select2($unit.data());
    }

    var subjects;
    function changeUnit(val) {
        $.each(subjects, function (i, subject) {
            $.each(subject,function (name,units) {
                if( val === name ){
                    var $unit = $("select[name='unit']");
                    initSelect($unit);
                    $.each(units,function (i,unit) {
                        $unit.append($("<option>", {value: unit}).text(_UNIT_MAP[unit]));
                    });
                    $unit.select2($unit.data());
                }
            });
        });
    }

    /**
     * 页面加载
     */
    function pageLoad() {
        initConfigInfo();

        // 绑定change点击事件
        var $subject = $("select[name='subject']");
        $subject.on('change', function () {
            changeUnit(this.value);
        });

        // 绑定button点击事件
        $('#insertButton').on('click', function () {
            initQuestionForm();
        });
        $('#commitButton').on('click', function () {
            onSaveQuestionInfo();
        });
        $('#searchButton').on('click', function () {
            onSearchQuestionInfo();
        });
        $('.input-group-add').on('change', function () {
            changeAnswer();
        });

    }

    pageLoad();
    PjaxApp.onPageLoad(pageLoad);
});