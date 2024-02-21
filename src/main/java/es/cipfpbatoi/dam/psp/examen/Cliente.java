package es.cipfpbatoi.dam.psp.examen;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {
    private int id;

    public Cliente(int id) {
        this.id = id;
    }

    public Cliente() {
        this.id += 1;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {

    }
}
