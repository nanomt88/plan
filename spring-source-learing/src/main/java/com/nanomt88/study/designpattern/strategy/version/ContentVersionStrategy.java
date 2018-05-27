package com.nanomt88.study.designpattern.strategy.version;

import reactor.core.publisher.Mono;

import java.io.InputStream;

public class ContentVersionStrategy extends AbstractFileNameVersionStrategy {

    @Override
    public Mono<String> getResourceVersion(InputStream resource) {
        return null;
    }
}
