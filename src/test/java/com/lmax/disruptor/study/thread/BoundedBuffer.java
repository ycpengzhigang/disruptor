package com.lmax.disruptor.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    final Lock lock = new ReentrantLock();

    final Condition notFull = lock.newCondition();

    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];

    int putptr, takeptr, count;// 计数器

    /**
     * 生产者生产数据，往数据里写数据
     * 
     * @param x
     * @throws InterruptedException
     */
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                // 数组已满，没有空间时，挂起等待，直到数组“非满”（notFull）
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            // 因为放入了一个数据，数组肯定不是空的了
            // 此时唤醒等待这notEmpty条件上的线程
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                // 数组是空的，没有数据可拿时，挂起等待，直到数组非空（notEmpty）
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            // 因为拿出了一个数据，数组肯定不是满的了
            // 此时唤醒等待这notFull条件上的线程
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

}
