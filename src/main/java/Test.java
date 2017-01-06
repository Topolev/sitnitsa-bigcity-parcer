import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
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


        String str = "http://www.buketik.by/cat/alstromeriya_43";
        Pattern p = Pattern.compile("^.*" + "http://www.buketik.by/");
        Matcher m = p.matcher(str);
        if (m.find()){
            System.out.println(str.substring(m.start(), m.end()));
        }


    }
}
