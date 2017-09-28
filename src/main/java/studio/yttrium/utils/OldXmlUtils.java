package studio.yttrium.utils;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.pojo.Blog;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 旧的原生的写法 已经弃用
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/12
 * Time: 11:01
 */
public class OldXmlUtils {

    @Test
    public void test() {
        delNavItem("166d691398a04eee88f84e99586d4667");
    }

    /**
     * 没有的话建立一个
     */
    private static void initXml() {
        File file = new File(ConstantValue.getXmlPath());
        if (!file.exists()) {
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                //必须要个跟结点
                Element root = document.createElement("root");
                Element nav = document.createElement("nav");

                Element blogs = document.createElement("blogData");

                root.appendChild(nav);
                root.appendChild(blogs);
                //加进入
                document.appendChild(root);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                transformer.transform(new DOMSource(document), new StreamResult(new File(ConstantValue.getXmlPath())));
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
                System.out.println("初始化建立xml玩文件" + e.getMessage());
                new RuntimeException(e);
            }
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();

            Node nav = root.getElementsByTagName("nav").item(0);
            Element ul = document.createElement("ul");
            ul.setAttribute("id", StringUtils.getUUID());
            //TITLE
            Element t = document.createElement("title");
            t.setTextContent(title);
            //URL
            Element u = document.createElement("url");
            u.setTextContent(url);
            //添加
            ul.appendChild(t);
            ul.appendChild(u);
            //添加
            nav.appendChild(ul);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println("保存失败" + e.getMessage());
            e.printStackTrace();
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();
            Element nav = (Element) document.getElementsByTagName("nav").item(0);
            NodeList uls = root.getElementsByTagName("ul");
            for (int i = 0; i < uls.getLength(); i++) {
                Element item = (Element) uls.item(i);
                String id = item.getAttribute("id");
                if (uuid.equals(id)) {
                    nav.removeChild(item);
                    break;
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println("删除失败" + e.getMessage());
            e.printStackTrace();
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();
            Element blogData = (Element) document.getElementsByTagName("blogData").item(0);
            NodeList blogs = root.getElementsByTagName("blog");
            for (int i = 0; i < blogs.getLength(); i++) {
                Element item = (Element) blogs.item(i);
                String fileName = item.getAttribute("fileName");
                if (blogName.equals(fileName)) {
                    return false;
                }
            }
            return true;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            System.out.println("删除失败" + e.getMessage());
            e.printStackTrace();
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
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

                Element root = document.getDocumentElement();

                Node blogData = root.getElementsByTagName("blogData").item(0);
                Element blog = document.createElement("blog");
                blog.setAttribute("id", StringUtils.getUUID());
                blog.setAttribute("fileName", b.getFileName());
                //title
                Element title = document.createElement("title");
                title.setTextContent(b.getTitle());
                //content
                Element content = document.createElement("content");
                content.setTextContent(b.getContent());
                //url
                Element url = document.createElement("url");
                url.setTextContent(b.getUrl());
                //time
                Element time = document.createElement("time");
                time.setTextContent(b.getTime());
                //webUrl
                Element webUrl = document.createElement("webUrl");
                webUrl.setTextContent(b.getWebUrl());
                //author
                Element author = document.createElement("author");
                author.setTextContent(b.getAuthor());
                //fileName
                Element fileName = document.createElement("fileName");
                fileName.setTextContent(b.getFileName());
                //添加
                blog.appendChild(title);
                blog.appendChild(content);
                blog.appendChild(url);
                blog.appendChild(time);
                blog.appendChild(webUrl);
                blog.appendChild(author);
                blog.appendChild(fileName);
                //添加
                blogData.appendChild(blog);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                transformer.transform(new DOMSource(document), new StreamResult(file));
            }
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println("保存失败" + e.getMessage());
            e.printStackTrace();
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();
            Element blogData = (Element) document.getElementsByTagName("blogData").item(0);
            NodeList blogs = root.getElementsByTagName("blog");
            for (int i = 0; i < blogs.getLength(); i++) {
                Element item = (Element) blogs.item(i);
                String id = item.getAttribute("id");
                if (uuid.equals(id)) {
                    blogData.removeChild(item);
                    break;
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println("删除失败" + e.getMessage());
            e.printStackTrace();
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            Element root = document.getDocumentElement();
            Element blogData = (Element) document.getElementsByTagName("blogData").item(0);
            NodeList blogs = root.getElementsByTagName("blog");
            for (int i = 0; i < blogs.getLength(); i++) {
                Element item = (Element) blogs.item(i);
                String fileName = item.getAttribute("fileName");
                if (delFile.getName().equals(fileName)) {
                    blogData.removeChild(item);
                    break;
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println("删除失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化xml中的博客列表
     */
    public static void initXmlBlog() {

        //删除原来有的
        Document document = null;
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);


            Element root = document.getDocumentElement();
            Element blogData = (Element) document.getElementsByTagName("blogData").item(0);
            NodeList blogs = blogData.getElementsByTagName("blog");

            for (int i = 0; i < blogs.getLength(); i++) {
                Element item = (Element) blogs.item(i);
                blogData.removeChild(item);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("删除所有Blog失败" + e.getMessage());
        }
        saveBlogs(BlogUtils.getBlogsByFileNames(FileUtils.getFileNames(new File(ConstantValue.getGitPath()))));
    }

    /**
     * 保存blogs
     * @param blogs
     */
    public static void saveBlogs(List<Blog> blogs) {
        if (blogs != null) {
            for (Blog b : blogs) {
                addBlogItem(b);
            }
        }
    }

    /**
     * 查找出所有的blog列表
     * @return
     */
    public static List<Blog> getBlogs(){

        Document document = null;
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);


            Element root = document.getDocumentElement();
            NodeList blogs = root.getElementsByTagName("blog");
            List<Blog> blogList = new ArrayList<>();
            for (int i = 0; i < blogs.getLength(); i++) {
                Blog blog = new Blog();
                Element item = (Element) blogs.item(i);

                blog.setId(item.getAttribute("id"));
                blog.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
                blog.setContent(item.getElementsByTagName("content").item(0).getTextContent());
                blog.setUrl(item.getElementsByTagName("url").item(0).getTextContent());
                blog.setTime(item.getElementsByTagName("time").item(0).getTextContent());
                blog.setWebUrl(item.getElementsByTagName("webUrl").item(0).getTextContent());
                blog.setAuthor(item.getElementsByTagName("author").item(0).getTextContent());
                blog.setFileName(item.getElementsByTagName("fileName").item(0).getTextContent());

                blogList.add(blog);
            }

            return blogList;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("读取所有Blog失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 查找通过Blog通过id
     * @param uuid
     * @return
     */
    public static Blog getBlog(String uuid){
        Blog blog = new Blog();
        Document document = null;
        try {
            File file = new File(ConstantValue.getXmlPath());
            if (!file.exists()) {
                initXml();
            }
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);


            Element root = document.getDocumentElement();
            NodeList blogs = root.getElementsByTagName("blog");
            List<Blog> blogList = new ArrayList<>();
            for (int i = 0; i < blogs.getLength(); i++) {
                Element item = (Element) blogs.item(i);
                String id = item.getAttribute("id");
                if (id.equals(uuid)) {
                    blog.setId(id);
                    blog.setTitle(item.getElementsByTagName("title").item(0).getTextContent());
                    blog.setContent(item.getElementsByTagName("content").item(0).getTextContent());
                    blog.setUrl(item.getElementsByTagName("url").item(0).getTextContent());
                    blog.setTime(item.getElementsByTagName("time").item(0).getTextContent());
                    blog.setWebUrl(item.getElementsByTagName("webUrl").item(0).getTextContent());
                    blog.setAuthor(item.getElementsByTagName("author").item(0).getTextContent());
                    blog.setFileName(item.getElementsByTagName("fileName").item(0).getTextContent());
                    break;
                }
            }

            return blog;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("读取所有Blog失败" + e.getMessage());
            return null;
        }
    }
}
