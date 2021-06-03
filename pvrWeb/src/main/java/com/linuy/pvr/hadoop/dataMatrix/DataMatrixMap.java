package com.linuy.pvr.hadoop.dataMatrix;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * step1
 * @author LongTeng
 * @date 2021/05/27
 **/
public class DataMatrixMap extends Mapper<LongWritable, Text, Text, Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split(",");
        String userId = values[0];
        String videoId = values[1];
        String score = values[2];

        outKey.set(userId);
        outValue.set(videoId + "_" + score);
        context.write(outKey, outValue);
    }
}
