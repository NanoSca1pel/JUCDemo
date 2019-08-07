package com.juc;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lhtao
 * @date 2019-04-25 14:01
 */
@NoArgsConstructor
@Data
public class Person {

    private Integer id;

    private String personName;

    public Person(String personName){

        this.personName = personName;
    }
}
