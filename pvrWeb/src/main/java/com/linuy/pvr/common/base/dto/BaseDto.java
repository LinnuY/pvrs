package com.linuy.pvr.common.base.dto;

import java.io.Serializable;

/**
 * @author LongTeng
 * @date 2021/03/06
 **/
public abstract class BaseDto<T> implements Serializable {

    public abstract T toEntity();
}
