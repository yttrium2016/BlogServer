<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>上传页面</title>
</head>
<body>

<fieldset>
    <legend>图片上传</legend>
    <h2>只能上传单张10M以下的 PNG、JPG、GIF 格式的图片</h2>
    <form action="/uploadImg.do" method="post" enctype="multipart/form-data">
        选择文件:<input id="file" type="file" onchange="fileChange(this);" name="file">
        <input type="submit" value="上传">

    </form>
    <button onclick="javascript:document.getElementById('file').click();">ajax提交试试看</button>

</fieldset>



<script src="/js/jquery-3.2.1.js"></script>
<script>
    function upload() {
        var formData = new FormData($("form")[0]);
        $.ajax({
            url: "/uploadImg.do",
            type: 'POST',
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                alert(data);
            },
            error: function (data) {
                alert(data);
            }
        });
        return false;
    }

    function fileChange(d) {
        var data = $(d).val();
        if (data != null && data != '') {
            console.log(d);
//            alert(data);
            upload();
        }
    }
</script>
</body>
</html>
