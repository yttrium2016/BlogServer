<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>博客系统</title>
    <link rel="stylesheet" media="screen" href="/css/zc.css"/>
</head>
<form id="msform">
    <!-- progressbar -->
    <ul id="progressbar">
        <li class="active">欢迎</li>
        <li>博客来源</li>
        <li>网站设置</li>
        <li>图片设置</li>
    </ul>
    <!-- fieldsets -->
    <fieldset>
        <h2 class="fs-title">欢迎使用本套博客系统</h2>
        <h3 class="fs-subtitle">作者:杨振宇</h3>
        <img src="/img/zc_logo.jpg" style="width: 240px;height: 240px;"><br>
        <input type="button" name="next" class="next action-button" value="继续"/>
    </fieldset>

    <fieldset>
        <h2 class="fs-title">选择博客来源</h2>
        <h3 class="fs-subtitle">目前只支持Github或者开源中国(Gitee)2种</h3>
        <select id="git" name="git">
            <option value="-1">请选择博客来源</option>
            <option value="github">Github</option>
            <option selected="selected" value="gitee">Gitee</option>
        </select>
        <input type="text" id="blogGitAddress" name="blogGitAddress" placeholder="博客Git地址"
               value="https://gitee.com/yttrium2016/blog.git"/>
        <input type="text" id="imgGitAddress" name="imgGitAddress" placeholder="图片Git地址"
               value="https://gitee.com/yttrium2016/img.git"/>
        <input type="text" id="gitUsername" name="gitUsername" placeholder="Git用户名" value="yttrium2016@163.com"/>
        <input type="password" id="gitPassword" name="gitPassword" placeholder="Git密码" value="Yzy6726053"/>

    <#-- 显示的上一页下一页 -->
        <input type="button" name="previous" class="git action-button" value="上一项"/>
        <input type="button" name="next" class="git action-button" value="下一项"/>
    <#-- 实际的上一页下一页 -->
        <input id="git_previous" type="button" hidden="hidden" name="previous" class="previous action-button"
               value="Previous"/>
        <input id="git_next" type="button" hidden="hidden" name="next" class="next action-button" value="Next"/>
    </fieldset>

    <fieldset>
        <h2 class="fs-title">网站设置</h2>
        <h3 class="fs-subtitle">设置网站的基本信息</h3>
        <input type="text" id="blogName" name="blogName" placeholder="博客标题" value="杨振宇装逼博客"/>
        <input type="text" id="blogAuthor" name="blogAuthor" placeholder="博客作者" value="杨振宇"/>
        <input type="text" id="blogUrl" name="blogUrl" placeholder="网站链接" value="#现在还不知道呢"/>
        <textarea id="blogContent" name="blogContent" placeholder="博客简介">没错，这个就是用来装逼的玩意....</textarea>


    <#-- 显示的上一页下一页 -->
        <input type="button" name="previous" class="web action-button" value="上一项"/>
        <input type="button" name="next" class="web action-button" value="下一项"/>

        <input id="web_previous" hidden="hidden" type="button" name="previous" class="previous action-button"
               value="Previous"/>
        <input id="web_next" hidden="hidden" type="button" name="next" class="next action-button" value="Next"/>
    </fieldset>

    <fieldset>
        <h2 class="fs-title">图片设置</h2>
        <h3 class="fs-subtitle">可以上传一张你喜欢的背景图片</h3>

        <div style="min-width: 450px;">
            <img id="img" src="" style="min-height:180px;max-height:250px;max-width: 450px;"/>
        </div>
        <input type="button" name="upload" class="upload action-button" value="选择图片"/>
        <input type="text" id="blogImgUrl" name="blogImgUrl" hidden="hidden"/>

    <#-- 显示的上一页下一页 -->
        <input type="button" name="previous" class="previous action-button" value="上一项"/>
        <input type="button" name="submit" class="submit action-button" value="提交"/>

    </fieldset>
</form>

