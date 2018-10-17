package com.wuyou.merchant;

import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.EncodeUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Date date = new Date(0);
        System.out.println(date.toString());
    }

    @Test
    public void aa() throws Exception {
        assertEquals(4, 2 + 2);
        String aa = Integer.toBinaryString(162);
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < 8 - aa.length(); j++) {
            result.append("0");
        }
        result.append(aa);
        System.out.println(result);
//        int[] aaa = {1};
//        if(aaa.length==1&&"1".equals(result.charAt(0))){
//            return false;
//        }
//        System.out.println("ddd"+"1".equals("123".substring(0,1)));

//        int[] data = {235,140,4};
//        int boo = 0;
//        int bytes = 0;
//        for (int i = 0; i < data.length; i++) {
//            String aa = Integer.toBinaryString(data[i]);
//            StringBuilder result = new StringBuilder();
//            for (int j = 0; j < 8 - aa.length(); j++) {
//                result.append("0");
//            }
//            result.append(aa);
//
//            if (i == 0) {
//                if(data.length==1&&"1".equals(result.substring(0,1))){
//                    boo = 1;
//                }
//                if ("0".equals(result.substring(0,1))) {
//                    boo = 2;
//                } else {
//                    bytes = result.indexOf("0")-1;
//                }
//            } else if (i < bytes) {
//                if (!("1".equals(result.substring(0,1)) && "0".equals(result.substring(1,2)))) {
//                    boo = 1;
//                }
//            }
//        }
//        boo = 2;
//        System.out.println(boo);

    }

}

