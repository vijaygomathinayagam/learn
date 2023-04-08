package com.learn.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CASingTest {
  public static void main(String args[]) {
    AtomicInteger counter = new AtomicInteger(0);

    class CasingIncrementWorker implements Callable<Integer> {
      public Integer call() {
        for (int i = 0; i < 100; i++) { counter.incrementAndGet(); }
        return counter.get();
      }
    }

    class CasingDecrementWorker implements Callable<Integer> {
      public Integer call() {
        for (int i = 0; i < 100; i++) { counter.decrementAndGet(); }
        return counter.get();
      }
    }

    ExecutorService executor = Executors.newFixedThreadPool(6);

    for (int i = 0; i < 10; i++) {
      executor.submit(new CasingIncrementWorker());
      executor.submit(new CasingDecrementWorker());
    }

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    executor.shutdown();
    System.out.println("Counter: " + counter.get());
  }
}
