package com.nanomt88.demo.gateway.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class TcResponse {

    private Map data = new HashMap();

    private byte[] originResponseBody;

    private String originResponseString;

    private Charset charset = StandardCharsets.UTF_8 ;

    @Override
    public String toString() {
        return "TcResponse{" +
                "data=" + data +
                ", originResponseString='" + originResponseString + '\'' +
                ", charset=" + charset +
                '}';
    }
}
