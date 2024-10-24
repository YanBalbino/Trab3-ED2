import components.Cliente;
import components.Servidor;
import components.OrdemServico;
import java.util.Random;


public class Test {
    public static void main(String[] args) {
        OrdemServico os = new OrdemServico();
        Cliente cliente = new Cliente();
        Servidor servidor = new Servidor();

        // teste com tamanho de base de dados menor para garantir colisões
        for (int i = 0; i < 10; i++){
            os = new OrdemServico(i, "nome " + i, "descrição " + i, "data " + i);
            cliente.CadastrarOS(os, servidor);
        }
        cliente.listarOS(servidor);

        cliente.buscarOS(5, servidor);
        cliente.buscarOS(4, servidor);
        cliente.buscarOS(6, servidor);

        System.out.println("\nTestanto autoajuste na bd\n");

        cliente.listarOS(servidor);
    }   
}
