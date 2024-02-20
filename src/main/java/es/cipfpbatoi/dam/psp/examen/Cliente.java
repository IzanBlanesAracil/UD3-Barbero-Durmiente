package es.cipfpbatoi.dam.psp.examen;

import java.util.concurrent.Semaphore;

public class Cliente {
    Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        Cliente inicio= new Cliente();
        inicio.inicio();

    }
    public void inicio () throws InterruptedException {

        semaphore.acquire();
        for (int i = 0; i <1000 ; i++) {
            System.out.println("SECCION CRITICA");
        }
        semaphore.release();

    }
}
