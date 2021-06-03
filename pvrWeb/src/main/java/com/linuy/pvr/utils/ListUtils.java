package com.linuy.pvr.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LongTeng
 * @date 2021/05/27
 **/
public class ListUtils<T> {

    public List<T> removeRepeat(List<T> originList) {
        List<T> resultList = new ArrayList<>();
        if (originList == null || originList.isEmpty()) {
            return resultList;
        }
        for (T t : originList) {
            if (!resultList.contains(t)) {
                resultList.add(t);
            }
        }
        return resultList;
    }
}
