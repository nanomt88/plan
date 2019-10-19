package com.nanomt88.study.designpattern.strategy.version;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VersionTest {

    @Test
    public void contentVersion(){
        ContentVersionStrategy strategy = new ContentVersionStrategy();
        String hash = "7fbe76cdac6093784895bb4989203e5a";
        String path = "font-awesome/css/font-awesome.min-" + hash + ".css";

        assertEquals(hash, strategy.extractVersion(path));
        assertNull(strategy.extractVersion("foo/bar.css"));
    }

    public void fixedVersion(){
        String version = "1df341f";
        String path = "js/foo.js";
        FixedVersionStrategy strategy = new FixedVersionStrategy(version);
        assertEquals(version, strategy.extractVersion(version + "/" + path));
        assertNull(strategy.extractVersion(path));
    }
}
