import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContadorMutex extends Thread {

    static final int NUM_THREADS = 100;
    static final int NUM_STEPS = 100000;

    static int sum = 0;

    // Mutex
    static Lock lock = new ReentrantLock();

    int id;

    public ContadorMutex(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        for (int i = 0; i < NUM_STEPS; i++) {

            // Entrada na seção crítica
            lock.lock();

            try {

                // Seção crítica
                sum += 1;

                if (sum % 100000 == 0) {

                    System.out.println(
                            "Thread "
                                    + id
                                    + " incrementou. Sum: "
                                    + sum
                    );
                }

            } finally {

                // Saída da seção crítica
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {

        ContadorMutex[] threads =
                new ContadorMutex[NUM_THREADS];

        long expected =
                (long) NUM_THREADS * NUM_STEPS;

        // Criação das threads
        for (int i = 0; i < NUM_THREADS; i++) {

            threads[i] = new ContadorMutex(i);

            threads[i].start();
        }

        // Espera todas terminarem
        for (int i = 0; i < NUM_THREADS; i++) {

            try {

                threads[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Resultado final
        System.out.println("\n--- Resultado: Mutex Java ---");

        System.out.println(
                "Valor esperado: " + expected
        );

        System.out.println(
                "Valor obtido:   " + sum
        );

        System.out.println(
                "Diferença:      " + (expected - sum)
        );
    }
}