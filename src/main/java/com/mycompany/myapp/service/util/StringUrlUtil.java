package com.mycompany.myapp.service.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Vladimir on 28.12.2016.
 */
public class StringUrlUtil {

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

    public static String deleteExtension(String text){
        if (text.contains(".")){
            return text.substring(0, text.indexOf(".")-1);
        }
        return text;
    }

}
