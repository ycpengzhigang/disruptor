package com.lmax.disruptor.study.handle;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterTest {
	public static void main(String[] args) {
		AtomicReferenceFieldUpdater<Dog,String> updater = AtomicReferenceFieldUpdater.newUpdater(Dog.class, String.class, "name");
	    Dog dog = new Dog();
	    updater.compareAndSet(dog,dog.name, "test");
	    System.out.println(dog.name);
	}
}

class Dog {
	public volatile String name = "dog1";
}
