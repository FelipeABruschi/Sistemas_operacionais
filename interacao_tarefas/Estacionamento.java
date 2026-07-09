import java.util.concurrent.Semaphore;
import java.util.Random;

public class Estacionamento extends Thread {

    static final int TOTAL_VAGAS = 5;
    static final int TOTAL_CARROS = 10;

    // Semáforo controlando as vagas
    static Semaphore vagas = new Semaphore(TOTAL_VAGAS);

    static Random random = new Random();

    int id;

    public Estacionamento(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        System.out.println("Carro " + id + " chegou e está procurando vaga...");

        try {

            // Se não houver vaga, bloqueia aqui
            vagas.acquire();

            System.out.println("--> Carro " + id + " ENTROU.");

            // Tempo dentro do estacionamento
            Thread.sleep((5 + random.nextInt(6)) * 1000);

            System.out.println("<-- Carro " + id + " SAIU. Liberando vaga...");

            // Libera a vaga
            vagas.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Estacionamento[] carros = new Estacionamento[TOTAL_CARROS];

        for (int i = 0; i < TOTAL_CARROS; i++) {

            carros[i] = new Estacionamento(i + 1);

            carros[i].start();

            try {

                // Chegada gradual dos carros
                Thread.sleep(200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Espera todos terminarem
        for (int i = 0; i < TOTAL_CARROS; i++) {

            try {
                carros[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nEstacionamento encerrado.");
    }
}