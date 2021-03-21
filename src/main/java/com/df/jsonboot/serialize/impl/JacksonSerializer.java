package com.df.jsonboot.serialize.impl;

import com.df.jsonboot.serialize.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * jackson 的反序列化实现
 *
 * @author qinghuo
 * @since 2021/03/21 10:53
 */
public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) {
        byte[] byteArray = new byte[0];
        try {
            byteArray = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        T result = null;
        try {
            result = objectMapper.readValue(bytes, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
