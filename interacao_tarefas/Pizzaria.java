public class Pizzaria {

    public static void main(String[] args) {

        Mesa mesa = new Mesa();

        // ================= COZINHEIRO =================

        new Thread(() -> {

            try {

                String[] cardapio = {
                        "Calabresa",
                        "Mussarela",
                        "Portuguesa",
                        "Frango",
                        "4 Queijos"
                };

                for (String pizza : cardapio) {

                    mesa.colocarPizza(pizza);

                    // ================= TESTES =================
                    // Cozinheiro rápido:
                    // Thread.sleep(500);

                    // Cozinheiro lento:
                    Thread.sleep(3000);

                    // Sem atraso:
                    // Thread.sleep(0);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        // ================= ENTREGADOR =================

        new Thread(() -> {

            try {

                for (int i = 0; i < 5; i++) {

                    mesa.retirarPizza();

                    // ================= TESTES =================
                    // Entregador rápido:
                    // Thread.sleep(500);

                    // Entregador lento:
                    Thread.sleep(4000);

                    // Sem atraso:
                    // Thread.sleep(0);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}