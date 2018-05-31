package com.wuyou.merchant;

import com.wuyou.merchant.util.EncodeUtil;

import org.junit.Test;

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
    }
    @Test
    public void aa() throws Exception {
        assertEquals(4, 2 + 2);
        String a = EncodeUtil.get3DES("aaa","1AE48E613CD877D041A21E6F383C7FE682F6C7EC");
        System.out.println(a);
    }
}