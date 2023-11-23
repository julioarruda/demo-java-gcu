# Alterado por GFT AI Impact Bot: Usando uma imagem base mais segura e confiável
FROM maven:3.6.3-openjdk-8-slim

# Alterado por GFT AI Impact Bot: Executando como usuário não root
RUN useradd -m myuser
USER myuser

# Alterado por GFT AI Impact Bot: Instalando apenas as dependências necessárias
RUN apt-get update && \
    apt-get install -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Copiando apenas os arquivos necessários
COPY pom.xml .
COPY src ./src

# Alterado por GFT AI Impact Bot: Construindo o projeto durante a fase de construção da imagem
RUN mvn package

# Alterado por GFT AI Impact Bot: Executando o aplicativo com o comando java em vez de mvn
CMD ["java", "-jar", "target/myapp-0.0.1-SNAPSHOT.jar"]