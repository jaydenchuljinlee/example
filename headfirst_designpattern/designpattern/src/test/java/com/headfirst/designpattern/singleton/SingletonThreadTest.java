package com.headfirst.designpattern.singleton;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingletonThreadTest {

    @Test
    public void 일반_객체_생성_방식은_실패한다() {

        Set<ChocolateBoilerNormal> set = Collections.synchronizedSet(new HashSet<>());
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            list.add(new Thread(() -> {
                set.add(ChocolateBoilerNormal.getInstance());
            }));
        }

        for(Thread t: list) {
            t.start();
        }

        assertTrue(set.size() > 1);
    }

    @Test
    public void 더블_체킹_방식은_성공한다() {
        Set<ChocolateBoilerSingleton> set = Collections.synchronizedSet(new HashSet<>());
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(() -> {
                ChocolateBoilerSingleton cs = ChocolateBoilerSingleton.getInstance();

                set.add(cs);
            }));
        }

        for(Thread t: list) {
            t.start();
        }

        System.out.println(set);

        assertEquals(1, set.size());
    }

    @Test
    public void ENUM_방식은_성공한다() {
        Set<ChocolateBoilerEnum> set = Collections.synchronizedSet(new HashSet<>());
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(() -> {
                ChocolateBoilerEnum cs = ChocolateBoilerEnum.UNIQUE_INSTANCE;

                set.add(cs);
            }));
        }

        for(Thread t: list) {
            t.start();
        }

        System.out.println(set);

        assertEquals(1, set.size());
    }
}
