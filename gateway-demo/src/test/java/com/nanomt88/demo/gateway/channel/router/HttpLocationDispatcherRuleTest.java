package com.nanomt88.demo.gateway.channel.router;

import com.nanomt88.demo.gateway.common.TcHttpRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpLocationDispatcherRuleTest {

    @Test
    public void isMatch(){
        HttpLocationDispatcherRule rule = new HttpLocationDispatcherRule();
        List<String> urls = new ArrayList<>();
        urls.add("/api/gateway.do");
        urls.add("/h5/*");
        urls.add("/miniapp/");
        rule.setLocations(urls);

        assertFalse(rule.isMatch(new TcHttpRequest("/api/aaa.do")));
        assertTrue(rule.isMatch(new TcHttpRequest("/api/gateway.do")));
        assertFalse(rule.isMatch(new TcHttpRequest("/api/gateway")));
        assertTrue(rule.isMatch(new TcHttpRequest("/h5/aaa.do")));
        assertTrue(rule.isMatch(new TcHttpRequest("/h5/")));
        assertFalse(rule.isMatch(new TcHttpRequest("/miniapp/aaa.do")));
        assertTrue(rule.isMatch(new TcHttpRequest("/miniapp/")));
    }
}