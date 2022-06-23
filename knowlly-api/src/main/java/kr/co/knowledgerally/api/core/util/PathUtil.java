package kr.co.knowledgerally.api.core.util;

public class PathUtil {
    private PathUtil() { }

    public static String replaceWindowPathToLinuxPath(String path) {
        return path.replaceAll("\\\\", "/");
    }
}
