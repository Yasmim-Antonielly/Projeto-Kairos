# Projeto Kairós - ESG Matchmaking & SROI Auditor

Bem-vindo à documentação oficial do **Projeto Kairós**. Este sistema completo (Full-Stack) tem como objetivo conectar **Projetos Sociais e ONGs** a **Investidores** com foco em métricas ESG (Ambiental, Social e Governança), garantindo transparência através de cálculos preditivos de impacto social (SROI) e auditorias automatizadas por Inteligência Artificial.

---

## 🏗️ Arquitetura da Solução

O sistema é dividido em três camadas principais:

1. **Banco de Dados Relacional (Oracle 21c):** Roda de forma independente via container Docker.
2. **Back-End API (Java 17 + Jersey/Grizzly):** Fornece a persistência via JDBC puro, expõe as rotas RESTful da aplicação e se comunica com o motor de IA.
3. **Front-End (React + Vite + TailwindCSS):** Oferece a interface administrativa rica (Dashboard) e se comunica com o Back-End via proxy.
4. **Inteligência Artificial (Groq - Llama 3.1 8B):** Integrada ao Java para processar relatórios em texto natural e retornar análises automatizadas de impacto social.

---

## 📌 Pré-requisitos

Para rodar este projeto, você precisará ter instalado na sua máquina:

* **Docker** e **Docker Compose**
* **Java Development Kit (JDK) 17**
* **Apache Maven**
* **Node.js** (versão 16 ou superior)
* **Conta na plataforma Groq** (para obter a chave de API gratuita)

---

## 🚀 Como Executar o Projeto Localmente

O passo a passo abaixo garante que todos os microsserviços subam na ordem correta, prevenindo falhas de conexão.

### Passo 1: Banco de Dados (Docker)
1. Abra o terminal na pasta raiz do projeto Back-End (`Projeto-Kairos`).
2. Suba o container do banco de dados (que mapeia a porta `1521` localmente):
   ```bash
   docker compose up -d
   ```
3. Aguarde cerca de 1 a 2 minutos para que o Oracle Database inicie totalmente pela primeira vez.
4. Execute o arquivo de estruturação (schema) direto de dentro do container para criar as tabelas e as dezenas de `seeds` já pré-configurados:
   ```bash
   sudo docker exec -i oracle-kairos sqlplus kairos/k123@localhost:1521/XEPDB1 < schema.sql
   ```

### Passo 2: Back-End (Java) & IA
O Back-End necessita de uma chave de API da Groq para a funcionalidade de auditoria.

1. No terminal do `Projeto-Kairos`, exporte a variável de ambiente (substitua pela sua chave real):
   ```bash
   export GROQ_API_KEY="gsk_SUA_CHAVE_AQUI_..."
   ```
2. Compile e inicie o servidor embutido Grizzly/Jersey (que rodará na porta `8080`):
   ```bash
   mvn clean compile exec:java
   ```
3. Mantenha esse terminal aberto.

### Passo 3: Front-End (Vite/React)
1. Abra um **novo terminal** na pasta do front-end (`kairos-front`).
2. Instale os pacotes:
   ```bash
   npm install
   ```
3. Inicie o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```
4. Abra o seu navegador e acesse **`http://localhost:5173`**.
   *(O Vite já está configurado com um proxy interno que redireciona todas as requisições `/api` diretamente para a porta `8080` do seu Java, eliminando qualquer problema de CORS).*

---

## 🗄️ Estrutura do Banco de Dados

As entidades do ecossistema foram projetadas para atender às normas ESG:

* **`KAIROS_ONG`**: Mantém o cadastro e informações legais das ONGs.
* **`KAIROS_PROJETO`**: Armazena os subprojetos conduzidos por uma determinada ONG.
* **`KAIROS_PROJETO_SOCIAL`**: Formato genérico/extensivo de um projeto, focado na captação do investimento e no cálculo das metas para a comunidade e categoria ESG (Ambiental, Social ou Governança).
* **`KAIROS_INVESTIDOR`**: Organizações dispostas a injetar capital em causas baseadas em suas Teses de Investimento (Corporate Ventures, Anjos, etc).
* **`KAIROS_AUDITORIA_IMPACTO`**: Tabela gerada durante o processo de avaliação SROI, consolidando valores investidos, retornos sociais gerados, comprovantes brutos e o log da análise sintática feita pela Inteligência Artificial.

---

## 🔌 Principais Endpoints (API REST)

O back-end trafega JSON através das seguintes rotas primárias:

### ONGs e Projetos
* `GET /ong`: Lista todas as ONGs.
* `GET /projetos`: Lista todos os projetos vinculados às ONGs.
* `POST /projetos/{id}/sroi`: Aciona o pipeline de auditoria e IA para consolidar e retornar o relatório SROI (Retorno Social sobre Investimento) daquele projeto específico.

### Projetos Sociais & Investidores (Matchmaking)
* `GET /projetos-sociais`: Lista o perfil de captação ESG dos projetos.
* `GET /investidores`: Retorna as teses e capacidades de capital dos fundos.

### Auditorias Consistidas
* `GET /auditorias`: Recupera o histórico auditado de projetos, com logs diretos da análise gerada via Groq LLM.

---

## 🧠 Integração com Inteligência Artificial
A auditoria manual de recibos e evidências sociais é custosa. Este projeto mitiga esse gargalo enviando resumos do progresso para o modelo **Llama 3.1 8B Instant** fornecido via Groq. A IA recebe um prompt sistêmico como `"auditor ESG especialista em impacto financeiro e métricas SROI"` e avalia instantaneamente quais são os pontos focais daquele relato, validando a integridade da solicitação antes de gravar o índice definitivo no banco.

---

> Projeto desenvolvido como demonstração arquitetural completa conectando banco de dados corporativo, back-end assíncrono em Java e front-end responsivo, garantindo as melhores práticas e padrões de mercado.
