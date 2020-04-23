# Aula 4 - SOAR: Tutorial 3

### Objetivos

Na aula de hoje, seguiremos o tutorial 3 do SOAR. Neste tutorial, o objetivo é lidar com problemas mais complexos, que exigem a decomposição de uma meta em metas intermediárias (subgoals), de forma a ser resolvido. Para isso, utilizaremos o pequeno jogo TankSoar, desenvolvido pelo grupo do SOAR para ilustrar situações mais complexas. O nosso agente no TankSoar possuirá agora um grande número de possíveis ações e comportamentos possíveis, e o Soar deverá ser capaz de elaborar uma estratégia que coordene essas ações no tempo. Isso exigirá o uso do mecanismo de impasses e a criação de sub-estados, que iremos exercitar durante a aula.

---
### Atividade 1

Execute o programa TankSoar e se familiarize com seu funcionamento. Verifique quais os sensores disponíveis e quais as ações possíveis a cada tanque, bem como os objetos que podem ser encontrados no ambiente. Crie 2 agentes com a base de regras simple-bot.soar e execute o programa.

---
### Atividade 2

Execute as atividades previstas no Tutorial 3.

---
### Atividade 3

Desenvolva um programa Soar, utilizando o mapa do ambiente, para fazer com que o tanque, além de suas atividades normais, dirija-se aos carregadores de energia e saúde, quando estiverem com baixa energia ou baixa saúde.