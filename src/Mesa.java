
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Familia Dias
 */
public class Mesa {
    private Filosofos[] filosofos;
    private int quantidadeFilosofos = 0;
    
    public Mesa(int quantidadeFilosofos){
        this.filosofos = new Filosofos[quantidadeFilosofos];
        this.quantidadeFilosofos = quantidadeFilosofos;
        for (int i = 0; i < quantidadeFilosofos; i++) 
            filosofos[i] = new Filosofos(i, this);
    }
    
    public void iniciarJantar(){
        for (Thread t : filosofos)
            t.start();
    }
    
    public void verificar(Filosofos f) {
      if (getFilosofoNaEsquerda(f.id).status != Filosofos.Status.COMENDO &&
              f.status == Filosofos.Status.FAMINTO &&
          getFilosofoNaDireita(f.id).status != Filosofos.Status.COMENDO) {
        f.status = Filosofos.Status.COMENDO;
        f.fSemaforo.release();
      }
    }
    
    public void verificarFilosofoNaEsquerda(Filosofos f){
        verificar(getFilosofoNaEsquerda(f.id));
    }
    
    public void verificarFilosofoNaDireita(Filosofos f){
        verificar(getFilosofoNaDireita(f.id));
    }
    
    private Filosofos getFilosofoNaEsquerda(int id) {
      return filosofos[id == 0 ? quantidadeFilosofos - 1 : id - 1];
    }

    private Filosofos getFilosofoNaDireita(int id) {
      return filosofos[(id + 1) % quantidadeFilosofos];
    }    
}

