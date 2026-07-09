import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProdutorConsumidor {

    static final int BUFFER_SIZE = 5;
    static final int TOTAL_ITENS = 15;

    static int[] buffer = new int[BUFFER_SIZE];

    static int indexProd = 0;
    static int indexCons = 0;

    // Controla vagas vazias
    static Semaphore semVagas = new Semaphore(BUFFER_SIZE);

    // Controla itens disponíveis
    static Semaphore semItens = new Semaphore(0);

    // Protege região crítica
    static Semaphore semaforo = new Semaphore(1);

    static Random random = new Random();

    // ================= PRODUTOR =================

    static class Produtor extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < TOTAL_ITENS; i++) {

                int item = random.nextInt(100);

                try {

                    // Espera vaga livre
                    semVagas.acquire();

                    // Entra na seção crítica
                    semaforo.acquire();

                    // Produz item
                    buffer[indexProd] = item;

                    System.out.println(
                            "[PRODUTOR] Produziu: "
                                    + item
                                    + " na posição "
                                    + indexProd
                    );

                    // Buffer circular
                    indexProd = (indexProd + 1) % BUFFER_SIZE;

                    // Sai da seção crítica
                    semaforo.release();

                    // Informa que há item disponível
                    semItens.release();

                    // Simula tempo de produção
                    Thread.sleep(random.nextInt(200));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ================= CONSUMIDOR =================

    static class Consumidor extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < TOTAL_ITENS; i++) {

                try {

                    // Espera existir item
                    semItens.acquire();

                    // Entra na seção crítica
                    semaforo.acquire();

                    // Consome item
                    int item = buffer[indexCons];

                    System.out.println(
                            "  [CONSUMIDOR] Consumiu: "
                                    + item
                                    + " da posição "
                                    + indexCons
                    );

                    // Buffer circular
                    indexCons = (indexCons + 1) % BUFFER_SIZE;

                    // Sai da seção crítica
                    semaforo.release();

                    // Libera vaga
                    semVagas.release();

                    // Consumidor mais lento
                    Thread.sleep(random.nextInt(500));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        Produtor produtor = new Produtor();
        Consumidor consumidor = new Consumidor();

        produtor.start();
        consumidor.start();

        try {

            produtor.join();
            consumidor.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nProcessamento concluído com sucesso.");
    }
}