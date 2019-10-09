package com.cloud.provider.utils;

import java.text.DecimalFormat;

/**
 * 数字格式显示转换
 */
public class DecimalFormatUtil {

    /**三位补零*/
    public static final  String  THREE_ZEROIZE="000";

    /**
     * 根据格式转换
     * @param pattern 要转换的模式，例如000，
     * @param data 数据
     * 例：当data是5，pattern是000，则结果是005，当data是100时，结果是100
     * @return
     */
    public static String format (String pattern,Object data){
        return new DecimalFormat(pattern).format(data);
    }

    public static void main(String[] args) {
        System.out.println(format("000", 1));
    }

}
