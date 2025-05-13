# Email do autor é único

### **necessidades**

- O email do autor precisa ser único no sistema

### **resultado esperado**

- Erro de validação no caso de email duplicado

### **informações de suporte geral**

1. [TODO FRAMEWORK MVC MINIMAMENTE MADURO POSSUI UM MECANISMO PRONTO DE REALIZAR VALIDAÇÃO CUSTOMIZADA. SPRING, NESTJS E ASP.NET CORE MVC TÊM.](https://drive.google.com/file/d/1wc5ChsPeGFjqypb9QI7tGRMl9dn0WkkL/view?usp=sharing)
2. Aqui provavelmente você terá um if em algum lugar para verificar a existência de um outro autor. Todo código que tem uma branch de código(if,else) tem mais chance de executar de maneira equivocada. Tente criar um teste automatizado para aumentar ainda mais a confiabilidade do seu código. [CRIAMOS TESTES AUTOMATIZADOS PARA QUE ELE NOS AJUDE A REVELAR E CONSERTAR BUGS NA APLICAÇÃO.​](https://drive.google.com/file/d/1SAvODkuAVuvM5Bm4cNp2W0Aes59GI-Eq/view?usp=sharing)
3. [COMO ALBERTO FARIA ESSE CÓDIGO?](https://drive.google.com/file/d/1zceQ8BTLA1D5gSJBrsq58fev66--Svsj/view?usp=sharing)

### informações de suporte para a combinação Java/Kotlin + Spring

1. Para receber os dados da request como json, temos a annotation @RequestBody
2. Usamos a annotation @Valid para pedir que os dados da request sejam validados
3. Para realizar as validações padrões existe a Bean Validation
4. [COMO CRIAR UM @RESTCONTROLLERADVICE PARA CUSTOMIZAR O JSON DE SAÍDA COM ERROS DE VALIDAÇÃO](https://drive.google.com/file/d/18q7IUF1EmeGrPFAab1CHIXP3COf5KNHd/view?usp=sharing)
5. [COMO EXTERNALIZAR AS MENSAGENS DE ERRO NO ARQUIVO DE CONFIGURAÇÃO.](https://drive.google.com/file/d/1FfYMfcbAODr3RKBFqtj8aj_Ztvjbkfhy/view?usp=sharing)

### **caso de aceitação**

- Dado que já existe um autor cadastrado com o email "autor@email.com"
- Quando tentar cadastrar um novo autor com o mesmo email "autor@email.com"
- Então o sistema deve retornar um erro de validação informando que o email já está em uso
- E não deve criar o novo registro de autor no sistema
