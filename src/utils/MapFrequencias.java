package utils;

public class MapFrequencias {
    private char[] arrayCaracteres;
    private int[] arrayFrequencias;
    private int size;

    public MapFrequencias(int capacidade) {
        arrayCaracteres = new char[capacidade];
        arrayFrequencias = new int[capacidade];
        size = 0;
    }

    public void add(char c) {
        for (int i = 0; i < size; i++) {
            if (arrayCaracteres[i] == c) {
                arrayFrequencias[i]++;
                return;
            }
        }
        arrayCaracteres[size] = c;
        arrayFrequencias[size] = 1;
        size++;
    }

    public char[] getarrayCaracteres() {
        char[] result = new char[size];
        System.arraycopy(arrayCaracteres, 0, result, 0, size);
        return result;
    }

    public int[] getarrayFrequencias() {
        int[] result = new int[size];
        System.arraycopy(arrayFrequencias, 0, result, 0, size);
        return result;
    }

    public int getSize() {
        return size;
    }
}