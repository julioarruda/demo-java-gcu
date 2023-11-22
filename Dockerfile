# Alterado por GFT AI Impact Bot: Usando uma imagem base mais segura
FROM openjdk:8-slim

# Alterado por GFT AI Impact Bot: Adicionando um usuário não root
RUN useradd -ms /bin/bash user

# Alterado por GFT AI Impact Bot: Atualizando e instalando pacotes necessários como usuário não root
USER user
RUN apt-get update && \
    apt-get install -y --no-install-recommends build-essential maven default-jdk cowsay netcat && \
    update-alternatives --config javac && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Copiando arquivos como usuário não root
COPY --chown=user:user . .

# Alterado por GFT AI Impact Bot: Executando o comando como usuário não root
CMD ["mvn", "spring-boot:run"]