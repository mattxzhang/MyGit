package com.ucsmy.pos.utils;

import android.net.UrlQuerySanitizer;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by ucs_zhangjiaheng on 2017/3/1.
 */
public class StringUtil {

    /**
     * <p>
     * 判断字符串是否为null或空，这个的空包括多个空格 <br>
     * 比如：<br>
     * StringUtil.isBlank(null) --> true <br>
     * StringUtil.isBlank("")  --> true <br>
     * StringUtil.isBlank("  ") --> true <br>
     * StringUtil.isBlank("abc ") --> false <br>
     * StringUtil.isBlank("a bc") --> false
     * <p>
     *
     * @param str 字符串对象
     * @return 验证结果
     */
    public static boolean isBlank(String str) {
        return str == null || str.equals("") || str.trim().equals("");
    }

    public static String getStringOrDefault(String valueStr, String defaultStr) {
        if (isNotBlank(valueStr)) {
            return valueStr;
        } else {
            return defaultStr;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * <p>
     * 判断字符串是否为null或空 <br>
     * 比如：<br>
     * StringUtil.isBlank(null) --> true <br>
     * StringUtil.isBlank("")  --> true <br>
     * StringUtil.isBlank("  ") --> false <br>
     * StringUtil.isBlank("abc ") --> false <br>
     * StringUtil.isBlank("a bc") --> false
     * </p>
     *
     * @param str 字符串对象
     * @return 验证结果
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static int getIntOrDefault(String valueStr, int defaultInt) {
        if (isNotBlank(valueStr)) {
            return Integer.valueOf(valueStr);
        } else {
            return defaultInt;
        }
    }

    /**
     * 字符串纯数字判断
     *
     * @param str
     * @return
     */
    public static boolean isPuleNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String reg = "^\\d*";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static JSONObject getUrlJson(String url) {
        JSONObject json = null;
        if (!TextUtils.isEmpty(url)) {
            String jsonString = url.substring(url.indexOf("?") + 1);
            try {
                jsonString = URLDecoder.decode(jsonString, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                jsonString = "";
                e1.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(jsonString)) {
                    json = new JSONObject(jsonString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static String getDomainName(String url) {
        String domain = "";
        if (!TextUtils.isEmpty(url)) {
            try {
                URL u = new URL(url);
                domain = u.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return domain;

    }

    /**
     * 截取链接后面参数
     *
     * @param url
     * @param key
     * @return
     */
    public static String getUrlParms(String url, String key) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
        sanitizer.parseUrl(url);
        String value = sanitizer.getValue(key);
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        return "";
    }

    public static Set<Integer> stringToSet(String text) {
        Set<Integer> idSet = new HashSet<Integer>();
        if (!TextUtils.isEmpty(text)) {
            text = text.replace("[", "").trim();
            text = text.replace("]", "");
            text = text.replace(" ", "");
            if (TextUtils.isEmpty(text)) {
                return idSet;
            }
            String[] ids = text.split(",");
            for (int i = 0; i < ids.length; i++) {
                idSet.add(Integer.valueOf(ids[i].trim()));
            }
        }
        return idSet;
    }


    public static String getSpitString(String src, String spitsChar, String replace) throws Exception {
        String spitString = "";
        try {
            if (src.contains(spitsChar)) {
                spitString = src.split(spitsChar)[0];
            } else {
                spitString = replace;
            }
        } catch (Exception e) {
            throw e;
        }
        return spitString;
    }


    public static int[] getSpitString(String src, String spitsChar) throws Exception {
        int[] result = new int[3];
        String[] spitString;
        try {
            if (src.contains(spitsChar)) {
                spitString = src.split(spitsChar);
                for (int i = 0; i < spitString.length; i++) {
                    result[i] = Integer.parseInt(spitString[i]);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public static String starStrFormatChange(String value) {
        String result = value;
        try {
            result = result.substring(0, 3) + "****" + result.substring(7, result.length());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String hide4BitPhoneNumber(String phoneNumber) {
        String result = "";
        if (isNotBlank(phoneNumber)) {
            String before = phoneNumber.substring(0, 3);
            String after = phoneNumber.substring(7);
            result = before + "****" + after;
        }
        return result;
    }

    public static String phoneNumberBlank(String phoneNumber) {
        StringBuilder stringBuffer = new StringBuilder();
        if (isNotBlank(phoneNumber)) {
            stringBuffer.append(phoneNumber.substring(0, 3));
            stringBuffer.append(" ");
            stringBuffer.append(phoneNumber.substring(3, 7));
            stringBuffer.append(" ");
            stringBuffer.append(phoneNumber.substring(7));
        }
        return stringBuffer.toString();
    }

    public static String formatCount(int num) {
        String result = "";
        try {
            float tem = Float.valueOf(num);
            if (tem >= 10000) {
                result = String.valueOf((float) (Math.round(tem / 1000)) / 10) + "万";
            } else {
                result = String.valueOf(num);
            }
        } catch (NumberFormatException e) {
            return "";
        }
        return result;
    }

    //是否是网址
    public static boolean isHttpStr(String str) {
        boolean ret = false;
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        if (!TextUtils.isEmpty(str)) {
            ret = pattern.matcher(str).matches();
        }
        return ret;
    }
}
