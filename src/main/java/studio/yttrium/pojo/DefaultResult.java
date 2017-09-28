package studio.yttrium.pojo;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/16
 * Time: 13:32
 */
public class DefaultResult {

    private int resultCode;

    private Object resultData;

    private String resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public Object getResultData() {
        return resultData;
    }

    public String getResultMessage() {
        return resultMessage;
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
}
