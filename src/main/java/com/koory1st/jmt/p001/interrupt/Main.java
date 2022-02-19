package com.koory1st.jmt.p001.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        log.info("主线程启动");

        MyThread myThread = new MyThread();

        myThread.start();

        TimeUnit.SECONDS.sleep(1L);

        myThread.stop();
    }

}

@Slf4j
class MyThread {
    Thread thread;

    public void start() {
        thread = new Thread(() -> {
            Thread thread = Thread.currentThread();
            while (true) {
                if (thread.isInterrupted()) {
                    log.info("线程被终止处理");
                    break;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                    log.info("线程被继续处理");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("线程被打断");
                    thread.interrupt(); // 打断标记会被修改，需要重置打断标记
                }
            }
        });

        thread.start();
    }

    public void stop() {
        log.info("打断");
        thread.interrupt();
    }

}