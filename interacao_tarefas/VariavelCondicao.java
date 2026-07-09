import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VariavelCondicao extends Thread {

    // Mutex
    static Lock lock = new ReentrantLock();

    // Variável de condição
    static Condition cond = lock.newCondition();

    // Condição real
    static int pronto = 0;

    int id;

    public VariavelCondicao(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println(
                "Thread " + id
                        + ": Cheguei e estou esperando o sinal..."
        );

        lock.lock();

        try {

            while (pronto == 0) {

                // await faz:
                // 1. libera o lock
                // 2. coloca thread para dormir
                // 3. ao acordar, readquire o lock
                cond.await();
            }

            System.out.println(
                    "Thread " + id
                            + ": Recebi o sinal! Trabalhando..."
            );

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {

            lock.unlock();
        }
    }

    public static void main(String[] args) {

        VariavelCondicao t1 =
                new VariavelCondicao(1);

        VariavelCondicao t2 =
                new VariavelCondicao(2);

        t1.start();
        t2.start();

        try {

            // Simula tempo do "chefe"
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Main: Preparando tudo... agora vou dar o sinal!"
        );

        lock.lock();

        try {

            // Altera condição
            pronto = 1;

            // Acorda TODAS as threads
            cond.signalAll();

            // signal() acordaria apenas uma

        } finally {

            lock.unlock();
        }

        try {

            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main: Finalizado.");
    }
}