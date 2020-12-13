$(() => {
   // 监控本页是否按下了回车键，如果动作发生，验证用户名密码，发送登录请求
    $(".pass").keydown((event) => {
        if (event.which === 13) {
            var username = $(".un").val();
            var password = $(".pass").val();

            if ((username.length < 6 || username.length > 20)
                || (password.length < 6 || password.length > 20)) {
                alert("请输入正确的用户名和密码")
            } else {
                var logData = {
                    username: username,
                    password: password
                }
                sendWithPost("/shiro_go_war_exploded/user/login", JSON.stringify(logData), loginSuccess, loginFail);
            }
        }
    });

    $(".submit").click(() => {
        alert("现在系统还没做好，不让你注册哦！");
    });
    $(".forgot").click(() => {
        alert("自己去后台改密码吧！");
    });
});