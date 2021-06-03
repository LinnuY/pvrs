package com.linuy.pvr.hadoop.dataMatrix;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
@Slf4j
public class DataMatrixRun {

    public static String inputPath = "/UserCF/step1_input/actionList.txt";
    public static String outputPath = "/UserCF/step1_output";
    private static String hdfs = "hdfs://arch1:8020";

    public int run() {
        try {
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(configuration, "DataMatrix");
            job.setJarByClass(DataMatrixRun.class);
            job.setJar("C:\\Users\\Passenger\\Documents\\IDEA\\LinuYProject\\pvrs\\pvrWeb\\target\\pvrWeb-0.0.1-SNAPSHOT.jar");
            job.setMapperClass(DataMatrixMap.class);
            job.setReducerClass(DataMatrixReduce.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileSystem fs = FileSystem.get(configuration);
            Path inPath = new Path(inputPath);
            if (fs.exists(inPath)) {
                FileInputFormat.addInputPath(job, inPath);
            } else {
                log.error(inPath + " is not exist!");
            }

            Path outPath = new Path(outputPath);
            fs.delete(outPath, true);
            FileOutputFormat.setOutputPath(job, outPath);

            return job.waitForCompletion(true) ? 1 : -1;
        } catch (ClassNotFoundException | InterruptedException | IOException e) {
            log.error("{0}", e);
        }
        return -1;
    }
}
