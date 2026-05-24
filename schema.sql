-- ==========================================
-- PROJETO KAIRÓS - BANCO DE DADOS E PERSISTÊNCIA
-- ARQUIVO: schema.sql (Oracle SQL DDL + DML)
-- ==========================================

-- --- DROP TABLES AND SEQUENCES (If they exist) ---
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE KAIROS_AUDITORIA_IMPACTO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE KAIROS_PROJETO_SOCIAL CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE KAIROS_INVESTIDOR CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE KAIROS_PROJETO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE KAIROS_ONG CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE KAIROS_PROJ_SOCIAL_SEQ';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE KAIROS_INVESTIDOR_SEQ';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE KAIROS_AUDITORIA_SEQ';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE KAIROS_ONG_SEQ';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE KAIROS_PROJETO_SEQ';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- --- CREATE SEQUENCES ---
CREATE SEQUENCE KAIROS_PROJ_SOCIAL_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE KAIROS_INVESTIDOR_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE KAIROS_AUDITORIA_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE KAIROS_ONG_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE KAIROS_PROJETO_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- --- CREATE TABLES ---

-- 1. PROJETO SOCIAL
CREATE TABLE KAIROS_PROJETO_SOCIAL (
    ID NUMBER(19) PRIMARY KEY,
    TITULO VARCHAR2(150) NOT NULL,
    DESCRICAO VARCHAR2(1000) NOT NULL,
    CATEGORIA_ESG VARCHAR2(50) NOT NULL, 
    ORCAMENTO_NECESSARIO NUMBER(15,2) NOT NULL,
    ONG_RESPONSAVEL VARCHAR2(100) NOT NULL,
    CNPJ VARCHAR2(18) NOT NULL,
    METAS_COMUNIDADE VARCHAR2(2000)
);

-- 2. INVESTIDOR
CREATE TABLE KAIROS_INVESTIDOR (
    ID NUMBER(19) PRIMARY KEY,
    NOME VARCHAR2(100) NOT NULL,
    TIPO_INVESTIDOR VARCHAR2(50) NOT NULL, 
    TESE_INVESTIMENTO VARCHAR2(1000),
    CRITERIOS_ESG VARCHAR2(1000),
    ORCAMENTO_DISPONIVEL NUMBER(15,2) NOT NULL,
    CNPJ_CPF VARCHAR2(18) NOT NULL
);

-- 3. AUDITORIA DE IMPACTO
CREATE TABLE KAIROS_AUDITORIA_IMPACTO (
    ID NUMBER(19) PRIMARY KEY,
    PROJETO_SOCIAL_ID NUMBER(19) NOT NULL,
    VALOR_INVESTIDO NUMBER(15,2) NOT NULL,
    VALOR_RETORNO_SOCIAL NUMBER(15,2) NOT NULL,
    INDICE_SROI NUMBER(8,2) NOT NULL, 
    CAMINHO_RECIBOS VARCHAR2(255),
    CAMINHO_FOTOS VARCHAR2(255),
    DADOS_BRUTOS_TEXTO CLOB,
    ANALISE_IA CLOB,
    STATUS_AUDITORIA VARCHAR2(50) DEFAULT 'EM_ANALISE' NOT NULL, 
    DATA_AUDITORIA DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT FK_AUDITORIA_PROJETO FOREIGN KEY (PROJETO_SOCIAL_ID) 
        REFERENCES KAIROS_PROJETO_SOCIAL(ID) ON DELETE CASCADE
);

-- 4. ONG
CREATE TABLE KAIROS_ONG (
    ID NUMBER(19) PRIMARY KEY,
    NOME VARCHAR2(150) NOT NULL,
    DESCRICAO VARCHAR2(1000) NOT NULL,
    AREA_ATUACAO VARCHAR2(100) NOT NULL,
    CNPJ VARCHAR2(18) NOT NULL
);

-- 5. PROJETO
CREATE TABLE KAIROS_PROJETO (
    ID NUMBER(19) PRIMARY KEY,
    TITULO VARCHAR2(150) NOT NULL,
    RELATO VARCHAR2(1000) NOT NULL,
    ORCAMENTO NUMBER(15,2) NOT NULL,
    STATUS VARCHAR2(50) NOT NULL,
    ONG_ID NUMBER(19) NOT NULL,
    CONSTRAINT FK_PROJETO_ONG FOREIGN KEY (ONG_ID) REFERENCES KAIROS_ONG(ID) ON DELETE CASCADE
);

-- --- INSERÇÕES INICIAIS (SEED DATA) ---

-- Projetos Sociais Seeds
INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE)
VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, 'Iniciativa de Reflorestamento e Preservação Hídrica', 'Plantio de mudas nativas em áreas de degradação ambiental para contenção de processos erosivos e revitalização da biodiversidade regional.', 'AMBIENTAL', 150000.00, 'Instituto EcoSustentável', '12.345.678/0001-90', 'Restaurar 10 hectares de mata ciliar e promover o engajamento de 500 membros da comunidade local em 12 meses.');

INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE)
VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, 'Programa de Inclusão Digital e Capacitação Tecnológica', 'Programa educacional focado no ensino de tecnologias da informação (desenvolvimento de software e análise de dados) voltado para populações em vulnerabilidade social.', 'SOCIAL', 220000.00, 'Fundação InovaTech', '98.765.432/0001-10', 'Capacitar 150 jovens anualmente com foco na empregabilidade e inserção no mercado de tecnologia em até 6 meses.');

INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE)
VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, 'Cooperativa de Gestão de Resíduos e Sustentabilidade Urbana', 'Estruturação de um ecossistema cooperativo de coleta seletiva e reciclagem, integrando inteligência de dados e economia circular em zonas urbanas densas.', 'AMBIENTAL', 95000.00, 'Associação Recicla Mais', '45.123.789/0001-50', 'Processar 50 toneladas de resíduos recicláveis por trimestre, regularizando a atividade e incrementando a renda de 40 famílias parceiras.');

INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE)
VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, 'Centro de Apoio Nutricional e Segurança Alimentar', 'Implantação de unidades de fornecimento de refeições balanceadas aliadas a cursos de aproveitamento integral de alimentos para a população de baixa renda.', 'SOCIAL', 180000.00, 'Rede Alimento Seguro', '33.222.111/0001-22', 'Distribuir 5.000 refeições mensais e realizar treinamentos de educação alimentar para 200 famílias da comunidade.');

INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE)
VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, 'Aceleração de Lideranças Femininas no Empreendedorismo', 'Fomento à governança corporativa ética e estruturação de negócios liderados exclusivamente por mulheres em regiões metropolitanas.', 'GOVERNANCA', 120000.00, 'Instituto Mulheres no Controle', '11.444.777/0001-88', 'Acelerar o crescimento de 60 startups com liderança feminina, fornecendo acesso a crédito facilitado e mentorias estratégicas.');

-- Investidores Seeds
INSERT INTO KAIROS_INVESTIDOR (ID, NOME, TIPO_INVESTIDOR, TESE_INVESTIMENTO, CRITERIOS_ESG, ORCAMENTO_DISPONIVEL, CNPJ_CPF)
VALUES (KAIROS_INVESTIDOR_SEQ.NEXTVAL, 'Horizon Corporate Ventures', 'FUNDO', 'Aportes estruturados em iniciativas focadas em sustentabilidade ambiental, educação contínua e mitigação de vulnerabilidades socioeconômicas.', 'Elevada rigidez na avaliação de impacto (SROI) e obrigatoriedade de relatórios trimestrais.', 5000000.00, '11.222.333/0001-44');

INSERT INTO KAIROS_INVESTIDOR (ID, NOME, TIPO_INVESTIDOR, TESE_INVESTIMENTO, CRITERIOS_ESG, ORCAMENTO_DISPONIVEL, CNPJ_CPF)
VALUES (KAIROS_INVESTIDOR_SEQ.NEXTVAL, 'Capital Futuro Positivo', 'FUNDO', 'Investimento estratégico em economia circular, eficiência energética e cadeias produtivas de zero carbono.', 'Prioridade em aderência aos Objetivos de Desenvolvimento Sustentável (ODS) da ONU, pilar Ambiental.', 3500000.00, '55.666.777/0001-88');

INSERT INTO KAIROS_INVESTIDOR (ID, NOME, TIPO_INVESTIDOR, TESE_INVESTIMENTO, CRITERIOS_ESG, ORCAMENTO_DISPONIVEL, CNPJ_CPF)
VALUES (KAIROS_INVESTIDOR_SEQ.NEXTVAL, 'Grupo Angel Partners ESG', 'ANJO', 'Fomento ao empreendedorismo social, democratização da tecnologia e equidade de gênero corporativa.', 'Foco no pilar Social (S) e Governança (G), buscando negócios que ofereçam inclusão com modelo escalável.', 1500000.00, '222.333.444-55');

INSERT INTO KAIROS_INVESTIDOR (ID, NOME, TIPO_INVESTIDOR, TESE_INVESTIMENTO, CRITERIOS_ESG, ORCAMENTO_DISPONIVEL, CNPJ_CPF)
VALUES (KAIROS_INVESTIDOR_SEQ.NEXTVAL, 'EcoSinergia Investimentos', 'CORPORATIVO', 'Apoio a projetos de mitigação climática, biotecnologia agrícola e saneamento sustentável.', 'Critérios consolidados em métricas globais de ESG; requer validação de terceiros em métricas ambientais.', 8000000.00, '99.888.777/0001-11');

