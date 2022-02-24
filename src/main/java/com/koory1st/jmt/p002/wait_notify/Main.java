package com.koory1st.jmt.p002.wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

class Lock {

}

@Slf4j
public class Main {
    static Integer conditionInt = 0;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new Lock();


        // t1线程需要等到条件满足才能继续向下执行
        new Thread(() -> {
            synchronized (lock) {
                while (conditionInt == 0) {
                    log.info("t1 条件不满足，进入waitSet等待");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 满足条件了，可以干活了
                log.info("t1 条件满足了，开始干活");
            }
        }, "t1")
                .start();

        // t2线程在3秒后改变条件
        TimeUnit.SECONDS.sleep(3L);
        new Thread(() -> {
            synchronized (lock) {
                log.info("t2 改变条件，通知t1");
                conditionInt = 1;
                // 使用notifyAll 让所有的waitSet的线程不再等待
                lock.notifyAll();
            }
        }, "t2")
                .start();
    }
}
