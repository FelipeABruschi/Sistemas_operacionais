import java.util.concurrent.Semaphore;

public class ContadorSemaforo {

    // Constantes
    static final int NUM_THREADS = 100;
    static final int NUM_STEPS = 100000;

    // Variável compartilhada
    static int sum = 0;

    // Semáforo binário (1 thread por vez)
    static Semaphore s = new Semaphore(1);

    // Classe da thread
    static class Worker extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < NUM_STEPS; i++) {
                try {
                    // --- Entrada na Seção Crítica ---
                    s.acquire();
                    // --- Seção Crítica ---
                    sum += 1;
                    if (sum % 100000 == 0) {
                        System.out.println("Sum: " + sum);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // --- Saída da Seção Crítica ---
                    s.release();
                }
            }
        }
    }

    public static void main(String[] args) {

        Thread[] threads = new Thread[NUM_THREADS];
        long expected = (long) NUM_THREADS * NUM_STEPS;

        // Criação das threads
        for (int i = 0; i < NUM_THREADS; i++) {

            threads[i] = new Worker();
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
        System.out.println("\n--- Resultado: Semáforo Java ---");
        System.out.println("Valor esperado: " + expected);
        System.out.println("Valor obtido:   " + sum);
        System.out.println("Diferença:      " + (expected - sum));
    }
}