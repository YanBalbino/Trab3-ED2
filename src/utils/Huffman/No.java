package utils.Huffman;

public class No{
    int freq;
    char caractere;
    No esquerda;
    No direita;

    public int getFreq() {
        return freq;
    }
    public void setFreq(int freq) {
        this.freq = freq;
    }

    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita(){
        return direita;
    }

    public void setDireita(No direita){
        this.direita = direita;
    }
}