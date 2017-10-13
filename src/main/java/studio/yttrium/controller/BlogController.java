package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.core.XmlOperation;
import studio.yttrium.pojo.Blog;
import studio.yttrium.utils.ConfigUtils;
import studio.yttrium.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 博客相关的Controller
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/27
 * Time: 23:16
 */
@Controller
public class BlogController {

    /**
     * 获取所有博客列表
     * @param index
     * @param size
     * @return
     */
    @RequestMapping("getBlogList")
    @ResponseBody
    public List<Blog> goBlogList(int index, int size) {
        return XmlOperation.getApplication().getBlogList((index - 1) * size + 1, size);
    }

    /**
     * 获取博客列表的数目
     * @return
     */
    @RequestMapping("getBlogCount")
    @ResponseBody
    public String getBlogCount() {
        return String.valueOf(XmlOperation.getApplication().getBlogCount());
    }

    /**
     * 获取单个博客内容
     * @param id
     * @return
     */
    @RequestMapping(value = "getBlog", produces = "application/text;charset=utf-8")
    @ResponseBody()
    public String goBlog(String id) {
        Blog blog = XmlOperation.getApplication().getBlog(id);
        File file = new File(ConstantValue.getBlogPath() + "/" + blog.getFileName());
        return FileUtils.File2String(file);
    }

    /**
     * 进入主页
     * @param view
     * @return
     */
    @RequestMapping("index")
    public String index(Model view) {
        if (ConfigUtils.getBoolean(ConstantValue.BLOG_FIRST_OPEN, true)) {
            return "welcome";
        }
        view.addAttribute("navs", XmlOperation.getApplication().getNavList());
        view.addAttribute("imgUrl", ConfigUtils.getString(ConstantValue.BLOG_IMG_URL, ""));
        view.addAttribute("blogName", ConfigUtils.getString(ConstantValue.BLOG_NAME, ""));
        view.addAttribute("blogContent", ConfigUtils.getString(ConstantValue.BLOG_CONTENT, ""));
        view.addAttribute("blogUrl", ConfigUtils.getString(ConstantValue.BLOG_URL, ""));
        return "index";
    }


    /**
     * 进入博客页面
     * @param request
     * @param id
     * @param view
     * @return
     * @throws IOException
     */
    @RequestMapping("blog")
    public String showBlog(HttpServletRequest request,String id, Model view) throws IOException {
        Blog blog = XmlOperation.getApplication().getBlog(id);
        if (blog == null) {
            return "common/error";
        }
        if (".md".equals(blog.getSuffix())) {
            view.addAttribute("js", "html = markdown.toHTML(html);");
        }
        StringBuffer url = request.getRequestURL();
        url.append("?id=");
        url.append(id);
        view.addAttribute("blog", blog);
        view.addAttribute("localUrl", url.toString());
        view.addAttribute("blogName", ConfigUtils.getString(ConstantValue.BLOG_NAME, ""));
        view.addAttribute("blogUrl", ConfigUtils.getString(ConstantValue.BLOG_URL, ""));
        view.addAttribute("navs", XmlOperation.getApplication().getNavList());
        return "blog";
    }

}
