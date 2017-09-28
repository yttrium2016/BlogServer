<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>404錯誤</title>

    <style type="text/css">
        /* Sticky footer styles
              -------------------------------------------------- */
        html,
        body {
            height: 100%;
            background: #fff;
            color: #fff;
            margin: 0;
            font-size: 14px;
            line-height:1.7;
            font-family:"Helvitica Neue", Helvitica, Arial, sans-serif;
            cursor: pointer;
        }
        .nav_list {
            display: inline-block;
            text-decoration: none;
            margin: 30px 0px 30px 30px;
            font-size: 20px;
        }
        .nav_list:hover {
            color: #000;
            border-bottom: 1px solid #000;
        }

        a {
            color: #797d88;
        }

        .wrap {
            margin: 0 auto;
            width: 500px;
            position: absolute;
            top: 50%;
            left: 50%;
            margin: -250px 0 0 -250px;
        }

        #photo {
            margin: 0 auto;
        }

        #alt {
            position: relative;
            top: 20px;
            left: 420px;
            color: #797d88;
            font-size: 12px;
        }
        #nav {
            height: 90px;
            border-top: 1px solid #e5e5e5;
            border-bottom: 1px solid #e5e5e5;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div id="404" class="wrap">
    <div id="photo">
        <div id="alt">T_T 手机不小心掉到「代码池」了！ <br />
            还是回<a href="/">首页</a>找找看吧!
        </div>
        <img src="http://static.zealer.com/images/error.png" width="494" height="302" alt="404 Not Found!"/>
    </div>
</div>
    <h1>404錯誤</h1>

<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<script>
    $(function() {

        $('body').bind('click', function() {
            window.location.href = '/';
        })
    })
</script>
</body>
</html>