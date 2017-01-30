package com.mycompany.myapp.service.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vladimir on 28.12.2016.
 */
public class StringBigCityUtil {
    public static final Logger LOG = LoggerFactory.getLogger(StringBigCityUtil.class);


    public static final String FORWARD_SLASH = "/";

    public static String deleteSlashFromBegin(String url) {
        StringBuilder str = new StringBuilder(url == null ? "" : url.trim());
        if (str.indexOf(FORWARD_SLASH) == 0) {
            str.deleteCharAt(0);
        }
        return str.toString();
    }

/*
    public static String deleteSlashFromBeginAndEnd(String url){
        StringBuilder str = new StringBuilder(url == null? "" : url.trim());
        if (str.indexOf(FORWARD_SLASH) == 0){
            str.deleteCharAt(0);
        }
        if ((str.lastIndexOf(FORWARD_SLASH) == (str.length()-1))&&(str.length()!=0)){
            str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }*/

    public static String deleteHtmlTags(String text) {
        return text.replaceAll("<[a-zA-Z\\s/]+>", "");
    }

    public static String deletePrefix(String prefix, String text) {
        if (StringUtils.isNotBlank(prefix) && text.contains(prefix)) {
            return text.substring(text.indexOf(prefix) + prefix.length(), text.length() - 1);
        }
        return text;
    }

    public static String addRootRoolIfIsNotExsite(String rootUrl, String currentUrl) {
        if (currentUrl.indexOf("http") == -1) {
            currentUrl = deleteSlashFromBegin(currentUrl);
            currentUrl = rootUrl + currentUrl;
        }
        return currentUrl;
    }


    public static String deleteRootUrl(String rootUrl, String currentUrl) {
        Pattern p = Pattern.compile("^.*" + rootUrl);
        Matcher m = p.matcher(currentUrl);
        if (m.find()) {
            currentUrl = currentUrl.substring(m.end());
        }

        rootUrl = rootUrl.replace("http", "https");
        p = Pattern.compile("^.*" + rootUrl);
        m = p.matcher(currentUrl);
        if (m.find()) {
            currentUrl = currentUrl.substring(m.end());
        }

        return currentUrl;
    }

    public static String deleteExtension(String text) {
        if (text.contains(".")) {
            return text.substring(0, text.indexOf(".") - 1);
        }
        return text;
    }


    private static Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static void main(String[] arg) {
        System.out.println("1 660 руб. Result: " + convertStrToPrice("1 660 руб."));
        System.out.println("1 335.15 руб. Result: " + convertStrToPrice("1 335.15 руб."));
        System.out.println("396.25 руб. Result: " + convertStrToPrice("396.25 руб."));
        System.out.println("560.00 руб.. Result: " + convertStrToPrice("560.00 руб."));
        System.out.println("90.00 руб. Result: " + convertStrToPrice("90.00 руб."));
        System.out.println("1 035.00 Result: " + convertStrToPrice("1 035.00"));
        System.out.println("320.00 Result: " + convertStrToPrice("320.00"));
        System.out.println("42.00 Result: " + convertStrToPrice("42.00"));
        System.out.println("42.50 руб Result: " + convertStrToPrice("42.50 руб"));
        System.out.println("42.05 руб Result: " + convertStrToPrice("42.05 руб"));
        System.out.println("19.90 Result: " + convertStrToPrice("19.90"));
        System.out.println("2.65 BYN Result: " + convertStrToPrice("2.65 BYN"));
        System.out.println("2.60руб  Result: " + convertStrToPrice("2.60руб "));
        System.out.println("134 р. 90 к. Result: " + convertStrToPrice("134 р. 90 к."));
        System.out.println("41 бел.руб.. Result: " + convertStrToPrice("41 бел.руб."));
        System.out.println("103,95 Br.. Result: " + convertStrToPrice("103,95 Br"));
        System.out.println("11,09руб... Result: " + convertStrToPrice("11,09руб."));
        System.out.println("11,9руб... Result: " + convertStrToPrice("11,9руб."));


    }


    public static Long convertStrToPrice(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        long result;


        int index = skipNotDigets(str, 0);

        StringBuffer buffer = new StringBuffer();
        while (index < str.length() && (digits.contains(str.charAt(index)) || str.charAt(index) == ' ')) {
            if (str.charAt(index) != ' ') {
                buffer.append(str.charAt(index));
            }
            index++;
        }
        Long integerPart = Long.valueOf(buffer.toString()) * 10000;


        index = skipNotDigets(str, index);

        if (index != str.length()) {
            buffer = new StringBuffer();
            while (index < str.length() && digits.contains(str.charAt(index))) {
                buffer.append(str.charAt(index));
                index++;
            }
            return integerPart + Long.valueOf(buffer.toString())*100;
        } else {
            return integerPart;
        }

    }



    private static int skipNotDigets(String str, int index) {
        while (index < str.length() && !digits.contains(str.charAt(index))) {
            index++;
        }
        return index;
    }

    private static int skipDigetsOnly(String str, int index) {
        while (index < str.length() && digits.contains(str.charAt(index))) {
            index++;
        }
        return index;
    }

}
