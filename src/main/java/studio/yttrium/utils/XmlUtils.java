package studio.yttrium.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.pojo.Blog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * 新的XML工具类 用了 dom4j 操作 贼方便
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/27
 * Time: 22:17
 */
public class XmlUtils {

    //操作的工具类
    private static SAXReader reader = new SAXReader();


    @Test
    public void test() {

        long a = System.currentTimeMillis();

        List<Blog> blogs = new ArrayList<>();

        for (int i = 4000; i < 6000; i++) {
            Blog blog = new Blog();
            blog.setTitle("标题"+i);
            blog.setUrl("aa");
            blog.setFileName("name"+i);
            blog.setTime(StringUtils.getNewDate("yyyy-MM-dd"));
            blog.setAuthor(ConfigUtils.getString(ConstantValue.BLOG_AUTHOR, "未知"));
            blog.setContent("没有简介");
            blog.setWebUrl("");
            blogs.add(blog);
        }

        saveBlogs(blogs);
        long b = System.currentTimeMillis();

        System.out.println("时间:"+(b-a));
    }

    /**
     * 没有的话建立一个
     */
    private static void initXml() {
        File file = new File(ConstantValue.getXmlPath());
        if (!file.exists()) {
            Document document = DocumentHelper.createDocument();
            //建立根节点
            Element root = document.addElement("root");
            //建立菜单节点
            root.addElement("nav");
            //建立博客节点
            root.addElement("blogData");
            //写入文件
            writerXmlFile(document);
        }
    }

