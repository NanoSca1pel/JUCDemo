package com.juc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lhtao
 * 原子引用
 * @date 2019-04-22 15:19
 */

@Getter
@ToString
@AllArgsConstructor
class User{

    String userName;
    int age;
}

public class AtomicReferenceDemo {

    public static void main(String[] args){

        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        AtomicReference<User> atomicReference = new AtomicReference<User>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t " + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t " + atomicReference.get().toString());

    }
}
