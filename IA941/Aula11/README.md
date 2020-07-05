# Aula 11 - LIDA 1: Entendendo a Arquitetura

## Objetivos

O objetivo da aula de hoje é iniciar o estudo da [arquitetura cognitiva LIDA](http://en.wikipedia.org/wiki/LIDA_%28cognitive_architecture%29). A arquitetura LIDA foi desenvolvida pelo grupo do Prof. Stan Franklin, da University of Memphis, nos EUA, e talvez seja a mais sofisticada das três arquiteturas estudadas nesse curso. A arquitetura é disponibilizada em Java, e seu código pode ser obtido [aqui](http://ccrg.cs.memphis.edu/framework.html). É necessário preencher um formulário aceitando a licença de uso, e o link definitivo para o código é enviado por e-mail. Caso tenha problemas em fazer o download da arquitetura, contacte o professor. Infelizmente, das três arquiteturas estudadas, essa talvez seja também a mais difícil de compreendermos e estudarmos, pois ela demanda o entendimento de diversos modelos do processo cognitivo, antes de podermos explorar o código. Na aula de hoje, portanto, acompanharemos um tutorial desenvolvido pelo grupo do Stan Franklin, para compreendermos o modelo teórico da arquitetura. Na próxima aula, faremos um estudo mais aprofundado do uso da arquitetura, já utilizando o framework com exemplos de código.

## Atividade 1

Acesse o tutorial do LIDA [aqui](http://ccrg.cs.memphis.edu/tutorial/index.html). O grupo do Stan Franklin nos dá algumas opções para varrer o tutorial. O Full Tutorial é muito longo, e não teríamos chance de realizá-lo inteiro dentro do tempo da aula. Recomendamos portanto o estudo do [Ciclo Cognitivo](http://ccrg.cs.memphis.edu/tutorial/synopsis.html), que pode ser feito durante a aula. Havendo dúvidas, você pode consultar partes do tutorial completo (que possui até trechos com áudio - traga seu fone de ouvido !!!) ou o professor. O Full Tutorial pode ser uma opção para você acompanhar durante a semana, preparando-se depois para a próxima aula, sedimentando o conhecimento adquirido na aula de hoje.

## Atividade 2

A atividade 1 nos dará uma bagagem teórica inicial para começarmos o tutorial do software, propriamente dito. Para seguir esse tutorial, obtenha o PDF com o tutorial [aqui](http://ccrg.cs.memphis.edu/assets/framework/The-LIDA-Tutorial.pdf). Siga o tutorial até o final. Neste tutorial ainda não haverá nenhuma atividade de programação. O tutorial somente introduz elementos sobre a estrutura do framework de software. Algum apoio adicional pode ser obtido a partir de algumas transparências que eu elaborei sumarizando a [arquitetura LIDA](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia006/LIDA.pdf). Documente sua compreensão do tutorial no seu relatório de atividades.

## Atividade 3

* Leia o seguinte artigo:

    (Frankling et.al. 2016) - "[A LIDA cognitive model tutorial](http://www.sciencedirect.com/science/article/pii/S2212683X16300196)" - Biologically Inspired Cognitive Architectures, Volume 16, April 2016, Pages 105–130 ([Cópia Local](https://moodle.ggte.unicamp.br/mod/resource/view.php?id=132625))

Caso você esteja na UNICAMP ou qualquer outra universidade conveniada à CAPES, você conseguirá fazer o download do PDF do artigo. Caso tenha dificuldades em fazer o download do artigo, entre em contato com o professor.

Procure no artigo, nas [páginas do grupo do Stan Franklin](http://ccrg.cs.memphis.edu/papers.html), no [Google Scholar](http://scholar.google.com/) e também na internet em geral, informações sobre o funcionamento dos seguintes módulos operados pela arquitetura:

* Arquitetura baseada em Codelets (o que é isso ?)
* PAM - Perceptual Associative Memory
* Behavior Network (Rede de Comportamentos)
* Global Workspace Theory (Mecanismo de Consciência do LIDA)

Esses assuntos são importantes para a sua compreensão da arquitetura, de forma que você consiga executar as atividades da próxima aula. Verifique principalmente sua compreensão dos seguintes assuntos:

* Como a arquitetura LIDA implementa a percepção do ambiente ?
* Como a arquitetura LIDA guarda/armazena informações de memória ?
* Como a arquitetura LIDA seleciona as ações que serão implementadas no ambiente ?
* Como a arquitetura LIDA executa a ação selecionada no ambiente ?

## Referências

* Código do [LIDA](https://github.com/CognitiveComputingResearchGroup/lida-framework) no Github
