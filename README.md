# FinVoice — Assistente Financeiro Inteligente

[![Spring AI Tests](https://github.com/Liken77/finvoice-ai/actions/workflows/spring-ai-tests.yml/badge.svg)](https://github.com/Liken77/finvoice-ai/actions/workflows/spring-ai-tests.yml)

O FinVoice é uma API de orçamento que registra, consulta e resume transações financeiras por endpoints REST e comandos de voz. A aplicação transcreve o áudio, interpreta a intenção com IA, executa casos de uso Java e devolve a resposta em MP3.

O código e a documentação completa estão no módulo [`05-spring-ai`](05-spring-ai/README.md).

## Evolução implementada

- resumo financeiro com total geral e totais por categoria;
- validação de valor, descrição e categoria das transações;
- validação de arquivos de áudio enviados ao assistente;
- respostas de erro padronizadas;
- testes unitários sem consumo da API da OpenAI;
- integração contínua com Java 25.

## Tecnologias

- Java 25;
- Spring Boot 4.0.5;
- Spring AI 2.0.0-M4;
- Spring Web e Spring Data JPA;
- OpenAI;
- MySQL e Docker Compose;
- Gradle e JUnit.

## Executando o projeto

```bash
cd 05-spring-ai
docker compose up -d
export OPENAI_API_KEY="sua-chave"
./gradlew bootRun
```

Para executar somente os testes locais:

```bash
cd 05-spring-ai
./gradlew test
```

Consulte o [`README do projeto`](05-spring-ai/README.md) para ver os endpoints, exemplos de requisições, arquitetura e configurações necessárias.

## Origem

Projeto desenvolvido por Pedro Henrique Andrade como evolução do desafio de Spring AI da DIO. A estrutura inicial pertence à trilha [`dio-spring-boot-learning-track`](https://github.com/digitalinnovationone/dio-spring-boot-learning-track).
