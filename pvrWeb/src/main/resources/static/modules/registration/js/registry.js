function submitUserInfo() {
    debugger;
    let confirmPassword = $("input[name='confirmPassword']").val();
    let password = $("input[name='password']").val();
    let sex = $("input[name='sexRadio']").val();
    if (confirmPassword !== password) {
        return ;
    }
    $.ajax({
        url: "/registry/toRegistry",
        type: "post",
        data: {
            username: $("input[name='username']").val(),
            password: password,
            nickname: $("input[name='nickname']").val(),
            email: $("input[name='email']").val(),
            address: $("input[name='address']").val(),
            sex: sex,
            phone: $("input[name='phone']").val()
        },
        success: function (responseBean) {
            if (responseBean.code === '0') {
                window.location.href = "/login";
            } else {

            }
        },
        error: function () {

        }
    });
}