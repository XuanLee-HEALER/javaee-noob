function loginSuccess(data) {
    if (data.resp_code === "1") {
        alert("登录成功，点击确定跳转到主页");
        window.location.href = "/shiro_go_war_exploded/html/user/user_info.html"
    } else {
        alert(data.resp_message);
    }
}

function loginFail(req, err) {
    console.log(err);
    alert("后台出错了，系统暂时不可用！");
}

function errHandler(req, err) {
    console.log(err);
    alert("后台出错了，此功能暂时不可用！");
}

modify_username = [];

function allUserList(data) {
    if (data.resp_code === "1") {
        // [{"password":"xxsxxs","department_name":"dota2","username":"burning"},{"password":"boboka","department_name":"dota2","username":"longdd"},{"password":"xnovagod","department_name":"dota2","username":"redpanda"}]
        var userList = JSON.parse(data.resp_message).user_list;
        userList.forEach(ele => {
            $("#userList>tbody").append("<tr id='" + ele.username + "'></tr>");
            $("#" + ele.username).append("<td>" + ele.username + "</td>")
                .append("<td>" + ele.password + "</td>")
                .append("<td>" + ele.department_name + "</td>");
        });

        $("#userList>tbody>tr").click(function() {
            var id = $(this).attr("id");
            if ($(this).hasClass("table-active")) {
                $(this).removeClass("table-active");
                modify_username.splice(modify_username.indexOf(id), 1);
            } else {
                $(this).addClass("table-active");
                modify_username.push(id);
            }
        });
    } else {
        alert(data.resp_message);
    }
}

function queryUserByIdAndUsername(data) {
    if (data.resp_code === "1") {
        $("#userList>tbody>tr").remove();
        var userList = JSON.parse(data.resp_message).user_list;
        userList.forEach(ele => {
            $("#userList>tbody").append("<tr id='" + ele.username + "'></tr>");
            $("#" + ele.username).append("<td>" + ele.username + "</td>")
                .append("<td>" + ele.password + "</td>")
                .append("<td>" + ele.department_name + "</td>");
        });
    } else {
        alert(data.resp_message);
    }
}

function createUser(data) {
    if (data.resp_code === "1") {
        $("#userList>tbody>tr").remove();
        sendWithGet("/shiro_go_war_exploded/user/userList", null, allUserList, errHandler);
    } else {
        alert(data.resp_message);
    }
}

function deleteUsers(data) {
    if (data.resp_code === "1") {
        $("#userList>tbody>tr").remove();
        sendWithGet("/shiro_go_war_exploded/user/userList", null, allUserList, errHandler);
    } else {
        alert(data.resp_message);
    }
}

function updateUser(data) {
    if (data.resp_code === "1") {
        $("#userList>tbody>tr").remove();
        sendWithGet("/shiro_go_war_exploded/user/userList", null, allUserList, errHandler);
    } else {
        alert(data.resp_message);
    }
}