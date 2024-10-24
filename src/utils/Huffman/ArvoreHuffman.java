package utils.Huffman;

import utils.Heap.MinHeap;

public class ArvoreHuffman{
    No raiz;

    public No getRaiz(){
        return raiz;
    }

    public void construirArvore(int tam, char[] arrayCaracteres, int[] arrayFrequencias){
        MinHeap minHeap = new MinHeap(tam);
        No no;

        for (int i = 0; i < tam; i++){
            no = new No();

            no.caractere = arrayCaracteres[i];
            no.freq = arrayFrequencias[i];

            no.esquerda = null;
            no.direita = null;

            minHeap.inserir(no);
        }

        raiz = null;

        while (minHeap.getSize() > 1){
            No x = minHeap.remover();
            No y = minHeap.remover();

            No z = new No();

            z.freq = x.freq + y.freq;
            z.caractere = '-';
            z.esquerda = x;
            z.direita = y;
            raiz = z;

            minHeap.inserir(z);
        }
    }

    // analisa o dicionário de códigos char a char e retorna a mensagem codificada
    public String comprimir(String msg, String[] codigos){
        String saida = "";
        for (char c : msg.toCharArray()){
            saida += codigos[c];
        }
        return saida;
    }

    // percorre a árvore até achar um caractere, depois volta pra raiz
    public String descomprimir(String msg){
        String saida = "";
        No atual = raiz;
        for (char c : msg.toCharArray()){
           if (c == '0'){
               atual = atual.esquerda;
           } else {
               atual = atual.direita;
           }
           if (atual.esquerda == null && atual.direita == null){
               saida += atual.caractere;
               atual = raiz;
           }
        }
        return saida;
    }

    // gera o código de cada caractere
    public void gerarRecursivamente(No no, String s, String[] codigos){
        if (no == null){
            return;
        }

        if (no.esquerda == null && no.direita == null){
            codigos[no.caractere] = s;
            return;
        }
        gerarRecursivamente(no.esquerda, s + "0", codigos);
        gerarRecursivamente(no.direita, s + "1", codigos);
    }

    // armazenar os códigos de Huffman, junto com seu caractere ASCII correspondente
    public String[] gerarCodigosHuffman(){
        String[] codigos = new String[256]; // string com ASCII posições
        gerarRecursivamente(raiz, "", codigos);

        return codigos;
    }

    public void imprimirCodigo(No no, String s){
        if (no.esquerda == null && no.direita == null){
            System.out.println(no.caractere + ":" + s);

            return;
        }
        imprimirCodigo(no.esquerda, s + " 0");
        imprimirCodigo(no.direita, s + " 1");
    }
}