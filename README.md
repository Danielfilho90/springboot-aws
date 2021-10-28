# djvbox
Tema: Controle de armazenamento de arquivos na nuvem

Contexto: Com a finalidade de limitar o armazenamento de arquivos em um serviço de nuvem por um determinado usuário dependendo do plano contratado por ele. É necessário um serviço que controle o número de arquivos e capacidade de armazenamento de acordo com o plano do usuário.

Objetivos: 

Implementar uma API que fornece operações necessárias para:

- Cadastrar/Atualizar usuário definindo o plano contratado por ele.
- Receber o arquivo e armazenar em algum serviço na nuvem (AWS, Google Cloud, IBM Cloud, etc.). Ao receber o arquivo, deverá haver uma validação de acordo com o plano do usuário, verificando se o usuário possui espaço livre de armazenamento.
- Remover arquivo do armazenamento.
- Listar arquivos armazenados, de preferência com paginação.
- Status do armazenamento: espaço disponível e número de arquivos.

Considerar o seguinte tipo de dados e domínios.

PLANO (Básico, Avançado, Ilimitado)

Como sugestão, considerar os armazenamentos para cada plano: 


Plano Básico - 100MB de armazenamento, 20 arquivos.
Plano Avançado - 1GB de armazenamento, 100 arquivos.
Plano Ilimitado - Armazenamento e arquivos ilimitados.

Validações para as operações deverão ser consideradas na implementação do serviço e deverá retornar uma identificação do problema ocorrido. Validações necessárias:

- Informações com domínio definido devem apenas aceitar valores válidos.
