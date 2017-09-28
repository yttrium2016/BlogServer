package studio.yttrium.pojo;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/29
 * Time: 10:31
 */
public class Blog {

    private String id;
    private String title;
    private String fileName;
    private String content;
    private String url;
    private String time;
    private String webUrl;
    private String author;

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
