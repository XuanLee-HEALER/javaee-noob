$(() => {
    sendWithGet("/shiro_go_war_exploded/user/userList", null, allUserList, errHandler);

    $("#readyCreate").click(() => {
        const data = {
            username: $("#inputUsername").val(),
            password: $("#inputPassword").val()
        };
        sendWithGet("/shiro_go_war_exploded/user/signup", data, createUser, errHandler);
    })
})