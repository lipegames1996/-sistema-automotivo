# Sistema Automotivo — Gestão de Estoque de Veículos

## Sobre o projeto

O Sistema Automotivo é uma API REST desenvolvida em Java com Spring Boot e MySQL para gerenciar o estoque de veículos de uma empresa do setor automotivo.

O projeto foi desenvolvido como atividade acadêmica do curso de Análise e Desenvolvimento de Sistemas (ADS).

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.0.9
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Git e GitHub
- Visual Studio Code

## Funcionalidades

### Gerenciamento de marcas
- Cadastrar marcas.
- Listar marcas.
- Consultar uma marca pelo ID.
- Atualizar marcas.
- Excluir marcas.

### Gerenciamento de modelos
- Cadastrar modelos.
- Listar modelos.
- Consultar modelos pelo ID.
- Atualizar modelos.
- Excluir modelos.
- Associar modelos às respectivas marcas.

### Gerenciamento de veículos
- Cadastrar veículos.
- Listar veículos.
- Consultar veículos pelo ID.
- Atualizar informações dos veículos.
- Excluir veículos.
- Associar veículos aos modelos cadastrados.

### Filtros de pesquisa
- Marca.
- Modelo.
- Ano.
- Preço mínimo.
- Preço máximo.
- Disponibilidade.

## Arquitetura do projeto

O sistema utiliza uma arquitetura organizada em camadas:

- **Model:** representa as entidades do sistema.
- **Repository:** realiza a comunicação com o banco de dados.
- **Service:** implementa as regras de negócio.
- **Controller:** disponibiliza os endpoints da API REST.

## Entidades

### Marca
- id
- nome

### Modelo
- id
- nome
- marca

### Veículo
- id
- marca
- modelo
- ano
- cor
- preco
- quilometragem
- disponivel
- modeloAssociado

## Endpoints da API

### Marcas

| Método | Endpoint | Funcionalidade |
|---|---|---|
| POST | /marcas | Cadastrar marca |
| GET | /marcas | Listar marcas |
| GET | /marcas/{id} | Consultar marca |
| PUT | /marcas/{id} | Atualizar marca |
| DELETE | /marcas/{id} | Excluir marca |

### Modelos

| Método | Endpoint | Funcionalidade |
|---|---|---|
| POST | /modelos | Cadastrar modelo |
| GET | /modelos | Listar modelos |
| GET | /modelos/{id} | Consultar modelo |
| PUT | /modelos/{id} | Atualizar modelo |
| DELETE | /modelos/{id} | Excluir modelo |

Também é possível filtrar modelos utilizando:

GET /modelos?marcaId=1

### Veículos

| Método | Endpoint | Funcionalidade |
|---|---|---|
| POST | /veiculos | Cadastrar veículo |
| GET | /veiculos | Listar veículos |
| GET | /veiculos/{id} | Consultar veículo |
| PUT | /veiculos/{id} | Atualizar veículo |
| DELETE | /veiculos/{id} | Excluir veículo |

Exemplo de pesquisa com filtros:

GET /veiculos?marca=Toyota&ano=2023&disponivel=true

## Banco de dados

O sistema utiliza MySQL para armazenar as informações.

Banco de dados utilizado:

sistema_automotivo

As entidades são mapeadas utilizando JPA e Hibernate.

## Como executar o projeto

### Pré-requisitos

- Java 21
- MySQL
- Git
- Maven ou Maven Wrapper

### Configuração

1. Clone o repositório.
2. Configure o MySQL.
3. Crie o banco de dados `sistema_automotivo`.
4. Configure a variável de ambiente `DB_PASSWORD` com a senha do MySQL.
5. Verifique a configuração de conexão em `application.properties`.

O projeto está configurado para utilizar o usuário `root` do MySQL local.

### Executar no Windows

Na pasta principal do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

http://localhost:8080

## Testes realizados

Foram realizados testes de:

- Cadastro de marcas, modelos e veículos.
- Consulta por ID.
- Listagem de registros.
- Atualização de informações.
- Exclusão de registros.
- Associação entre marcas, modelos e veículos.
- Filtragem de veículos por múltiplos critérios.
- Persistência de dados no MySQL.

## Autor

Projeto acadêmico desenvolvido por Luis Filipe Fonseca Lino.

Curso: Análise e Desenvolvimento de Sistemas (ADS).