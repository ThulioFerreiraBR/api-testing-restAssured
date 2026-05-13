# QA API Automation Framework - DummyJSON

## 📌 Visão Geral

Este projeto é um framework de automação de testes de API construído em **Java + Rest Assured + JUnit 5**, utilizando a API pública [DummyJSON](https://dummyjson.com/) como base de testes.

O objetivo é simular uma estrutura real de testes usada em ambientes de empresas, com foco em qualidade de código, escalabilidade, reutilização e boas práticas de automação.

---

## 🎯 Objetivo do Projeto

Validar de forma automatizada os principais fluxos da API DummyJSON, cobrindo:

- Testes funcionais (happy path)
- Testes negativos (validação de erros e edge cases)
- Testes de contrato (estrutura de resposta)
- Testes de autenticação e autorização
- Regras de negócio
- Validação de integridade dos dados

---

## 🧰 Tecnologias Utilizadas

- Java 17
- Maven
- Rest Assured
- JUnit 5
- Hamcrest Matchers
- GitHub Actions (CI/CD)

---

## 🧱 Arquitetura do Framework

O projeto foi estruturado seguindo boas práticas de automação e separação de responsabilidades:

- `clients/` → Camada responsável por chamadas HTTP (API Clients)
- `tests/` → Casos de teste organizados por domínio
- `factories/` → Geração de dados dinâmicos para testes
- `models/` → DTOs de requisição e resposta
- `utils/` → Validadores reutilizáveis e helpers
- `base/` → Configuração base dos testes

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 17+
- Maven 3+

### Executar todos os testes

```bash
mvn clean test
```

---

## 🔄 CI/CD (GitHub Actions)

Este projeto possui pipeline automatizado com GitHub Actions, executando:

- Build do projeto
- Execução da suíte de testes
- Validação contínua a cada push e pull request

---

## 🧠 Boas Práticas Aplicadas

- Separação de responsabilidades (Clients, Tests, Utils)
- Uso de Factory Pattern para geração de massa de dados
- Reutilização de validações com métodos utilitários
- Organização por domínio de negócio
- Padronização de assertions
- Cobertura de cenários positivos e negativos
- Estrutura escalável para evolução do framework

---

## 📈 Roadmap

Melhorias futuras planejadas:

- Integração com Allure Reports para relatórios visuais
- Execução paralela de testes
- Dockerização do ambiente de testes
- Expansão de cobertura de contrato
- Implementação de testes de performance básicos

---

📌 Sobre o Projeto

Este projeto faz parte do meu portfólio de QA Automation Engineer, com foco em demonstrar habilidades práticas em automação de APIs, arquitetura de testes e boas práticas de engenharia de qualidade.

---

👤 Autor

Desenvolvido como parte de estudos e evolução profissional em QA Automation.