    /**
     * XML写入文件
     *
     * @param document
     */
    public static void writerXmlFile(Document document) {
        //输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置编码
        format.setEncoding("UTF-8");
        //XMLWriter 指定输出文件以及格式
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(ConstantValue.getXmlPath())), "UTF-8"), format);
            //写入新文件
            writer.write(document);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个菜单超链接
     *
     * @param title
     * @param url
     */
    public static void addNavItem(String title, String url) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element nav = rootElement.element("nav");

            Element ul = nav.addElement("ul");
            ul.addAttribute("id", StringUtils.getUUID());

            //TITLE
            Element t = ul.addElement("title");
            t.setText(title);
            //URL
            Element u = ul.addElement("url");
            u.setText(url);

            writerXmlFile(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("保存nav失败" + e.getMessage());
        }
    }

    /**
     * 删除一个菜单超链接
     *
     * @param uuid
     */
    public static void delNavItem(String uuid) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element nav = rootElement.element("nav");
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = nav.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                String id = e.attributeValue("id");
                if (uuid.equals(id)) {
                    nav.remove(e);
                    break;
                }
            }

            writerXmlFile(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("删除nav失败" + e.getMessage());
        }
    }

    /**
     * 检查名字是否重复
     *
     * @param blogName
     * @return
     */
    public static boolean checkBlogName(String blogName) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                return false;
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                String fileName = e.attributeValue("fileName");
                if (blogName.equals(fileName)) {
                    return false;
                }
            }
            return true;
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("删除失败" + e.getMessage());
        }
        return false;
    }

    /**
     * 添加一个博客文章
     *
     * @param b
     */
    public static void addBlogItem(Blog b) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            if (checkBlogName(b.getFileName())) {
                Document document = reader.read(file);
                Element rootElement = document.getRootElement();

                Element blogData = rootElement.element("blogData");

                Element blog = blogData.addElement("blog");

                blog.addAttribute("id", StringUtils.getUUID());
                blog.addAttribute("fileName", b.getFileName());
                //title
                Element title = blog.addElement("title");
                title.setText(b.getTitle());
                //content
                Element content = blog.addElement("content");
                content.setText(b.getContent());
                //url
                Element url = blog.addElement("url");
                url.setText(b.getUrl());
                //time
                Element time = blog.addElement("time");
                time.setText(b.getTime());
                //webUrl
                Element webUrl = blog.addElement("webUrl");
                webUrl.setText(b.getWebUrl());
                //author
                Element author = blog.addElement("author");
                author.setText(b.getAuthor());
                //fileName
                Element fileName = blog.addElement("fileName");
                fileName.setText(b.getFileName());

                writerXmlFile(document);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("保存失败" + e.getMessage());
        }
    }

    /**
     * 删除一个博客文章通过id
     *
     * @param uuid
     */
    public static void delBlogItem(String uuid) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                String id = e.attributeValue("id");
                if (uuid.equals(id)) {
                    blogData.remove(e);
                    break;
                }
            }
            //写入文件
            writerXmlFile(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("删除失败" + e.getMessage());
        }
    }

    /**
     * 删除一个博客文章通过文件
     *
     * @param delFile
     */
    public static void delBlogItem(File delFile) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                String fileName = e.attributeValue("fileName");
                if (delFile.getName().equals(fileName)) {
                    blogData.remove(e);
                    break;
                }
            }
            //写入文件
            writerXmlFile(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("删除失败" + e.getMessage());
        }
    }

    /**
     * 删除名字不在内的Blog
     *
     * @param names
     */
    public static void deleteBlogNotInNames(List<String> names) {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                String fileName = e.attributeValue("fileName");
                if (!names.contains(fileName))
                    blogData.remove(e);
            }
            writerXmlFile(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("删除所有Blog失败" + e.getMessage());
        }
    }

    /**
     * 初始化xml中的博客列表
     */
    public static void initXmlBlog() {

        List<String> fileNames = FileUtils.getFileNames(new File(ConstantValue.getGitPath()));

        //删除原来有的已经被删除的根据名字(好像不用的)
        deleteBlogNotInNames(fileNames);

        //保存数据
        saveBlogs(BlogUtils.getBlogsByFileNames(fileNames));
    }

    /**
     * 查找出所有的blog列表
     *
     * @return
     */
    public static List<Blog> getBlogList(int index, int size) {

        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            List<Blog> blogList = new ArrayList<>();
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            int i = 0;
            while (iterator.hasNext()) {
                Element item = iterator.next();
                i++;
                if (index <= i && i < (index + size)) {
                    Blog blog = new Blog();
                    blog.setId(item.attributeValue("id"));
                    blog.setTitle(item.elementText("title"));
                    blog.setContent(item.elementText("content"));
                    blog.setUrl(item.elementText("url"));
                    blog.setTime(item.elementText("time"));
                    blog.setWebUrl(item.elementText("webUrl"));
                    blog.setAuthor(item.elementText("author"));
                    blog.setFileName(item.elementText("fileName"));
                    blogList.add(blog);
                }
            }

            return blogList;

        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("读取所有Blog失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 查找出所有的blog列表
     *
     * @return
     */
    public static List<Map<String, String>> getNavList() {

        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element nav = rootElement.element("nav");
            List<Map<String, String>> navList = new ArrayList<>();
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = nav.elementIterator();
            while (iterator.hasNext()) {
                Element item = iterator.next();
                Map<String, String> map = new HashMap<>();

                map.put("title", item.elementText("title"));
                map.put("id", item.attributeValue("id"));
                map.put("url", item.elementText("url"));

                navList.add(map);
            }

            return navList;

        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("读取所有Nav失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 保存blogs
     *
     * @param blogs
     */
    public static void saveBlogs(List<Blog> blogs) {
        if (blogs != null && blogs.size() > 0) {
            try {
                File file = new File(ConstantValue.getXmlPath());
                if (!file.exists()) {
                    initXml();
                }

                Document document = reader.read(file);
                Element rootElement = document.getRootElement();

                Element blogData = rootElement.element("blogData");
                Blog b = null;
                for (int i= 0;i<blogs.size();i++){
                    b = blogs.get(i);
                    if (checkBlogName(b.getFileName())) {

                        Element blog = blogData.addElement("blog");

                        blog.addAttribute("id", StringUtils.getUUID());
                        blog.addAttribute("fileName", b.getFileName());
                        //title
                        Element title = blog.addElement("title");
                        title.setText(b.getTitle());
                        //content
                        Element content = blog.addElement("content");
                        content.setText(b.getContent());
                        //url
                        Element url = blog.addElement("url");
                        url.setText(b.getUrl());
                        //time
                        Element time = blog.addElement("time");
                        time.setText(b.getTime());
                        //webUrl
                        Element webUrl = blog.addElement("webUrl");
                        webUrl.setText(b.getWebUrl());
                        //author
                        Element author = blog.addElement("author");
                        author.setText(b.getAuthor());
                        //fileName
                        Element fileName = blog.addElement("fileName");
                        fileName.setText(b.getFileName());
                    }
                }

                writerXmlFile(document);
            } catch (DocumentException e) {
                e.printStackTrace();
                System.out.println("保存失败" + e.getMessage());
            }
        }
    }

    /**
     * 查找通过Blog通过id
     *
     * @param uuid
     * @return
     */
    public static Blog getBlog(String uuid) {
        Blog blog = new Blog();
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            Iterator<Element> iterator = blogData.elementIterator();
            while (iterator.hasNext()) {
                Element item = iterator.next();
                String id = item.attributeValue("id");
                if (id.equals(uuid)) {
                    blog.setId(id);
                    blog.setTitle(item.elementText("title"));
                    blog.setContent(item.elementText("content"));
                    blog.setUrl(item.elementText("url"));
                    blog.setTime(item.elementText("time"));
                    blog.setWebUrl(item.elementText("webUrl"));
                    blog.setAuthor(item.elementText("author"));
                    blog.setFileName(item.elementText("fileName"));
                    return blog;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("读取所有Blog失败" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取所有Blog的个数
     * @return
     */
    public static int getBlogCount() {
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Element blogData = rootElement.element("blogData");
            List<Blog> blogList = new ArrayList<>();
            //同时迭代当前节点下面的所有子节点
            Iterator<Element> iterator = blogData.elementIterator();
            int i = 0;
            while (iterator.hasNext()) {
                Element item = iterator.next();
                i++;
            }

            return i;

        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("读取所有Blog数目失败" + e.getMessage());
            return 0;
        }
    }
}
