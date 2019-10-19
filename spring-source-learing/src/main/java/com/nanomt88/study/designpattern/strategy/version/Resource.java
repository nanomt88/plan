package com.nanomt88.study.designpattern.strategy.version;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *   资源类
 * @author nanomt88@gmail.com
 * @date 18.5.29 5:37
 * @updateLog:
 *      TODO  update by nanomt88@gmail.com, 18.5.29 5:37
 */
public class Resource {

    public boolean isFile() {
        return false;
    }

    public URL getURL() throws IOException{
        return null;
    }

    public File getFile() throws IOException{
        return null;
    }

    public ReadableByteChannel readableChannel() throws IOException {
        return Channels.newChannel(getInputStream());
    }

    public InputStream getInputStream() throws IOException{
        return null;
    }
}
