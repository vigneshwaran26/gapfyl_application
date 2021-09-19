package com.gapfyl.util;

import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 13/04/21
 **/

public class Common {

    public static Date getCurrentUTCDate() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    public static String generateToken(String str) {
        return MD5.getMD5(str + new Date().getTime());
    }

    public static boolean isImage(String contentType) { return contentType.contains("image"); }

    public static boolean isNullOrEmpty(Map map) { return map == null || map.isEmpty(); }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNull(Object obj) { return obj == null; }
}
