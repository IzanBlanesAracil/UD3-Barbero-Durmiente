package es.cipfpbatoi.dam.psp.examen;

import java.util.Random;

public class Barbero implements Runnable{
    private Barberia shop;

    public Barbero(Barberia shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (true) {
            try {
                Cliente cliente = new Cliente();
                shop.atenderCliente();
                System.out.println("El barbero atiende al cliente " + cliente.getId());
                Thread.sleep(random.nextInt(1000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
