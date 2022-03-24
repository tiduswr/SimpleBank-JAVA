<p align="center">
	<img src="https://i.ibb.co/ysdMQVJ/logos-simple-bank.jpg" />
</p>

<p align="center">Projeto para o 5º Periodo do Curso de Computação!</p>

## Visão Geral

O sistema SimpleBANK permite um ambiente completo capaz de gerenciar um ambiente bancário simples, mas eficaz, utilizando de funcionalidades essenciais para o controle do negócio. Será capaz administrar cadastro de Clientes, Administradores, Contas Bancárias e Usuários. Descrevendo a classe PESSOA, teremos características de especialização onde os funcionários podem ser classificados em Cliente e Administradores, onde cada cargo será atribuído níveis de acesso ao sistema através da Classe Usuário. O sistema é capaz de realizar transações bancárias e armazenar os registros de históricos no banco de dados, garantindo assim um controle de ações realizadas. Utilizando o padrão MVC, a camada Model se comunica com o Controller para receber requisições do usuário e retornar os dados processados para ser apresentado nos Formulários(Camada View). A persistência de Dados é realizada através das Classes DAO, um padrão de projeto robusto e de fácil implementação, garantindo a integridade dos dados do Sistema. Através da Interface DatabaseConnect é possível implementar outros bancos de dados, possibilitando uma maior manutenção do Sistema.

- Diagrama de Classes:

<img src="https://i.ibb.co/J35z83G/UML-CLASS-SIMPLE-BANK.png" />

- Diagrama de Uso:

<img src="https://i.ibb.co/nRB44gZ/Simple-Bank-drawio.png" />

## Tela Exemplo do Sistema

<img src="https://i.ibb.co/RhMB4Jf/tela-exemplo.jpg" />