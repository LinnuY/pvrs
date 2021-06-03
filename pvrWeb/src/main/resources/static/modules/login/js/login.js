
function submitLoginInfo() {
    $.ajax({
        url: "/login/toLogin",
        type: "post",
        data: $("#form-signin").serializeObject(),
        dataType: "json",
        success: function (responseBean) {
            if (responseBean.code === '0') {
                alert('登录成功');
                window.location.href = "/index";
            } else {
                alert('')
            }
        },
        error: function () {

        }

    })
}