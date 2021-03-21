package com.df.jsonboot.serialize;

/**
 * 序列化接口
 *
 * @author qinghuo
 * @since 2021/03/21 10:44
 */
public interface Serializer {

    /**
     * 将一个object对象序列化成二进制数组
     *
     * @param object 需要被序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes 要被反序列化的二进制数组
     * @param tClass 反序列成的类型
     * @param <T> 当前方法使用的泛型
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> tClass);
}
