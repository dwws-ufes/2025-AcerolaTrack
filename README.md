# AcerolaTrack

Sistema de gerenciamento de tempo e tarefas desenvolvido com Spring Boot e Vaadin Flow.

## Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven (opcional, pois o projeto inclui Maven Wrapper)

## Configuração do Ambiente

### 1. Subindo o Container Docker

O projeto utiliza Docker para o banco de dados. Para iniciar o container:

```bash
# Navegue até a pasta do docker-compose
cd dev/docker

# Inicie os containers
docker-compose up -d
```

### 2. Compilando a Aplicação

Na raiz do projeto, execute:

```bash
# Para sistemas Unix/Linux/MacOS
./mvnw clean install

# Para Windows (PowerShell/CMD)
.\mvnw clean install
```

### 3. Executando a Aplicação

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
