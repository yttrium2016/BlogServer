package studio.yttrium.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.utils.ConfigUtils;
import studio.yttrium.utils.JGitUtils;
import studio.yttrium.core.XmlOperation;

/**
 * 定时器 ... 每天晚上重新更新下 是否有新的Blog
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/28
 * Time: 11:27
 */
@Component
public class WebTask {
    // 每五秒执行一次
    @Scheduled(cron = "0 59 23 * * ?")
    public void TaskJob() {
        //删除仓库 重新下载
        JGitUtils.initGitRepository(ConstantValue.getBlogPath(),
                ConfigUtils.getString(ConstantValue.BLOG_GIT_ADDRESS,""),
                ConfigUtils.getString(ConstantValue.GIT_USERNAME,""),
                ConfigUtils.getString(ConstantValue.GIT_PASSWORD,""));
        //重新生成xml文件
        XmlOperation.getApplication().initXmlBlog();
    }
}
