package studio.yttrium.utils;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;
import studio.yttrium.constant.ConstantValue;

import java.io.File;
import java.io.IOException;

/**
 * 自定义JGIT工具类
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/31
 * Time: 14:35
 */
public class JGitUtils {


    @Test
    public void test() throws IOException, GitAPIException {
        initGitRepository();
    }

    /**
     * git操作对象
     */
    private static Git git = null;

    /**
     * 获取git对象
     *
     * @return
     */
    public static Git getGit() {
        //git仓库地址
        if (git == null) {
            try {
                git = Git.open(new File(ConstantValue.getGitPath() + "/.git"));
            } catch (IOException e) {
                System.out.println("本地仓库不存在" + e.getMessage());
                e.printStackTrace();
                new RuntimeException(e);
            }
        }
        return git;
    }


    /**
     * 初始化本地git仓库 有就删掉
     */
    public static void initGitRepository() {
        File file = new File(ConstantValue.getGitPath());
        FileUtils.deleteAllFilesOfDir(file);
        cloneRepository(ConstantValue.getGitPath());
    }

    /**
     * 下载仓库 从默认地址
     */
    public static void cloneRepository() {
        cloneRepository(ConstantValue.getGitPath());
    }

    /**
     * 下载远程仓库
     *
     * @throws IOException
     * @throws GitAPIException
     * @throws RuntimeException
     */
    public static void cloneRepository(String localPath) {
        File file = new File(localPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                if (files != null && files.length > 0) {
                    System.out.println("目录 " + file.getPath() + " 不为空！");
                    return;
                }
            } else {
                System.out.println("目录不能为文件");
                return;
            }
        } else {
            file.mkdir();
        }

        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(
                ConfigUtils.getString(ConstantValue.GIT_USERNAME, ""),
                ConfigUtils.getString(ConstantValue.GIT_PASSWORD, ""));

        //克隆代码库命令

        CloneCommand cloneCommand = Git.cloneRepository();

        Git git = null;
        try {
            git = cloneCommand.setURI(ConfigUtils.getString(ConstantValue.GIT_ADDRESS, "")) //设置远程URI
                    .setBranch("master") //设置clone下来的分支
                    .setDirectory(file) //设置下载存放路径
                    .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("下载仓库出错" + e.getMessage());
            new RuntimeException(e);
        } finally {
            if (git != null) {
                git.close();
            }
        }
    }

    /**
     * 本地添加代码
     *
     * @param fileName
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitAdd(String fileName) {
        //提交代码
        try {
            getGit().add().addFilepattern(fileName).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git add " + fileName + "失败" + e.getMessage());
            new RuntimeException(e);
        }
    }

    /**
     * 本地提交代码
     *
     * @param message
     * @throws IOException
     * @throws GitAPIException
     * @throws JGitInternalException
     */
    public static void gitCommit(String message) {
        //提交代码
        try {
            getGit().commit().setAll(true).setMessage(message).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git commit " + message + "失败" + e.getMessage());
            new RuntimeException(e);
        }

    }

    /**
     * 本地删除代码
     *
     * @param fileName
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitRemove(String fileName) {
        //提交代码
        try {
            getGit().rm().addFilepattern(fileName).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git rm " + fileName + "失败" + e.getMessage());
            new RuntimeException(e);
        }
    }

    /**
     * push本地代码到远程仓库地址
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitPush() {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(
                ConfigUtils.getString(ConstantValue.GIT_USERNAME, ""),
                ConfigUtils.getString(ConstantValue.GIT_PASSWORD, ""));

        try {
            getGit().push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git push 失败" + e.getMessage());
            new RuntimeException(e);
        }
    }

    /**
     * Checkout从远端仓库获取所有
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitCheckout() {
        try {
            getGit().checkout().setAllPaths(true).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git checkout 失败" + e.getMessage());
            new RuntimeException(e);
        }
    }

    /**
     * 添加文件推送到远端
     *
     * @param fileName
     * @param message
     */
    public static void addCommitPush(String fileName, String message) {
        //更新远端
        gitCheckout();

        //本地添加
        gitAdd(fileName);

        //提交代码
        gitCommit(message);

        //推送
        gitPush();
    }

    /**
     * 删除文件推送到远端
     *
     * @param fileName
     * @param message
     */
    public static void removeCommitPush(String fileName, String message) {
        //更新远端
        gitCheckout();

        //本地添加
        gitRemove(fileName);

        //提交代码
        gitCommit(message);

        //推送
        gitPush();
    }

    /**
     * 关闭Git 可以删除
     */
    public static void deleteRepository() {
        gitClose();
        File file = new File(ConstantValue.getGitPath());
        FileUtils.deleteAllFilesOfDir(file);
    }


    /**
     * 关闭Git占用 用于删除
     */
    public static void gitClose() {
        if (getGit() != null) {
            getGit().close();
            git = null;
        }
    }

}
