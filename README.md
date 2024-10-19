
## Desenvolvimento de uma base de dados sobre ordens de serviço (OS)
para reparar falhas em equipamentos.

* Tarefa: **Simulação de uma aplicação cliente/servidor, onde:**  
  * Uma classe ou conjunto de classes implementa o cliente que pode fazer buscas
na base de dados;
  * Uma classe ou conjunto de classes implementa o servidor que controla a base
de dados.

* Mensagens:  
  * Uma mensagem é um objeto que encapsula os atributos de uma OS, a operação
que vai ser feita e alguma outra opção que você queira protocolar nesta
comunicação.
  * As mensagens entre cliente e servidor devem ser “enviadas” usando
compressão de dados.
  * Cliente comprime antes de enviar a mensagem e servidor descomprime
após receber a mensagem.
  * Utilize o algoritmo de Huffman.
    * Use um algoritmo de processamento de strings para verificar os
caracteres e suas frequências nas mensagens.
    * Use lista de prioridade como estrutura auxiliar durante a
compressão.
    * Não é permitido usar estruturas já prontas.

* Ordens de serviço:
  * Cada ordem de serviço possui dados como código, nome, descrição e hora da
solicitação.
* Sobre a cache e a base de dados:
  * A base de dados deve ser implementada como uma Tabela Hash.
  * A modelagem da tabela hash (função de dispersão e tratamento de
colisões) faz parte da interpretação do projeto (use orientação a
objetos). Justifique as escolhas.
    * Se a tabela hash usar o tratamento de colisões com
encadeamento exterior, use uma lista autoajustável como lista
de colisão.
    * É importante que a base de dados seja uma tabela hash
redimensionável.
  * A cache pode ser implementada como uma lista de prioridades ou uma lista
autoajustável para 30 elementos e deve utilizar:
  * A cache só deve ser preenchida a partir de buscas.
    * Crie uma função para fazer um número de buscas que preencha
a cache.
  * Operação de busca na cache.
    * Buscas devem ser feitas primeiro na cache. Caso o item de
busca não esteja na cache, a busca é redirecionada para a base
de dados.
    * Inserções, alterações e remoções devem ser feitas na base de
dados e refletidas na cache.
    * Política para esvaziamento da cache: vai depender da estrutura que for
escolhida. Justifique a escolha.
  * Justifique a escolha da função de dispersão e, eventualmente, do mecanismo de
tratamento de colisões para as tabelas da base de dados.

