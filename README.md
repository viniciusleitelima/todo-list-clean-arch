## Todo List Clean Architecture (Java + Spring Boot + MongoDB)

API REST para gerenciar tarefas (to‑dos) implementada com princípios de Clean Architecture. O objetivo é manter regras de negócio desacopladas de frameworks, facilitando testes, manutenção e evolução.

### Sumário
- **Visão geral**
- **Arquitetura (Clean Architecture)**
- **Tecnologias**
- **Modelo de dados**
- **Endpoints (API)**
- **Tratamento de erros**
- **Como executar**
- **Estrutura do projeto**

### Visão geral
Esta aplicação é um resultado de estudo sobre clean arch, o objetivo é criar um todo list para consolidar conhecimentos de clean arch.
Com isso foi implementados endpoints para criar, consultar, atualizar, completar e excluir tarefas. 
As regras de negócio vivem na camada de domínio; 
Os frameworks e integrações (HTTP, banco, mapeamentos) ficam nas camadas externas.

### Arquitetura (Clean Architecture)
Camadas e responsabilidades principais:

- **domain**: regra de negócio pura (sem dependência de frameworks)
    - `model`: `Task` (entidade de domínio)
    - `enums`: `StatusEnum` (OPEN, IN_PROGRESS, DONE)
    - `usecase`: contratos e implementações dos casos de uso (`CreateTaskUseCase`, `RetrieveTaskByIdUseCase`, `RetrieveAllTaskUseCase`, `UpdateTaskUseCase`, `CompleteTaskUseCase`, `DeleteTaskUseCase` e respectivos `*Impl`)
    - `exception`: `BusinessException` (exceções de negócio)

- **adapter**: portas de entrada/saída (depende do domínio)
    - `controller`: `TaskController` (exposição REST)
    - `repository`: `TaskRepository` (porta de saída, interface que o domínio conhece)

- **infra**: detalhes de implementação
    - `persistence`: `TaskEntity` (documento MongoDB), `TaskMongoRepository` (Spring Data Mongo), `TaskRepositoryImpl` (implementa a porta `TaskRepository`)
    - `mapper`: `TaskMapper` e `TaskMapperImpl` (converte entre `Task`, `TaskEntity` e `TaskDTO`)
    - `dto`: `TaskDTO` (payloads REST)
    - `handler`: `RestControllerExceptionHandler` e `ErrorMessage` (tratamento e formato de erros)
    - `config`: `TaskUseCaseConfig` (wiring dos beans das dependências e casos de uso)

Fluxo resumido de uma requisição:
`HTTP -> TaskController -> UseCase -> TaskRepository (porta) -> TaskRepositoryImpl (Mongo) -> TaskMongoRepository`

### Tecnologias
- Java 17, Maven
- Spring Boot 3.5.5 (Web, Spring Data MongoDB)
- Lombok
- MapStruct (presente no `pom.xml`; mapeamento atual implementado manualmente em `TaskMapperImpl`)

Observação: O `application.properties` contém propriedades de H2/JPA herdadas; nesta branch (`withMongoRepository`) a persistência efetiva é MongoDB via `spring.data.mongodb.*`.

### Modelo de dados
`TaskDTO` (usado na API) e `Task` (domínio) possuem os campos:
- `id`: string (UUID gerado na criação)
- `author`: string
- `title`: string
- `status`: enum (OPEN, IN_PROGRESS, DONE)
- `dueDate`: datetime ISO‑8601
- `createdAt`: datetime ISO‑8601
- `updatedAt`: datetime ISO‑8601

`TaskEntity` armazena o `status` como string e é anotado com `@Document(collection = "task")` para MongoDB.

Regras relevantes:
- Criação define `id` (UUID), `createdAt` e `updatedAt` como `now()`
- Atualização é parcial (somente campos não nulos são aplicados em `TaskMapper.updateEntity`)
- Completar tarefa força `status = DONE`

### Endpoints (API)
Base path: `/api/tasks`

