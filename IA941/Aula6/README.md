# Aula 6 - SOAR: Controlando o WorldServer3D

### Objetivo

Nas aulas anteriores, seguimos os tutoriais básicos do Soar para entender seu funcionamento. Para isso, nos servimos de alguns exemplos de interfaceamento do Soar com outros programas (Eaters e TankSoar), que já estavam preparados. Na aula de hoje, utilizaremos o Soar para controlar uma aplicação externa por meio da interface SML. O sistema que desenvolveremos será a mente artificial de um agente capaz de controlar o robô no ambiente WorldServer3D que utilizamos na primeira aula. Para facilitar o acesso ao WS3D e ao WS3DProxy, providenciamos abaixo os links para os referidos projetos no GitHub:

* Código Fonte do [WS3D](https://github.com/CST-Group/ws3d)
* Código Fonte do [WS3DProxy](https://github.com/CST-Group/WS3DProxy)

### Atividade 1

Para essa atividade, utilizaremos um exemplo de um controlador que utiliza o SOAR como um controlador reativo para a tomada de decisões. O download dos arquivos com o código fonte desse programa pode ser feito aqui:

* [DemoJSOAR](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia941/DemoJSOAR-20170411.zip) (código fonte)

Abra o código fonte do DemoJSOAR no Netbeans, e faça uma análise de seu funcionamento. Para tanto, siga o roteiro a seguir, registrando as eventuais respostas a perguntas em seu relatório.

Abra o código da classe Main.java, que contém o método main. Observe o uso da classe NativeUtils para fazer o dowload do arquivo soar-rules.soar a partir do próprio arquivo JAR do DemoJSOAR.

O loop principal de simulação do DemoJSOAR também se encontra na classe Main. Explique seu funcionamento.

Acesse o código da classe SoarBridge.java, para compreender em mais detalhes o que está acontecendo. Investigue o funcionamento dos métodos step() e mstep() dessa classe. Observe que essa classe já se utiliza das classes de apoio em WS3DProxy. Entenda e explique como é feito o acesso ao WorldServer3D, por meio do WS3DProxy.

Explique como é feita a leitura do estado do ambiente no WorldServer3D, e como esses dados sensoriais são enviado para o SOAR. Da mesma forma, explique como os dados enviados pelo SOAR são aproveitados para controlar a criatura no WorldServer3D. Registre suas conclusões no relatório de atividades.

Acesse o conteúdo do arquivo de regras SOAR: soar-rules.soar e tente entender seu funcionamento. Explique o princípio lógico de seu funcionamento.

Observe que os botões disponibilizam a capacidade para se executar o SOAR passo a passo a passo, bem como em micro-passos (micro-steps). O botão de play-pause, permite que se pare a simulação em uma posição específica, onde pode-se então analisar as decisões para aquela posição. Na execução passo a passo, um ciclo completo de inferência é executado a cada instante. Na execução por micro-steps, cada uma das fases da inferência é executada: INPUT -> PROPOSAL -> DECISION -> APPLY -> OUTPUT. Podem ser necessários diversos ciclos como esse para que se chegue no HALT, que é quando a inferência deu um HALT. A execução em micro-steps pode ser utilizada junto com o JSoarDebugger para se verificar o efeito das regras em cada micro-passo.
Para inibir o JSoarDebugger junto com a simulação, basta substituir a seguinte linha do arquivo Main.java:

```SoarBridge soarBridge = new SoarBridge(e,soarRulesPath,true);```

por

```SoarBridge soarBridge = new SoarBridge(e,soarRulesPath,false);```

Observe que o JSoarDebugger é diferente do SoarDebugger utilizado nos experimentos com o SOAR. É uma versão full-java do mesmo. No demo apresentado para estudo, o aplicativo DemoJSOAR já inicializa automaticamente o JSoarDebugger. Esse recurso facilita o processo de depuração das regras SOAR durante o desenvolvimento da atividade 2. Na versão a ser entregue na Atividade 2, o JSoarDebugger deve ser inibido, seguindo-se o procedimento acima.

### Atividade 2

No DemoJSOAR, um conjunto de regras simplificado é apresentado, que faz com que o agente adote uma estratégia reativa, que simplesmente vai pegando todas as jóias e comidas que aparecem pela frente. Nessa atividade, propõe-se que um novo conjunto de regras seja desenvolvido para a aplicação, de modo a criar uma estratégia DELIBERATIVA (ao invés de reativa) de comportamento. Em uma estratégia reativa, o agente simplesmente reage à situação atual, tentando definir qual a melhor ação a ser executada em função das informações que dispõe. Em uma estratégia deliberativa, ao contrário, existe uma meta a ser atingida, um estado final que se deseja atingir, e é necessário deliberar todas as ações intermediárias que são necessárias para que o objetivo seja atingido. Para o desenvolvimento desta estratégia vocês devem levar em consideração, principalmente, o Tutorial 5 do SOAR, onde se explica como o SOAR pode ser usado para PLANEJAR, visando atingir um estado futuro.

Cada criatura no WorldServer3D possui um "leaflet", ou seja, uma meta na obtenção de jóias. Modifique o programa DemoSOAR (e também o soar-rules.soar), de tal forma que ele leve em consideração o leaflet de cada criatura para o controle da criatura.

Coloque duas criaturas competindo em um mesmo ambiente, para ver qual delas consegue completar seu "leaflet" com mais rapidez.

Escolha um colega de turma que já tenha concluído essa atividade, e efetue uma competição entre o seu controlador e o desenvolvido por ele.

Ao final dessa aula os seguintes deliverables devem ser entregues ao professor via e-mail:

*  Arquivo ZIP contendo: o JAR do WS3D, o JAR do DemoSOAR.
*  Um Shell Script (bash) startando o WS3D com uma criatura sozinha 
*  Um Shell Script (bash), startando o WS3D com a competição das 2 criaturas.
*  Arquivo ZIP com o código fonte da sua versão do programa DemoSOAR
*  Arquivo PDF contendo o Relatório de Atividades
