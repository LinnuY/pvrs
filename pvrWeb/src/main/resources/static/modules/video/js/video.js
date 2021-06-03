function fileSubmit() {
    debugger;
    let formData = new FormData();
    formData.append('title',$('#title').val());
    formData.append('videoName', $('#videoName').val());
    formData.append('username', $('#userName').val());
    formData.append('videoFile', document.getElementById('videoFile').files[0]);
    formData.append('coverImageFile', document.getElementById('coverImageFile').files[0]);
    formData.append('description', $('#description').val());

    $.ajax({
        url: '/video/uploadAction',
        type: "post",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            debugger;
            let code = data.code;
            if (code === '0') {
                alert("上传成功");
                window.location.href = "/index";
            } else {
                let message = data.message;
                alert(message);
            }
        }
    })
}

function likes() {
    debugger
    document.getElementById("isLikeImg").src="/static/modules/video/svg/likes.svg";
    $.ajax({
        url: '/video/likes',
        type: 'post',
        data: {
            videoId: $("#videoPageId").val(),
            userId: $("#userId").val(),
        },
        success: function (data) {

        }
    })
}

function saves() {
    document.getElementById("isSaveImg").src="/static/modules/video/svg/saves.svg";
    $.ajax({
        url: '/video/saves',
        type: 'post',
        data: {
            videoId: $("#videoPageId").val(),
            userId: $("#userId").val(),
        },
        success: function (data) {

        }
    })
}
