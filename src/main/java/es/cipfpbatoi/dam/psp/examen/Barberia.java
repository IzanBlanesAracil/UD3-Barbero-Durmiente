package es.cipfpbatoi.dam.psp.examen;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition barberoDormido = lock.newCondition();
    private final Condition clienteEsperando = lock.newCondition();
    private final Queue<Cliente> colaClientes = new LinkedList<>();
    private boolean barberoOcupado = false;

    public void llegadaCliente() {
        lock.lock();
        try {
            if (barberoOcupado) {
                clienteEsperando.await(1, TimeUnit.SECONDS);
            }
            colaClientes.add(new Cliente());
            barberoOcupado = true;
            clienteEsperando.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void atenderCliente() {
        lock.lock();
        try {
            if (colaClientes.isEmpty()) {
                barberoDormido.await();
            }
            Cliente cliente = colaClientes.poll();
            barberoOcupado = false;
            clienteEsperando.signal();
            System.out.println("Cliente atendido");
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 15) * 1000L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void dormirBarbero() {
        lock.lock();
        try {
            while (!colaClientes.isEmpty()) {
                barberoDormido.await();
            }
            barberoOcupado = false;
            System.out.println("Barbero durmiendo");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
