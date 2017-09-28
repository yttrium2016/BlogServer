<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <title>${title ! "标题"}</title>
    <meta name="description" content=""/>

    <meta name="HandheldFriendly" content="True"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="shortcut icon" href="/img/favicon.ico">

    <link rel="stylesheet" type="text/css" href="/css/screen.css"/>

    <link rel="canonical" href="${localUrl ! ''}"/>
    <meta name="referrer" content="origin"/>
</head>
<body class="post-template nav-closed">

<#-- 右侧的侧边栏 -->
<#-- 争取可配置 -->
<#if navs?? && (navs?size > 0) >
<div class="nav">
    <h3 class="nav-title">菜单</h3>
    <a href="#" class="nav-close">
        <span class="hidden">关闭</span>
    </a>
    <ul>
        <#list navs as map>
            <li id="${map["id"]}" class="nav-" role="presentation"><a href="${map["url"]}">${map["title"]}</a></li>
        </#list>
    </ul>
    <a class="subscribe-button icon-feed" href="#">订阅</a>
</div>
</#if>

<span class="nav-cover"></span>

<div class="site-wrapper">


<#-- 没有图片加上no-cover这个class 有图片 就没有了 加上style -->
<#-- post-head 半高北京-->
    <header class="main-header post-head" style="background-image: url(http://www.yttrium2016.cn/content/images/2017/06/---1.jpg)">
    <#if navs?? && (navs?size > 0) >
        <nav class="main-nav overlay clearfix">

            <a class="menu-button icon-menu" href="#"><span class="word">菜单</span></a>
        </nav>
    </#if>
    </header>

    <main class="content" role="main">
        <article class="post">

            <header class="post-header">
                <h1 class="post-title">${title ! "标题"}</h1>
                <section class="post-meta">
                    <time class="post-date" datetime="${dateTemp ! '时间'}">${dateTemp ! '时间'}</time>
                </section>
            </header>

            <section class="post-content">
            <#-- 内容 -->
                <div id="content">

                </div>
            </section>

            <footer class="post-footer">

            <#-- 图标 -->
                <figure class="author-image">
                    <a class="img" href="#y"
                       style="background-image: url(http://www.yttrium2016.cn/content/images/2017/06/---.jpg)"><span
                            class="hidden">杨振宇的头像</span></a>
                </figure>

                <section class="author">
                    <h4><a href="#y">杨振宇</a></h4>

                    <p>继续阅读此作者的<a href="#y">更多文章</a>。</p>
                    <div class="author-meta">


                    </div>
                </section>


                <section class="share">
                    <h4>分享此博文</h4>
                    <div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#"
                                                                                                      class="bds_tsina"
                                                                                                      data-cmd="tsina"
                                                                                                      title="分享到新浪微博"></a><a
                            href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#"
                                                                                               class="bds_douban"
                                                                                               data-cmd="douban"
                                                                                               title="分享到豆瓣网"></a><a
                            href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a></div>
                    <script>window._bd_share_config = {
                        "common": {
                            "bdSnsKey": {},
                            "bdText": "",
                            "bdMini": "2",
                            "bdMiniList": false,
                            "bdPic": "",
                            "bdStyle": "1",
                            "bdSize": "24"
                        }, "share": {}
                    };
                    with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>
                </section>

            </footer>

        </article>
    </main>


    <footer class="site-footer clearfix">
        <section class="copyright"><a href="http://www.yttrium2016.cn">全栈工程师 修炼指南</a> &copy; 2017</section>
    </footer>

</div>


<script src="/js/jquery-3.2.1.js"></script>
<script src="/js/jquery.fitvids.js"></script>
<script src="/js/blog.js"></script>
<script src="/js/markdown.js"></script>
<script>

</script>

</body>
</html>
