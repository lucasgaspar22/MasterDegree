# Aula 6 - SOAR: Controlando o WorldServer3D

### Objetivo

Nas aulas anteriores, seguimos os tutoriais básicos do Soar para entender seu funcionamento. Para isso, nos servimos de alguns exemplos de interfaceamento do Soar com outros programas (Eaters e TankSoar), que já estavam preparados. Na aula de hoje, utilizaremos o Soar para controlar uma aplicação externa por meio da interface SML. O sistema que desenvolveremos será a mente artificial de um agente capaz de controlar o robô no ambiente WorldServer3D que utilizamos na primeira aula. Para facilitar o acesso ao WS3D e ao WS3DProxy, providenciamos abaixo os links para os referidos projetos no GitHub:

* Código Fonte do [WS3D](https://github.com/CST-Group/ws3d)
* Código Fonte do [WS3DProxy](https://github.com/CST-Group/WS3DProxy)

### Atividade 1

Para essa atividade, utilizaremos um exemplo de um controlador que utiliza o SOAR como um controlador reativo para a tomada de decisões. O download dos arquivos com o código fonte desse programa pode ser feito aqui:

* [DemoJSOAR](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia941/DemoJSOAR-20170411.zip) (código fonte)

Abra o código fonte do DemoJSOAR no Netbeans, e faça uma análise de seu funcionamento. Para tanto, siga o roteiro a seguir.

Abra o código da classe Main.java, que contém o método main. Observe o uso da classe NativeUtils para fazer o dowload do arquivo soar-rules.soar a partir do próprio arquivo JAR do DemoJSOAR.

O loop principal de simulação do DemoJSOAR também se encontra na classe Main. Entenda seu funcionamento.

Acesse o código da classe SoarBridge.java, para compreender em mais detalhes o que está acontecendo. Investigue o funcionamento dos métodos step() e mstep() dessa classe. Observe que essa classe já se utiliza das classes de apoio do WS3DProxy. Entenda como é feito o acesso ao WorldServer3D, por meio do WS3DProxy.

Tente analisar como é feita a leitura do estado do ambiente no WorldServer3D, e como esses dados sensoriais são enviado para o SOAR. Da mesma forma, analise como os dados enviados pelo SOAR são aproveitados para controlar a criatura no WorldServer3D.

Acesse o conteúdo do arquivo de regras SOAR: soar-rules.soar e tente entender seu funcionamento. Analise o princípio lógico de seu funcionamento.

Observe que os botões disponibilizam a capacidade para se executar o SOAR passo a passo a passo, bem como em micro-passos (micro-steps). O botão de play-pause, permite que se pare a simulação em uma posição específica, onde pode-se então analisar as decisões para aquela posição. Na execução passo a passo, um ciclo completo de inferência é executado a cada instante. Na execução por micro-steps, cada uma das fases da inferência é executada: INPUT -> PROPOSAL -> DECISION -> APPLY -> OUTPUT. Podem ser necessários diversos ciclos como esse para que se chegue no HALT, que é quando a inferência deu um HALT. A execução em micro-steps pode ser utilizada junto com o JSoarDebugger para se verificar o efeito das regras em cada micro-passo.

Para inibir o JSoarDebugger junto com a simulação, basta substituir a seguinte linha do arquivo Main.java:

```SoarBridge soarBridge = new SoarBridge(e,soarRulesPath,true);```

por

```SoarBridge soarBridge = new SoarBridge(e,soarRulesPath,false);```

Observe que o JSoarDebugger é diferente do SoarDebugger utilizado nos experimentos com o SOAR. É uma versão full-java do mesmo. No demo apresentado para estudo, o aplicativo DemoJSOAR já inicializa automaticamente o JSoarDebugger. Esse recurso facilita o processo de depuração das regras SOAR durante o desenvolvimento da atividade 2. Na versão a ser entregue na Atividade 2, o JSoarDebugger deve ser inibido, seguindo-se o procedimento acima.

Por fim, para completar essa atividade, modifique as regras que controlam o Soar, para que ao invés de sair pegando tudo o que vê pela frente, ele atue de maneira seletiva, buscando jóias ou alimentos da seguinte maneira:

* Se o nível de energia for menor do que 30%, faça ele localizar o alimento mais próximo conhecido e ir até ele comê-lo. Observe que um alimento conhecido é mais do que um alimento que está sendo percebido no campo visual da criatura. Será necessário criar uma memória FORA do Soar (ou seja, no Java), armazenando todos os alimentos que já foram vistos, e não somente os que estão visíveis no campo visual. Da mesma forma, será necessário gerenciar essa memória, para apagar os alimentos que forem comidos pela criatura, quando esta o fizer.
* Se o nível de energia for maior do que 30%, faça ele localizar a jóia mais próxima conhecida que pertença a um Leaflet, pegá-la e armazená-la na Bag (aqui também observe que uma jóia conhecida não é simplesmente uma jóia que está visível no campo visual da criatura - será necessário criar uma memória para as jóias do mesmo modo que com os alimentos).
* Se o nível de energia for maior do que 30%, e a criatura já possuir um conjunto de jóias em seu Leaflet, que permita a troca dessas jóias por pontos, a criatura deve dar prioridade (em relação a buscar novas jóias) por ir até um delivery spot e trocar as jóias por pontos.

Crie um mecanismo de geração aleatória de jóias e alimentos no mundo, para que a criatura seja capaz de eventualmente ser capaz de compor um Leaflet e trocar as jóias por pontos. Observe que se forem introduzidas muitas jóias que não são úteis para o Leaflet no ambiente, a criatura acabará eventualmente colidindo com essas jóias e ficando presa. Uma estratégia para evitar isso é enterrar as jóias que não são necessárias para compor um Leaflet.

### Atividade 2

Na atividade 1, desenvolvemos uma estratégia **reativa**, que simplesmente vai pegando as jóias e comidas necessárias, e eventualmente a criatura acumula um conjunto de jóias que pode ser trocada por pontos, compondo um Leaflet. Na atividade 2, nossa proposta é desenvolver um novo conjunto de regras para criar uma estratégia **DELIBERATIVA** (ao invés de reativa) de comportamento. Em uma estratégia reativa, o agente simplesmente reage à situação atual, tentando definir qual a melhor ação a ser executada em função das informações que dispõe. Em uma estratégia deliberativa, ao contrário, existe uma meta a ser atingida, um estado final que se deseja atingir, e é necessário deliberar todas as ações intermediárias que são necessárias para que o objetivo seja atingido. Para o desenvolvimento desta estratégia vocês devem levar em consideração, principalmente, o Tutorial 5 do SOAR, onde se explica como o SOAR pode ser usado para PLANEJAR, visando atingir um estado futuro.

O nosso objetivo, no caso, é gerar um plano que nos leve, a partir do estado atual, a coletar um conjunto de jóias que possam ser trocadas por pontos. O agente deve, a partir das jóias conhecidas (lembrando que as jóias conhecidas são mais do que simplesmente as jóias que estão sendo visualizadas), verificar quais são os Leaflets que são possíveis de completar, escolher o Leaflet que rende mais pontos e planejar as ações necessárias para trocar essas jóias por pontos.

Entretanto, é necessário considerar que dentre as jóias conhecidas, pode ainda não ser possível compor um Leaflet, pois nenhuma combinação é possível com as jóias disponíveis. Nesse caso, adotaremos uma solução que utiliza sub-goaling. O primeiro sub-goaling é descobrir se, a partir das jóias conhecidas, é possível compor um Leaflet. Se isso for possível, o planejador deve planejar uma sequência de ações que levem o agente a coletar todas as jóias e trocar as jóias por pontos. Se isso não for possível, o sub-goal passa então ao modo reativo desenvolvido na Atividade 1.

Desenvolva uma maneira de testar o comportamento deliberativo, criando uma situação em que fique claro que o agente está adotando um comportamento deliberativo, e não simplesmente agindo reativamente até conseguir trocar jóias por pontos.

Ao final dessa aula o seguinte deliverable deve ser entregue ao professor via e-mail:

* Um ÚNICO arquivo ZIP contendo:
  * o código executável do WS3D,
  * o código executável do DemoSOAR,
  * um Shell Script (bash) startando o WS3D com o agente da atividade 1,
  * um Shell Script (bash), startando o WS3D com o agente da atividade 2.
  * o código fonte da sua versão do programa DemoSOAR

Caso o arquivo seja grande demais para fazer o upload no Moodle, envie-o para o Google Drive e disponibilize um link para o acesso ao mesmo no Moodle. Envie tudo em um único arquivo. O download de um diretório no Google Drive é muito mais lento e complicado do que o de um único arquivo.