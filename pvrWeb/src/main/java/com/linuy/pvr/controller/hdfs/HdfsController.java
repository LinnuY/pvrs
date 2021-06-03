package com.linuy.pvr.controller.hdfs;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.common.base.controller.BaseController;
import com.linuy.pvr.entity.User;
import com.linuy.pvr.utils.HdfsUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author LongTeng
 * @date 2021/03/09
 **/
@RestController
@RequestMapping("hdfs")
public class HdfsController {

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/mkdir")
    public ResponseBean<Object> mkdir(@RequestParam("path") String path) throws Exception {
        ResponseBean<Object> responseBean = new ResponseBean<>();
        if (StringUtils.isEmpty(path)) {
            responseBean.addError("请求参数为空");
            return responseBean;
        }
        // 创建空文件夹
        boolean isOk = HdfsUtil.mkdir(path);
        if (isOk) {
            responseBean.addSuccess("create dir success");
        } else {
            responseBean.addError("create dir fail");
        }
        return responseBean;
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readPathInfo")
    public ResponseBean<Object> readPathInfo(@RequestParam("path") String path) throws Exception {
        List<Map<String, Object>> list = HdfsUtil.readPathInfo(path);
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.addSuccess(list);
        return responseBean;
    }

    /**
     * 获取HDFS文件在集群中的位置
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/getFileBlockLocations")
    public ResponseBean<Object> getFileBlockLocations(@RequestParam("path") String path) throws Exception {
        BlockLocation[] blockLocations = HdfsUtil.getFileBlockLocations(path);
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        responseBean.addSuccess(blockLocations);
        return responseBean;
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/createFile")
    public ResponseBean<Object> createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseBean<Object> responseBean = new ResponseBean<Object>();
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            return responseBean.addError("请求参数为空");
        }
        HdfsUtil.createFile(path, file);
        return responseBean.addSuccess("create file success");
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readFile")
    public ResponseBean<Object> readFile(@RequestParam("path") String path) throws Exception {
        String targetPath = HdfsUtil.readFile(path);
        return new ResponseBean<>(targetPath);
    }

    /**
     * 读取HDFS文件转换成Byte类型
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/openFileToBytes")
    public ResponseBean<Object> openFileToBytes(@RequestParam("path") String path) throws Exception {
        byte[] files = HdfsUtil.openFileToBytes(path);
        return new ResponseBean<>(files);
    }

    /**
     * 读取HDFS文件装换成User对象
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/openFileToUser")
    public ResponseBean<Object> openFileToUser(@RequestParam("path") String path) throws Exception {
        User user = HdfsUtil.openFileToObject(path, User.class);
        return new ResponseBean<>(user);
    }

    /**
     * 读取文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/listFile")
    public ResponseBean<Object> listFile(@RequestParam("path") String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return new ResponseBean<>("请求参数为空");
        }
        List<Map<String, String>> returnList = HdfsUtil.listFile(path);
        return new ResponseBean<>(returnList);
    }

    /**
     * 重命名文件
     *
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    public ResponseBean<Object> renameFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName) throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return new ResponseBean<>("请求参数为空");
        }
        boolean isOk = HdfsUtil.renameFile(oldName, newName);
        if (isOk) {
            return new ResponseBean<>("rename file success");
        } else {
            return new ResponseBean<>("rename file fail");
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteFile")
    public ResponseBean<Object> deleteFile(@RequestParam("path") String path) throws Exception {
        boolean isOk = HdfsUtil.deleteFile(path);
        if (isOk) {
            return new ResponseBean<>("delete file success");
        } else {
            return new ResponseBean<>("delete file fail");
        }
    }

    /**
     * 上传文件
     *
     * @param path
     * @param uploadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResponseBean<Object> uploadFile(@RequestParam("path") String path, @RequestParam("uploadPath") String uploadPath) throws Exception {
        HdfsUtil.uploadFile(path, uploadPath);
        return new ResponseBean<>("upload file success");
    }

    /**
     * 下载文件
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    public ResponseBean<Object> downloadFile(@RequestParam("path") String path, @RequestParam("downloadPath") String downloadPath) throws Exception {
        HdfsUtil.downloadFile(path, downloadPath);
        return new ResponseBean<>("download file success");
    }

    /**
     * HDFS文件复制
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    @PostMapping("/copyFile")
    public ResponseBean<Object> copyFile(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath) throws Exception {
        HdfsUtil.copyFile(sourcePath, targetPath);
        return new ResponseBean<>("copy file success");
    }

    @PostMapping("/existFile")
    public ResponseBean<Object> existFile(@RequestParam("path") String path) throws Exception {
        boolean isExist = HdfsUtil.existFile(path);
        return new ResponseBean<>("file isExist: " + isExist);
    }

}
