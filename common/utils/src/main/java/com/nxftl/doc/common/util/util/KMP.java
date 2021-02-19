package com.nxftl.doc.common.util.util;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/19 9:01
 * @discription KMP
 */
public class KMP {

    private static int[] getNext(String sub) {

        int j = 1, k = 0;
        int[] next = new int[sub.length()];
        next[0] = -1;
        next[1] = 0;
        //
        while (j < sub.length() - 1) {
            if (sub.charAt(j) == sub.charAt(k)) {
                next[j + 1] = k + 1;
                j++;
                k++;
            } else if (k == 0) {
                next[j + 1] = 0;
                j++;
            } else {
                k = next[k];
            }

        }
        return next;
    }

    public static int kmp(String src, String sub) {
        // 首先生成模式串sub的next[j]
        int[] next = getNext(sub);
        int i = 0, j = 0, index = -1;
        while (i < src.length() && j < sub.length()) {
            if (src.charAt(i) == sub.charAt(j)) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = next[j];
            }
        }

        // 得到开始匹配的位置索引
        if (j == sub.length()) {
            index = i - sub.length();
        }
        return index;
    }


}
