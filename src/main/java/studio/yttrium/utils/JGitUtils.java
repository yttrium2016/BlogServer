package studio.yttrium.utils;

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
    }

    /**
     * git操作对象
     */
    private static Git git = null;


    private static String localTemp = null;

    /**
     * 获取git对象
     *
     * @return
     */
    public static Git getGit(String localFile) {
        //git仓库地址
        try {
            if (!localFile.equals(localTemp)) {
                if (git != null)
                    git.close();
                git = Git.open(new File(localFile + "/.git"));
                localTemp = localFile;
            }
        } catch (IOException e) {
            System.out.println("本地仓库不存在" + e.getMessage());
            e.printStackTrace();
            new RuntimeException(e);
            return null;
        }
        return git;
    }


    /**
     * 初始化本地git仓库 有就删掉
     */
    public static boolean initGitRepository(String localFile, String gitUrl, String username, String password) {
        gitClose();
        File file = new File(localFile);
        FileUtils.deleteAllFilesOfDir(file);
        cloneRepository(localFile, gitUrl, username, password);
        return true;
    }

    /**
     * 下载远程仓库
     *
     * @throws IOException
     * @throws GitAPIException
     * @throws RuntimeException
     */
    public static void cloneRepository(String localPath, String gitUrl, String username, String password) {
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

        //设置远程服务器上的用户名和密码 (可以不用)
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(username, password);

        //克隆代码库命令
        Git git = null;
        try {
            git = Git.cloneRepository().setURI(gitUrl) //设置远程URI
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
    public static boolean gitAdd(String localFile,String fileName) {
        //提交代码
        try {
            getGit(localFile).add().addFilepattern(fileName).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git add " + fileName + "失败" + e.getMessage());
            new RuntimeException(e);
            return false;
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
    public static boolean gitCommit(String localFile,String message) {
        //提交代码
        try {
            getGit(localFile).commit().setAll(true).setMessage(message).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git commit " + message + "失败" + e.getMessage());
            new RuntimeException(e);
            return false;
        }

    }

    /**
     * 本地删除代码
     *
     * @param fileName
     * @throws IOException
     * @throws GitAPIException
     */
    public static boolean gitRemove(String localFile,String fileName) {
        //提交代码
        try {
            getGit(localFile).rm().addFilepattern(fileName).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git rm " + fileName + "失败" + e.getMessage());
            new RuntimeException(e);
            return false;
        }
    }

    /**
     * push本地代码到远程仓库地址
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public static boolean gitPush(String localFile,String username, String password) {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(username, password);

        try {
            getGit(localFile).push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git push 失败" + e.getMessage());
            new RuntimeException(e);
            return false;
        }
    }

    /**
     * Checkout从远端仓库获取所有
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public static boolean gitCheckout(String localFile) {
        try {
            getGit(localFile).checkout().setAllPaths(true).call();
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("git checkout 失败" + e.getMessage());
            new RuntimeException(e);
            return false;
        }
    }

    /**
     * 添加文件推送到远端
     *
     * @param fileName
     * @param message
     */
    public static boolean addCommitPush(String localFile,String fileName, String message, String username, String password) {
        //更新远端
        if(!gitCheckout(localFile))
            return false;
        //本地添加
        if(!gitAdd(localFile,fileName))
            return false;

        //提交代码
        if(!gitCommit(localFile,message))
            return false;

        //推送
        if(!gitPush(localFile,username, password))
            return false;

        return true;
    }

    /**
     * 删除文件推送到远端
     *
     * @param fileName
     * @param message
     */
    public static boolean removeCommitPush(String localFile,String fileName, String message, String username, String password) {
        //更新远端
        if(!gitCheckout(localFile))
            return false;

        //本地添加
        if(!gitRemove(localFile,fileName))
            return false;

        //提交代码
        if(!gitCommit(localFile,message))
            return false;

        //推送
        if(!gitPush(localFile,username, password))
            return false;

        return true;
    }

    /**
     * 关闭Git 可以删除
     */
    public static void deleteRepository() {
        gitClose();
        File file = new File(ConstantValue.getBlogPath());
        FileUtils.deleteAllFilesOfDir(file);
    }


    /**
     * 关闭Git占用 用于删除
     */
    public static void gitClose() {
        if (git != null)
            git.close();
        git = null;
        localTemp = null;
    }

}
