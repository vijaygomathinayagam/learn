package com.learn.concurrency;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LatchWorker implements Callable<String> {
  private static Random rand = new Random();
  private CountDownLatch latch;

  public LatchWorker(final CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public String call() throws Exception {
    Thread.sleep(rand.nextInt(20) * 100);
    System.out.println("completed one execution");
    latch.countDown();
    return null;
  }
}

public class LatchTest {
  public static void main(String args[]) {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    CountDownLatch latch = new CountDownLatch(15);

    for (int i = 0; i < 15; i++) {
      executor.submit(new LatchWorker(latch));
    }
    executor.shutdown();
    System.out.println("All Threads are created.");

    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("All threads completed execution");
  }
}
