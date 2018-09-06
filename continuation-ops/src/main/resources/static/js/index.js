$(function () {

    function changeVerCode(value) {

    }

    function pageLoad(){
        // 绑定change点击事件
        var $username = $("#username");
        $username.on('blur', function () {
            changeVerCode(this.value);
        });
    }

    pageLoad();
});