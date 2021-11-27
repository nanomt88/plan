package com.nanomt88.demo.gateway.common;

import lombok.Data;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

@Data
public class TcHttpRequest extends TcRequest{

    private String url ;

    private HttpServletRequest httpServletRequest;

    public TcHttpRequest() {
    }

    public TcHttpRequest(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TcHttpRequest{" +
                "url='" + url + '\'' +
                ", httpServletRequest=" + httpServletRequest +
                "} " + super.toString();
    }
}
