package studio.yttrium.pojo;

/**
 * 默认返回实体类
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/16
 * Time: 13:32
 */
public class DefaultResult {

    private int resultCode; //1.成功 0.失败

    private Object resultData; //数据内容

    private String resultMessage; //返回信息

    private String resultUrl; //返回信息

    public int getResultCode() {
        return resultCode;
    }

    public Object getResultData() {
        return resultData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}
