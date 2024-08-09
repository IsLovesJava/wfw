package cn.pkx.wfw.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String get() {
        return get(false);
    }

    public static String get(boolean withDash) {
        String s = UUID.randomUUID().toString();
        return withDash ? s : s.replace("-", "");
    }
}
