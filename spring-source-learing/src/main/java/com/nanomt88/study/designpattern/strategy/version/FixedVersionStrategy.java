package com.nanomt88.study.designpattern.strategy.version;

import reactor.core.publisher.Mono;

import java.io.InputStream;

public class FixedVersionStrategy extends AbstractPrefixVersionStrategy {

    protected FixedVersionStrategy(String version) {
        super(version);
    }

    @Override
    public Mono<String> getResourceVersion(InputStream resource) {
        return null;
    }
}
