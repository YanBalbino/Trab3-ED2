package components;

import utils.MapFrequencias;
import utils.Huffman.ArvoreHuffman;

public class Mensagem {
    String content;
    String[] codigosHuffman;
    ArvoreHuffman arv;

    public Mensagem(String content){
        this.content = content;
        MapFrequencias map = new MapFrequencias(content.length());

        for (char c : content.toCharArray()){
            map.add(c);
        }

        this.arv = new ArvoreHuffman();
        this.arv.construirArvore(map.getSize(), map.getarrayCaracteres(), map.getarrayFrequencias());
        String[] codigos = arv.gerarCodigosHuffman();
        this.content = arv.comprimir(content, codigos);
    }


}
