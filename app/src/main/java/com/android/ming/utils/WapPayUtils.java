package com.android.ming.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by YO on 2016/9/3.
 */
public class WapPayUtils {
    public  static String generateTraceno() {
        return Long.toString(new Date().getTime());
    }
    public  static String signature(Map<String, String> param, String keyValue)
            throws Exception {
        Set<String> set = param.keySet();
        List<String> keys = new ArrayList<String>(set);
        Collections.sort(keys);
        boolean start = true;
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = param.get(key);
            if (value != null && !value.trim().equals("")
                    && !"signature".equalsIgnoreCase(key)) {
                if (!start) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
                start = false;
            }
        }
        String CHARSET = "GBK";
        sb.append("&" + keyValue);
        String src = sb.toString();
        System.out.println("签名数据:" + src);
        String result= new String(Hex.encodeHex(DigestUtils.md5(src.getBytes(CHARSET))));
//		String result = DigestUtils.md5Hex(src.getBytes(CHARSET)).toUpperCase();
        System.out.println("签名结果:" + result);
        return result;
    }

}
