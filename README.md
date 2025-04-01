# 📊 Occurrence Tracking API

Bem-vindo à **Occurrence Tracking API**! Esta API permite o gerenciamento de ocorrências associadas a clientes e endereços, com upload de imagens como evidências utilizando o MinIO.

---

## ✅ Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL + Flyway
- MinIO (Armazenamento de imagens)
- Swagger/OpenAPI
- JUnit + Mockito (Testes Unitários)
- Docker e Docker Compose

---

## 🧱 Arquitetura do Projeto

- **Controller**: Camada de entrada da API (HTTP)
- **Service**: Regras de negócio
- **Repository**: Acesso ao banco com JPA
- **DTOs**: Comunicação entre camadas
- **Entities**: Representações do banco
- **MinIO**: Upload e acesso a evidências (imagens)

---

## ✅ Funcionalidades

- Registro e finalização de ocorrências
- Upload de evidências por ocorrência
- Listagem e filtros por nome, CPF, cidade e data
- Cadastro, busca e exclusão de clientes
- Cadastro, busca e exclusão de endereços
- Autenticação com JWT (registro e login)
- Verificação do usuário logado com o endpoint `/me`
- Documentação interativa com Swagger

---

## 🔐 Segurança

A autenticação é feita via **JWT**. Após realizar o login, inclua o token no header:

```http
Authorization: Bearer <seu_token>
```

---

## 🐳 Como rodar com Docker

```bash
# 1. Clone o repositório
git clone https://bitbucket.org/robinhokcond1/occurrence-tracking-api.git
cd occurrence-tracking-api

# 2. Execute com Docker Compose
docker-compose up -d
```

### 🤝 Acesso:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html#
- **MinIO Console**: http://localhost:9000  
  Usuário: `admin`  
  Senha: `admin123`

---

## 🥪 Executando Testes

```bash
./mvnw test
```

Os testes cobrem:

- Camada de serviço
- Repositórios (com Testcontainers)
- Upload de imagens (mockado)
- Regras de negócio e validações

---

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

---

## 📌 Exemplos de Uso

### ➕ Registrar Ocorrência
`POST /ocorrencias`

```json
{
  "clienteId": 1,
  "enderecoId": 1,
  "dataOcorrencia": "2025-03-20T14:00:00"
}
```

---

### ➕ Cadastrar Cliente
`POST /clientes`

```json
{
  "nome": "Rafaela Alves",
  "cpf": "29540986764",
  "dataNascimento": "2025-03-21T00:01:50.053Z"
}
```

---

### ➕ Cadastrar Endereço
`POST /enderecos`

```json
{
  "logradouro": "Avenida Liberdade, 117",
  "bairro": "Bela Vista",
  "cep": "13770-000",
  "cidade": "Caconde",
  "estado": "SP"
}
```

---

## 🙋‍♂️ Autor

Desenvolvido por **Robson Ramos** – 2025  
Caso tenha dúvidas ou sugestões, fique à vontade para entrar em contato!

---

## ⭐ Contribuição

Se você gostou do projeto, deixe uma ⭐ no repositório. Isso ajuda bastante!
