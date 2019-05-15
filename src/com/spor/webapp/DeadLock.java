package com.spor.webapp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        deadlock(lock1, lock2);
        deadlock(lock2, lock1);
    }

    private static void deadlock(Lock lock1, Lock lock2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " working");
            lock1.lock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " waiting");
            lock2.lock();
            try {
                System.out.println("doSmth");
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }).start();
    }
}
