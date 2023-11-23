# Alterado por GFT AI Impact Bot: Usando uma imagem base mais segura e confiável
FROM maven:3.6.3-openjdk-8-slim

# Alterado por GFT AI Impact Bot: Executando como usuário não root
RUN useradd -ms /bin/bash newuser
USER newuser

# Alterado por GFT AI Impact Bot: Instalando apenas as dependências necessárias
RUN apt-get update && \
    apt-get install -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Copiando o diretório atual para o container
COPY --chown=newuser:newuser . .

# Alterado por GFT AI Impact Bot: Executando o comando como usuário não root
CMD ["mvn", "spring-boot:run"]