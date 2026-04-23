Formulário de contato

1. **Captura no Local (Frontend):**
   Quando o usuário clica em "Enviar Candidatura", o JavaScript entra em ação e captura os campos digitados (`nome`, `email`, `telefone`, `faixa etária` e `motivação`), montando um "pacote de dados" no formato universal **JSON**.

2. **UX Interativa:**
   O botão muda automaticamente para **"Enviando..."** (com um pequeno ícone de carregamento girando) e é desativado temporariamente para impedir que o usuário clique duas vezes e mande a mesma mensagem repetida.

3. **Comunicação pela Rede:**
   Usando a API local do navegador (`fetch`), ele envia um requisição (HTTP POST) invisível para a rota do nosso servidor: `/api/volunteers`.

4. **Validação e Banco de Dados (Backend):**
   No servidor, a tabela entra pelo controlador (que nós comentamos antes `createVolunteer`). O Spring Boot checa automaticamente se os dados como e-mail são válidos (através do `@Valid`). Se a validação estiver OK, e tudo estiver direitinho, **ele salva** aquele candidato no banco de dados.

5. **Exibição do Feedback ao Usuário:**
   O backend devolve um códio **201 Created**. O Frontend reconhece esse sucesso, exibe uma caixinha verde na tela dizendo *"Candidatura enviada com sucesso! Logo entraremos em contato."*, e em seguida esvazia/limpa totalmente o formulário para o estado original!
