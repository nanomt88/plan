package com.nanomt88.study.designpattern.proxy;

/**
 * 租客
 *
 * @author nanomt88
 * @create 2018-03-10 20:43
 **/
public interface Renter {

    /**
     * 租房子
     * @param house 推荐的房子
     * @return
     */
    boolean findHouse(House house);

    /**
     * 租客名称
     * @return
     */
    String getName();

    /**
     * 预期租房要求
     * @return
     */
    House getExpect();
}
