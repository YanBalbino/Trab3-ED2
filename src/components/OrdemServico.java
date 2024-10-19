package components;

public class OrdemServico {
    int codigo;
    String nome;
    String descricao;
    String data;

    public OrdemServico(int codigo, String nome, String descricao, String data){
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }

    // impressão em linha única
    public void imprimirOS(){
        System.out.print("Código: " + this.getCodigo() + " | ");
        System.out.print("Nome: " + this.getNome()+ " | ");
        System.out.print("Descrição: " + this.getDescricao()+ " | ");
        System.out.print("Data: " + this.getData()+ " | \n");
    }

    public int getCodigo(){
        return codigo;
    }

    public void setCodigo(int codigo){
        if (codigo < 0){
            throw new IllegalArgumentException("Código inválido");
        }
        this.codigo = codigo;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        if (nome == null || nome.trim().equals("")){
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        if (descricao == null || descricao.trim().equals("")){
            throw new IllegalArgumentException("Descrição inválida");
        }
        this.descricao = descricao;
    }

    public String getData(){
        return data;
    }
    
    public void setData(String data){
        if (data == null){
            throw new IllegalArgumentException("Data inválida");
        }
        this.data = data;
    }
}
