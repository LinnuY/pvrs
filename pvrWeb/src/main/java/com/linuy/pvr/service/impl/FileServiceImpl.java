package com.linuy.pvr.service.impl;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.entity.UserBehaviorLog;
import com.linuy.pvr.repository.UserBehaviorLogRepository;
import com.linuy.pvr.service.FileService;
import com.linuy.pvr.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/18
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    UserBehaviorLogRepository userBehaviorLogRepository;

    @Override
    public ResponseBean<Object> save(MultipartFile file) {
        ResponseBean<Object> responseBean = new ResponseBean<>();
        try {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            String fileName = new SimpleDateFormat("yyyyMMddHH").format(new Date()) + file.getOriginalFilename();
            String fileURL = path + "static/videos/" + fileName;
            File uploadFile = new File(fileURL);

            if (!file.isEmpty()) {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(uploadFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                log.info(file.getOriginalFilename() + ":文件上传成功");
                return responseBean.addSuccess(fileName);
            } else {
                log.error("File is Empty!");
                return responseBean.addError("File is Empty!");
            }
        } catch (Exception e) {
            log.error("{}", e.toString());
            return responseBean.addError(e.toString());
        }
    }

    public ResponseBean<String> exportUserBehavior() {
        ResponseBean<String> responseBean = new ResponseBean<>();
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String fileName = "userBehavior.cvs";
        String fileURL = path + "static/" + fileName;
        File file = new File(fileURL);
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        List<UserBehaviorLog> dataList = userBehaviorLogRepository.findAll();
        dataList = new ListUtils<UserBehaviorLog>().removeRepeat(dataList);

        try {
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            if (dataList != null && !dataList.isEmpty()) {
                for (UserBehaviorLog data : dataList) {
                    bufferedWriter.append(data.toString()).append('\r');
                }
            }
            responseBean.addSuccess("文件导出成功", fileURL);
        } catch (Exception e) {
            log.error("{}", e.toString());
            responseBean.addError(e.toString());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (Exception e) {
                    log.error("{}", e.toString());
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    log.error("{}", e.toString());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    log.error("{}", e.toString());
                }
            }
        }
        return responseBean;
    }
}
