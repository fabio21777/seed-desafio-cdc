
# Começo do fluxo de pagamento - parte2

### **necessidades**

Receber também o parâmetro relativo ao carrinho de compras no formulário final. O json montado pelo cliente relativo ao carrinho tem o seguinte formato:

```json
{
  "total": decimal,
  "itens": [
    {
      "idLivro": number,
      "quantidade": "number"
    },
    {
      "idLivro": number,
      "quantidade": number
    }
  ]
}
```

### **restrição**

- o total é não nulo
- o total é maior que zero
- tem pelo menos um item no carrinho
- idLivro é obrigatório e precisa existir
- quantidade é obrigatória
- quantidade é maior que zero
- o total calculado no servidor precisa ser igual ao total enviado

### **resultado esperado**

- Compra gerada com um status de iniciada
- status 201 gerado com o endereço de detalhe da compra

### sobre a utilização do material de suporte aqui

Estamos continuando o processo da criação de uma compra dentro do nosso sistema. Agora você tem um desafio de criar as classes que representam a estrutura do pedido. Tal estrutura está fora do seu controle, já que foi a aplicação responsável por controlar o carrinho de compras que decidiu.

Existem alguns desafios para você nessa funcionalidade. Como você vai modelar o pedido? Como você vai verificar se o total especificado no json do carrinho realmente é o total relativo para aquele conjunto de itens?

### **informações de suporte para a feature**

1. A prioridade do código é funcionar
   [AQUI](https://drive.google.com/file/d/1yZIhgjrV5HghcDSvIKmNaWF5FKAgcWS9/view?usp=sharing) e
   [AQUI](https://drive.google.com/file/d/10QO8jZJ2WTIJFCJ2-1iyQxAH7YmxiOX5/view?usp=sharing)
