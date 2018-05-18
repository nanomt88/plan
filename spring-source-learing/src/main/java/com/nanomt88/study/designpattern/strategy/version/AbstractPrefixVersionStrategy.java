package com.nanomt88.study.designpattern.strategy.version;

/**
 *   解析version在前面的 url 路径
 *      例如： "version/static/myresource.js".
 * @author nanomt88@gmail.com
 * @date 18.5.16 8:07
 * @updateLog:
 *         update by nanomt88@gmail.com, 18.5.16 8:07
 */
public abstract class AbstractPrefixVersionStrategy implements VersionStrategy {

    private final String prefix;


    protected AbstractPrefixVersionStrategy(String version) {
        this.prefix = version;
    }


    @Override
    public String extractVersion(String requestPath) {
        return requestPath.startsWith(this.prefix) ? this.prefix : null;
    }

    @Override
    public String removeVersion(String requestPath, String version) {
        return requestPath.substring(this.prefix.length());
    }

    @Override
    public String addVersion(String path, String version) {
        if (path.startsWith(".")) {
            return path;
        }
        else if (this.prefix.endsWith("/") || path.startsWith("/")) {
            return this.prefix + path;
        }
        else {
            return this.prefix + '/' + path;
        }
    }
}
