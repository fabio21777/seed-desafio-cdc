## Exibir a página de detalhe de um livro

### **Implementação da página de detalhe**

Precisamos criar uma página com as mesmas informações que encontramos na página de detalhe da Casa do Código. Aqui está a página real =>
[HTTPS://WWW.CASADOCODIGO.COM.BR/PRODUCTS/LIVRO-SPRING-BOOT](https://www.casadocodigo.com.br/products/livro-spring-boot)

**A ideia aqui é implementar todo código necessário para que tenhamos uma página com quase todas informações da página de detalhe da CDC.**

### **necessidades**

- Ter um endpoint que em função de um id de livro retorne os detalhes necessários para montar a página.

### **restrições**

- Se o id não existir é para retornar 404

### **Resultado esperado**

- que o front possa montar a página

### **sobre a utilização do material de suporte aqui**

Esta é uma feature que reutiliza as habilidades trabalhadas até aqui. Tente implementar sem o uso do material de suporte.

### **informações de suporte para a feature**

1. É importante você lembrar que estamos trabalhando a divisão de responsabilidade pelo olhar da dificuldade de entendimento. Uma pergunta que você pode se fazer aqui é: crio uma classe nova aqui ou apenas adiciono um método em um controller existente, dado que eu respeite a carga intrínseca máxima? Lembre de conferir a ideia de
[CONTROLLERS 100% COESOS](https://drive.google.com/file/d/10f3lT3lB2CEXdyss7ZjeSVzmDkzEU57d/view?usp=sharing)

2.

3. É normal uma mesma classe possuir mais de uma forma de exibição de seus dados no sistema.
[Ver exemplo](https://drive.google.com/file/d/1hFVUZrwNdqV0W4EY0zYDIm3vUSgJGf4...)

---

### **Critérios de Aceitação**

- A página deve conter todas as principais informações visíveis da página da Casa do Código.
- O endpoint de detalhe deve retornar um JSON com os dados esperados conforme mock da CDC.
- O status HTTP 404 deve ser retornado caso o `id` informado não exista.
- O layout deve seguir a estrutura e estilos utilizados no restante da aplicação.
- A exibição deve ser testada em resolução desktop e mobile.
