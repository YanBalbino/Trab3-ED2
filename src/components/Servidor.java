package components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import utils.TabelaHash.No;
import utils.TabelaHash.TabelaHashEncadeada;

public class Servidor {
    private TabelaHashEncadeada baseDados;
    private static int totalRegistros;
    File logs;

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

        String logOp = "";
        
        switch (operacao){
            case 1: // buscar
                logOp += "Busca";

                int codigo = Integer.parseInt(partes[1]);
                No busca = buscarOS(codigo);

                if (busca != null){
                    String content = "1@" + busca.os.toString();
                    resposta = new Mensagem(content);
                }
                else{
                    resposta = new Mensagem("1@OS não encontrada");
                    atualizarLog(logOp + "\n" + "null" + "\n");
                    break;
                }
                atualizarLog(logOp + "\n" + busca.os.toString() + "\n");
                break;

            case 2: // cadastrar
                logOp += "Cadastrar";

                String[] osArray = partes[1].split("\\|");

                OrdemServico os = new OrdemServico(Integer.parseInt(osArray[0]), osArray[1], osArray[2], osArray[3]);
                CadastrarOS(os);
                resposta = new Mensagem("2@OK");

                atualizarLog(logOp + "\n" + os.toString() + "\n");
                break;

            case 3: // listar
                logOp += "Listar";

                String content = "3@" + listarOS();
                Mensagem msgLista = new Mensagem(content);
                resposta = msgLista;
                atualizarLog(logOp + "\n" + listarOS() + "\n");
                break;

            case 4: // alterar
                logOp += "Alterar";
                String[] osArrayAlt = partes[1].split("\\|");
                int codigoOS = Integer.parseInt(osArrayAlt[0]);
                
                OrdemServico osAlt = new OrdemServico(codigoOS, osArrayAlt[1], osArrayAlt[2], osArrayAlt[3]);
                OrdemServico alterada = alterarOS(codigoOS, osAlt);
                resposta = new Mensagem("4@OK");

                atualizarLog(logOp + "\n" + alterada.toString() + "\n");
                break;

            case 5: // remover
                logOp += "Remover";

                int codigoOSRem = Integer.parseInt(partes[1]);
                OrdemServico removida = removerOS(codigoOSRem);
                resposta = new Mensagem("5@OK");

                atualizarLog(logOp + "\n" + removida.toString() + "\n");
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
            return busca;
        }
        else{
            System.out.println("Ordem de serviço não encontrada.");
        }
        return null;
    }  

    public void CadastrarOS(OrdemServico os){
        if (baseDados.fatorCarga() > 0.9){
            expandirBD();
        }
        baseDados.inserir(os);
        totalRegistros++;
    }

    public String listarOS(){
        String lista = baseDados.imprimirTabelaHash();
        return lista;
    }

    public OrdemServico alterarOS(int codigoOS, OrdemServico os){
        OrdemServico alterada = baseDados.alterar(codigoOS, os);
        System.out.println("Ordem de serviço alterada no servidor com sucesso");
        return alterada;
    }

    public OrdemServico removerOS(int codigoOS){
        return baseDados.remover(codigoOS);
    }  

    public int qtRegistrosAtual(){
        return baseDados.getQtRegistros();
    }

    public void expandirBD(){
        TabelaHashEncadeada expandida = baseDados.resize();
        setBaseDados(expandida);
    }

    public File getLogs(){
        return this.logs;
    }

    public String logsToString(){
        String logsContent = "";
        try{
            Scanner sc = new Scanner(logs);
            while (sc.hasNextLine()){
                logsContent += sc.nextLine() + "\n";
            }
            sc.close();
        } catch (FileNotFoundException e){
            System.out.println("Erro ao ler arquivo de logs.");
        }
        return logsContent;
    }

    private void atualizarLog(String logContent){
        try{
            FileWriter fw = new FileWriter(this.logs, true);
            fw.write(logContent + "\n");
            fw.close();
        } catch (IOException e){
            System.out.println("Erro ao atualizar arquivo de logs.");
        }
    }

    // contador por Brute force
    public int contadorFrequencia(String s, String padrao){
        int cont = 0;

        int M = padrao.length();
        int N = s.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++)
                if (s.charAt(i + j) != padrao.charAt(j))
                    break;
            if (j == M) {
                cont++;
            }
        }

        return cont;
    }
}