-- Auditoria de Impacto Seeds
INSERT INTO KAIROS_AUDITORIA_IMPACTO (ID, PROJETO_SOCIAL_ID, VALOR_INVESTIDO, VALOR_RETORNO_SOCIAL, INDICE_SROI, CAMINHO_RECIBOS, CAMINHO_FOTOS, DADOS_BRUTOS_TEXTO, ANALISE_IA, STATUS_AUDITORIA)
VALUES (KAIROS_AUDITORIA_SEQ.NEXTVAL, 1, 150000.00, 450000.00, 3.00, 'raw_data/receipts/projeto_1_recibos_consolidado.pdf', 'raw_data/photos/projeto_1_antes_depois.jpg', 'Despesas com mudas: R$75.000; Infraestrutura logística: R$35.000; Monitoramento ambiental: R$40.000.', 'Análise IA Validada: A iniciativa gerou aproximadamente R$3,00 de retorno social para cada R$1,00 aportado. O impacto foi qualificado como excepcional no eixo de preservação hídrica e engajamento comunitário.', 'APROVADO');

INSERT INTO KAIROS_AUDITORIA_IMPACTO (ID, PROJETO_SOCIAL_ID, VALOR_INVESTIDO, VALOR_RETORNO_SOCIAL, INDICE_SROI, CAMINHO_RECIBOS, CAMINHO_FOTOS, DADOS_BRUTOS_TEXTO, ANALISE_IA, STATUS_AUDITORIA)
VALUES (KAIROS_AUDITORIA_SEQ.NEXTVAL, 2, 220000.00, 990000.00, 4.50, 'raw_data/receipts/projeto_2_comprovantes.pdf', 'raw_data/photos/projeto_2_alunos.jpg', 'Locação de equipamentos: R$100.000; Corpo docente e licenças de software: R$120.000.', 'Análise IA Validada: Retorno altíssimo (SROI de 4.5). A taxa de empregabilidade alcançada resultou em um crescimento expressivo da renda per capita das famílias beneficiadas, gerando forte tração socioeconômica.', 'APROVADO');

-- ONGs Seeds
INSERT INTO KAIROS_ONG (ID, NOME, DESCRICAO, AREA_ATUACAO, CNPJ) 
VALUES (KAIROS_ONG_SEQ.NEXTVAL, 'Instituto EcoSustentável', 'Organização focada na preservação ambiental e na elaboração de políticas de sustentabilidade.', 'Meio Ambiente', '12.345.678/0001-90');

INSERT INTO KAIROS_ONG (ID, NOME, DESCRICAO, AREA_ATUACAO, CNPJ) 
VALUES (KAIROS_ONG_SEQ.NEXTVAL, 'Fundação InovaTech', 'Fomento à educação e inclusão sociodigital de jovens talentos.', 'Educação', '98.765.432/0001-10');

INSERT INTO KAIROS_ONG (ID, NOME, DESCRICAO, AREA_ATUACAO, CNPJ) 
VALUES (KAIROS_ONG_SEQ.NEXTVAL, 'Associação Recicla Mais', 'Promoção da economia circular em zonas urbanas densamente povoadas.', 'Sustentabilidade', '45.123.789/0001-50');

INSERT INTO KAIROS_ONG (ID, NOME, DESCRICAO, AREA_ATUACAO, CNPJ) 
VALUES (KAIROS_ONG_SEQ.NEXTVAL, 'Rede Alimento Seguro', 'Combate à insegurança alimentar através de estratégias escaláveis.', 'Saúde e Nutrição', '33.222.111/0001-22');

-- PROJETOS Seeds
INSERT INTO KAIROS_PROJETO (ID, TITULO, RELATO, ORCAMENTO, STATUS, ONG_ID) 
VALUES (KAIROS_PROJETO_SEQ.NEXTVAL, 'Programa de Revitalização de Nascentes', 'Mapeamento e recuperação de nascentes em perímetro urbano utilizando bioengenharia.', 250000.00, 'EM_ANDAMENTO', 1);

INSERT INTO KAIROS_PROJETO (ID, TITULO, RELATO, ORCAMENTO, STATUS, ONG_ID) 
VALUES (KAIROS_PROJETO_SEQ.NEXTVAL, 'Laboratório de Inovação Aberta', 'Implementação de espaços maker equipados com impressoras 3D e kits de robótica.', 320000.00, 'PLANEJAMENTO', 2);

INSERT INTO KAIROS_PROJETO (ID, TITULO, RELATO, ORCAMENTO, STATUS, ONG_ID) 
VALUES (KAIROS_PROJETO_SEQ.NEXTVAL, 'Central de Triagem Automatizada', 'Modernização do fluxo de esteiras de separação de resíduos usando tecnologias de baixo custo.', 150000.00, 'CONCLUIDO', 3);

INSERT INTO KAIROS_PROJETO (ID, TITULO, RELATO, ORCAMENTO, STATUS, ONG_ID) 
VALUES (KAIROS_PROJETO_SEQ.NEXTVAL, 'Distribuição de Alimentos Otimizada', 'Logística inteligente para redução de desperdício na cadeia de distribuição alimentar.', 95000.00, 'EM_ANDAMENTO', 4);

COMMIT;
