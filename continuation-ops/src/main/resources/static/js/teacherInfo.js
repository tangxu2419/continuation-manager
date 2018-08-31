$(function () {


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
            data["id"] = $("id").val();
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
                $("#updateModal").modal('hide');
            },
            error: function (result) {
                alert("系统异常");

            }
        });
    }

    /**
     * TODO 获取所有配置：级别/科目
     */
    function initConfigInfo() {
        var $subject = $('#subject');
        $subject.append($("<option>", {value: "MATH"}).text("数学"));
        $subject.append($("<option>", {value: "CHINESE"}).text("语文"));
        $subject.append($("<option>", {value: "ENGLISH"}).text("英语"));
        $subject.select2($subject.data());
    }

    /**
     * 页面加载
     */
    function pageLoad() {
        var $teacherLevel = $('#teacherLevel');
        $teacherLevel.select2($teacherLevel.data());
        var $isVoided = $('#isVoided');
        $isVoided.select2($isVoided.data());

        initConfigInfo();

        // 绑定button点击事件
        $('#insertButton').on('click', function () {
            initTeacherForm();
        });
        $('#commitButton').on('click', function () {
            onSaveTeacherInfo();
        });


    }

    pageLoad();
    PjaxApp.onPageLoad(pageLoad);

});