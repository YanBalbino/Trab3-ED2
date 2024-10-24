package components;

import utils.TabelaHash.No;


public class Cache {
    private final int TAMANHO = 30;
    private No primeiro;
    private int qtRegistros;

    public Cache() {
        primeiro = new No();
        qtRegistros = 0;
    }

    public void adicionar(OrdemServico novaOS) {
        int qtRegistros = getQtRegistros();
        No novo = new No();
        novo.os = novaOS;
        
        // cache eviction por Least Recently Used (LRU)
        if (qtRegistros == TAMANHO) {
            No atual = primeiro;
            No penultimo = new No();

            while (atual.proximo != null){
                penultimo = atual;
                atual = atual.proximo;
            }

            // remover último registro
            // novo registro é adicionado no começo da cache por MF
            novo.proximo = primeiro;
            primeiro = novo;
            penultimo.proximo = null;
            atual = null; 

            return;
        }

        // primeiro registro na cache
        if (qtRegistros == 0) {
            primeiro = novo;
            incrementQtRegistros();
            return;
        }

        // adicionar no começo da cache
        novo.proximo = primeiro;
        primeiro = novo;
        incrementQtRegistros();
    }

    public OrdemServico buscar(int codigo){
        if (this.qtRegistros == 0){
            return null;
        }
        No atual = primeiro;
        No anterior = new No();

        while (atual != null){
            if (atual.os.getCodigo() == codigo){
                if (atual == primeiro){
                    return atual.os;
                }

                // AUTOAJUSTE POR MOVER PARA FRENTE (MF)
                anterior.proximo = atual.proximo;
                atual.proximo = primeiro;
                primeiro = atual;

                return atual.os;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        
        return null;
    }

    public void alterar(int codigo, OrdemServico alterada){
        No atual = primeiro;
        while (atual != null){
            if (atual.os.getCodigo() == codigo){
                atual.os = alterada;
                return;
            }
            atual = atual.proximo;
        }
    }

    public void remocaoDireta(int codigo){
        No atual = primeiro;
        No anterior = new No();
        while (atual != null){
            if (atual.os.getCodigo() == codigo){
                if (atual == primeiro)
                    primeiro = atual.proximo;
                
                else
                    anterior.proximo = atual.proximo;
                
                atual = null;
                decrementQtRegistros();
                return;
            }
            anterior = atual;
            atual = atual.proximo;
        }
    }

    public void listarCache(int qtRegistros){
        No atual = primeiro;
        if (qtRegistros == 0){
            System.out.println("Cache vazia.");
            return;
        }
        while (atual != null){
            System.out.println("OS Código: " + atual.os.getCodigo());
            atual = atual.proximo;
        }
    }

    public boolean isRegistradoCache(int codigo){
        No atual = primeiro;
        while (atual != null){
            if (atual.os.getCodigo() == codigo){
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public int getQtRegistros(){
        return qtRegistros;
    }
    public void setQtRegistros(int novaQtRegistros){
        this.qtRegistros = novaQtRegistros;
    }

    public void incrementQtRegistros(){
        this.qtRegistros++;
    }
    public void decrementQtRegistros(){
        this.qtRegistros--;
    }
}
