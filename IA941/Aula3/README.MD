# Aula 3 - SOAR: Tutorial 2
---
## Objetivos

Na aula de hoje, desenvolveremos o Tutorial 2 do Soar. Particularmente, utilizaremos o Soar para controlar o comportamento de criaturas artificiais bem simples, os assim chamados "Eaters" em um jogo do tipo Pacman.  Para tanto, utilizaremos o mecanismo de estados e operadores do Soar para diagnosticar o estado de cada Eater, propor diferentes tipos de ações a cada instante e mostrar como o Soar faz para interfacear com o jogo, para controlá-lo de fato.

---
## Atividade 1

Execute os passos para inicializar o jogo Eaters, conforme consta no Tutorial 2 do Soar, e crie uma criatura com as regras ```move-to-food.soar```. Execute o programa e explique o que acontece quando o programa é executado. Clique em Reset, e execute agora o programa passo a passo, para entendê-lo melhor. Clone o agente, e refaça a simulação. Edite o programa ```move-to-food.soar``` com o Visual Soar e tente entender sua lógica. Como o estado atual da criatura é considerado nas regras ? Como a decisão de ação escolhida é aplicada de fato ao jogo ? Como esse conjunto de regras escolhe a direção para a qual a criatura deve se mover ? Para que serve a regra ```apply*move-to-food*remove-move``` ? O que aconteceria se ela não existisse ? Quais são as limitações desse programa ? O que seria necessário fazer para que ele não ficasse paralizado, depois de um tempo ?

---

## Atividade 2

Execute as atividades descritas nas seções de 2 a 5 do Tutorial, para consolidar seu conhecimento sobre:

* Uso da interface de entrada e saída de um programa Soar
* Uso de shortcuts em programas Soar
* Uso do SoarJavaDebugger para acompanhar o processo de escolha e aplicação de operadores, por meio de traces.
* Diferença entre ações o-supported e i-supported, e WMEs persistentes
* Uso de preferências entre operadores
* Uso de extensões em regras
* Uso do VisualSoar para detectar erros em regras
* Uso de comandos do Debugger em tempo de execução
---
## Atividade 3

Desenvolva um programa Soar que não fique "preso" em posições que não contenham comida, que sejam capazes de executar jumps e que não fiquem "indo e voltando" caso tenham se movido para uma determinada direção (seções 6 a 8 do tutorial). 

---
## Atividade 4

Utilizando a estrutura top-state descrita na seção 10 do tutorial, tente desenvolver um conjunto de regras Soar que sistematicamente busque por comida quando estiver cercado por células sem comida.

---

## ATENÇÃO:

Alguns alunos estavam com problema na execução do Eaters.sh, que durante o carregamento de um agente, acabava capotando. Este problema não tem nada a ver com o programa Eaters.sh, mas é um bug no tema oxygen-gtk na hora de renderizar a interface gráfica do Eaters.sh. Aparentemente, há problemas nos parâmetros default do oxygen, que leva o programa a um crash, quando este tenta desenhar uma parte da tela. Para que o aplicativo rode normalmente, é necessário fazer a seguinte alteração, em um console:

```
cd
rm .gtkrc-2.0
touch .gtkrc-2.0
```

Essa alteração é necessária, pois o simples apagamento do ```.gtkrc-2.0``` (que instala o tema oxygen) resolve somente parcialmente o problema, pois após um novo login do usuário, o arquivo é reconstruído. Após essa alteração, o ```.gtkrc``` fica permanentemente vazio (o comando touch cria um arquivo com 0 bytes) e os aplicativos do Soar devem funcionar normalmente. Caso isso não aconteça, entre em contato com o professor.