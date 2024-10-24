import components.Servidor;


public class Test {
    public static void main(String[] args) {
        Servidor server = new Servidor();

        String nome = "Ordem de Serviço 1 Serviço jgsjbgnjksnbjk gsjkgshjv cigj Serviço";
        String padrao = "Serviço";
        int r = server.contadorFrequencia(nome, padrao);
        System.out.println(r);
    }   
}
