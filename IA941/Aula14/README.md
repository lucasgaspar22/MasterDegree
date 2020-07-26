# Aula 14 - CST: Controlando o WorldServer3D

### Objetivo

O objetivo da aula de hoje é utilizar o CST - Cognitive Systems Toolkit, sendo desenvolvido pelo meu grupo de pesquisa, para controlar uma criatura artificial no WorldServer3D, de modo análogo ao que foi feito com o SOAR, Clarion e LIDA. Para tanto, é necessário que entendamos o funcionamento do CST, e desenvolvamos uma mente artificial usando o CST para controlar a criatura.

### Atividade

A proposta desta atividade é desenvolver um sistema de controle (uma mente artificial) para a criatura do WorldServer3D, utilizando o CST. Nosso desafio será semelhante ao desafio que foi proposto na implementação com o SOAR. A criatura deve obter sua meta de aquisição de jóias a partir de seu "leaflet", e buscar de maneira mais rápida possível cumprir a busca de jóias que lhe foi delegada. Inicialmente desenvolva as atividades propostas no Tutorial I do CST.

Links para o código:

* CST: https://github.com/CST-Group/cst

* DemoCST: https://github.com/CST-Group/DemoCST

* WorldServer: https://github.com/CST-Group/ws3d

* WS3DProxy: https://github.com/CST-Group/WS3DProxy
  
### Observações

Os projetos CST e DemoCST necessitam da ferramenta Gradle para compilar. Caso esteja utilizando o Netbeans, é necessário instalar os seguinte plugins para projetos com o Gradle funcionarem:

* Gradle
* Groovy

Para instalar os plugins, a partir do Netbeans selecione:

Ferramentas -> Plugins

Clique em: Plugins Disponíveis

E selecione os plugins Gradle e Groovy, e conclua a instalação dos plugins. Não é necessário ser root para instalar os plugins. Eles ficam instalados no seu usuário. Tendo problemas, peça ajuda ao professor.