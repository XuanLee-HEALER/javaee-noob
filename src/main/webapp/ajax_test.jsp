<!DOCTYPE html>
<html lang="zh-Hans">
<head>
    <meta charset="UTF-8">
    <title>Ajax Go</title>
</head>
<body>
    <form id="login">
        <input type="text" name="username">
        <input type="password" name="password">
    </form>
    <button id="submit">Click</button>

    <script src="js/jquery-3.5.1.js"></script>
    <script>
        $(() => {
            $("#submit").click(() => {
                var username = $("form input:text[name='username']").val();
                var password = $("form input:password").val();

                $.ajax({
                    url: "/shiro_go_war_exploded/user/login",
                    dataType: "json",
                    method: "post",
                    data: {
                        username: username,
                        password: password,
                    },
                    success: data => console.log(data),
                    error: err => console.log(err)
                })
                $.ajax({
                    url: "/shiro_go_war_exploded/user/login",
                    dataType: "json",
                    method: "get",
                    data: {
                        username: "nihao",
                        password: "woyehao",
                    },
                    success: data => console.log(data),
                    error: err => console.log(err)
                })
            })
        })
    </script>
</body>
</html>