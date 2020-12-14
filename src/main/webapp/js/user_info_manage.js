$(() => {
    let before_modify = "";

    sendWithGet("/shiro_go_war_exploded/user/userList", null, allUserList, errHandler);

    $("#readyCreate").click(() => {
        const data = {
            username: $("#inputUsername").val(),
            password: $("#inputPassword").val()
        };
        sendWithGet("/shiro_go_war_exploded/user/signup", data, createUser, errHandler);
    });

    $("#queryUser").click(() => {
        const data = {
            id: $("#queryUserId").val(),
            username: $("#queryUsername").val()
        };
        sendWithGet("/shiro_go_war_exploded/user/selectByIdAndUsername", data, queryUserByIdAndUsername, errHandler);
    });

    $("#deleteUserBtn").click(() => {
        const data = {
            usernames: modify_username
        };
        sendWithPost("/shiro_go_war_exploded/user/deleteByUser", JSON.stringify(data), deleteUsers, errHandler);
    });

    $("#updateUserBtn").click(function() {
        if ($(".table-active").length !== 1) {
            $("#updateUserBody").children().remove();
            $("#updateUserBody").append("<p>请选择单条记录进行修改</p>");
            $("#updateSaveBtn").attr("disabled", "disabled");
        } else {
            $("#updateUserBody>p").remove();
            $("#updateSaveBtn").removeAttr("disabled");
            $("#updateUserBody").append(`<form>
                                    <div class="row mb-3">
                                        <label for="inputUsername" class="col-sm-2 col-form-label">用户名</label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control" id="isUpdatedUsername">
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <label for="inputPassword" class="col-sm-2 col-form-label">密码</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" id="isUpdatedPassword">
                                        </div>
                                    </div>
                                </form>`);
            before_modify = $($(".table-active")
                .children()[0])
                .html()
            $("#isUpdatedUsername").val(before_modify);
            $("#isUpdatedPassword").val(
                $($(".table-active").children()[1])
                    .html());
        }
    });

    $("#updateSaveBtn").click(() => {
        const data = {
            oldUsername: before_modify,
            newUsername: $("#isUpdatedUsername").val(),
            newPassword: $("#isUpdatedPassword").val()
        };
        sendWithPost("/shiro_go_war_exploded/user/updateUserInfoByUsername", JSON.stringify(data), deleteUsers, errHandler);
    });
})