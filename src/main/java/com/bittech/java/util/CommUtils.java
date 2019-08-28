package com.bittech.java.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:封装公共工具方法,如加载配置文件、json序列化等
 */
public class CommUtils {
    private static final Gson GSON = new GsonBuilder().create();
    /**
     * 加载配置文件
     *
     * @param fileName 要加载的配置文件名称
     * @return
     */
    // ctrl+shift+T
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        //class.getClassLoader：获得当前类型的类加载器
        //getResourceAsStream（String str）参数为文件路径
        //得到制定文件名***的文件输入流
        InputStream in = CommUtils.class.getClassLoader()
                .getResourceAsStream(fileName);
        try {
            //加载配置文件
            properties.load(in);
        } catch (IOException e) {
            return null;
        }
        //返回配置文件
        return properties;
    }

    /**
     * 将任意对象序列化为json 字符串
     * @param obj
     * @return
     */
    public static String object2Json(Object obj) {

        return GSON.toJson(obj);
    }

    /**
     * 将任意json字符串反序列化为对象
     * @param jsonStr json字符串
     * @param objClass 反序列化的类反射对象
     * @return
     */
    public static Object json2Object(String jsonStr,Class objClass) {

        return GSON.fromJson(jsonStr,objClass);
    }

}
