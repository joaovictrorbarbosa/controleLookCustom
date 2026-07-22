# LookCustom - Sistema de Gestao de Estoque

[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Live Demo](https://img.shields.io/badge/Vercel-Live%20Demo-black.svg)](https://controle-look-custom.vercel.app/login.html)

> Sistema Web completo (SaaS) desenvolvido para automacao, gerenciamento e controle de estoque de venda de produtos personalizados, com foco em seguranca, isolamento de dados por usuario e persistencia confiavel.

Aplicacao em Producao (Frontend): https://controle-look-custom.vercel.app/login.html

---

## Principais Funcionalidades

* Autenticacao e Autorizacao: Login seguro com criptografia de senhas (BCrypt) e emissao de tokens JWT (JSON Web Tokens).
* Isolamento de Dados (Multi-Tenancy): Cada usuario autenticado possui acesso estritamente isolado aos seus proprios registros de produtos e movimentacoes.
* Gestao de Estoque: Cadastro, atualizacao, remocao e listagem de produtos/camisetas por tamanho, cor e quantidade.
* Arquitetura Stateless: API RESTful preparada para alta escalabilidade e facil integracao com clientes web/mobile.
* Versionamento de Banco: Migracoes de schema automatizadas e controladas via Flyway.

---

## Tecnologias Utilizadas

### Backend
* Linguagem e Framework: Java 17 / Spring Boot 3
* Seguranca: Spring Security + JWT
* Persistencia: Spring Data JPA / Hibernate
* Banco de Dados: PostgreSQL
* Migracoes: Flyway Migration

### Frontend e Infraestrutura
* Frontend: HTML5, CSS3, JavaScript (Fetch API)
* Hospedagem Frontend: Vercel
* Conteinerizacao: Docker e Docker Compose

---

## Arquitetura da Solucao

```text
[ Cliente Web / Vercel ] 
       │
  (Requisicoes HTTP/CORS + Bearer JWT)
       ▼
[ API Spring Boot (Producao em Nuvem) ] ──► [ Spring Security / Filter JWT ]
       │
  (Spring Data JPA / Flyway)
       ▼
[ Banco PostgreSQL ]
```

Como Executar ou Rodar o Projeto
1. Acesso Direto (Ambiente de Producao)
A aplicacao web completa esta no ar e integrada com o backend em nuvem:

Link de Acesso: https://controle-look-custom.vercel.app/login.html

2. Execucao Local para Desenvolvimento (Opcional)
Pre-requisitos
Java 17+

Docker e Docker Compose

Passo a Passo
Clone o repositorio:

Bash
git clone [https://github.com/joaovictrorbarbosa/controleLookCustom.git](https://github.com/joaovictrorbarbosa/controleLookCustom.git)
cd controleLookCustom
Sube os containers da API e Banco de Dados:

Bash
docker compose up -d
Ambiente local:
A API estara pronta em http://localhost:8080 para testes locais.
