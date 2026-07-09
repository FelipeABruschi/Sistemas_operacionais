import java.util.LinkedList;
import java.util.Queue;

class Mesa {

    // Buffer/Fila de pizzas
    private Queue<String> fila = new LinkedList<>();

    // Capacidade máxima do buffer
    private final int LIMITE = 2;

    // ================= COZINHEIRO =================

    public synchronized void colocarPizza(String pizza)
            throws InterruptedException {

        // Enquanto o buffer estiver cheio
        while (fila.size() == LIMITE) {

            System.out.println(
                    "[COZINHEIRO] Buffer cheio. Esperando..."
            );

            wait();
        }

        // Adiciona pizza
        fila.add(pizza);

        System.out.println(
                "[COZINHEIRO] Pizza adicionada: "
                        + pizza
                        + " | Pizzas no buffer: "
                        + fila.size()
        );

        // Acorda todos interessados
        notifyAll();
    }

    // ================= ENTREGADOR =================

    public synchronized void retirarPizza()
            throws InterruptedException {

        // Enquanto buffer estiver vazio
        while (fila.isEmpty()) {

            System.out.println(
                    "    [ENTREGADOR] Sem pizzas. Esperando..."
            );

            wait();
        }

        // Remove pizza da fila
        String pizza = fila.poll();

        System.out.println(
                "    [ENTREGADOR] Retirou pizza: "
                        + pizza
                        + " | Pizzas restantes: "
                        + fila.size()
        );

        // Acorda todos interessados
        notifyAll();
    }
}