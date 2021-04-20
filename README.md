# Trabalho de Graduação - Back-End

Aluno: Douglas Hiromi Nishiama - RA: 1460281623012

Orientador: Eduardo Sakaue

Título: Gestor de Outsourcing de Impressão

## Descrição
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Trabalho desenvolvido para monitorar, armazenar e gerar rateios automáticos para o serviço de Outsourcing de impressão.

## Fundamentos técnicos
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A linguagem de programação utilizada para desenvolver o back-end da aplicação foi o JAVA, através do Spring Framework e para realizar a integração dos dispositivos a serem monitorados e a aplicação foi utilizado a biblioteca SNMP4J que fornece toda as dependências necessárias para a comunicação. E para o front-end foi utilizada a linguagem de programação Javascript através do Vue Framework.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O banco de dados de dados utilizado foi o MYSQL.

## Rotas
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Abaixo seguem as rotas desenvolvidas:<br>

### Impressora:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de todas as impressoras: http://localhost:8082/printerManager/impressora/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de impressora por Id: http://localhost:8082/printerManager/impressora/:id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Post de uma nova impressora de rede: http://localhost:8082/printerManager/impressora/cadastrar<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Post de uma nova impressora USB: http://localhost:8082/printerManager/impressora/cadastraroffline<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Delete de impressora por Id: http://localhost:8082/printerManager/impressora/deletar/:id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put de uma atualização de impressora por Patrimonio: http://localhost:8082/printerManager/impressora/atualizar/:patrimonio<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put de uma atualização dos contadores de todas as impressora: http://localhost:8082/printerManager/impressora/agente<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put de uma atualização dos contadores de impressora por Serial: http://localhost:8082/printerManager/impressora/contador/:serial<br>

### Departamento:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de todos os departamentos: http://localhost:8082/printerManager/departamento/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de departamento por Id: http://localhost:8082/printerManager/departamento/:Id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de departamento por Gestor (Gestor Id): http://localhost:8082/printerManager/departamento/gestor/:Id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Post de um novo departamento: http://localhost:8082/printerManager/departamento/cadastrar<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put de atualização de um departamento por Id: http://localhost:8082/printerManager/departamento/atualizar/:Id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Delete de um departamento por Id: http://localhost:8082/printerManager/departamento/deletar/:Id<br>

### Gestor:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de todos os gestores: http://localhost:8082/printerManager/gestor/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de gestor por Id: http://localhost:8082/printerManager/gestor/:Id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de gestor por Email: http://localhost:8082/printerManager/gestor/gestor/email/:Email<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Post de um novo gestor: http://localhost:8082/printerManager/gestor/cadastrar<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Put de atualização de um gestor por Id: http://localhost:8082/printerManager/gestor/atualizar/:Id<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Delete de um gestor por Id: http://localhost:8082/printerManager/gestor/deletar/:Id<br>

### Histórico:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de todos os historicos: http://localhost:8082/printerManager/historico/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Post de um novo historico: http://localhost:8082/printerManager/historico/cadastrar<br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Get de todos os historicos por Mês e Ano agrupados por Centro de Custos: http://localhost:8082/printerManager/historico/:Mes/:Ano<br>