<form id="imgForm" method="post" enctype="multipart/form-data" style="display: none">
    <input id="file" type="file" name="file">
</form>
<script src="/js/jquery-3.2.1.js"></script>
<script src="/js/jquery.easing.min.js" type="text/javascript"></script>
<script src="/js/zc.js" type="text/javascript"></script>
<script src="/js/layer/layer.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {

        //git设置页面
        $(".git").click(function (e) {
            var value = $(this).attr('name')
            if (value == 'previous')
                $('#git_previous').click();
            if (value == 'next') {
                var git = $('#git').val();
                if (git == '-1') {
                    layer.msg("请选择Git来源")
                    return
                }
                var blogGitAddress = $('#blogGitAddress').val();
                if (blogGitAddress == '' || blogGitAddress == null) {
                    layer.msg("请输入博客Git地址")
                    return
                }
                var imgGitAddress = $('#imgGitAddress').val();
                if (imgGitAddress == '' || imgGitAddress == null) {
                    layer.msg("请输入图片Git地址")
                    return
                }
                var gitUsername = $('#gitUsername').val();
                if (gitUsername == '' || gitUsername == null) {
                    layer.msg("请输入博客Git用户名")
                    return
                }
                var gitPassword = $('#gitPassword').val();
                if (gitPassword == '' || gitPassword == null) {
                    layer.msg("请输入博客Git密码")
                    return
                }
                //请求下载仓库
                var index = layer.load(1);
                $.ajax({
                    url: "/initGit.do",
                    type: 'POST',
                    data: {
                        git: git,
                        blogGitAddress: blogGitAddress,
                        imgGitAddress: imgGitAddress,
                        gitUsername: gitUsername,
                        gitPassword: gitPassword
                    },
                    dataType: "json",
                    success: function (data) {
                        layer.close(index);
                        layer.msg(data.resultMessage);
                        if (data.resultCode == 1) {
                            $('#git_next').click();
                        }
                    },
                    error: function (data) {
                        layer.close(index);
                        layer.msg("网络翻船中...");
                    }
                });

            }
        });

        //web设置页面
        $(".web").click(function (e) {
            var value = $(this).attr('name')
            if (value == 'previous')
                $('#web_previous').click();
            if (value == 'next') {
                if ($('#blogName').val() == '' || $('#blogName').val() == null) {
                    layer.msg("请输入博客标题")
                    return
                }
                if ($('#blogAuthor').val() == '' || $('#blogAuthor').val() == null) {
                    layer.msg("请输入博客作者")
                    return
                }
                if ($('#blogUrl').val() == '' || $('#blogUrl').val() == null) {
                    layer.msg("请输入网站链接")
                    return
                }
                $('#web_next').click();
            }
        });

        $(".upload").click(function (e) {
            $('#file').click();
        })

        $("#file").change(function (e) {
            var data = $(this).val();
            if (data != null && data != '') {
                var formData = new FormData($("form")[1]);
                var index = layer.load(1);
                $.ajax({
                    url: "/uploadImg.do",
                    type: 'POST',
                    data: formData,
                    async: true,
                    cache: false,
                    dataType: "json",
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        layer.close(index);
                        layer.msg(data.resultMessage);
                        if (data.resultCode == 1) {
                            //上传成功返回图片
                            $('#img').attr("src", data.resultData);
                            $('#blogImgUrl').val(data.resultData);
                        }
                    },
                    error: function (data) {
                        layer.close(index);
                    }
                });
            }
        })

        $(".submit").click(function () {
            var formData = $("#msform").serialize()
            var index = layer.load(1);
            $.ajax({
                url: "/submitConfig.do",
                type: 'POST',
                data: formData,
                dataType: "json",
                success: function (data) {
                    layer.close(index);
                    layer.msg(data.resultMessage);
                    if (data.resultCode == 1) {
                        //上传成功返回图片
                        window.location.reload();
                    }
                },
                error: function (data) {
                    layer.close(index);
                }
            });
            return false;
        })
    });
</script>
</html>