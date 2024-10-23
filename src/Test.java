import components.Cache;
import components.OrdemServico;
import utils.No;


public class Test {
    public static void main(String[] args) {
        Cache c = new Cache();

        OrdemServico os1 = new OrdemServico(1, "OS1", "Descricao1", "01/01/2021");
        OrdemServico os2 = new OrdemServico(2, "OS2", "Descricao2", "02/02/2022");
        No n = new No();
        n.os = os1;
        c.adicionar(n);
        n = new No();
        n.os = os2;
        c.adicionar(n);

        c.listarCache();
    }
}
