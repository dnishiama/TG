# TG1 - 5º semestre de BD

Professor da Disciplina: Giuliano Bertoti 

# TG

Aluno: Douglas Hiromi Nishiama - RA: 1460281623012

Orientador: A definir 

Título do TG: GESTOR DE OUTSOURCING DE IMPRESSÃO

### 1. INTRODUÇÃO
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A FVE é composta por 5 unidades distribuídas em 4 unidades em São José dos Campos e 1 unidade em Jacareí. O Service Desk é composto por 4 técnicos, que atendem todas as unidades a partir da unidade Urbanova.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Devido ao alto volume de impressão, falta de mão de obra qualificada, diversidade de modelos de equipamentos, custo elevado de consumíveis e de peças, o sistema de outsourcing de impressão foi considerado e adotado pela FVE.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O primeiro contrato foi feito com a empresa X (por questões éticas, a empresa citada será tratada como empresa X), basicamente baseava-se em uma franquia com um volume de impressão mínimo mensal. Se o número de impressões superasse essa franquia, o excedente era cobrado por página impressa, além disso, impressoras adicionais ao contrato inicial tinham um custo adicional, variando de preço dependendo do modelo e do tipo de equipamento. As manutenções preventivas e corretivas também estavam previstas no contrato.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dado o início do contrato, foi feito uma vistoria nos equipamentos da FVE e os equipamentos que precisaram de manutenção corretiva ou preventiva foram levados para a empresa para o procedimento e os equipamentos que não eram viáveis para o negócio foram substituídas por equipamentos locados da empresa X.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Logo no início do contrato surgiu o primeiro problema, a coleta dos contadores de impressão era feita de modo manual, onde o técnico se deslocava até o local de instalação de cada equipamento e imprimia o relatório da impressora. Este método era inviável pois ao imprimir estes relatórios, desperdiçava-se muitas folhas e a FVE pagava por estas páginas impressas, além de ocupar o tempo de um colaborador para realizar tal tarefa. 
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Outros problemas foram surgindo no decorrer do contrato, suprimentos com vazamento de toner e de má qualidade, chamados de manutenção sem atendimento e mão de obra desqualificada. Isso tudo além de prejudicar o andamento dos trabalhos cotidianos da FVE, deixava a equipe de Service Desk com inúmeros chamados, atrasando o atendimento de outras demandas.
Devido a estes problemas e ao fato de que ao renovar o contrato haveria um reajuste, surgiu a necessidade de buscar outras soluções no mercado, foi iniciado um projeto de troca de fornecedor.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Os requisitos desse projeto consistiam em alocar equipamentos de um mesmo fabricante e modelo para centralizar e facilitar o gerenciamento dos suprimentos, máquinas com maiores capacidades de impressão por minuto, suprimentos originais ou paralelos e a coleta dos dados deveria ser de maneira automática. Quanto à manutenção, o requisito era de um tempo de atendimento de no máximo 36 horas.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Foi elaborado um documento com os requisitos da FVE e enviado às empresas concorrentes. Após receber todas as propostas, foi feito uma análise e as empresas que não atenderam os requisitos já foram eliminadas nesta fase. O critério de escolha final foi o menor preço.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Definido a empresa que seria contratada (por questões éticas, será citada como empresa Y), foi elaborado um plano de implementação pois no contrato vigente da empresa X havia uma cláusula de exclusividade, impossibilitando a troca previa dos equipamentos.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No dia do encerramento do contrato da empresa X, foi mobilizado equipes da empresa Y e do Service Desk da FVE para distribuir os equipamentos priorizando os setores críticos. A implantação foi realizada com alguns problemas, porém resolvidos rapidamente.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Após a implementação, a solução oferecida de coleta de dados dos equipamentos foi o software Print Supervision da empresa Oki Data, parceira da empresa contratada. Após configurar todos os equipamentos no software, foi configurado o envio de e-mails com os relatórios.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Esta aplicação atende os requisitos solicitados, porém surgiu a necessidade de auditar os dados recebidos da empresa e de manter um histórico de produção de cada equipamento de modo que o estudo pudesse ser feito de maneira rápida e independente da empresa Y.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A partir dessa necessidade, surgiu a ideia de realizar uma aplicação Web para gerir e auditar os dados de cobrança recebidos da empresa, baseando a auditoria no histórico de contadores coletados diretamente das impressoras e comparando com os dados recebidos pela empresa Y.

