# Actividad: El Problema del Barbero Dormilón en Java

* [1. Objetivo](#1-objetivo)
* [2. Contexto](#2-contexto)
* [3. Requisitos](#3-requisitos)
* [4. Tareas](#4-tareas)
* [5. Esquema para Trabajar](#5-esquema-para-trabajar)
* [6. Entrega](#6-entrega)
* [7. Consideraciones](#7-consideraciones)
* [8. Referencias](#8-referencias)

![El Barbero Durmiente](md_media/Barbero.webp)

## 1. Objetivo

Implementar una solución al clásico problema de concurrencia del barbero dormilón utilizando mecanismos de concurrencia en Java, como hilos ([`Thread`][Thread]), bloqueos reentrantes ([`ReentrantLock`][ReentrantLock]), variables de condición ([`Condition`][Condition]), y semáforos ([`Semaphore`][Semaphore]).

## 2. Contexto

En una barbería hay un barbero, una silla de barbero, y N (Para este ejemplo 5) sillas para los clientes esperar si el barbero está ocupado. Si no hay clientes, el barbero se sienta en la silla y se duerme. Cuando llega un cliente, este tiene que despertar al barbero si está dormido o, si el barbero está atendiendo a otro cliente, esperar en una de las sillas disponibles. Si todas las sillas están ocupadas, el cliente se va.

## 3. Requisitos

1. **Barbero y Clientes:** Deben ser representados por hilos separados.
2. **Sincronización:** Utiliza [`ReentrantLock`][ReentrantLock] y [`Condition`][Condition] para sincronizar el acceso a la silla del barbero y la espera de los clientes.
3. **Entrada Aleatoria:** Los clientes deben llegar en momentos aleatorios, por ejemplo entre 5 y 10 segundos.
4. **Tiempo de Corte Aleatorio:** El barbero toma un tiempo aleatorio para cortar el cabello a cada cliente. Entre 10 y 15 segundos.
5. **Cola de Espera:** Implementa una cola para gestionar los clientes que esperan. Esta cola en una primera aproximación puede realizarse como lo que hemos visto en clase de utilizar una [`LinkedList`][LinkedList] por ejemplo, pero luego veremos las colas ([`Queue`][Queue]) que nos servirán precisamente para este tipo de propósitos.

## 4. Tareas

1. **Diseñar la Barbería:** Crea clases para representar la barbería, el barbero, y los clientes. Usa la estructura [`LinkedList`][LinkedList] para la cola de espera de los clientes.~~~~
2. **Implementar la Lógica de Sincronización:** Usa [`ReentrantLock`][ReentrantLock] para proteger el acceso a la silla del barbero y [`Condition`][Condition] para manejar el sueño y el despertar del barbero, así como la espera de los clientes.
3. **Simular Entradas y Tiempos Aleatorios:** Utiliza la clase [`ThreadRandom`][ThreadLocalRandom] para simular la llegada aleatoria de clientes y el tiempo que toma cada corte de cabello. Esta clase se utiliza para generar eficientemente números aleatorios desde varios hilos
4. **Probar la Solución:** Asegúrate de que tu solución maneje correctamente situaciones como el barbero durmiendo cuando no hay clientes, clientes esperando cuando el barbero está ocupado, y clientes yéndose cuando no hay sillas disponibles.

## 5. Esquema para Trabajar

A continuación se detalla un esquema básico de como podría ser la solución para implementar el barbero y los clientes, aunque posiblemente la mejor solución sea utilizar una clase `Barbería` que gestione los clientes y todo el protocolo de espera.

```plantuml
@startuml
actor Cliente
entity Barbero

== Flujo de Actividades ==
loop cada cliente
    Cliente -> Barbero: Llega cliente
    alt Si el Barbero está durmiendo
        Barbero -> Barbero: Despertar
    else
        Barbero -> Barbero: Añadir cliente a la cola
    end
end

Barbero -> Barbero: Verificar cola de clientes
loop mientras haya clientes en la cola
    Barbero -> Barbero: Atender cliente
    ... Tiempo de corte ...
    Barbero -> Cliente: Cliente atendido
    Cliente -> Cliente: Sale de la barbería
    Barbero -> Barbero: Verificar cola de clientes
end

alt Si no hay más clientes
    Barbero -> Barbero: Dormir
end
@enduml

```

## 6. Entrega

Tu programa debe compilar y ejecutarse sin errores, mostrando en la consola el flujo de eventos en la barbería (por ejemplo, llegada de clientes, el barbero cortando el cabello, clientes esperando o yéndose).

## 7. Consideraciones

Es muy importante que te plantees primero el diseño de la solución antes de empezar a escribir el código. Como se ha comentado repetidas veces en ejercicios anteriores, lo más importante es que las soluciones sean correctas desde el punto de vista de la concurrencia, no que funcionen, por tanto se ha de insistir en la parte del diseño mucho más que en cualquier otro tipo de programa que no acceda a recursos de forma concurrente.

## 8. Referencias

* [Thread]
* [ReentrantLock]
* [Condition]
* [Semaphore]
* [ThreadLocalRandom]
* [LinkedList]
* [Queue]

[Thread]: https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html
[ReentrantLock]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantLock.html
[Condition]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html
[Semaphore]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Semaphore.html
[ThreadLocalRandom]: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadLocalRandom.html
[LinkedList]: https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
[Queue]: https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html

Puntos Clave para la Reflexión:

1.- Decisión del Cliente: Considera cómo un cliente decide si esperará o se irá cuando llegue a la barbería. ¿Qué 
factores influyen en esta decisión y cómo se puede implementar esta lógica de manera que sea coherente con el
comportamiento esperado?

Los factores son que si el cliente viene y están todas las sillas ocupadas para atender al barbero, tendría que ir otro 
día o en otro momento más tarde. También, si no es así, puede esperar un tiempo en la barbería a que alguna de las
sillas quede vacía.

2.- Manejo de la Cola de Espera: Reflexiona sobre la estructura de datos que podría representar mejor la cola de espera.
¿Cómo garantizarías que los clientes sean atendidos en el orden correcto, especialmente cuando el barbero se desocupa y 
está listo para atender al siguiente cliente?

El manejo de la cola de espera se puede controlar haciendo uso de una lista de tipo LinkedList que permite tanto para
añadir como eliminar en la primera posición, última posición o en cualquiera. Para controlar que las silla estén vacías
lógicamente, se tiene que condicionar si la lista está vacía, en caso de lo contrarío puede ser que las sillas no estén
todas llenas, recorriendo la lista y con la misna condición de que si está vaciá algún sitio el cliente aproveche esa 
silla vaciá para esperar hasta que al barbero lo pueda atender. Si todas las sillas están llenas, no lo meterá en la 
lista de las sillas y tendra que venir otro día.

3.- Concurrencia y Sincronización: Piensa en cómo gestionarías la concurrencia en este escenario. ¿Cómo asegurarías que 
el barbero no sea despertado por un cliente cuando ya está atendiendo a otro? ¿Cómo manejarías las situaciones en las 
que múltiples clientes llegan al mismo tiempo cuando solo queda una silla de espera disponible?

Normalmente si en la barbería no hay ningún cliente tendrá que dormir, pero si ha llegado un cliente a la barbería, el 
barbero se despertará para poder atenderlo.

Para manejar que los clientes entren a la barbería a la vez, se necesitará utilizar el objeto semaphore, que permite 
controlar cuantos hilos pueden realizar la ejecución al mismo tiempo (uno en uno, dos en dos...)

4.- Justicia y Eficiencia: Considera el equilibrio entre la justicia (asegurando que todos los clientes tengan una 
oportunidad justa de ser atendidos) y la eficiencia (minimizando el tiempo de espera para los clientes y el tiempo 
inactivo para el barbero). ¿Cómo impactan tus decisiones de diseño en este equilibrio?

Yo creo que mi diseño es justo y tiene sentido, puesto que entiendo que es objetivo que aquel que primero llegue a la 
barbería sea la persona primera en ser atendida, todo ello teniendo en cuenta que existan sillas libres, haciendo que 
haya equilibrio entre las sillas disponibles y la justicia de mantener un orden de llegada y las distintas opciones de 
espera si las sillas están llenas.