- POST `/api/tasks` — cria tarefa
    - Request (JSON):
      ```json
      {
        "author": "Alice",
        "title": "Comprar leite",
        "status": "OPEN",
        "dueDate": "2025-09-20T18:00:00"
      }
      ```
    - Response 201 (JSON):
      ```json
      {
        "id": "b1d1f7d6-2d5a-4a2a-9f3d-2b3e4c5d6e7f",
        "author": "Alice",
        "title": "Comprar leite",
        "status": "OPEN",
        "dueDate": "2025-09-20T18:00:00",
        "createdAt": "2025-09-17T10:30:00",
        "updatedAt": "2025-09-17T10:30:00"
      }
      ```

- GET `/api/tasks/{id}` — busca por id
    - Response 200 (JSON): igual ao modelo acima

- GET `/api/tasks` — lista todas
    - Response 200 (JSON): lista de `TaskDTO`

- PUT `/api/tasks/{id}` — atualização parcial
    - Request (exemplo, apenas campos a atualizar):
      ```json
      {
        "title": "Comprar leite e pão",
        "status": "IN_PROGRESS"
      }
      ```
    - Response 200 (JSON): tarefa atualizada

- POST `/api/tasks/{id}/complete` — marca como concluída (`DONE`)
    - Response 200 (JSON): tarefa com `status = DONE`

- DELETE `/api/tasks/{id}` — remove tarefa
    - Response 204 (no content)

#### Exemplos de cURL
```bash
# Criar
curl -sS -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"author":"Alice","title":"Comprar leite","status":"OPEN","dueDate":"2025-09-20T18:00:00"}' | jq

# Listar
curl -sS http://localhost:8080/api/tasks | jq

# Buscar por id
curl -sS http://localhost:8080/api/tasks/{id} | jq

# Atualizar
curl -sS -X PUT http://localhost:8080/api/tasks/{id} \
  -H 'Content-Type: application/json' \
  -d '{"title":"Comprar leite e pão","status":"IN_PROGRESS"}' | jq

# Completar
curl -sS -X POST http://localhost:8080/api/tasks/{id}/complete | jq

# Excluir
curl -sS -X DELETE http://localhost:8080/api/tasks/{id} -i
```

### Tratamento de erros
`RestControllerExceptionHandler` padroniza respostas de erro no formato `ErrorMessage`:
```json
{
  "status": 422,
  "timestamp": "2025-09-17T10:35:12",
  "code": "task.retrieve.not.found",
  "message": "task.retrieve.not.found"
}
```
Mapeamentos principais:
- `BusinessException` → 422 UNPROCESSABLE_ENTITY (usa `code`/`message` da exceção)
- `HttpClientErrorException` → 400 BAD_REQUEST
- `HttpServerErrorException` → 500 INTERNAL_SERVER_ERROR
- `MethodArgumentNotValidException` → 400 com `details` de campos

Se houver `MessageSource` configurado com bundles, as mensagens podem ser internacionalizadas.

### Como executar
Pré‑requisitos:
- Java 17
- Maven 3.9+
- MongoDB acessível

Configure as variáveis de ambiente esperadas pelo `application.properties`:
```bash
export MONGO_HOST='mongodb://localhost:27017'
export MONGO_DB='todo_db'
```

Opcional: subir MongoDB via Docker
```bash
docker run -d --name mongo -p 27017:27017 mongo:6
```

Executar a aplicação:
```bash
mvn spring-boot:run
```

Rodar testes:
```bash
mvn test
```

### Estrutura do projeto (resumo)
```
src/main/java/com/example/todo_list_clean_arch/
  adapter/
    controller/TaskController.java
    repository/TaskRepository.java
  domain/
    enums/StatusEnum.java
    exception/BusinessException.java
    model/Task.java
    usecase/(*UseCase.java, *UseCaseImpl.java)
  infra/
    config/TaskUseCaseConfig.java
    dto/TaskDTO.java
    handler/ErrorMessage.java, RestControllerExceptionHandler.java
    mapper/TaskMapper.java, TaskMapperImpl.java
    persistence/
      entity/TaskEntity.java
      repository/TaskMongoRepository.java, TaskRepositoryImpl.java
```

### Notas
- A presença de propriedades H2/JPA é histórica; a persistência atual usa MongoDB. Garanta que `spring.data.mongodb.uri` e `spring.data.mongodb.database` estejam devidamente definidos via variáveis de ambiente.

