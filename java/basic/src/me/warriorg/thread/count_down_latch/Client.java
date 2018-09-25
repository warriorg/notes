package me.warriorg.thread.count_down_latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        final int n = 10;
        CountDownLatch doneSignal = new CountDownLatch(n);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < n; i++) {
            executor.execute(new WorkerRunnable(doneSignal, i));
        }

        doneSignal.await();
        System.out.println("结束");
    }
}
