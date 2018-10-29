package me.warriorg.thread.count_down_latch;

import java.util.concurrent.CountDownLatch;

public class WorkerRunnable implements Runnable {

    private CountDownLatch countDownLatch;
    private int i;

    public WorkerRunnable(CountDownLatch latch, int i) {
        this.countDownLatch = latch;
        this.i = i;
    }

    @Override
    public void run() {
        doWork();
        this.countDownLatch.countDown();
    }

    private void doWork() {
        System.out.println("当前的线程: " + i);
    }
}
