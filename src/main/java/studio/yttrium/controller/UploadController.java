package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
public class UploadController {

    @RequestMapping("upload")
    public String toUpload() {
        return "upload";
    }


    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(MultipartFile file, HttpServletRequest request) throws IOException {

        String result = "";
        String back = "<a href=\"#\" onClick=\"javascript :history.back(-1);\">返回上一页</a>";
        if (file != null) {// 判断上传的文件是否为空
            String path = null;// 文件路径
            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
            long size = file.getSize();
            System.out.println("上传的文件原名称:" + fileName + "文件大小:" + size);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    // 设置存放图片文件的路径
                    path = realPath +/*System.getProperty("file.separator")+*/trueFileName;
                    System.out.println("存放图片文件的路径:" + path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    System.out.println("文件成功上传到指定目录下");
                    result = "文件成功上传到指定目录下" + path;
                } else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    result = "不是我们想要的文件类型,请按要求重新上传" + back;
                }
            } else {
                System.out.println("文件类型为空");
                result = "文件类型为空" + back;
            }
        } else {
            System.out.println("没有找到相对应的文件");
            result = "没有找到相对应的文件" + back;
        }
        return result;
    }
}
