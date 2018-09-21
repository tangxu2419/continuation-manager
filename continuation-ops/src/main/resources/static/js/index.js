$(function () {

    function changeVerCode(username) {
        if( username ){
            $.get('/login/init', {
                username: username
            }, function (result, status) {
                if (status === "success") {
                    const data = result.data;
                    $("#kaptchaImage").attr("src","data:image/jpeg;base64,"+data.imageBase64);
                    $("div[name='verify']").show();
                }else{
                    alert(result.message);
                }
            });
        }else{
            $("div[name='verify']").hide();
        }
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