import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vladimir on 18.12.2016.
 */
public class Test {

    public static void main(String[] args) {
        /*String test = "1.26 руб.".replaceAll("[^\\d.]+|\\.(?!\\d)", "");
        System.out.println(test);
        Float t = Float.parseFloat(test);
        System.out.println(t);*/


       /* String str = "text/html; charset=Windows-1251";
        /*Pattern p = Pattern.compile(".+charset\\s*=\\s*");
        Matcher m = p.matcher(str);
        if (m.find()){
            System.out.println(str.substring(m.start(), m.end()));


        }*/

       /* List first = new ArrayList();
        Object obj = new Object();


        String newStr = str.replaceFirst(".+charset\\s*=\\s*", "");
        System.out.println(newStr);
*/
      /* System.out.println(StringUtils.isBlank(" "));*/

/*
        String str = "http://www.buketik.by/cat/alstromeriya_43";
        Pattern p = Pattern.compile("^.*" + "http://www.buketik.by/");
        Matcher m = p.matcher(str);
        if (m.find()){
            System.out.println(str.substring(m.start(), m.end()));
        }*/



       /* String str = "    10.5коп";


        long result = 0;

        int start = skipNotDigets(str, 0);
        int finish = skipDigetsOnly(str,start);
        String integerPart = str.substring(start, finish);

        System.out.println(integerPart);
        start = skipNotDigets(str, finish);
        finish = skipDigetsOnly(str,start);

        if (start != finish){
            String fractionalPart = str.substring(start, finish);
            result = result + Long.valueOf(integerPart)*10000 + Long.valueOf(fractionalPart)*100;
        } else{
            result = result + Long.valueOf(integerPart)*10000;
        }
        System.out.println(result);*/

        String str = "10 руб 5 ру";

        /*long result ;

        int start = skipNotDigets(str, 0);
        int finish = skipDigetsOnly(str,start);
        String integerPart = str.substring(start, finish);

        start = skipNotDigets(str, finish);

        String delimeter = str.substring(finish, start).trim();
        boolean isFractionalNumber = delimeter.equals(".") || delimeter.equals(",");

        finish = skipDigetsOnly(str,start);

        if (start != finish){
            String fractionalPart = str.substring(start, finish);
            if (isFractionalNumber && fractionalPart.length()==1) fractionalPart = fractionalPart + "0";
            result = Long.valueOf(integerPart)*10000 + Long.valueOf(fractionalPart)*100;
        } else{
            result = Long.valueOf(integerPart)*10000;
        }
        System.out.println(result);*/


        String pattern1 = "test";
        String pattern2 = "test";

        String text = "   background-image  :       url     (     \"https://static.tildacdn.com/tild3136-3232-4639-b031-323736636361/34.jpg\");";


        String regexString = "background-image\\s*:\\s*url\\s*[(]\\s*['\"](.*)['\"]";
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String textInBetween = matcher.group(1);
            System.out.println(textInBetween);
            // Since (.*?) is capturing group 1
            // You can insert match into a List/Collection here
        }






    }

    private static Set<Character> digits = new HashSet<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));
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
