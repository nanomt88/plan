package com.nanomt88.demo;


import java.util.Arrays;


/**
 *  快速排序示例：
 *   选取一个数作为参照，通常是数组首元素。数组两个指针分别从头尾两段向中间移动，
 *   若右侧数小于参照数，左侧大于参照数；则将左右两侧数字交换，使小于参照数排左边、大于参照数排右边。
 *   最后将参照数与右侧小于它的数交换。将左右两侧的数，分别递归进行同样规则的排序，最后得到有序数组
 *
 *
 * @author nanomt88@gmail.com
 * @date 19.7.2 8:10
 * @updateLog:
 *       update by nanomt88@gmail.com, 19.7.2 8:10
 */
public class QuickSort {



    public static void main(String[] args) {
        int[] array = {8, 13, 2, 7, 9, 3, 14, 11, 4, 5, 12, 6};
        fastSort(array, 0, array.length - 1);
    }
    /**
     * 快速排序算法
     * @param array
     * @param left
     * @param right
     */
    public static void fastSort(int[] array, int left, int right) {
        //第一位
        int start = left;
        //最后一位
        int end = right;
        //参照位
        int key = array[left];

        for (; right > left; right--) {
            if (array[right] > key) {
                continue;
            }
            for (; left < right; left++) {
                //将参照位右边比参照位小的
                //与参照位左边比参照位大的  进行互换
                if (array[left] > key) {
                    int tmp = array[right];
                    array[right] = array[left];
                    array[left] = tmp;
                    System.out.println(Arrays.toString(array));
                    break;
                }
            }
        }
        // 将参照位移动到中间来，保持左侧的比参照位小；右侧的比参照位大
        int tmp = array[left];
        array[left] = key;
        array[start] = tmp;
        System.out.println(Arrays.toString(array));
        // 将左侧比参照位小的数组 进行递归排序
        if (left-1> start) {
            fastSort(array, start, left-1);
        }
        //将右侧壁参照位大的数组 进行递归排序
        if (end-1> left) {
            fastSort(array, left+1, end);
        }
    }
}
