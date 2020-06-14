# Aula 7 - Clarion 1

## Objetivos

Nesta aula, iniciaremos o estudo da arquitetura Clarion, desenvolvida pelo grupo do Prof. Ron Sun na Universidade de Michigan. Para tanto, será necessário um pequeno estudo teórico do funcionamento da arquitetura, antes do desenvolvimento das atividades práticas. A arquitetura conta com uma home-page onde a grande maioria do material necessário pode ser obtido. Essa home-page pode ser encontrada aqui: [Clarion Cognitive Architecture.](http://www.clarioncognitivearchitecture.com/)

Infelizmente, a página de links para artigos desse site está com problemas, apontando os links para lugares errados. Felizmente, os links corretos dos artigos podem ser obtidos [aqui](http://www.cogsci.rpi.edu/%7Ersun/clarion-pub.html).

Existem diversas fontes de informação que são importantes. A mais importante é um texto relativamente antigo, mas que é bastante detalhado nas especificações do Clarion, e que eu recomendo que vocês consultem. É o "R. Sun, [A Detailed Specification of CLARION 5.0](http://www.cogsci.rpi.edu/~rsun/sun.tutorial.pdf) . Technical report. 2003". Apesar dele citar o Clarion 5.0, o detalhamento é similar para a versão 6.1.1, que utilizaremos em nossas aulas. Uma versão local desse documento, sem as imensas bordas em branco que existem no documento original, e que dificultam a leitura, pode ser encontrada [aqui](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/%5BSun%202003%5D%20A%20Tutorial%20on%20Clarion%205.0.pdf).

## Atividades

Nesta aula, seguiremos o tutorial do Clarion, utilizando o material disponibilizado pelos autores da arquitetura. Para tanto, devemos fazer o download da versão mais recente do Clarion no site dos autores: [Downloads: Clarion Cognitive Architecture](http://www.clarioncognitivearchitecture.com/downloads). A versão mais recente até o momento é a versão 6.1.1, que utilizaremos nesse curso. Caso a página dos autores apresente algum problema, uma cópia do mesmo pode ser obtida também [aqui](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia006/The%20Clarion%20Library%206.1.1.zip). Efetue o download da arquitetura e instale os arquivos em sua máquina.

O tutorial está dividido em várias pastas, com diversos arquivos PDF, dentro do diretório Tutorials. Uma versão compactada com todos os tutoriais em um mesmo documento pode ser encontrada [aqui](http://faculty.dca.fee.unicamp.br/gudwin/sites/faculty.dca.fee.unicamp.br.gudwin/files/ia006/TutorialClarion.pdf).

A versão 6.1.1 do Clarion foi desenvolvida em C# em .NET. Em nosso laboratório, utilizaremos o mono, que é uma implementação do .NET para Linux, e o monodevelop, uma ferramenta semelhante ao Netbeans, para desenvolvimento em C#.

Após a instalação da arquitetura, execute o monodevelop no Linux e procure ambientar-se a ele. Para tanto, vamos iniciar executando um Hello World da arquitetura Clarion, utilizando o mono. Os demos estão no diretório Samples.

A idéia é seguirmos todos os tutoriais nesta e na próxima aula. Pode parecer muita coisa, a princípio, mas a maioria dos tutoriais correspondem a apenas pequenos trechos de código. Muitas partes da arquitetura ainda não foram implementadas no Clarion, e correspondem somente a templates pré-preparados para quando essas partes ficarem disponíveis. Existem diversas maneiras de se seguir os tutoriais. Poderíamos pegar um dos sub-sistemas, por exemplo, o ACS, e ir do básico, passando pelo intermediário e depois o avançado, e depois passar para outro subsistema, como MS e MCS. Sugiro que sigam a sequência da versão compactada disponibilizada acima. Entretanto, caso prefiram uma outra sequência, não há problemas.

Durante a leitura do tutorial, haverão vários pontos que podem ficar pouco claros. Para dirimir essas dúvidas, o texto da especificação detalhada, também disponibilizada anteriormente, será o contraponto perfeito. Caso as dúvidas não se solucionem com essa leitura, não hesite em chamar o professor para ajudá-lo. 

Para auxiliar a compreensão da arquitetura Clarion, estou disponibilizando alguns slides de um curso de Clarion que dei. Esses slides podem ser encontrados aqui.