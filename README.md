# Trabalho de Graduação

Aluno: Douglas Hiromi Nishiama - RA: 1460281623012

Orientador: Eduardo Sakaue

Título: Gestor de Outsourcing de Impressão

## Descrição
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trabalho desenvolvido para monitorar, armazenar e gerar rateios automáticos para o serviço de Outsourcing de impressão.

## Fundamentos técnicos
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A linguagem de programação utilizada para desenvolver o back-end da aplicação foi o JAVA, através do Spring Framework e para realizar a integração dos dispositivos a serem monitorados e a aplicação foi utilizado a biblioteca SNMP4J que fornece toda as dependências necessárias para a comunicação. E para o front-end foi utilizada a linguagem de programação Javascript através do Vue Framework.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O banco de dados de dados utilizado foi o MYSQL.

## Rotas
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Abaixo seguem as rotas desenvolvidas para serem consumidas<br>

### Impressora:
Get de todas as impressoras: http://localhost:8082/printerManager/impressora/<br>
Get de impressora por Id: http://localhost:8082/printerManager/impressora/:id<br>
Post de uma nova impressora de rede: http://localhost:8082/printerManager/impressora/cadastrar<br>
Post de uma nova impressora USB: http://localhost:8082/printerManager/impressora/cadastraroffline<br>
Delete de impressora por Id: http://localhost:8082/printerManager/impressora/deletar/:id<br>
Put de uma atualização de impressora por Patrimonio: http://localhost:8082/printerManager/impressora/atualizar/:patrimonio<br>
Put de uma atualização dos contadores de todas as impressora: http://localhost:8082/printerManager/impressora/agente<br>
Put de uma atualização dos contadores de impressora por Serial: http://localhost:8082/printerManager/impressora/contador/:serial<br>

### Departamento:
Get de todos os departamentos: http://localhost:8082/printerManager/departamento/<br>
Get de departamento por Id: http://localhost:8082/printerManager/departamento/:Id<br>
Get de departamento por Gestor (Gestor Id): http://localhost:8082/printerManager/departamento/gestor/:Id<br>
Post de um novo departamento: http://localhost:8082/printerManager/departamento/cadastrar<br>
Put de atualização de um departamento por Id: http://localhost:8082/printerManager/departamento/atualizar/:Id<br>
Delete de um departamento por Id: http://localhost:8082/printerManager/departamento/deletar/:Id<br>

### Gestor:
Get de todos os gestores: http://localhost:8082/printerManager/gestor/<br>
Get de gestor por Id: http://localhost:8082/printerManager/gestor/:Id<br>
Get de gestor por Email: http://localhost:8082/printerManager/gestor/gestor/email/:Email<br>
Post de um novo gestor: http://localhost:8082/printerManager/gestor/cadastrar<br>
Put de atualização de um gestor por Id: http://localhost:8082/printerManager/gestor/atualizar/:Id<br>
Delete de um gestor por Id: http://localhost:8082/printerManager/gestor/deletar/:Id<br>

### Histórico:
Get de todos os historicos: http://localhost:8082/printerManager/historico/<br>
Get de todos os historicos por Mês e Ano: http://localhost:8082/printerManager/historico/:Mes/:Ano<br>
Post de um novo historico: http://localhost:8082/printerManager/historico/cadastrar<br>
