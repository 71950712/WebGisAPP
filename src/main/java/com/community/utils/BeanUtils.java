package com.community.utils;

import java.io.*;

public class BeanUtils {

    /**
     * 对象序列化为byte数组
     *
     * @param obj
     * @return
     */
    public static byte[] beanToByte(Object obj) {
        byte[] array = null;
        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteArray)) {
            outputStream.writeObject(obj);
            outputStream.flush();
            array = byteArray.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;


    }
    /**
     * 字节数组转为Object对象
     *
     * @param bytes
     * @return
     */
    public static Object byteToObj(byte[] bytes) {
        Object readObject = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream inputStream = new ObjectInputStream(in)){
            readObject = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //反序列化失败，则直接结束程序，保证数据完整
            System.err.println("反序列化对象失败！");
            System.exit(0);
        }
        return readObject;
    }

}
