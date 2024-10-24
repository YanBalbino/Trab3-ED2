package components;

import utils.Huffman.ArvoreHuffman;

public class Mensagem {
    String content;
    String[] codigosHuffman;
    ArvoreHuffman arv;

    public Mensagem(String content){
        this.content = content;
        char[] arrayCaracteres = gerarArrayCaracteres(content);
        int[] arrayFrequencias = gerarArrayFrequencias(arrayCaracteres);

        arv = new ArvoreHuffman();
        arv.construirArvore(arrayCaracteres.length, arrayCaracteres, arrayFrequencias);

        String[] codigos = arv.gerarCodigosHuffman();
        this.content = arv.comprimir(content, codigos);
    }

    public char[] gerarArrayCaracteres(String content){
        return content.toCharArray();
    }
    public int[] gerarArrayFrequencias(char[] arrayCaracteres){
        int[] arrayFrequencias = new int[arrayCaracteres.length];
        for (int i = 0; i < arrayCaracteres.length; i++){
            for (int j = 0; j < arrayCaracteres.length; j++){
                if (arrayCaracteres[i] == arrayCaracteres[j]){
                    arrayFrequencias[i]++;
                }
            }
            arrayFrequencias[i] = 1;
        }
        return arrayFrequencias;
    }
}
