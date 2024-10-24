package components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Cliente{
    private Cache cache;

    public Cliente(){
        cache = new Cache();
    }

    public Cache getCache(){
        return cache;
    }

    public Mensagem sendToServer(Mensagem msg, Servidor servidor){
        return servidor.sendToClient(msg);
    }

    public String[] processarMensagem(Mensagem msg){
        String conteudo = msg.arv.descomprimir(msg.content);
        return conteudo.split("@");
    }

    // buscar (operação 1)
    public OrdemServico buscarOS(int codigo, Servidor servidor){
        OrdemServico busca = cache.buscar(codigo);

        if (busca == null){
            String content = "1@" + codigo;
            Mensagem msg = new Mensagem(content);
            Mensagem resposta = sendToServer(msg, servidor);
            
            OrdemServico os = new OrdemServico();
            
            String[] partes = processarMensagem(resposta);

            if (partes[0].equals("1")){
                if (partes[1].equals("OS não encontrada")){
                    System.out.println("Ordem de serviço não encontrada.\n");
                    return null;
                }
                os = os.toOrdemServico(partes[1]);
            }
            busca = os; 
            cache.adicionar(busca);
        }

        imprimirCache(cache.getQtRegistros());
        return busca;
    }

    // cadastrar (operação 2)
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

        String content = "2@" + os.toString();
        Mensagem msg = new Mensagem(content);
        Mensagem resposta = sendToServer(msg, servidor);

        String[] partes = processarMensagem(resposta);

        if (partes[0].equals("2") && partes[1].equals("OK"))
            imprimirCache(cache.getQtRegistros());
            return;
        
    }

    // listar todas as informações no servidor (operação 3)
    public void listarOS(Servidor servidor){
        String content = "3@list";
        Mensagem msg = new Mensagem(content);
        Mensagem resposta = sendToServer(msg, servidor);

        String[] partes = processarMensagem(resposta);
        String lista = partes[1];
        System.out.println(lista); 
        imprimirCache(cache.getQtRegistros());
    }

    public void imprimirCache(int qtRegistros){
        System.out.println("\nCache atual:");
        cache.listarCache(qtRegistros);
    }

    // alterar (operação 4)
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

        String content = "4@" + os.toString();
        Mensagem msg = new Mensagem(content);
        Mensagem resposta = sendToServer(msg, servidor);

        String[] partes = processarMensagem(resposta);

        if (partes[0].equals("4")){
            OrdemServico alterada = new OrdemServico();
            //alterada = alterada.toOrdemServico(partes[1]);
            System.out.println("Ordem de serviço alterada com sucesso.\n");
            return;
        }
         
        if (cache.isRegistradoCache(codigoOS)){
            cache.alterar(codigoOS, os);
        }
        else{
            System.out.println("Ordem de serviço não registrada na cache, nenhuma alteração feita.");
        }
        imprimirCache(cache.getQtRegistros());
    }

    // remover (operação 5)
    public void removerOS(int codigoOS, Servidor servidor){
        if (servidor.isRegistrado(codigoOS) == false  && cache.isRegistradoCache(codigoOS) == false){
            System.out.println("Código de OS não registrado no sistema");
            return;
        }

        String content = "5@" + codigoOS;
        Mensagem msg = new Mensagem(content);
        Mensagem resposta = sendToServer(msg, servidor);
        
        String[] partes = processarMensagem(resposta);

        if (partes[0].equals("5")){
            OrdemServico removida = new OrdemServico();
            //removida = removida.toOrdemServico(partes[1]);
        }

        cache.remocaoDireta(codigoOS);
        imprimirCache(cache.getQtRegistros());
    }  

    // acessar a quantidade atual de registros na base de dados
    public int qtRegistros(Servidor servidor){
        imprimirCache(cache.getQtRegistros());
        return servidor.qtRegistrosAtual();
    }

    // gerar as primeiras 100 OS para testes
    public void gerarOSInicio(Servidor servidor){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (int i = 0; i < 100; i++){
            LocalDateTime hoje = LocalDateTime.now();
            String dataFormatada = hoje.format(formatter);
            OrdemServico os = new OrdemServico(i, "OS numero " + i, "Descricao da OS " + i, dataFormatada);
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

