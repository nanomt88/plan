package com.nanomt88.demo.gateway.common;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;

@Data
public class TcRequest{

    private Map data;

    private byte[] originRequestBody;

    private String originRequestString;

    @Override
    public String toString() {
        return "TcRequest{" +
                "data=" + data +
                ", originRequestString='" + originRequestString + '\'' +
                '}';
    }
}
