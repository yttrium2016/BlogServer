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
 * Time: 13:14
 */
public class BlogGithub {

    private static List<Blog> blogs = null;

    public static List<Blog> getBlogs() {
        if (blogs == null || blogs.size() < 1) {
            blogs = new ArrayList<Blog>();
            blogs = getBlogByGithub();
        }
        //TODO 测试用记得删除
        blogs = getBlogByGithub();
        return blogs;
    }

    /**
     * 远程获取Blog列表
     *
     * @return
     */
    private static List<Blog> getBlogByGithub() {
        List<Blog> list = new ArrayList<Blog>();

        Document doc = null;
        String url = "https://github.com/yttrium2016/Blog";

        try {
            long start = System.currentTimeMillis();
            doc = Jsoup.connect(url).get();
            Elements tbody = doc.getElementsByTag("tbody");
            for (Element z : tbody) {
                Elements items = z.getElementsByClass("js-navigation-item");
                for (Element e : items) {
                    Blog blog = new Blog();
                    Elements contents = e.getElementsByClass("content");
                    for (Element a : contents) {
                        Elements contentss = a.getElementsByAttribute("title");
                        for (Element b : contentss) {
                            String href = b.attr("href");
                            String title = b.attr("title");
                            if (title != null) {
                                blog.setUrl("getBlog?blog=" + title);
                                String[] strArr = title.split("--");
                                if (strArr.length > 0) {
                                    blog.setTime(strArr[0]);
                                    blog.setTitle(strArr[1]);
                                    blog.setAuthor(strArr[2]);
                                }
                                blog.setWebUrl(href);
                            }
                        }
                    }
                    Elements messages = e.getElementsByClass("message");
                    for (Element a : messages) {
                        Elements contentss = a.getElementsByAttribute("title");
                        for (Element b : contentss) {
                            String title = b.attr("title");
                            if (title != null) {
                                blog.setContent(title);
                            }
                        }
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

       /* Document doc = null;
//        blogUrl = java.net.URLEncoder.encode(blogUrl, "utf-8");
        String url = "https://raw.githubusercontent.com/yttrium2016/Blog/master/" + blogUrl;


        System.out.println("地址访问：" + url);

        //用HttpClient发送请求，分为五步
        //第一步：创建HttpClient对象
        HttpClient httpCient = HttpClients.createDefault();
        //第二步：创建代表请求的对象,参数是访问的服务器地址
        HttpGet httpGet = null;

        httpGet = new HttpGet(url);


        try {
            //第三步：执行请求，获取服务器发还的相应对象
            HttpResponse httpResponse = httpCient.execute(httpGet);
            //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //第五步：从相应对象当中取出数据，放到entity当中
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串

                //在子线程中将Message对象发出去
                return response;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return "";*/

        String url = "https://raw.githubusercontent.com/yttrium2016/Blog/master/" + blogUrl;

        System.out.println("地址访问：" + url);

        try {
            Connection.Response response = Jsoup.connect(url).execute();
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
