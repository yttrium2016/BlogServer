package studio.yttrium.git;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import studio.yttrium.pojo.Blog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/31
 * Time: 16:10
 */
public class BlogGitee {
    private static List<Blog> blogs = null;

    public static List<Blog> getBlogs() {
        if (blogs == null || blogs.size() < 1) {
            blogs = new ArrayList<Blog>();
            blogs = getBlogByGitee();
        }
        //TODO 测试用记得删除
        blogs = getBlogByGitee();
        return blogs;
    }

    /**
     * 远程获取Blog列表
     *
     * @return
     */
    private static List<Blog> getBlogByGitee() {
        List<Blog> list = new ArrayList<Blog>();

        Document doc = null;
        String url = "https://gitee.com/iyzy/Blog";

        try {
            long start = System.currentTimeMillis();
            doc = Jsoup.connect(url).get();
            Elements tbody = doc.getElementsByClass("tree-content");
            for (Element z : tbody) {
                Elements items = z.getElementsByClass("tree-item");
                System.out.println(items.size());
                for (int i = 0; i < items.size(); i += 2) {
                    Blog blog = new Blog();
                    Element element = items.get(i);
                    Elements five = element.getElementsByClass("five");
                    Elements title = five.get(0).getElementsByAttribute("title");
                    String titleStr = title.get(0).attr("title");
                    String href = title.get(0).attr("href");

                    if (titleStr != null) {
                        blog.setUrl("getBlog?blog=" + titleStr);
                        String[] strArr = titleStr.split("--");
                        if (strArr.length > 0) {
                            blog.setTime(strArr[0]);
                            if (strArr.length > 1) {
                                blog.setAuthor(strArr[2]);
                                blog.setTitle(strArr[1]);
                            }
                        }
                        blog.setWebUrl(href);
                    }
//
                    Elements nine = element.getElementsByClass("nine");
                    if (nine != null && nine.size() > 0) {
                        Elements content = nine.get(0).getElementsByAttribute("title");
                        String contentStr = content.get(0).attr("title");
                        blog.setContent(contentStr);
                        System.out.println(titleStr + "," + contentStr);
                    }
                    list.add(blog);
                }


                long end = System.currentTimeMillis();
                System.out.println("时间" + (end - start));

                return list;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取详细内容
     *
     * @param blogUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getBlogStr(String blogUrl) throws UnsupportedEncodingException {

        String url = "https://gitee.com/iyzy/Blog/raw/master/" + blogUrl;

        System.out.println("地址访问：" + url);

        try {
            Connection.Response response = Jsoup.connect(url).execute();
            String body = response.body();
            return body;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
