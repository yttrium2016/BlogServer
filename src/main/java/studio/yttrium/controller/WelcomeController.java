package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import studio.yttrium.constant.ConstantValue;
import studio.yttrium.core.XmlOperation;
import studio.yttrium.pojo.DefaultResult;
import studio.yttrium.utils.ConfigUtils;
import studio.yttrium.utils.JGitUtils;
import studio.yttrium.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 上传的Controller
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/28
 * Time: 21:44
 */
@Controller
public class WelcomeController {


    /**
     * 初始化git仓库 包括 img和blog
     * @param git
     * @param blogGitAddress
     * @param imgGitAddress
     * @param gitUsername
     * @param gitPassword
     * @return
     */
    @RequestMapping("initGit")
    @ResponseBody
    public DefaultResult initGit(String git,String blogGitAddress,String imgGitAddress,String gitUsername,String gitPassword) {

        DefaultResult result = new DefaultResult();

        if (StringUtils.isBlank(git,blogGitAddress,imgGitAddress,gitUsername,gitPassword)){
            result.setResultCode(0);
            result.setResultMessage("所有选项必须填写..");
            return result;
        }

        //这里保存数据
        ConfigUtils.putString(ConstantValue.GIT_TYPE, git);
        ConfigUtils.putString(ConstantValue.BLOG_GIT_ADDRESS, blogGitAddress);
        ConfigUtils.putString(ConstantValue.IMG_GIT_ADDRESS, imgGitAddress);
        ConfigUtils.putString(ConstantValue.GIT_USERNAME, gitUsername);
        ConfigUtils.putString(ConstantValue.GIT_PASSWORD, gitPassword);

        if (JGitUtils.initGitRepository(
                ConstantValue.getBlogPath(),
                ConfigUtils.getString(ConstantValue.BLOG_GIT_ADDRESS, ""),
                ConfigUtils.getString(ConstantValue.GIT_USERNAME, ""),
                ConfigUtils.getString(ConstantValue.GIT_PASSWORD, "")) &&
                JGitUtils.initGitRepository(
                        ConstantValue.getImgPath(),
                        ConfigUtils.getString(ConstantValue.IMG_GIT_ADDRESS, ""),
                        ConfigUtils.getString(ConstantValue.GIT_USERNAME, ""),
                        ConfigUtils.getString(ConstantValue.GIT_PASSWORD, ""))) {
            result.setResultCode(1);
            result.setResultMessage("初始化Git仓库成功");
        } else {
            result.setResultCode(0);
            result.setResultMessage("初始化Git仓库失败");
        }
        return result;
    }

    /**
     * 提交保存信息
     * @param request
     * @return
     */
    @RequestMapping("submitConfig")
    @ResponseBody
    public DefaultResult submitConfig(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();

        String git = request.getParameter("git");
        String blogName = request.getParameter("blogName");
        String blogContent = request.getParameter("blogContent");
        String blogAuthor = request.getParameter("blogAuthor");
        String blogUrl = request.getParameter("blogUrl");

        String blogGitAddress = request.getParameter("blogGitAddress");
        String imgGitAddress = request.getParameter("imgGitAddress");

        String gitUsername = request.getParameter("gitUsername");
        String gitPassword = request.getParameter("gitPassword");

        if (!StringUtils.isBlank(git, blogName, blogAuthor, blogUrl, blogGitAddress, imgGitAddress, gitUsername, gitPassword)) {

            ConfigUtils.putString(ConstantValue.GIT_TYPE, git);
            ConfigUtils.putString(ConstantValue.BLOG_NAME, blogName);
            ConfigUtils.putString(ConstantValue.BLOG_NAME, blogName);
            ConfigUtils.putString(ConstantValue.BLOG_CONTENT, blogContent);
            ConfigUtils.putString(ConstantValue.BLOG_AUTHOR, blogAuthor);
            ConfigUtils.putString(ConstantValue.BLOG_URL, blogUrl);

            ConfigUtils.putString(ConstantValue.BLOG_GIT_ADDRESS, blogGitAddress);
            ConfigUtils.putString(ConstantValue.IMG_GIT_ADDRESS, imgGitAddress);

            ConfigUtils.putString(ConstantValue.GIT_USERNAME, gitUsername);
            ConfigUtils.putString(ConstantValue.GIT_PASSWORD, gitPassword);

            ConfigUtils.putBoolean(ConstantValue.BLOG_FIRST_OPEN, false);
            result.setResultCode(1);
            result.setResultMessage("保存成功");
            result.setResultUrl("index.shtml");
            //初始化Blog列表
            XmlOperation.getApplication().initXmlBlog();
        } else {
            result.setResultCode(0);
            result.setResultMessage("所填字段不能为空");
        }

        return result;
    }


    /**
     * 背景上传接口
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult uploadImg(MultipartFile file, HttpServletRequest request) throws IOException {
        DefaultResult result = new DefaultResult();

        if (file != null) {// 判断上传的文件是否为空
            String path = null;// 文件路径
            String type = null;// 文件类型

            String fileName = file.getOriginalFilename();// 文件原名称
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空
                if ("PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 自定义的文件名称
                    String trueFileName = StringUtils.getUUID() + "_" + fileName;
                    // 设置存放图片文件的路径
                    path = ConstantValue.getImgPath() + File.separator + trueFileName;
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));

                    if (JGitUtils.addCommitPush(ConstantValue.getImgPath(), trueFileName, "上传背景图片", ConfigUtils.getString(ConstantValue.GIT_USERNAME,""), ConfigUtils.getString(ConstantValue.GIT_PASSWORD,""))) {

                        String url = ConstantValue.getGitWeb(ConfigUtils.getString(ConstantValue.IMG_GIT_ADDRESS,"")) + trueFileName;
                        ConfigUtils.putString(ConstantValue.BLOG_IMG_URL, url);
                        result.setResultData(url);
                        result.setResultMessage("图片保存成功..");
                        result.setResultCode(1);
                    } else {
                        result.setResultMessage("图片本地保存成功..上传失败 请重新输入Git设置");
                        result.setResultCode(0);
                    }
                } else {
                    result.setResultMessage("请上传jpg,png类型图片");
                    result.setResultCode(0);
                }
            } else {
                result.setResultMessage("文件不能为空");
                result.setResultCode(0);
            }
        } else {
            result.setResultMessage("没有找到文件");
            result.setResultCode(0);
        }
        return result;
    }
}
