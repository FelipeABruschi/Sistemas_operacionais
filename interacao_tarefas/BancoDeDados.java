import java.util.concurrent.Semaphore;

public class BancoDeDados extends Thread {

    static final int MAX_CONEXOES = 3;
    static final int TOTAL_THREADS = 10;

    // Semáforo controlando o número de conexões
    static Semaphore bancoDeDados = new Semaphore(MAX_CONEXOES);

    int id;

    public BancoDeDados(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("[Thread " + id + "] Tentando conectar ao banco...");

        try {

            // Tenta adquirir uma das vagas
            bancoDeDados.acquire();

            // --- Início da Seção Crítica ---
            System.out.println("  >>> [Thread " + id + "] CONECTADA. (Usando 1 de "
                    + MAX_CONEXOES + " vagas)");

            // Simula uso do banco
            Thread.sleep(2000);

            System.out.println("  <<< [Thread " + id + "] Desconectando e liberando vaga...");
            // --- Fim da Seção Crítica ---

            // Libera a vaga
            bancoDeDados.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        BancoDeDados[] threads = new BancoDeDados[TOTAL_THREADS];

        System.out.println("Iniciando simulação. Limite de conexões: "
                + MAX_CONEXOES + "\n");

        // Cria e inicia as threads
        for (int i = 0; i < TOTAL_THREADS; i++) {

            threads[i] = new BancoDeDados(i);

            threads[i].start();
        }

        // Espera todas terminarem
        for (int i = 0; i < TOTAL_THREADS; i++) {

            try {
                threads[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nSimulação finalizada.");
    }
}