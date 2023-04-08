package com.learn.concurrency;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Callable<String> {
  private static Random rand = new Random();
  private CyclicBarrier barrier;

  public Worker(final CyclicBarrier barrier) {
    this.barrier = barrier;
  }

  @Override
  public String call() throws Exception {
    Thread.sleep(100 * rand.nextInt(20));
    System.out.println(Thread.currentThread().getName() + " execution.");
    barrier.await();
    return null;
  }
}

public class BarrierTest {
  public static void main(String args[]) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    CyclicBarrier barrier = new CyclicBarrier(10, () -> System.out.println("completed 10"));

    for (int i = 0; i < 100; i++) {
      executor.submit(new Worker(barrier));
    }

    executor.shutdown();
  }
}
