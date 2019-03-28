package me.warriorg.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author: 高士勇
 * @date: 2019-03-28
 */
public class MyMapper extends ObjectMapper {
    public MyMapper() {
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
