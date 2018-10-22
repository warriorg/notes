#### Java 多线程系列 CountDownLatch

一个或多个线程等待其他线程完成操作后在在执行



CountDownLatch通过一个计数器来实现，await方法阻塞直到 countDown() 调用计数器归零之后释放所有等待的线程，并且任何后续的await调用立即返回。这是一次性现象 - 计数无法重置。如果您需要重置计数的版本，请考虑使用CyclicBarrier。



```java

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
```



```bash
当前的线程: 7
当前的线程: 6
当前的线程: 2
当前的线程: 1
当前的线程: 9
当前的线程: 0
当前的线程: 5
当前的线程: 4
当前的线程: 8
当前的线程: 3
结束
```

