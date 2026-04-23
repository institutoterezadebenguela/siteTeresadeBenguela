# Relatório de Configuração Centralizada e Exceptions - Instituto Tereza de Benguela (v2)

Arquitetura distribuída requer controle universal. Na expansão da v2, isolamos as infraestruturas que sujavam controladores individuais para arquivos centrais especializados nas pastas `config` e `exception`.

---

## 1. Configurações Globais CORS (`WebConfig`)
Os CORS outrora definidos na anotação `@CrossOrigin` da Base do Controller foram exterminados do projeto modular.
- A classe implementa o contrato Spring `WebMvcConfigurer`.
- A API libera domínios universais limpos `allowedOrigins("*")` gerenciando requisições REST globalizadas e mantendo o código dos métodos limpo para a equipe de Backend.

---

## 2. Global Exception Handler (`GlobalExceptionHandler`)
Foco na interceptação orgânica via *Aspect-Oriented Programming (AOP)* padrão. A anotação chave da classe se chama `@RestControllerAdvice`.
- Mapeia ativamente a exceção de Formulário `MethodArgumentNotValidException` quando métodos não passam no crível visual gerado no frontend via `@Valid`. 
- Traduz stack traces difusos e erros grotescos de back-end em saídas JSON bonitas, devolvendo os nomes dos campos que estouraram e o formato legível diretamente para o desenvolvedor Javascript.
- Cobre com tratamento elegante em Internal Server Errors as exceções genéricas nativas `Exception.class`.
