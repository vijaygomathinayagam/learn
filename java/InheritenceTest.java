import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class BankAccount {
    private int amount;

    public synchronized void deposit(int depositAmount) {
        amount += depositAmount;
    }

    public int getAmount() { return amount; }
}

class Worker implements Runnable {
    private BankAccount bankAccount;

    Worker(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void run() {
        bankAccount.deposit(100);
    }
}

public class InheritenceTest {

    static Logger logger = Logger.getLogger("inheritencetest");
    
    public static void main(String[] args) throws InterruptedException {
        Object a = new InheritenceTest();
        logger.setLevel(Level.ALL);
        logger.info(a.toString());
        if (a instanceof InheritenceTest)
        logger.info("yes");

        BankAccount bankAccount = new BankAccount();
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            es.submit(new Worker(bankAccount));
        }
        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
        logger.info(String.format("balance: %d", bankAccount.getAmount()));
    }

    public String toString() {
        logger.entering(this.getClass().getSimpleName(), "toString");
        return "hello";
    }
}
