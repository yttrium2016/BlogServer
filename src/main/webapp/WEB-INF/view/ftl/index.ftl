<#-- 取得 应用的绝对根路径 -->
<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>${blogName ! '博客标题'}</title>
    <meta name="description" content="${blogContent ! '博客简介'}"/>
    <meta name="HandheldFriendly" content="True"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="/img/favicon.ico">
    <link rel="stylesheet" type="text/css" href="/css/screen.css"/>
</head>
<body class="home-template nav-closed">

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

<#--背景-->
<#if imgUrl == '' >
    <header class="main-header no-cover " >
</#if>

<#if imgUrl != '' >
    <header class="main-header" style="background-image: url(${imgUrl ! '/img/index_wallpaper.jpg'})">
</#if>
    <#if navs?? && (navs?size > 0) >
        <nav class="main-nav overlay clearfix">

            <a class="menu-button icon-menu" href="#"><span class="word">菜单</span></a>
        </nav>
    </#if>
        <div class="vertical">
            <div class="main-header-content inner">
                <h1 class="page-title">${blogName ! '博客标题'}</h1>
                <h2 class="page-description">${blogContent ! '博客简介'}</h2>
            </div>
        </div>
        <a class="scroll-down icon-arrow-left" href="#content" data-offset="-45"><span class="hidden">下拉显示</span></a>
    </header>

    <main id="content" class="content" role="main">
    </main>


    <footer class="site-footer clearfix">
        <section class="copyright"><a href="${blogUrl ! '#'}">${blogName ! '博客标题'}</a> &copy; 2017</section>
    </footer>

</div>


<script src="/js/jquery-3.2.1.js"></script>
<script src="/js/jquery.fitvids.js"></script>

<script src="/js/handlebars-v4.0.10.js"></script>
<script id="blogTpl" type="text/x-handlebars-template">
    {{#each blogs}}
    <article class="post">
        <header class="post-header">
            <h2 class="post-title"><a href="{{ this.url }}{{ this.id }}">{{ this.title }}</a></h2>
        </header>
        <section class="post-excerpt">
            <p>{{ this.content }} <a class="read-more" href="{{ this.url }}{{ this.id }}">&raquo;</a>
            </p>
        </section>
        <footer class="post-meta">
            <img class="author-thumb" src="/img/logo.jpg" alt="{{ this.author }}"
                 nopin="nopin"/>
            <a href="#">{{ this.author }}</a>

            <time class="post-date" datetime="{{ this.time }}">{{ this.time }}</time>
            <a class="read-more" style="float:right" href="{{ this.url }}{{ this.id }}">阅读更多</a>
        </footer>
    </article>
    {{/each}}
</script>
<script src="/js/index.js"></script>
</body>
</html>
