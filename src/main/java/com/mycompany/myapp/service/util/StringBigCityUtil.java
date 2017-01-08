package com.mycompany.myapp.service.util;

import org.apache.commons.lang3.StringUtils;
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

    private static Set<Character> digits = new HashSet<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));

    public static final String FORWARD_SLASH = "/";

    public static String deleteSlashFromBeginAndEnd(String url){
        StringBuilder str = new StringBuilder(url == null? "" : url.trim());
        if (str.indexOf(FORWARD_SLASH) == 0){
            str.deleteCharAt(0);
        }
        if (str.lastIndexOf(FORWARD_SLASH) == (str.length()-1)){
            str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    public static String deleteHtmlTags(String text){
        return text.replaceAll("<[a-zA-Z\\s/]+>", "");
    }

    public static String deletePrefix(String prefix, String text){
        if(StringUtils.isNotBlank(prefix)&& text.contains(prefix)){
            return text.substring(text.indexOf(prefix) + prefix.length(), text.length() - 1);
        }
        return text;
    }

    public static String deleteRootUrl(String rootUrl, String currentUrl){
        Pattern p = Pattern.compile("^.*" + rootUrl);
        Matcher m = p.matcher(currentUrl);
        if (m.find()){
            currentUrl = currentUrl.substring(m.end());
        }
        return currentUrl;
    }

    public static String deleteExtension(String text){
        if (text.contains(".")){
            return text.substring(0, text.indexOf(".")-1);
        }
        return text;
    }


    public static Long convertStrToPrice(String str){
        if (StringUtils.isBlank(str)){
            return null;
        }
        long result;

        int start = skipNotDigets(str, 0);
        int finish = skipDigetsOnly(str,start);
        String integerPart = str.substring(start, finish);

        start = skipNotDigets(str, finish);

        String delimeter = str.substring(finish, start).trim();
        boolean isFractionalNumber = delimeter.equals(".") || delimeter.equals(",");

        finish = skipDigetsOnly(str,start);

        LOG.debug("Convert str {}", str);
        if (start != finish){
            String fractionalPart = str.substring(start, finish);
            if (isFractionalNumber && fractionalPart.length()==1) fractionalPart = fractionalPart + "0";
            result = Long.valueOf(integerPart)*10000 + Long.valueOf(fractionalPart)*100;
        } else{
            result = Long.valueOf(integerPart)*10000;
        }
        return result;
    }

    private static int skipNotDigets(String str, int index){
        while(index < str.length() && !digits.contains(str.charAt(index))){
            index++;
        }
        return index;
    }

    private static int skipDigetsOnly(String str, int index){
        while(index < str.length() && digits.contains(str.charAt(index))){
            index++;
        }
        return index;
    }

}
