package com.linuy.pvr.hadoop.similarityMatrix;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public class SimilarityMatrixRun {

    private static String inputPath = "/UserCF/step1_output";
    private static String outputPath = "/UserCF/step2_output";
    //将step1中输出的转置矩阵作为全局缓存
    private static String cache = "/UserCF/step1_output/part-r-00000";

    private static String hdfs = "hdfs://arch1:8020";

    public int run() {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(conf, "SimilarityMatrix");
            //如果未开启,使用 FileSystem.enableSymlinks()方法来开启符号连接。
            FileSystem.enableSymlinks();
            //要使用符号连接，需要检查是否启用了符号连接
            boolean areSymlinksEnabled = FileSystem.areSymlinksEnabled();
            System.out.println(areSymlinksEnabled);
            //添加分布式缓存文件
            job.addCacheArchive(new URI(cache + "#itemUserScore1"));


            //配置任务map和reduce类
            job.setJarByClass(SimilarityMatrixRun.class);
            job.setJar("C:\\Users\\Passenger\\Documents\\IDEA\\LinuYProject\\pvrs\\pvrWeb\\target\\pvrWeb-0.0.1-SNAPSHOT.jar");
            job.setMapperClass(SimilarityMatrixMapper.class);
            job.setReducerClass(SimilarityMatrixReduce.class);

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
        } catch (ClassNotFoundException | InterruptedException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
