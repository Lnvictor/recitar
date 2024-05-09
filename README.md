# ccb-recitativos

### Objetivo
Sistema implementado para o controle das contagens de recitativos das reuniões de jovens e menores da comum congregação
do Jardim Santa Mônica - Campinas-SP

Com o sistema, o usuário será capaz de cadastrar novas contagens, 
bem como editar e deletar contagens passadas.

Voce pode acessar pelo seguinte link: [https://ccb-recitativos.onrender.com/login](https://ccb-recitativos.onrender.com/login)

### Tecnologias Utilizadas

 - Spring Boot (JAVA)
 - PostgreSQL
 - Docker

### Prévia

![img_1.png](img_1.png)

### O que falta ser implementado

- Acesso por roles (Leitura, escrita, admin, etc...)
- Registrar quem atendeu o culto
- Tela de perfil
- Implementar validação para add contagens somente aos domingos(visto que as reuniões ocorrem neste dia)
- Registrar reuniões de auxiliares (PARTES DE PALAVRA)
- Implementar CI/CD

### Bugs conhecidos

- [#3 - Edit não funciona](https://github.com/Lnvictor/ccb-recitativos/issues/3)