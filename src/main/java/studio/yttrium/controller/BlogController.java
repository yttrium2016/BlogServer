package studio.yttrium.controller;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.pojo.Blog;
import studio.yttrium.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/27
 * Time: 23:16
 */
@Controller
public class BlogController {


    @RequestMapping("github")
    @ResponseBody
    public String github(HttpServletRequest request) throws IOException, GitAPIException {

        long s = System.currentTimeMillis();
        JGitUtils.initGitRepository();
        XmlUtils.initXmlBlog();
        long e = System.currentTimeMillis();
        System.out.println("时间：" + (e - s));
        return "时间：" + (e - s);
    }


    @RequestMapping("getBlogList")
    @ResponseBody
    public List<Blog> goBlogList(int index, int size) {
        return XmlUtils.getBlogList((index - 1) * size + 1, size);
    }

    @RequestMapping("getBlogCount")
    @ResponseBody
    public String getBlogCount() {
        return String.valueOf(XmlUtils.getBlogCount());
    }

    @RequestMapping(value = "getBlog", produces = "application/json;charset=utf-8")
    @ResponseBody()
    public String goBlog(String id) {
        Blog blog = XmlUtils.getBlog(id);
        File file = new File(ConstantValue.getGitPath() + "/" + blog.getFileName() + ".md");
        return FileUtils.File2String(file);
    }


    @RequestMapping("blogView")
    public String blogView() {
        return "blogView";
    }

    @RequestMapping("submitConfig")
    public String submitConfig(HttpServletRequest request) {

        String blogName = request.getParameter("blogName");
        String blogContent = request.getParameter("blogContent");
        String blogAuthor = request.getParameter("blogAuthor");
        String gitAddress = request.getParameter("gitAddress");
        String gitUsername = request.getParameter("gitUsername");
        String gitPassword = request.getParameter("gitPassword");


        ConfigUtils.putString(ConstantValue.BLOG_NAME, blogName);
        ConfigUtils.putString(ConstantValue.BLOG_CONTENT, blogContent);
        ConfigUtils.putString(ConstantValue.BLOG_AUTHOR, blogAuthor);
        ConfigUtils.putString(ConstantValue.GIT_ADDRESS, gitAddress);
        ConfigUtils.putString(ConstantValue.GIT_USERNAME, gitUsername);
        ConfigUtils.putString(ConstantValue.GIT_PASSWORD, gitPassword);

        ConfigUtils.putBoolean(ConstantValue.BLOG_FIRST_OPEN, true);

        return "redirect:index.shtml";
    }

    @RequestMapping("index")
    public String index(Model view) {
        if (!ConfigUtils.getBoolean(ConstantValue.BLOG_FIRST_OPEN, false)) {
            return "welcome";
        }
        view.addAttribute("navs", XmlUtils.getNavList());
//        view.addAttribute("bj_url", ConfigUtils.getString(ConstantValue.IMG_PATH, "") + "login_wallpaper.jpg");
        view.addAttribute("blogName", ConfigUtils.getString(ConstantValue.BLOG_NAME, ""));
        view.addAttribute("blogContent", ConfigUtils.getString(ConstantValue.BLOG_CONTENT, ""));
        return "index";
    }


    @RequestMapping("blog")
    public String showBlog(String id, Model view) throws IOException {
        Blog blog = XmlUtils.getBlog(id);
        if (blog == null) {
            return "common/error";
        }
        view.addAttribute("title",blog.getTitle());
        view.addAttribute("dateTemp",blog.getTime());
        view.addAttribute("navs", XmlUtils.getNavList());
        return "blog";
    }

}
