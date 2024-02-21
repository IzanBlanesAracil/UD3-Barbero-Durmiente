package es.cipfpbatoi.dam.psp.examen;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Barberia barberia = new Barberia();
        Thread barbero = new Thread(() -> {
            while (true) {
                barberia.atenderCliente();
            }
        });
        barbero.start();

        Thread clientes = new Thread(() -> {
            while (true) {
                barberia.llegadaCliente();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clientes.start();
    }
}
