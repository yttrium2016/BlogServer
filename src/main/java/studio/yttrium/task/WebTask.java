package studio.yttrium.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import studio.yttrium.utils.JGitUtils;
import studio.yttrium.utils.XmlUtils;

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
        JGitUtils.initGitRepository();
        //重新生成xml文件
        XmlUtils.initXmlBlog();
    }
}
