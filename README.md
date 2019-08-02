# Disco Project  
## Requisitos  
Maven, Java 8  
## Getting Started  
O projeto foi divido em módulos. Para realizar build de todos os módulos, basta utilizar o maven no projeto raíz. Também é possível dar build nos projetos de forma individual. Todos são projetos maven, utilizando Spring como principal framework, e Java 8.  
Cada módulo representa um microserviço:  
disco-service-registry: Microserviço de registro de serviços.  
disco-cashback: Microserviço para consulta e atualização dos valores de cashback.  
disco-catalog: Microserviço para consulta de discos.  
disco-sales: Microserviço para consulta e registro de vendas.  
disco-commons: Pacote contendo libs comuns, como logs.  
O microserviço disco-service-registry deve ser iniciado primeiro, para todos os serviços conseguirem "se comunicar". Após o mesmo, os outros serviços podem ser iniciados em qualquer ordem.  
## Configs  
Para comodidade, todos os serviços estão compartilhando o mesmo banco de dados MongoDB remoto no mlab.com. As credenciais de acesso estão configurados no arquivo application.properties de cada microserviço.  
Foi desenvolvido uma camada de segurança para consumir os microserviços. As credenciais de acesso também estão configurados no arquivo application.properties de cada módulo, bem como a url de acesso ao disco-service-registry.  
## Endpoints  
Os endpoints estão disponíveis em: CashbackEnpoint.java, CatalogEndpoint.java, SaleEndpoint.java.  
Para carregar os valores de cashback, consumir o serviço /load de CashbackEndpoint.java.  
Para carregar o catálogo de discos utilizando a api do Spotify, consumir o serviço /load de CatalogEndpoint.java.  
## TODO  
Swagger.  
Docker.  
Ampliar classes de testes. Apenas SaleService está coberto.  
