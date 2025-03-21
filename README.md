
# 📊 Occurrence Tracking API

Bem-vindo à **Occurrence Tracking API**! Esta API foi desenvolvida para realizar o gerenciamento de ocorrências vinculadas a clientes e seus respectivos endereços, com suporte a upload de evidências em imagens utilizando o **MinIO** como storage. O projeto inclui autenticação com **JWT**, arquitetura em camadas, testes unitários e integração com o banco de dados **PostgreSQL**.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- **Flyway**
- MinIO (Storage de Evidências)
- Swagger/OpenAPI
- JUnit + Mockito (Testes Unitários)
- Docker

## 🧱 Arquitetura do Projeto

- **Controller**: Camada de entrada da API (HTTP)
- **Service**: Regras de negócio
- **Repository**: Acesso ao banco de dados com JPA + consultas customizadas
- **DTOs**: Transferência de dados entre camadas
- **Entities**: Representações das tabelas do banco
- **MinIO**: Upload e acesso a imagens como evidências de ocorrências

## ✅ Funcionalidades

- Registro de ocorrências
- Upload de evidências por ocorrência
- Listagem e busca com filtros (nome, CPF, cidade, data)
- Cadastro, busca e exclusão de clientes
- Cadastro, busca e exclusão de endereços
- Autenticação e registro de usuários com JWT
- Endpoint `/me` para verificar usuário autenticado
- Documentação Swagger em `/swagger-ui/index.html#`

## 🔐 Segurança

A autenticação é feita via **JWT Token**. Após realizar o login, inclua o token retornado no header das suas requisições:

```
Authorization: Bearer <seu_token>
```

## 🐳 Como rodar o projeto com Docker

### 1. Clone o projeto

```bash
git clone https://seu-usuario@bitbucket.org/seu-usuario/occurrence-tracking-api.git
cd occurrence-tracking-api
```

### 2. Execute os containers com Docker Compose

```bash
docker-compose up -d
```

Isso irá subir:
- Banco de dados PostgreSQL
- MinIO
- API

### 3. Acesse a aplicação

- Swagger UI: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#)
- MinIO Console: [http://localhost:9000](http://localhost:9000)
  - Usuário: `admin`
  - Senha: `admin123`

## 🧪 Executando Testes

Para rodar os testes unitários:

```bash
./mvnw test
```

Os testes cobrem:
- Repositórios
- Serviços de Cliente, Endereço, Ocorrência e Fotos
- Upload para MinIO
- Validações de regra de negócio

## 📂 Estrutura de Pastas

```
src/
├── controller
├── dto
├── entity
├── repository
├── repository.implement
├── service
├── util
└── security
```

## 📝 Exemplo de Uso - Registro de Ocorrência

```http
POST /ocorrencias
Authorization: Bearer <token>
Content-Type: application/json

{
  "clienteId": 1,
  "enderecoId": 1,
  "dataOcorrencia": "2025-03-20T14:00:00"
}
```
## 📝 Exemplo de Uso - Cadastro de Cliente

```http
POST /ocorrencias
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Rafaela Alves",
  "cpf": "29540986764",
  "dataNascimento": "2025-03-21T00:01:50.053Z"
}

```

## 📝 Exemplo de Uso - Cadastro de Endereco

```http
POST /ocorrencias
Authorization: Bearer <token>
Content-Type: application/json

{
  "logradouro": "Avenida Liberdade, 117",
  "bairro": "Belas Vista",
  "cep": "13770-000",
  "cidade": "Caconde",
  "estado": "São Paulo"
}

```
## 🙋‍♂️ Autor

Desenvolvido por Robson Ramos — 2025  
Caso tenha dúvidas ou sugestões, entre em contato!

---

⭐ Se você gostou do projeto, não esqueça de dar uma estrela no repositório!
