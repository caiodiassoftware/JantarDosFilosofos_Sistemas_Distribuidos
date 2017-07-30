
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Familia Dias
 */
public class Filosofos extends Thread {
    
    public enum Status {PENSANDO, FAMINTO, COMENDO};
    public Semaphore mutex, fSemaforo;
    public int id;
    public Status status;
    private Mesa mesa;

    public Filosofos(int id, Mesa mesa) {
      this.id = id;
      fSemaforo = new Semaphore(0);
      mutex = new Semaphore(1); 
      status = Status.PENSANDO;
      this.mesa = mesa;
    }
    
    public void run() {
      try {
        while (true) {
          System.out.println("O Filosofo " + id + " está " + status);
          switch(status) {
          case PENSANDO: 
            relaxar();
            mutex.acquire();
            status = Status.FAMINTO; 
            break;
          case FAMINTO:
            mesa.verificar(this);
            mutex.release();
            fSemaforo.acquire();
            status = Status.COMENDO; 
            break;
          case COMENDO:
            relaxar();
            mutex.acquire();
            status = Status.PENSANDO;
            mesa.verificarFilosofoNaEsquerda(this); 
            mesa.verificarFilosofoNaDireita(this); 
            mutex.release();
            break;          
          }
        }
      } catch(Exception e) {
          e.printStackTrace();
      }
    }

    private void relaxar() throws InterruptedException {
        Thread.sleep((long) Math.round(Math.random() * 5000));
    }    
}
