# 🤖 TradeZero PUC: O Bot que Desafia os Mercados

*"Não é sobre prever o futuro, mas sobre aprender a dançar com a incerteza."*  
**15 dias. 1 desafio. Uma revolução em trading algorítmico.**

---

## 🌟 A História do Projeto

No coração da maratona de Ciência de Dados da PUC-GOIÁS, nasceu o **TradeZero** - uma inteligência artificial que combina a ousadia de um trader profissional com a precisão de algoritmos de última geração. Durante 15 dias intensos, nossa equipe construiu não apenas um bot, mas um **sistema autônomo** que aprende a navegar nos mercados financeiros usando uma arquitetura híbrida inspirada no MuZero/AlphaZero.

Seu diferencial? Uma mente dividida em duas partes fundamentais:

### 🧠 **Cérebro de Dados (Backend)**
Uma estrutura Java/Spring Boot que funciona como o **sistema nervoso central**, organizando todos os aspectos do universo trading:
- **Dimensões do Mercado**: Temporalidade, ações, agentes (modelado em `DateTimeDim`, `StockDim`, `AgentDim`)
- **Operações Complexas**: Rastreamento de ordens, gerenciamento de risco, análise de portfólio (implementado em `OrderFactController`, `RiskManagementFactService`)
- **APIs Especializadas**: Versionamento, documentação Swagger e segurança robusta (via `OpenApiConfig`, `SecurityConfig`)

### 💥 **Cérebro de Guerra (AI Core)**
Um núcleo Python/TensorFlow que **aprende através de simulações**:
- **Geração de Realidades Alternativas**: `FinancialDataGenerator` cria cenários de mercado sintéticos
- **Arquitetura Neuro-Simbólica**: Camadas customizadas como `MarketPositionalEncoding` decifram padrões temporais
- **Treinamento Estratégico**: Usando callbacks como `SelectiveFreezing` para otimização focalizada
- **Modelos Transformers**: `LatentModel` para representação de estados de mercado latentes

---

## 🚀 Funcionalidades-Chave

1. **Simulação Multi-Mercado**  
   Gera cenários realistas via `financial_data_generator.py` usando dados históricos de `candlestick.csv`

2. **Aprendizado por Contexto**  
   `TokenAndPositionEmbedding` codifica tempo e posições de mercado simultaneamente

3. **Otimização Adaptativa**  
   Schedulers como `SineDecayScheduler` ajustam dinamicamente as taxas de aprendizado

4. **Defesas Contra Overfitting**  
   Regularizadores de mercado e técnicas de aumento de dados em `FinancialAugmentationLayer`

5. **Auto-Diagnóstico**  
   Script `verify_latent_vector.py` monitora a saúde do modelo durante o treinamento

---

## 🛠️ Tecnologias da Revolução

- **Backend**: Spring Boot 3, JPA, Lombok, OpenAPI 3
- **AI Core**: TensorFlow 2.12, KerasNLP, Docker
- **Data Pipeline**: PostgreSQL, Hibernate, Pandas
- **Infraestrutura**: Docker Compose, Maven

---

## ⚡ Comece a Revolução

```bash
# Inicie o ecossistema de dados
cd backend && mvn spring-boot:run

# Entre na mente do trader
docker-compose -f training/docker-compose.yml up --build

# Observe a evolução (treino + análise)
python training/train_market.py --phase=discovery