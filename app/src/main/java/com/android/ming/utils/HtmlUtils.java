package com.android.ming.utils;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by YO on 2016/9/12.
 */
public class HtmlUtils {

    /**
     * 使用URLConnection根据url读取html源代码
     *
     * @param urlString
     * @return
     */
    public static String getHtmlString(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection ucon = url.openConnection();
            InputStream instr = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(instr);
            ByteArrayBuffer bau = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                bau.append((byte) current);
            }
            return EncodingUtils.getString(bau.toByteArray(), "utf_8");
        } catch (Exception e) {
            return "";
        }
    }
}
