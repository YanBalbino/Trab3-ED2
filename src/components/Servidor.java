package components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import utils.No;
import utils.TabelaHashEncadeada;

public class Servidor {
    private TabelaHashEncadeada baseDados;
    private static int totalRegistros;
    File logs;

    //TODO implementar compressão e descompressão

    public Servidor() {
        // quantidade inicial de OS cadastradas: 100
        baseDados = new TabelaHashEncadeada(100);
        totalRegistros = 0;
        try{
            this.logs = new File("logs.log");
            if (this.logs.exists()){
                this.logs.delete();
                this.logs.createNewFile();
            } else {
                this.logs.createNewFile();
            }

        } catch (IOException e){
            System.out.println("Erro ao criar arquivo de logs de operações.");
        }
    }

    public void setBaseDados(TabelaHashEncadeada bd){
        this.baseDados = bd;
    }

    private TabelaHashEncadeada getBD(){
        return this.baseDados;
    }
    
    public static int getTotalRegistros(){
        return totalRegistros;
    }

    public boolean isRegistrado(int codigo){
        No busca = baseDados.buscar(codigo);
        if (busca != null){
            return true;
        }
        return false;
    }

    public Mensagem sendToClient(Mensagem msg){
        return processarMensagem(msg);
    }

    private Mensagem processarMensagem(Mensagem msg){
        String conteudo = msg.arv.descomprimir(msg.content);
        String[] partes = conteudo.split("@");
        Mensagem resposta = null;
        int operacao = Integer.parseInt(partes[0]);
        
        switch (operacao){
            case 1:
                int codigo = Integer.parseInt(partes[1]);
                No busca = buscarOS(codigo);

                if (busca != null){
                    String content = "1@" + busca.os.toString();
                    resposta = new Mensagem(content);
                }
                break;

            case 2:
                String[] osArray = partes[1].split("|");
                OrdemServico os = new OrdemServico(Integer.parseInt(osArray[0]), osArray[1], osArray[2], osArray[3]);
                CadastrarOS(os);
                resposta = new Mensagem("2@OK");
                break;

            case 3: //TODO implementar listagem comprimida
                String content = "3@" + listarOS();
                Mensagem msgLista = new Mensagem(content);
                resposta = sendToClient(msgLista);
                break;

            case 4:
                int codigoOS = Integer.parseInt(partes[1]);
                String[] osArrayAlt = partes[2].split("|");
                OrdemServico osAlt = new OrdemServico(Integer.parseInt(osArrayAlt[0]), osArrayAlt[1], osArrayAlt[2], osArrayAlt[3]);
                alterarOS(codigoOS, osAlt);
                resposta = new Mensagem("4@OK");
                break;

            case 5:
                int codigoOSRem = Integer.parseInt(partes[1]);
                removerOS(codigoOSRem);
                resposta = new Mensagem("5@OK");
                break;

            default:
                System.out.println("Operação inválida");
                
                break;
        }
        return resposta;

    }

    public No buscarOS(int codigo){
        No busca = baseDados.buscar(codigo);
        if (busca != null){
            atualizarLog("Busca");
            return busca;
        }
        else{
            System.out.println("Ordem de serviço não encontrada.");
        }
        atualizarLog("Busca");
        return null;
    }  

    public void CadastrarOS(OrdemServico os){
        if (baseDados.fatorCarga() > 0.9){
            expandirBD();
        }
        baseDados.inserir(os);
        totalRegistros++;

        atualizarLog("Cadastro");
    }

    public String listarOS(){
        String lista = baseDados.imprimirTabelaHash();

        atualizarLog("Listagem");

        return lista;
    }

    public void alterarOS(int codigoOS, OrdemServico os){
        baseDados.alterar(codigoOS, os);
        System.out.println("Ordem de serviço alterada no servidor com sucesso");
        atualizarLog("Alteração");
    }

    public void removerOS(int codigoOS){
        baseDados.remover(codigoOS);
        atualizarLog("Remoção");
    }  

    public int qtRegistrosAtual(){
        return baseDados.getQtRegistros();
    }

    public void expandirBD(){
        TabelaHashEncadeada expandida = baseDados.resize();
        setBaseDados(expandida);
    }

    private void atualizarLog(String operacao){
        TabelaHashEncadeada bd = getBD();
        No temp;

        try{
            FileWriter fw = new FileWriter(this.logs, true);
            fw.write("\nOperação: " + operacao);
            fw.close();
        } catch (IOException e){
            System.out.println("Erro ao atualizar arquivo de logs.");
        }

        for (int i = 0; i < bd.getTam(); i++){
            temp = bd.getNoAt(i);
            try{
                FileWriter fw;
                while (temp != null){
                    fw = new FileWriter(this.logs, true);
                    
                    fw.write("\n");
                    fw.write(temp.os.getCodigo() + " " + temp.os.getNome() +  " " + temp.os.getDescricao() 
                    +  " " + temp.os.getData() + "\n");
    
                    fw.close();
                    
                    temp = temp.proximo;
                }
                

            } catch (IOException e){
                System.out.println("Erro ao atualizar arquivo de logs.");
            }
        }
    }
}
