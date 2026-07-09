import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContaMutex extends Thread {

    static int saldo = 100;

    // Mutex
    static Lock lock = new ReentrantLock();

    int valor;

    public ContaMutex(int valor) {
        this.valor = valor;
    }

    @Override
    public void run() {

        // Entrada na seção crítica
        lock.lock();

        try {

            int temp = saldo; // Passo 1: leitura

            // Força troca de contexto
            Thread.sleep(100);

            temp += valor; // Passo 2: soma

            saldo = temp; // Passo 3: escrita

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {

            // Saída da seção crítica
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ContaMutex t1 =
                new ContaMutex(50);

        ContaMutex t2 =
                new ContaMutex(30);

        t1.start();
        t2.start();

        try {

            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Saldo final esperado: 180"
        );

        System.out.println(
                "Saldo final obtido: " + saldo
        );
    }
}