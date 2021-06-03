package com.linuy.pvr.hadoop.dataMatrix;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public class DataMatrixReduce extends Reducer<Text, Text, Text, Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Map<String, Integer> map = new HashMap<>();
        String videoId = key.toString();

        for (Text value : values) {
            String[] split = value.toString().split("_");
            String userId = split[0];
            String score = split[1];

            if (map.get(userId) == null) {
                map.put(userId, Integer.parseInt(score));
            } else {
                Integer preScore = map.get(userId);
                map.put(userId, preScore + Integer.parseInt(score));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String userId = entry.getKey();
            String score = String.valueOf(entry.getValue());
            stringBuilder.append(userId).append("_").append(score).append(",");
        }
        String line = null;
        if (stringBuilder.toString().endsWith(",")) {
            line = stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        outKey.set(videoId);
        outValue.set(line);
        context.write(outKey, outValue);
    }
}
