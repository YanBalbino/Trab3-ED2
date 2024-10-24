import utils.Huffman.ArvoreHuffman;

public class Test {
    public static void main(String[] args) {
        int n = 6;
        char[] vetorDeCaracteres = { 'a', 'b', 'c', 'd', 'e', 'f' };
        int[] vetorDeFrequencias = { 5, 9, 12, 13, 16, 45 };

        String s = "";
        for (int i = 0; i < vetorDeFrequencias.length; i++) {
            for (int j = 0; j < vetorDeFrequencias[i]; j++) {
            s += vetorDeCaracteres[i];
            }
        }
        ArvoreHuffman arv = new ArvoreHuffman();
        arv.construirArvore(n, vetorDeCaracteres, vetorDeFrequencias);
        arv.imprimirCodigo(arv.getRaiz(), " ");
        
        String[] codigos = arv.gerarCodigosHuffman();

        String comprimida = arv.comprimir(s, codigos);
        System.out.println("Mensagem comprimida: " + comprimida);
        System.out.println("Mensagem descomprimida: " + arv.descomprimir(comprimida));
    }
}
