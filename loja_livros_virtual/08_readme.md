## Começo do fluxo de pagamento - parte1

### **necessidades**

Uma coisa importante. Na cdc, você não faz um cadastro e tem suas compras associadas. Toda vez você coloca seu email, cpf/cnpj etc. Como isso vai ser implementado depende da aplicação.

Os seguintes campos precisam ser preenchidos:
- email
- nome
- sobrenome
- documento(cpf/cnpj)
- endereco
- complemento
- cidade
- pais
- estado (caso aquele país tenha estado)
- telefone
- cep

### **restrição**

- email obrigatório e com formato adequado
- nome obrigatório
- sobrenome obrigatório
- documento(cpf/cnpj) obrigatório e só precisa ser um cpf ou cnpj
- endereco obrigatório
- complemento obrigatório
- cidade obrigatório
- país obrigatório
- se o país tiver estados, um estado precisa ser selecionado
- estado (caso aquele país tenha estado) - apenas se o país tiver cadastro de estados
- telefone obrigatório
- cep é obrigatório

### **resultado esperado**

- Compra parcialmente gerada, mas ainda não gravada no banco de dados. Falta os dados do pedido em si que vão ser trabalhados no próximo cartão.

### **sobre a utilização do material de suporte aqui**

Este começo de fechamento de compra envolve muitos passos. Decidimos começar pegando apenas os dados do formulário relativo a pessoa que está comprando. Este é um formulário um pouco mais desafiador, já que possuímos algumas validações customizadas que precisam ser feitas. Não tem nada que você não tenha trabalhado até aqui, mas é mais uma chance de você treinar sua habilidade para conhecer mais das tecnologias e colocar em prática alguns dos pilares que vem nos norteando. ​

### **informações de suporte para a feature**

1. [Link para o material de suporte](https://drive.google.com/file/d/1yZIhgjrV5HghcDSvIKmNaWF5FKAgcWS9/view?usp=sharing)

---

### **casos de validação**

- Se o email for inválido, o sistema deve exibir uma mensagem clara e não permitir o envio do formulário.
- Caso o CPF/CNPJ esteja em um formato incorreto, deve-se bloquear a submissão e indicar o erro.
- Países que não possuem estados devem ignorar o campo "estado".
- CEPs inválidos devem ser validados com base na máscara definida para o país.
- Telefones devem conter DDD válido (com base em tabela nacional) e número no formato local.
- Campos obrigatórios não preenchidos devem mostrar mensagem de erro antes de permitir o envio.
