package com.wuyou.merchant;

import com.wuyou.merchant.util.CommonUtil;
import com.wuyou.merchant.util.EncodeUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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
        int A[] = { 3,2,1,4};  // 针对计数排序设计的输入，每一个元素都在[0,100]上且有重复元素
        int n = A.length;
        CommonUtil.CountingSort(A, n);
        System.out.println("计数排序结果：");
        for (int i = 0; i < n; i++)
        {
            System.out.println(A[i] + "");
        }
    }
}

