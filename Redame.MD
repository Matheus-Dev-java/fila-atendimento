# API de Fila de Atendimento

API REST desenvolvida em Java 21 com Spring Boot para gerenciamento de filas de atendimento. O sistema permite o cadastro de clientes, geracao de senhas numeradas, chamada do proximo cliente por ordem de prioridade e registro automatico do tempo de cada atendimento.

---

## Tecnologias

- Java 21
- Spring Boot 3.3.0
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Maven

---

## Pre-requisitos

- Java 21 instalado
- Maven instalado
- PostgreSQL instalado e rodando localmente
- Thunder Client (extensao do VS Code) ou Postman para testar os endpoints

---

## Como rodar o projeto

**1. Criar o banco de dados**

Abra o terminal com o psql disponivel no PATH e execute:

```bash
psql -U postgres -c "CREATE DATABASE fila_atendimento;"
```

No Windows, se o psql nao estiver no PATH, use o caminho completo:

```bash
"/c/Program Files/PostgreSQL/17/bin/psql" -U postgres -c "CREATE DATABASE fila_atendimento;"
```

Ou abra o pgAdmin, clique com o botao direito em Databases e crie um banco chamado `fila_atendimento`.

**2. Configurar as credenciais**

Abra o arquivo `src/main/resources/application.properties` e ajuste usuario e senha conforme sua instalacao local do PostgreSQL:

```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**3. Subir a aplicacao**

```bash
mvn spring-boot:run
```

A aplicacao sobe na porta `8080`. As tabelas sao criadas automaticamente pelo Hibernate na primeira execucao.

---

## Endpoints

### Clientes

**Cadastrar cliente**

```
POST http://localhost:8080/clientes
```

Body:
```json
{
  "nome": "Carlos Souza",
  "tipoPrioridade": "NORMAL"
}
```

O campo `tipoPrioridade` aceita apenas dois valores: `NORMAL` ou `PREFERENCIAL`.

Retorno:
```json
{
  "id": 1,
  "nome": "Carlos Souza",
  "tipoPrioridade": "NORMAL",
  "dataCadastro": "2026-05-13T10:00:00"
}
```

---

**Listar todos os clientes**

```
GET http://localhost:8080/clientes
```

Sem body. Retorna a lista completa de clientes cadastrados.

---

**Buscar cliente por ID**

```
GET http://localhost:8080/clientes/1
```

Sem body. Retorna os dados do cliente com o ID informado.

---

### Senhas

**Gerar senha para um cliente**

```
POST http://localhost:8080/senhas/gerar/1
```

Sem body. O ID na URL deve ser o ID do cliente cadastrado. A senha gerada recebe o prefixo `P` para clientes PREFERENCIAL e `N` para clientes NORMAL, seguido de um numero sequencial.

Retorno:
```json
{
  "id": 1,
  "codigo": "N001",
  "tipoPrioridade": "NORMAL",
  "nomeCliente": "Carlos Souza",
  "atendida": false,
  "geradaEm": "2026-05-13T10:01:00"
}
```

---

**Listar senhas nao atendidas (fila atual)**

```
GET http://localhost:8080/senhas/fila
```

Sem body. Retorna todas as senhas que ainda estao aguardando atendimento.

---

**Contar total na fila**

```
GET http://localhost:8080/senhas/fila/total
```

Sem body. Retorna a quantidade de clientes ainda aguardando.

```json
{
  "totalNaFila": 3
}
```

---

### Atendimentos

**Chamar proximo cliente**

```
POST http://localhost:8080/atendimentos/chamar-proximo
```

Sem body. O sistema sempre prioriza senhas do tipo PREFERENCIAL. Caso nao haja nenhuma preferencial na fila, chama a proxima NORMAL pela ordem de chegada. Registra automaticamente o horario de inicio do atendimento.

Retorno:
```json
{
  "id": 1,
  "codigoSenha": "P001",
  "nomeCliente": "Dona Maria",
  "iniciadoEm": "2026-05-13T10:05:00",
  "finalizadoEm": null,
  "duracaoSegundos": null
}
```

---

**Finalizar atendimento**

```
PATCH http://localhost:8080/atendimentos/finalizar/1
```

Sem body. O ID na URL deve ser o ID do atendimento retornado no passo anterior. Registra o horario de finalizacao e calcula automaticamente a duracao em segundos.

Retorno:
```json
{
  "id": 1,
  "codigoSenha": "P001",
  "nomeCliente": "Dona Maria",
  "iniciadoEm": "2026-05-13T10:05:00",
  "finalizadoEm": "2026-05-13T10:07:30",
  "duracaoSegundos": 150
}
```

---

**Listar atendimentos finalizados**

```
GET http://localhost:8080/atendimentos/finalizados
```

Sem body. Retorna o historico completo de atendimentos encerrados, ordenados do mais recente para o mais antigo.

---

## Fluxo completo de teste recomendado

1. Cadastrar um cliente NORMAL
2. Cadastrar um cliente PREFERENCIAL
3. Gerar senha para o cliente NORMAL
4. Gerar senha para o cliente PREFERENCIAL
5. Chamar o proximo: o sistema deve chamar o PREFERENCIAL primeiro, independente da ordem de chegada
6. Finalizar o atendimento usando o ID retornado
7. Chamar o proximo novamente: agora o NORMAL sera chamado
8. Finalizar o segundo atendimento
9. Consultar o historico de finalizados e verificar os tempos registrados

---

## Estrutura de pastas

```
src/main/java/com/example/filaatendimento/
    controller/
        AtendimentoController.java
        ClienteController.java
        SenhaController.java
    service/
        AtendimentoService.java
        ClienteService.java
        SenhaService.java
    repository/
        AtendimentoRepository.java
        ClienteRepository.java
        SenhaRepository.java
    model/
        Atendimento.java
        Cliente.java
        Senha.java
        enums/
            TipoPrioridade.java
    dto/
        AtendimentoResponseDTO.java
        ClienteRequestDTO.java
        ClienteResponseDTO.java
        SenhaResponseDTO.java
    FilaAtendimentoApplication.java
src/main/resources/
    application.properties
```

---

## Desenvolvido por  Matheus