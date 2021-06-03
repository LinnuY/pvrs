package com.linuy.pvr.timedTask;

import com.linuy.pvr.common.ResponseBean;
import com.linuy.pvr.hadoop.dataMatrix.DataMatrixRun;
import com.linuy.pvr.hadoop.filter.FilterRun;
import com.linuy.pvr.hadoop.recommendationMatrix.RecommendationMatrixRun;
import com.linuy.pvr.hadoop.similarityMatrix.SimilarityMatrixRun;
import com.linuy.pvr.hadoop.transposeMatrix.TransposeMatrixRun;
import com.linuy.pvr.service.FileService;
import com.linuy.pvr.service.HdfsApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ClassUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Configuration
@EnableScheduling
@Slf4j
public class CollaborativeFiltering {

    @Autowired
    FileService fileService;
    @Autowired
    HdfsApiService hdfsApiService;

    @Scheduled(cron = "0 0 */1 * * ? ")
    public void task() {
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " begin start collaborativeFiltering task");

        ResponseBean<String> responseBean = fileService.exportUserBehavior();
        if (responseBean.isSuccess()) {
            hdfsApiService.uploadFileToHDFS(responseBean.getData(), DataMatrixRun.inputPath);
        }
        if (new DataMatrixRun().run() == -1) {
            return;
        }
        if (new SimilarityMatrixRun().run() == -1) {
            return;
        }
        if (new TransposeMatrixRun().run() == -1) {
            return;
        }
        if (new RecommendationMatrixRun().run() == -1) {
            return;
        }
        if (new FilterRun().run() == -1) {
            return;
        }

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String fileName = "videoList.cvs";
        String fileURL = path + "static/" + fileName;
        hdfsApiService.downloadTarget("/UserCF/step5_output/part-r-00000", fileURL);

        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " end start collaborativeFiltering task");
    }
}
