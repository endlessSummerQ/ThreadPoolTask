import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTask {
    public static void main(String[] args) {
        Object lock = new Object();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        Thread.sleep(1500);
                        System.out.println(Thread.currentThread().getName() + " waiting");
                        lock.wait();
                        Thread.sleep(1500);
                        System.out.println(Thread.currentThread().getName() + " finished");
                        Thread.sleep(1500);
                        System.out.println("Спасибо! До свидания -)");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(1500);
                        System.out.println(Thread.currentThread().getName() + " started");
                        Scanner scanner = new Scanner(System.in);
                        Thread.sleep(1500);
                        System.out.println("Введите что-нибудь для продолжения программы");
                        scanner.nextLine();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + " finished");
                }
            }
        });

        executorService.shutdown();
    }
}