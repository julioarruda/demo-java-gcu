# Alterado por GFT AI Impact Bot: Usando uma imagem base mais segura e confiável
FROM maven:3.6.3-openjdk-8-slim

# Alterado por GFT AI Impact Bot: Executando como usuário não root
RUN useradd -ms /bin/bash user
USER user

# Alterado por GFT AI Impact Bot: Atualizando e instalando apenas as dependências necessárias
RUN apt-get update && \
    apt-get install -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Copiando apenas os arquivos necessários
COPY pom.xml .
COPY src ./src

# Alterado por GFT AI Impact Bot: Construindo o projeto com Maven
RUN mvn package

# Alterado por GFT AI Impact Bot: Executando o aplicativo com o comando java em vez de mvn
CMD ["java", "-jar", "target/my-app-1.0-SNAPSHOT.jar"]