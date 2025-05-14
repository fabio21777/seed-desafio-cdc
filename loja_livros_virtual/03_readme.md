# Cadastro de uma categoria

## Necessidades

- Toda categoria precisa de um nome

## Restrições

- O nome é obrigatório
- O nome não pode ser duplicado

## Resultado esperado

- Uma nova categoria cadastrada no sistema e status 200 retorno
- Caso alguma restrição não seja atendida, retorne 400 e um json informando os problemas de validação

## Casos de aceitação

- Deve aceitar uma categoria com nome válido e retornar status 200
- Deve rejeitar uma categoria sem nome e retornar status 400 com mensagem apropriada
- Deve rejeitar uma categoria com nome duplicado e retornar status 400 com mensagem apropriada

