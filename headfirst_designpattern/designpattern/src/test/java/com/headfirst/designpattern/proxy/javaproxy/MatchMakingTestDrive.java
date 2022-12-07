package com.headfirst.designpattern.proxy.javaproxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.HashMap;

public class MatchMakingTestDrive {
    HashMap<String, Person> datingDB = new HashMap<>();

    @BeforeEach
    public void beforeEach() {
        Person joe = new PersonImpl();
        joe.setName("Joe Javabean");
        joe.setInterests("cars, computers, music");
        joe.setGeekRating(7);
        datingDB.put(joe.getName(), joe);
    }

    @Test
    public void 동적_프록시_테스트() {
        Person joe = getPersonFrmDatabase("Joe Javabean");
        Person ownerProxy = getOwnerProxy(joe);

        System.out.println("Name is " + ownerProxy.getName());

        ownerProxy.setInterests("bowling, Go");

        System.out.println("Interests set from owner proxy");

        try {
            ownerProxy.setGeekRating(10);
        } catch(Exception e) {
            System.out.println("Can't set rating from owner proxy");
        }
        System.out.println("Rating is " + ownerProxy.getGeekRating());


    }

    private Person getOwnerProxy(Person person) {
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person)
        );
    }

    private Person getPersonFrmDatabase(String name) {
        return (Person)datingDB.get(name);
    }
}
