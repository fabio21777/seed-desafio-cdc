# Cadastro de um novo autor

## necessidades
- É necessário cadastrar um novo autor no sistema. Todo autor tem um nome, email e uma descrição. Também queremos saber o instante exato que ele foi registrado.

## restrições
- O instante não pode ser nulo
- O email é obrigatório
- O email tem que ter formato válido
- O nome é obrigatório
- A descrição é obrigatória e não pode passar de 400 caracteres

## resultado esperado
- Um novo autor criado e status 200 retornado

## informações de suporte geral
1. Será que você fez um código parecido com esse exemplo [AQUI](https://drive.google.com/file/d/1TZ57A-QRAmlhyyuqBPW2sTCMr3gtdyP5/view?usp=sharing)?
2. Se a resposta para o ponto 1 foi sim, recomendo de novo esse material aqui sobre [ARQUITETURA X DESIGN](https://drive.google.com/file/d/1zyoz7tx4iqQI_A6QKRsDG3JvA1f3p1IM/view?usp=sharing). Também acho que vai ser legal você olhar a [MINHA IMPLEMENTAÇÃO LOGO DE CARA](https://drive.google.com/file/d/1inm4z8yHDh3bnkcbezEshkrb2ILSFq2T/view?usp=sharing), apenas para ter uma ideia de design que estou propondo.
3. [CONTROLLERS 100% COESOS](https://drive.google.com/file/d/10f3lT3lB2CEXdyss7ZjeSVzmDkzEU57d/view?usp=sharing) para lembrar você a nossa ideia de ter controllers que utilizam todos os atributos.
4. Como foi que você fez para receber os dados da requisição? Será que aproveitou a facilidade do framework e recebeu a sua entidade(objeto que faz parte do domínio) direto no método mapeado para um endereço? [DÁ UMA OLHADA NESSE PILAR AQUI.](https://drive.google.com/file/d/1SMwN_Dd9MdWI047o5dGJuBdPygbc6giX/view?usp=sharing)
5. Dado que você separou os dados que chegam da request do objeto de domínio, como vai fazer para converter dessa entrada para o domínio? [SUGIRO OLHAR UM POUCO SOBRE NOSSA IDEIA DE FORM VALUE OBJECTS](https://drive.google.com/file/d/18Mu6IG0CzuDtTjoPsFJWscOxG2LZvv6O/view?usp=sharing).
6. Muitos dos problemas de uma aplicação vem do fato dela trabalhar com objetos em estado inválido. O ponto mais crítico em relação a isso é justamente quando os dados vêm de outra fonte, por exemplo um cliente externo. É por isso que temos o seguinte pilar: quanto mais externa é a borda mais proteção nós temos. Confira uma explicação sobre ele [AQUI](https://drive.google.com/file/d/1P_860b6FL8mIj9X8yyQyW4B2YNL2kW5V/view?usp=sharing) e depois [AQUI](https://drive.google.com/file/d/1BgjdHCbrPP8ZuTRLi5tn2a7iPepr1sCR/view?usp=sharing)
7. [TODO FRAMEWORK MVC MINIMAMENTE MADURO POSSUI UM MECANISMO PRONTO DE REALIZAR VALIDAÇÃO CUSTOMIZADA. SPRING, NESTJS E ASP.NET CORE MVC TÊM.](https://drive.google.com/file/d/1wc5ChsPeGFjqypb9QI7tGRMl9dn0WkkL/view?usp=sharing)
8. Nome,email e descrição são informações obrigatórias. Como você lidou com isso? [INFORMAÇÃO NATURAL E OBRIGATÓRIA ENTRA PELO CONSTRUTOR](https://drive.google.com/file/d/1988eYtK-AqS6FVET1zO04HzjM6egHoKM/view?usp=sharing)
9. Deixamos pistas que facilitem o uso do código onde não conseguimos resolver com compilação. Muitas vezes recebemos String, ints que possuem significados. Um email por exemplo. Se você não pode garantir a validação do formato em compilação, [QUE TAL DEIXAR UMA DICA PARA A OUTRA PESSOA](https://drive.google.com/file/d/1TMENbD2V_87FmEGzwjTb4zqUnucsDnKM/view?usp=sharing)?
10. Utilize um insomnia ou qualquer outra forma para verificar o endpoint
11. [PEGUE CADA UMA DAS CLASSES QUE VOCÊ CRIOU E REALIZE A CONTAGEM DA CARGA INTRÍNSECA](https://drive.google.com/file/d/1MwuEjVO9evwVsYK5t5hB0q22uHj7CwSQ/view?usp=sharing). Esse é o viés de design que estamos trabalhando. Precisamos nos habituar a fazer isso para que se torne algo automático na nossa vida.
12. [COMO ALBERTO FARIA ESSE CÓDIGO](https://drive.google.com/file/d/1inm4z8yHDh3bnkcbezEshkrb2ILSFq2T/view?usp=sharing)?

## informações de suporte para a combinação Java/Kotlin + Spring
1. Para receber os dados da request como json, temos a annotation @RequestBody
2. Usamos a annotation @Valid para pedir que os dados da request sejam validados
3. Para realizar as validações padrões existe a Bean Validation
4. [COMO CRIAR UM @RESTCONTROLLERADVICE PARA CUSTOMIZAR O JSON DE SAÍDA COM ERROS DE VALIDAÇÃO](https://drive.google.com/file/d/18q7IUF1EmeGrPFAab1CHIXP3COf5KNHd/view?usp=sharing)
5. [COMO EXTERNALIZAR AS MENSAGENS DE ERRO NO ARQUIVO DE CONFIGURAÇÃO.](https://drive.google.com/file/d/1FfYMfcbAODr3RKBFqtj8aj_Ztvjbkfhy/view?usp=sharing)

## validação de aceitação
1. Verificar se o endpoint de cadastro de autor está acessível via POST
2. Testar o cadastro com dados válidos e verificar retorno 200
3. Testar o cadastro com email inválido e verificar mensagem de erro apropriada
4. Testar o cadastro sem nome e verificar mensagem de erro apropriada
5. Testar o cadastro sem email e verificar mensagem de erro apropriada
6. Testar o cadastro sem descrição e verificar mensagem de erro apropriada
7. Testar o cadastro com descrição maior que 400 caracteres e verificar mensagem de erro apropriada
