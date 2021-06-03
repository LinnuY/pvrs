package com.linuy.pvr.common;

import lombok.Data;

/**
 * @author LongTeng
 * @date 2021/02/12
 **/
@Data
public class Constant {

    public static final Integer MAX_AGE = 7 * 24 * 60 * 60;

    public static final String sessionUsername = "PvrUsername";
    public static final String sessionPassword = "PvrPassword";

    public static final String COOKIE_USER = "PvrUser";

    public static final int SEVEN_DAY = 7 * 24 * 60 * 60;
}
