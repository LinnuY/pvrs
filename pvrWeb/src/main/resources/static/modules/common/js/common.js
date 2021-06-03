let userInfo;

/**
 * 将表单元素序列化为JSON对象
 * 基于jQuery serializeArray()
 */
$.fn.serializeObject = function () {
    let o = {};
    let a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function queryUserInfo() {
    $.ajax({
        url: "index/queryUserInfo",
        type: "post",
        success: function (responseBean) {
            if (isLogin(responseBean)) {
                userInfo = responseBean.data;
            } else {
                window.location.href = '../login';
            }
            userInfo = responseBean.data;
        }
    })
}

function isLogin(responseBean) {
    let code = responseBean.code;
    switch (code) {
        case '100':
        case '101':
        case '102':
        case '103':
            return false;
        default:
            return true;
    }
}