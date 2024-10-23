package components;

import utils.No;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Cliente{
    private Cache cache;

    public Cliente(){
        cache = new Cache();
    }

    // buscar
    public No buscarOS(int codigo, Servidor servidor){
        No busca = cache.buscar(codigo);
        if (busca == null){
            busca = servidor.buscarOS(codigo);
            if (busca == null)
                return null;
            
            cache.adicionar(busca);
        }
        return busca;
    }

    // cadastrar
    public void CadastrarOS(OrdemServico os, Servidor servidor){
        if (os == null){
            System.out.println("Ordem de serviço inválida");
            return;
        }
        if (os.codigo < 0){
            System.out.println("Código inválido");
            return;
        }
        if (os.nome == null || os.nome.trim().equals("")){
            System.out.println("Nome inválido");
            return;
        }
        if (os.descricao == null || os.descricao.trim().equals("")){
            System.out.println("Descrição inválida");
            return;
        }
        servidor.CadastrarOS(os);
        
    }

    // listar todas as informações no servidor
    public void listarOS(Servidor servidor){
        servidor.listarOS();
    }

    public void imprimirCache(){
        cache.listarCache();
    }

    // alterar 
    public void alterarOS(int codigoOS, OrdemServico os, Servidor servidor){
        if (os == null){
            System.out.println("Ordem de serviço inválida");
            return;
        }
        if (servidor.isRegistrado(codigoOS) == false && cache.isRegistradoCache(codigoOS) == false){
            System.out.println("Código de OS não registrado no sistema");
            return;
        }
        if (os.nome == null || os.nome.trim().equals("")){
            System.out.println("Nome inválido");
            return;
        }
        if (os.descricao == null || os.descricao.trim().equals("")){
            System.out.println("Descrição inválida");
            return;
        }

        servidor.alterarOS(codigoOS, os);  
        if (cache.isRegistradoCache(codigoOS)){
            cache.alterar(codigoOS, os);
        }
        else{
            System.out.println("Ordem de serviço não registrada na cache, nenhuma alteração feita.");
        }
    }

    // remover
    public void removerOS(int codigoOS, Servidor servidor){
        if (servidor.isRegistrado(codigoOS) == false  && cache.isRegistradoCache(codigoOS) == false){
            System.out.println("Código de OS não registrado no sistema");
            return;
        }
        servidor.removerOS(codigoOS);
        cache.remocaoDireta(codigoOS);
    }  

    // acessar a quantidade atual de registros na base de dados
    public int qtRegistros(Servidor servidor){
        return servidor.qtRegistrosAtual();
    }

    // gerar as primeiras 100 OS para testes
    public void gerarOSInicio(Servidor servidor){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < 100; i++){
            LocalDateTime hoje = LocalDateTime.now();
            String dataFormatada = hoje.format(formatter);
            OrdemServico os = new OrdemServico(i, "OS número " + i, "Descrição da OS " + i, dataFormatada);
            CadastrarOS(os, servidor);
        }

        System.out.println("Primeiras 100 OS geradas.");
    }

    // preencher a cache com 30 buscas
    public void preencherCache(Servidor servidor){
        for (int i = 0; i < 30; i++){
            buscarOS(i, servidor);
        }
        System.out.println("Cache preenchida.");
    }
}

