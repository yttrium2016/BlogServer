<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>欢迎界面</title>
</head>
<body>
    <h1>欢迎进入博客配置界面</h1><br>
    <form action="/submitConfig.do" method="post">
        博客名<input type="text" name="blogName"/><br>
        博客简介<input type="text" name="blogContent"/><br>
        博客作者<input type="text" name="blogAuthor"/><br>
        github地址<input type="text" name="gitAddress"/><br>
        github用户名<input type="text" name="gitUsername"/><br>
        github密码<input type="text" name="gitPassword"/><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>