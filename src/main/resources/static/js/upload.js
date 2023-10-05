function uploadFunction() {

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/conn/uploader",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function(data) {

            if (data == 1) {
                $('#statusMessage').html('파일업로드 성공.');
                $('#statusMessage').css("color", "green");
            }
            else {
                $('#statusMessage').html('파일업로드 실패.');
                $('#statusMessage').css("color", "red");
            }
        },
        error: function(e) {
            $('#statusMessage').html('파일업로드 에러.');
            $('#statusMessage').css("color", "red");
        }
    });
}