package com.nanomt88.study.designpattern.strategy.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  解析version 在 url文件名中的策略
 *      例如："static/myresource-version.js"
 * @author nanomt88@gmail.com
 * @date 18.5.16 8:09
 * @updateLog:
 *         update by nanomt88@gmail.com, 18.5.16 8:09
 */
public abstract class AbstractFileNameVersionStrategy implements VersionStrategy{

    private static final Pattern pattern = Pattern.compile("-(\\S*)\\.");

    @Override
    public String extractVersion(String requestPath) {
        Matcher matcher = pattern.matcher(requestPath);
        if (matcher.find()) {
            String match = matcher.group(1);
            return (match.contains("-") ? match.substring(match.lastIndexOf('-') + 1) : match);
        }
        else {
            return null;
        }
    }

    @Override
    public String removeVersion(String requestPath, String version) {
        return requestPath.replace("-" + version, "");
    }

    @Override
    public String addVersion(String requestPath, String version) {
        String baseFilename = stripFilenameExtension(requestPath);
        String extension = getFilenameExtension(requestPath);
        return (baseFilename + '-' + version + '.' + extension);
    }

    private String stripFilenameExtension(String path) {
        int extIndex = path.lastIndexOf(".");
        if (extIndex == -1) {
            return path;
        }

        int folderIndex = path.lastIndexOf("/");
        if (folderIndex > extIndex) {
            return path;
        }

        return path.substring(0, extIndex);
    }

    private  String getFilenameExtension( String path) {
        if (path == null) {
            return null;
        }

        int extIndex = path.lastIndexOf(".");
        if (extIndex == -1) {
            return null;
        }

        int folderIndex = path.lastIndexOf("/");
        if (folderIndex > extIndex) {
            return null;
        }

        return path.substring(extIndex + 1);
    }
}