#### 1.1. Objetivos do Trabalho 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O objetivo geral deste trabalho é desenvolver um sistema web capaz de auditar as cobranças do serviço de outsourcing de impressão e manter um histórico de produção de cada equipamento.
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Para a consecução deste objetivo foram estabelecidos os objetivos específicos:
- Tratar de maneira individual como é feito a comunicação dos equipamentos via SNMP, pois cada modelo de equipamento é feito de maneira distinta.
- Equipamentos não conectados a rede, deverão ter seus dados inseridos manualmente.
- Armazenar os dados coletados dos equipamentos em um banco de dados.
- A auditoria das faturas baseia-se no confronto dos dados recebidos no relatório da fatura com os dados já armazenados no banco de dados (coletado diretamente dos equipamentos) e se não houver divergência nos dados armazenar no banco de dados na tabela de fatura.
- Após a fatura passar pela auditoria, o gestor de cada área receberá um e-mail com a produção do(s) equipamento(s) sob seu comando.

### 2. FUNDAMENTAÇÃO TÉCNICA

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Para obter informações da impressora pela rede, utiliza-se o protocolo Simple Network Management Protocol (SNMP), aos quais estão modelados em bases de informações chamados Management Information Base (MIB).<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A linguagem de programação utilizada para desenvolver a aplicação foi o JAVA, através do framework Spring e para realizar a integração dos dispositivos a serem monitorados e a aplicação foi utilizado a biblioteca SNMP4J que fornece toda as dependências necessárias para a comunicação.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O banco de dados de dados utilizado foi o MYSQL pela sua compatibilidade com o framework Spring, interface amigável e custo.

#### 2.1. O modelo OSI
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Para entender como o a aplicação irá receber as informações da impressora, se faz necessário entender como os dados trafegam pela rede.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O modelo OSI não é uma arquitetura de rede, analisando pela perspectiva que o modelo não especifica os protocolos utilizados. Porém é correto afirmar que o modelo OSI é um padrão para protocolos de rede, isto é, regras de comunicação utilizadas para estabelecer a comunicação entre dois ou mais equipamentos em uma rede.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estas regras são divididas em 7 camadas (TABELA ABAIXO): Física, Enlace, Rede, Transporte, Sessão, Apresentação e Aplicação.

Camada -	Função<br>
7ª Camada - Aplicação -	Funções especializadas.<br>
6ª Camada - Apresentação -	Formatação de dados e conversão de caracteres e códigos.<br>
5ª Camada - Sessão	- Negociação e estabelecimento de conexão com outro nó.<br>
4ª Camada - Transporte -	Meios e métodos para a entrega de dados ponta-a-ponta.<br>
3ª Camada - Rede	- Roteamento de pacotes através de uma rede ou várias.<br>
2ª Camada - Enlace	- Detecção e correção de erros introduzidos pelo meio de transmissão.<br>
1ª Camada - Física	- Transmissão dos bits através dos meios físicos.<br>

#### 2.1.1. Camada Física
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A primeira camada é a camada Física, nesta camada estão os dispositivos por onde os dados são transmitidos por sinais elétricos, óticos ou eletromagnéticos.

#### 2.1.2. Camada de Enlace ou Ligação
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A camada de enlace é responsável pelo controle de quadros que um emissor envia, garantindo que o emissor enviará apenas um volume de quadros que o receptor pode processar.

#### 2.1.3. Camada de Rede
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Uma das suas principais funções é fazer o roteamento dos quadros de dados entre o host de origem com o host de destino através de uma ou mais redes. Uma outra função desta camada é fragmentar os pacotes e remontar para atender as limitações de algumas subredes. 

#### 2.1.4. Camada Transporte
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A principal função desta camada, é definir como e por onde os pacotes trafegaram pela rede. Nesta camada, os dados são recebidos da camada de sessão e particionados em pacotes menores. Esta camada possui dois tipos de serviços principais, um serviço rápido que as mensagens são limitadas em tamanho, mas não existe garantia de entrega, ordem de recebimento ou ausência de duplicação e um serviço lento de alta confiabilidade e sem limites de tamanho nas mensagens.

