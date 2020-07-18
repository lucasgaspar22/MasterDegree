# Aula 13 - LIDA: Controlando o WorldServer3D

## Objetivo

O objetivo desta aula é estudar o exemplo DemoLIDA do uso da arquitetura cognitiva LIDA para controlar o WorldServer3D e promover seu aperfeiçoamento por meio de novos módulos e funções.

## Atividade 1

Antes de mais nada, faça o "Pull from ..."  do GitHub da versão mais nova do [WorldServer3D](https://github.com/CST-Group/ws3d). Foram modificadas algumas funções do mesmo para ele funcionar com o DemoLIDA e portanto a versão antiga pode não funcionar a contento. Se necessário, faça também o "Pull from" do [WS3DProxy](https://github.com/CST-Group/WS3DProxy).

Faça o download do código do [DemoLIDA](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia006/DemoLIDA.zip) e estude o código do mesmo com o auxílio do Netbeans.

Observe atentamente os arquivos no diretório configs do DemoLIDA. Ali estão os arquivos de configuração que são imprescindíveis para o framework LIDA conseguir funcionar.

A inicialização do programa está na classe Run. O método AgentStarter.main do Framework LIDA é utilizado para invocar a inicialização do LIDA. O AgentStarter.main procura então o arquivo configs/lidaConfig.properties, que aponta para os diversos outros arquivos utilizados pelo LIDA.

O arquivo configs/Agents.xml é um arquivo XML contendo toda a descrição dos módulos do framework a serem carregados e inicializados. É nele que estão definidos os subsistemas do LIDA que serão utilizados, bem como os detectors e modules desenvolvidos para controlar o WorldServer3D. Abra o arquivo Agents.xml e procure entender como é feito o binding dos detectors e modules desenvolvidos no DemoLIDA.

Os arquivos LidaFactories.xsd e LidaXMLSchema.xsd são Arquivos XSD (XML Schema Definition) utilizados para descrever o “formato/padrão” que os arquivos XML utilizados pelo LIDA devem seguir, ou seja, eles indicam quais tags XML podem ser utilizadas e de que maneira devem ser utilizadas nos arquivos XML do LIDA. Esses arquivos são utilizados internamente pelo Framework LIDA para fazer o parsing dos arquivos XML do LIDA e verificar sua integridade.

O arquivo factoryData.xml complementa as informações disponíveis no arquivo Agents.xml, com dados de inicialização do framework.

Os arquivos guiCommands.properties e guiPanels.properties são utilizados para customizar a janela de controle que o LIDA disponibiliza para o usuário visualizar os detalhes internos da arquitetura em funcionamento. O usuário pode utilizar controles padrões fornecidos pelo LIDA, ou pode criar seu próprio controle para visualizar os estados internos da arquitetura.

O arquivo logging.properties permite a configuração de alguns detalhes do mecanismo de logging utilizado pelo Framework.

## Atividade 2

O janela de controle do LIDA está desabilitada por default no DemoLIDA. Habilite a janela de controle do LIDA, alterando a propriedade lida.gui.enable=false para lida.gui.enable=true no arquivo lidaConfig.properties.

Desenvolva um painel para ser acoplado na interface de controle do LIDA, que indique a ação sendo realizada a cada instante pela arquitetura.

## Atividade 3

Implemente no DemoLIDA um mecanismo por meio do qual a criatura consiga detectar blocos a partir do ambiente, e consiga se movimentar de uma origem até um destino sem colidir com os blocos (desviando-se deles). Teste esse novo mecanismo criando