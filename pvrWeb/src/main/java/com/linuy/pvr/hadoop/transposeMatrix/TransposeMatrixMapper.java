package com.linuy.pvr.hadoop.transposeMatrix;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public class TransposeMatrixMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();
    /**
     * key:1
     * value:1	1_0,2_3,3_-1,4_2,5_-3
     * */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] rowAndLine = value.toString().split("\t");

        //矩阵行号	物品ID
        String itemID = rowAndLine[0];
        //列值	用户ID_分值
        String[] lines = rowAndLine[1].split(",");


        for(int i = 0 ; i<lines.length; i++){
            String userID = lines[i].split("_")[0];
            String score = lines[i].split("_")[1];

            //key:列号	 用户ID	value:行号_值	 物品ID_分值
            outKey.set(userID);
            outValue.set(itemID+"_"+score);

            context.write(outKey, outValue);
        }
    }
}