#### 2.1.5. Camada Sessão
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A principal função desta camada é estabelecer uma conexão com o outro nó de aplicação na rede. 

#### 2.1.6. Camada Apresentação
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A função da camada de apresentação é garantir que a informação transmitida possa ser entendida e utilizada pelo receptor.

#### 2.1.7. Camada Aplicação
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A camada de aplicação é a camada que possui o maior número de protocolos existentes pois esta camada é a mais próxima do usuário e pelo fato de cada usuário possui uma necessidade diferente. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;É nesta camada que temos os protocolos mais conhecidos como o HTTP, FTP, DNS, SMTP e o protocolo utilizado neste projeto o SNMP.

#### 2.2. O Protocolo SNMP
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O Protocolo SNMP teve sua origem na RFC 1067 de 1988 e está atualmente na versão 3. Em síntese, este protocolo basicamente facilita a troca de informações de gerenciamento entre os dispositivos da rede. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O SNMP foi desenvolvido para facilitar o monitoramento e gerenciamento de ativos de rede. Atualmente, é um dos protocolos mais utilizados para monitoramento para essa finalidade devido ao fato de trabalhar com informações de equipamentos de diversos fabricantes. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;É um protocolo da camada de aplicação do modelo OSI e utiliza por padrão a porta 161 do protocolo de transporte UDP. Esta característica, permite a abstração das outras camadas e permite o gerenciamento de ativos de fora da rede.

#### 2.3. O Framework Spring
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O Spring é um framework baseado em Java criado com o objetivo de facilitar o desenvolvimento de aplicações, explorando os conceitos de Inversão de Controle e Injeção de Dependências. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ao adotá-lo, se tem à nossa disposição uma tecnologia que fornece recursos necessários para grande parte das aplicações, como módulos para 
 
### 3. DESENVOLVIMENTO
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nesse projeto foi utilizado o padrão de projeto MVC (ModelView-Controller) e implementado pelo framework Spring. 

#### 3.1. Arquitetura do Sistema
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Foi criado uma classe Agente que busca os dados nas impressoras e cadastra no banco de dados. Com base nas informações do Agente, a classe Impressora é atualizada com o ultimo contador obtido pelo Agente.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ao receber as faturas, é necessário checar se as informações recebidas são coerentes com os contadores das impressoras. Para isso, o demonstrativo recebido é lido pela aplicação através de um submit manual do arquivo e cada informação é conferida cruzando os dados da classe Impressora com as linhas do demonstrativo.

#### 3.2. Classe Agente
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O Agente é responsável por fazer atualização do banco de dados em tempo real, e possui uma tabela no banco de dados exclusiva para ele.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O Agente pesquisa o IP da impressora na classe Impressora e se conecta com o equipamento através do protocolo SNMP, obtém primeiro os dados de fabricante e modelo e a partir de cada fabricante e modelo é feito um filtro com os OID’s relevantes para este projeto, insere estas informações obtidas no banco de dados e atualiza o atributo ContadorAtual da classe Impressora. 

#### 3.3. Classes Model 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Neste projeto, há apenas 3 classes Model: Impressora, Departamento e Gestor. 

#### 3.3.1. Impressora
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A classe Impressora possui os atributos id do tipo long, patrimônio do tipo int, fabricante do tipo String, modelo do tipo String, serial do tipo String, ip do tipo String, macAddress do tipo String, contadorAtualMono do tipo int e contadorAtualColor do tipo int.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Seus métodos são os métodos getters, setters e o constructor. 

#### 3.3.2. Departamento
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A classe Departamento possui os atributos id do tipo long, campus do tipo String, bloco do tipo String, departamento do tipo String, custo do tipo String, gestor do tipo Gestor.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Seus métodos são os métodos getters, setters e o constructor. 

#### 3.3.3. Gestor
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A classe Gestor possui os atributos id do tipo long, nome do tipo String, email do tipo String.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Seus métodos são os métodos getters, setters e o constructor. 

