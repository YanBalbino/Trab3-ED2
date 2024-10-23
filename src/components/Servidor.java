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

    public void listarOS(){
        baseDados.imprimirTabelaHash();
        atualizarLog("Listagem");
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
