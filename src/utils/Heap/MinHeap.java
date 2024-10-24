package utils.Heap;

import utils.Huffman.No;

public class MinHeap {
   private No[] heap = null;

   public MinHeap(No[] array){
    this.setHeap(array);
   }

   public MinHeap(int tamanho){
    this.setHeap(new No[tamanho]);
   }

   public No[] getHeap() {
      return heap;
   }

    public void setHeap(No[] heap) {
        this.heap = heap;
    }

    public int piso(double n){
        return (int) n;
    }
    
    public void subir(int i, No[] heap){
        if (i == 0) return;  

        No temp;
        int indicePai = (i - 1) / 2; 

        if (heap[i].getFreq() < heap[indicePai].getFreq()) {
            temp = heap[i];
            heap[i] = heap[indicePai];
            heap[indicePai] = temp;
            subir(indicePai, heap); 
        }
    }

    public void descer(int i, No[] heap, int n){
        int indiceFilho;
        No temp;

        indiceFilho = 2 * i + 1;

        if (indiceFilho < n) {
            if (indiceFilho < n - 1) {
                if (heap[indiceFilho].getFreq() > heap[indiceFilho + 1].getFreq())
                indiceFilho++;
            }

            if (heap[i].getFreq() > heap[indiceFilho].getFreq()) {

                temp = heap[i];
                heap[i] = heap[indiceFilho];
                heap[indiceFilho] = temp;

                descer(indiceFilho, heap, n);
            }
        }
    }

    public int getSize(){
        return getSize(getHeap());
    }

    public int getSize(No[] heap){
        for (int i = 0; i < getHeap().length; i++){
            if (getHeap()[i] == null){
                return i;
            }
        }
        return getHeap().length;
    }

    public void construirHeap(){
        int i;
        int n = getSize(getHeap());

        for (i = piso((n - 1) / 2); i >= 0; i--){
            descer(i, getHeap(), n);
        }
    }

    public void construirHeap(No[] vetor){
        int i;
        int n = getSize(vetor);

        for (i = piso((n - 1) / 2); i >= 0; i--){
            descer(i, vetor, n);
        }
    }

    public void heapsort(){
        heapsort(getHeap());
    }

    private No[] heapsort(No[] vetor){
        int n = getSize(vetor);
        construirHeap(vetor);

        for (int i = n - 1; i > 0; i--) {
            No temp = vetor[0];
            vetor[0] = vetor[i];
            vetor[i] = temp;

            descer(0, vetor, i);
        }
        return vetor;
    }

    public void inserir(No no){
        int n = getSize(heap);
        No[] h = getHeap();
        if (n == 0){
            h[0] = no;
            return;
        }

        if (n == h.length){
            No[] novo = new No[h.length * 2];
            for (int i = 0; i < h.length; i++){
                novo[i] = h[i];
            }
            h = novo;
            setHeap(h);
        }

        this.getHeap()[n] = no;
        subir(n, h);
    }

    public No remover() {
        No[] h = getHeap();
        int tam = getSize(h);

        if (tam != 0) {
            No r = h[0];

            h[0] = h[tam - 1];
            h[tam - 1] = null;
            descer(0, h, tam - 1);

            return r;
        } else {
            return null;
        }
    }

    public void imprimir(){
        int i;

        System.out.println();
        System.out.println("Heap atual:");

        

        for (i = 0; i < getHeap().length; i++){
            if (getHeap()[i] == null){
                break;
            }
            System.out.print("["+ i + "]" + getHeap()[i].getFreq() + " ");
        }
        System.out.println();
    }

}
