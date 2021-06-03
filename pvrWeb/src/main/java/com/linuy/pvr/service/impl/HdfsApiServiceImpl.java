package com.linuy.pvr.service.impl;

import com.linuy.pvr.service.HdfsApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;


/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Slf4j
@Service
public class HdfsApiServiceImpl implements HdfsApiService {

    public Boolean uploadFileToHDFS(String srcPath, String targetPath) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://arch1:8020");
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        try {
            FileSystem fileSystem = FileSystem.get(URI.create("hdfs://arch1:8020"), configuration, "passenger");
            Path src = new Path(srcPath);
            Path target = new Path(targetPath);
            fileSystem.copyFromLocalFile(false, src, target);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
        return true;
    }

    @Override
    public Boolean downloadTarget(String srcPath, String targetPath) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://arch1:8020");
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        try {
            FileSystem fs = FileSystem.get(URI.create("hdfs://arch1:8020"), configuration);
            Path ofile = new Path(srcPath);
            File nfile = new File(targetPath);
            FSDataInputStream in = fs.open(ofile);
            FileOutputStream out = new FileOutputStream(nfile);
            IOUtils.copyBytes(in, out, 2048, true);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
        return true;
    }
}
