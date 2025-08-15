# AcerolaTrack

Sistema de gerenciamento de tempo e tarefas desenvolvido com Spring Boot e Vaadin Flow.

## Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven (opcional, pois o projeto inclui Maven Wrapper)

## Configuração do Ambiente

### 1. Configurar variáveis de ambiente
Antes de executar o projeto, você precisa configurar as variáveis de ambiente:
```bash
cp .env.example .env
```

E alterae as variáveis que irá utilizadae

### 2. Subindo o Container Docker

O projeto utiliza Docker para o banco de dados. Para iniciar o container:

```bash
# Navegue até a pasta do docker-compose
cd dev/docker

# Inicie os containers
docker-compose up -d
```

### 3. Compilando a Aplicação

Na raiz do projeto, execute:

```bash
# Para sistemas Unix/Linux/MacOS
./mvnw clean install

# Para Windows (PowerShell/CMD)
.\mvnw clean install
```

### 4. Executando a Aplicação

Após a compilação, execute:

```bash
# Para sistemas Unix/Linux/MacOS
./mvnw spring-boot:run

# Para Windows (PowerShell/CMD)
.\mvnw spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## Estrutura do Projeto

- `src/main/java`: Código fonte Java
- `src/main/resources`: Arquivos de configuração
- `dev/docker`: Arquivos Docker para ambiente de desenvolvimento

## Troubleshooting

Se encontrar problemas com o Docker:
1. Verifique se o Docker está rodando: `docker ps`
2. Verifique os logs: `docker-compose logs`
3. Reinicie os containers: `docker-compose down && docker-compose up -d`

Se encontrar problemas com a aplicação:
1. Limpe o cache do Maven: `./mvnw clean`
2. Verifique se a porta 8080 está disponível
3. Verifique os logs da aplicação no console

## Web Semântica

Consultas alvo que tentamos abordar com a ontologia e o sistema WEB:

- Listar todos os projetos

``` C#

PREFIX dg: <https://w3id.org/dingo#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?project ?projectName
WHERE {
    ?project a dg:Project ;
            rdfs:label ?projectName .
}
ORDER BY ?projectName

```

- Listar todos os Workers

``` C#
PREFIX dg: <https://w3id.org/dingo#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX schema: <http://schema.org/>

SELECT ?worker ?username
WHERE {
    ?worker a dg:Person ;
            foaf:name ?username ;
            schema:active true .
}
ORDER BY ?username
```

- Consultar Workers com Roles específicos (TODO)

``` C#
PREFIX dg: <https://w3id.org/dingo#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX pto: <http://pto.example/ontology#>
PREFIX schema: <http://schema.org/>

SELECT ?worker ?name ?role
WHERE {
    ?worker a dg:Person ;
            foaf:name ?name ;
            pto:hasRole ?role ;
            schema:active true .
    FILTER(REGEX(STR(?role), "WORKER|MANAGER"))
}
ORDER BY ?name
```


- Consultar todas as tasks

``` C#
PREFIX pto: <http://pto.example/ontology#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dcterms: <http://purl.org/dc/terms/>
PREFIX schema: <http://schema.org/>

SELECT ?task ?description ?creationDate ?dueDate
WHERE {
    ?task a pto:Task ;
          rdfs:label ?description ;
          dcterms:created ?creationDate .
    OPTIONAL { ?task schema:dueDate ?dueDate }
}
ORDER BY ?creationDate
```

- Consultar todas as tasks de um projeto específico

``` C#
PREFIX pto: <http://pto.example/ontology#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <http://schema.org/>

SELECT ?task ?taskName ?startTime ?endTime
WHERE {
    ?task a pto:Task ;
          rdfs:label ?taskName ;
          pto:partOfProject <http://acerolatrack.org/project/1> ;  # Substitua 1 pelo ID do projeto
          schema:startTime ?startTime ;
          schema:endTime ?endTime .
}
ORDER BY ?startTime
```

- Consultar todas as pessoas em um projeto (project report).

- Consultar todas as _time entries_ relacionadas a uma determinada _Task_

- Consultar todas as pessoas que trabalharam em uma task
