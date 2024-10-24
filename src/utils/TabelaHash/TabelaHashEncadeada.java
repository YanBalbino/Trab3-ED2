package utils.TabelaHash;


import components.OrdemServico;

public class TabelaHashEncadeada {
	private int M;
	private int qtRegistros;
	private No tabela[];
	
	public TabelaHashEncadeada(int tam) {
		setTam(tam);
		qtRegistros = 0;
		this.tabela = new No[this.M];
	}
	
	public No getNoAt(int i){
		return this.tabela[i];
	}

	public int getTam(){
		return this.M;
	}

	public void setTam(int tam){
		if (tam < 0){
			return;
		}
		this.M = tam;
	}

	public int getQtRegistros(){
		return this.qtRegistros;
	}

	public double fatorCarga(){
		return this.getQtRegistros() / getTam();
	}

	// cria uma nova tabela com o novo tamanho e copia os elementos antigos
	public TabelaHashEncadeada resize(){
		int novoTam = getNovoTam();
		TabelaHashEncadeada novaTabela = new TabelaHashEncadeada(novoTam);
		No no;
		for (int i = 0; i < this.M; i++){
			no = this.tabela[i];
			while (no != null){
				novaTabela.inserir(no.os);
				no = no.proximo;
			}
		}
		return novaTabela;
	}

	// função multiplicativa
	public int hash(int ch) {
		double A = (Math.sqrt(5) - 1) / 2; 
		return (int) (M * ((ch * A) % 1));
	}

	// inserção
	public void inserir(OrdemServico nova) {
		
		int h = this.hash(nova.getCodigo());
		No no = this.tabela[h];
		
		while(no != null) {
			
			if (no.os.getCodigo() == nova.getCodigo()) {
				break;
			}	
			
			no = no.proximo;
		}

		if (no == null) {
			
			no = new No();
			no.os = nova;
			no.proximo = this.tabela[h];
			this.tabela[h] = no;
			qtRegistros++;
		}
		
	}
	
	// buscar
	public No buscar(int codigo) {
		
		int h = this.hash(codigo);
		No no = this.tabela[h];
		No anterior = new No(); // referência do anterior para autoajuste

		while(no != null) {
			
			if (no.os.getCodigo() == codigo) {
				if (no == this.tabela[h]) 
					return no;

				// AUTOAJUSTE POR TRANSPOSIÇÃO (TR) DE ORDENS DE SERVIÇO
				OrdemServico temp = anterior.os;
				anterior.os = no.os;
				no.os = temp;

				return anterior;
				
			}	
			anterior = no;
			no = no.proximo;
		}
		return null;
		
	}

	// alterar
	public OrdemServico alterar(int codigo, OrdemServico alterada) {
		
		int h = this.hash(codigo);
		No no = this.tabela[h];
		
		while(no != null) {
			
			if (no.os.getCodigo() == codigo) {
				no.os = alterada;
				break;
			}	
			
			no = no.proximo;
		}
		return no.os;
	}

	// remover
	public OrdemServico remover(int codigo) {
		
		int h = this.hash(codigo);
		No no = this.tabela[h];
		No anterior = null;
		
		while(no != null) {
			
			if (no.os.getCodigo() == codigo) {
				break;
			}	
			
			anterior = no;
			no = no.proximo;
		}
		
		if (no == null || anterior == null) 
			this.tabela[h] = no.proximo;
		
		else 
			anterior.proximo = no.proximo;
		
		qtRegistros--;
		return no.os;
	}
	
	// listar todos os registros
	public String imprimirTabelaHash() {
		No no;
		String tabela = "";
		for(int i = 0; i < this.M; i++) {
			
			no = this.tabela[i];
			
			tabela = tabela + "Índice " + i + ":" + "\n";
			
			while (no != null) {
				tabela = tabela + " --> " + no.os.toString() + "\n";
				no = no.proximo;
				
			}
			tabela = tabela + "\n";
			
		}
		return tabela;
	}

	// funções necessárias ao resize

	private int getNovoTam(){
		return buscarPrimoAbaixo(buscarPotencia2Acima(M));
	}

	private int buscarPotencia2Acima(int n){
		int potencia = 1;
		while (potencia < n){
			potencia *= 2;
		}
		return potencia;
	}

	private int buscarPrimoAbaixo(int n){
		int primo = n;
		boolean ehPrimo = false;
		while (!ehPrimo){
			primo--;
			ehPrimo = true;
			for (int i = 2; i < primo; i++){
				if (primo % i == 0){
					ehPrimo = false;
					break;
				}
			}
		}
		return primo;
	}
}
