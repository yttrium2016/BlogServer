/**
 * Main JS file for Casper behaviours
 */

/* globals jQuery, document */
(function ($, undefined) {
    "use strict";

    var $document = $(document);

    $document.ready(function () {

        var $postContent = $(".post-content");
        $postContent.fitVids();

        $(".scroll-down").arctic_scroll();

        $(".menu-button, .nav-cover, .nav-close").on("click", function(e){
            e.preventDefault();
            $("body").toggleClass("nav-opened nav-closed");
        });

    });

    // Arctic Scroll by Paul Adam Davis
    // https://github.com/PaulAdamDavis/Arctic-Scroll
    $.fn.arctic_scroll = function (options) {

        var defaults = {
            elem: $(this),
            speed: 500
        },

        allOptions = $.extend(defaults, options);

        allOptions.elem.click(function (event) {
            event.preventDefault();
            var $this = $(this),
                $htmlBody = $('html, body'),
                offset = ($this.attr('data-offset')) ? $this.attr('data-offset') : false,
                position = ($this.attr('data-position')) ? $this.attr('data-position') : false,
                toMove;

            if (offset) {
                toMove = parseInt(offset);
                $htmlBody.stop(true, false).animate({scrollTop: ($(this.hash).offset().top + toMove) }, allOptions.speed);
            } else if (position) {
                toMove = parseInt(position);
                $htmlBody.stop(true, false).animate({scrollTop: toMove }, allOptions.speed);
            } else {
                $htmlBody.stop(true, false).animate({scrollTop: ($(this.hash).offset().top) }, allOptions.speed);
            }
        });

    };
})(jQuery);


//从这开始自己写的
var index = 1;
var size = 5;
var count = 0;

var context = {
    blogs: [
        {"title": "标题", "content": "内容", "url": "地址", "time": '时间', "author": "管理员"}
    ]
};

$(document).ready(function () {
    getBlogCount();
});

function getBlogCount() {
    $.ajax({
        type: "post",
        async: true,
        url: "/getBlogCount.do",
        dataType: "text",
        data: {},
        success: function (data) {
            count = parseInt(data);
            if (count != 0)
                getBlogList();
        }
        , error: function (data) {
            console.log("/getBlogCount.do请求error");
            console.log(data);
        }
    });
}


//上一页
function prev() {
    index--;
    getBlogList();
}

//下一页
function next() {
    index++;
    getBlogList();
}

//获取博客列表
function getBlogList() {
    $.ajax({
        type: "post",
        async: true,
        url: "/getBlogList.do",
        dataType: "json",
        data: {
            index: index,
            size: size
        },
        success: function (data) {

            //清空下列表
            $('#content').empty();

            var tpl = $("#blogTpl").html();
            //预编译模板
            var template = Handlebars.compile(tpl);
            //数据赋值
            context.blogs = data;
            //转化为html代码
            var html = template(context);
            //输出
            $('#content').append(html);

            //添加分页插件
            var pagination = '<nav class="pagination" role="navigation">';

            if (index > 1) {
                pagination += '<a class="newer-posts" href="javascript:void(0);" onclick="prev();"><span aria-hidden="true">&larr;</span> 上一页</a>'
            }

            pagination += '<span class="page-number">第 ' + index + ' 页 &frasl; 共 ' + parseInt((count + size - 1) / size) + ' 页</span>';

            if (index < parseInt((count + size - 1) / size)) {
                pagination += '<a class="older-posts" href="javascript:void(0);" onclick="next();">下一页 <span aria-hidden="true">&rarr;</span></a>'
            }
            pagination += '</nav>';

            //输出
            $('#content').append(pagination);

        }
        , error: function (data) {
            console.log("/getBlogList.do请求error");
            console.log(data);
        }
    });


}