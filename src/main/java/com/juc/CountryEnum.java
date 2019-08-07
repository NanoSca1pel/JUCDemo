package com.juc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lhtao
 * 枚举可以做微型的数据库作用  可以多个参数
 * @date 2019-04-26 13:42
 */
@Getter
@AllArgsConstructor
public enum CountryEnum {

    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "韩"),
    FIVE(5, "赵"),
    SIX(6, "魏");

    private Integer retCode;

    private String retMessage;

    //private String retMessage2;

    public static CountryEnum forEach_CountryEnum(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum countryEnum : myArray) {
            if (index == countryEnum.getRetCode()) {
                return countryEnum;
            }
        }
        return null;
    }
}
