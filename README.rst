SION
=======

O Sistema de Inscrição Online de Processos Seletivos - SION foi desenvolvido em JAVA 8, utilizando os frameworks JSF, PrimeFaces, Hibernate com JPA, SpringSecurity para a autenticação e controle dos usuários, JasperRepot e IReport para a geração de relatórios, JUnit para os testes e o Maven para gerenciar as dependências, além de utilizar o JRebel para diminuir o redeploy. O sistema gera boleto com o BOPEPO e lê o retorno do banco com o auxilio do Textgit.

O sistema foi desenvolvido com o intuito de facilitar o gerenciamento das inscrições de um processo seletivo ou concurso, sendo a  confirmação da inscrição feita de forma automática, através do arquivo de retorno do banco, ou manualmente.


Quickstart
----------

Para o ambiente de desenvolvimento é necessário ter as seguintes ferramentas:

`Java`::

   Java SE Development Kit 8 update 77
      Versão utilizada no desenvolvimento do sistema

   Java SE Development Kit 7 update 67
      O iReport só abre com o JDK 7, portanto se faz necessário caso queira alterar algum relatório

Ferramenta de desenvolvimento::

   NetBeans IDE 8.1

Ferramenta de desenvolvimento de relatórios::

   iReport-5.6.0
   
SGBD (banco de dados)::

   MariaDB
      Fornecido através do pacote de aplicativos XAMPP

Servidor de aplicação::

   GlassFish Server Open Source Edition 5.0

Glassfish Configuration
-----------------------

Para implantar o projeto é necessário realizar algumas configurações

Console de administração do domínio::

   http://localhost:4848/

Configuração Data Source de Banco
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Criar o Pool de conexões (JDBC Connection Pools) ::

MySQL::

   Pool Name: SionPool
   Resource Type: javax.sql.DataSource
   DataSource Classname: com.mysql.jdbc.jdbc2.optional.MysqlDataSource
   Properties:
      User: user_sion
      Password: siondb
      URL: jdbc:mysql://localhost:3306/siondb
      Port:3306
      ServerName: localhost

PostgreSQL::

   Pool Name: SionPool
   Resource Type: javax.sql.ConnectionPoolDataSource
   DataSource Classname: org.postgresql.ds.PGConnectionPoolDataSource
   Properties:
      user: user_sion
      password: siondb
      portNumber: 5433
      serverName: localhost
      databaseName: siondb
      ssl:false

JDBC Resources ::

   jndiName: jdbc/SionDS
   PoolNane: SionPool
   Status: enabled

Configuração JMS de fila (queue)
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

JMS Destination Resource ::

   JNDI Name: jms/emailQueue
   Physical Destination Name: emailQueue
   Resource Type: javax.jms.Queue
   Status: Enabled

JMS Connection Factory ::

   JNDI Name: jms/emailQueueFactory
   Resource Type: javax.jms.QueueConnectionFactory
   Status: Enabled

JavaMail Session
~~~~~~~~~~~~~~~~

::

   JNDI Name: mail/gmailSMTP
   Mail Host: smtp.gmail.com
   User Default: <email>@gmail.com
   Default Sender Address: <email>@gmail.com
   Status: Enabled
   Properties:
      mail.smtp.socketFactory.class: javax.net.ssl.SSLSocketFactory
      mail.smtp.socketFactory.port: 465
      mail.smtp.socketFactory.fallback: false
      mail.smtp.port: 465
      mail.smtp.auth: true
      mail.smtp.password: <senha do email>
