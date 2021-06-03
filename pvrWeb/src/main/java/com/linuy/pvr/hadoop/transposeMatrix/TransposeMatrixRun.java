package com.linuy.pvr.hadoop.transposeMatrix;

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
public class TransposeMatrixRun {

    private static String inputPath = "/UserCF/step1_output";
    private static String outputPath = "/UserCF/step3_output";
    private static String hdfs = "hdfs://arch1:8020";

    public int run() {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(conf, "step3");


            //配置任务map和reduce类
            job.setJarByClass(TransposeMatrixRun.class);
            job.setJar("C:\\Users\\Passenger\\Documents\\IDEA\\LinuYProject\\pvrs\\pvrWeb\\target\\pvrWeb-0.0.1-SNAPSHOT.jar");
            job.setMapperClass(TransposeMatrixMapper.class);
            job.setReducerClass(TransposeMatrixReduce.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileSystem fs = FileSystem.get(conf);
            Path inpath = new Path(inputPath);
            if (fs.exists(inpath)) {
                FileInputFormat.addInputPath(job, inpath);
            } else {
                System.out.println(inpath);
                System.out.println("不存在");
            }

            Path outpath = new Path(outputPath);
            fs.delete(outpath, true);
            FileOutputFormat.setOutputPath(job, outpath);

            return job.waitForCompletion(true) ? 1 : -1;
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
