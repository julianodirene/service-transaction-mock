# service-transaction-mock

## Introdução

O service-transaction-mock é uma API REST que serve como mock para consulta de transações. O objetivo é uma API que a partir dos parâmetros gere aleatoriamente transações sem dependências externas ao projeto, como banco de dados ou bibliotecas de terceiros. Além disso quando passados os mesmos parâmetros deve retornar a mesma resposta.

## Como executar o serviço

A maneira mais fácil de executar esse serviço é através do docker. Após instalar o docker e docker-compose basta executar o seguinte comando:

   	docker-compose up -d

## Consultar transações por usuário e mês/ano

Atualmente existe apenas uma consulta de transaçoes que é realizada por usuário, ano e mês e deve ser realizada conforme o seguinte exemplo: 

**Requisição**
```shell
GET /{idUsuario}/transacoes/{ano}/{mes}'
```

**Resposta**
```shell
Status 200 OK
```
```json
[
  {
    "descricao": "Descrição da transação",
    "data": 1617321600000,
    "valor": 453616
  }
]
```