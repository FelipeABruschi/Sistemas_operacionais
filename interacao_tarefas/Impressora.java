import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Impressora extends Thread {

    // Mutex da impressora
    static Lock travaImpressora = new ReentrantLock();

    String nomePc;

    public Impressora(String nomePc) {
        this.nomePc = nomePc;
    }

    @Override
    public void run() {

        // Tenta pegar a "chave" da impressora
        travaImpressora.lock();

        try {

            // Seção crítica
            System.out.println(
                    "Computador ["
                            + nomePc
                            + "] começou a imprimir..."
            );

            for (int i = 1; i <= 3; i++) {

                System.out.println(
                        "Computador ["
                                + nomePc
                                + "]: Imprimindo página "
                                + i
                                + "..."
                );

                // Simula tempo de impressão
                Thread.sleep(1000);
            }

            System.out.println(
                    "Computador ["
                            + nomePc
                            + "] terminou e liberou a impressora!\n"
            );

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {

            // Libera a "chave"
            travaImpressora.unlock();
        }
    }

    public static void main(String[] args) {

        Impressora pc1 = new Impressora("A");
        Impressora pc2 = new Impressora("B");

        pc1.start();
        pc2.start();

        try {

            pc1.join();
            pc2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}