# Aula 1

### Objetivos
Em nosso curso, estudaremos 4 diferentes arquiteturas cognitivas, SOAR, Clarion, LIDA e CST. O curso foi desenvolvido de uma forma bastante pragmática, permitindo que o aluno conheça intimamente cada uma dessas arquiteturas e consiga utilizá-la para a construção de um sistema inteligente de controle. Para tal, utilizaremos um mesmo cenário, ou seja, um mundo virtual 3D onde um pequeno robô deve desenvolver pequenas atividades inteligentes, e desenvolveremos soluções utilizando cada uma das arquiteturas cognitivas mencionadas anteriormente para controlar esse pequeno robô, fazendo com que o mesmo execute as tarefas solicitadas. Dessa forma, os alunos poderão de maneira bem pragmática, entender as diversas diferenças que existem entre as 4 arquiteturas, compreendendo suas vantagens e desvantagens, bem como peculiaridades das diversas implementações. 

O objetivo desta primeira aula é oferecer aos alunos um primeiro contato com esse ambiente virtual, que será posteriormente utilizado em cada uma das arquiteturas cognitivas. Para tanto, as seguintes atividades devem ser realizadas:
* Download do Código do Ambiente Virtual que iremos utilizar em nossos trabalhos, sua compilação no Netbeans e sua execução em linha de comando, por meio de um bash script.
* Geração de um Controlador Manual para o Ambiente Virtual

---
### Atividade 1: Download e Compilação do Código do WorldServer3D

Neste curso, utilizaremos um ambiente virtual desenvolvido pelo nosso grupo de pesquisa aqui no DCA-FEEC-UNICAMP, o WorldServer3D (WS3D). Neste ambiente virtual, é possível criar um conjunto de criaturas virtuais, que são controladas por seus sensores e atuadores, gerenciados por meio de Sockets. Nesta atividade, faremos o download do código Java do WS3D, e faremos sua compilação e execução. Em seguida, geraremos um código executável, na forma de um arquivo JAR que poderá ser utilizado para executar o WS3D.

O download do código fonte do [WS3D](https://github.com/CST-Group/ws3d) pode ser feito a partir da página do projeto no GitHub.

Para baixar o código do GitHub a partir diretamente do Netbeans, clique no menu:

Equipe -> Git -> Clonar

e preencha https://github.com/CST-Group/ws3d.git em "URL do Repositório". Preencha a "Pasta de Destino" com um diretório conveniente onde você pretende armazenar o código fonte. Não há necessidade de preencher nem Usuário nem Senha. Esses campos podem ficar vazios. O Netbeans deve baixar o código fonte e em seguida oferecer para abrir o projeto recém encontrado

---

### Atividade 2: Geração de um Controlador Manual para o Ambiente Virtual

ara finalizar as atividades desta aula, desenvolveremos um template de um sistema de controle de uma criatura virtual, para gerar um controlador manual que possa operar uma criatura no WS3D, e da mesma maneira geraremos uma Aplicação Java com esse controlador. Ao final da aula de hoje, deve ser enviado ao professor, via e-mail, um arquivo ZIP contendo o seguinte:
* Código Fonte (Java) da aplicação de controle manual do WS3D
* Código Executável (arquivo JAR) com sua versão compilada do WS3D
* Código Executável (arquivo JAR) com sua versão compilada da aplicação de controle manual do WS3D
* Arquivo Shell Script (BASH) com os comandos para executar o WS3D e em seguida a App de controle manual
* Arquivo PDF com um relatório das atividades executadas na aula. 

O acesso a uma aplicação WS3D sendo executada é feito por meio de um socket, aberto na porta 4011. Por meio desse socket, foi criado um protocolo muito simples, onde comandos podem ser enviados ao WS3D e uma resposta deve ser capturada no retorno. Essa resposta pode ser somente um acknowledgment, ou pode conter informações solicitadas, dependendo do comando enviado. Para conhecer a lista de comandos disponíveis, é necessário estudar o código contido no arquivo Main.java do código do WS3D, onde a lista de comandos e suas funções de processamento são definidas. Este é o modo padrão para ter acesso a todos os comandos disponíveis no WS3D. Para facilitar o acesso ao WS3D, entretanto, nosso grupo de pesquisa desenvolveu uma biblioteca Proxy, que abstrai toda a camada de rede, e permite que outros programas escritos em Java possam acessar o WS3D sem a necessidade de tratar os detalhes operacionais do processamento de sockets. O projeto [WS3DProxy](https://github.com/CST-Group/WS3DProxy) pode, a semelhança do WS3D ser obtido a partir do seu repositório no GitHub.

O trecho de código a seguir ilustra o uso do WS3DProxy:
```
WS3DProxy proxy = new WS3DProxy();
try {   
     World w = World.getInstance();
     w.reset();
     World.createFood(0, 350, 75);
     World.createFood(0, 100, 220);
     World.createFood(0, 250, 210);
     Creature c = proxy.createCreature(100,450,0);
     c.start();
     WorldPoint position = c.getPosition();
     double pitch = c.getPitch();
     double fuel = c.getFuel();
     c.moveto(V_SPEED, V_X, V_Y);
    } catch (CommandExecException e) {
     System.out.println("Erro capturado"); 
}
```

Inicialmente criamos uma instância do Proxy, capturamos uma instância para o mundo virtual na forma de um objeto da classe World, e a partir desse objeto podemos efetuar uma série de ações, tais como resetar o mundo, criar instâncias de comidas, jóias e criaturas. Tendo-se uma referência para uma criatura, é possível iniciar a mesma, ler seus sensores e enviar comandos via atuadores. Há diversos outros comandos para sensores e atuadores além dos mostrados acima. Veja o código fonte do WS3DProxy para ver as opções disponíveis.

Para finalizar o exercício da aula de hoje, deve-se utilizar o WS3DProxy para se gerenciar a movimentação da criatura manualmente, através do uso do teclado e/ou do mouse. No relatório desta atividade, devem estar disponibilizados tanto o código fonte gerado como o executável do programa de controle, na forma de uma AppJava. Os alunos devem gerar um shell script que inicialize tanto o WS3D como seu software de controle e disponibilizar tudo em um arquivo ZIP em seu repositório de atividades, conforme indicado acima.