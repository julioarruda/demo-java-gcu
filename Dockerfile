# Alterado por GFT AI Impact Bot: Usando uma imagem base mais segura e confiável
FROM maven:3.6.3-openjdk-8-slim

# Alterado por GFT AI Impact Bot: Executando como usuário não root para evitar privilégios excessivos
RUN useradd -m myuser
USER myuser

# Alterado por GFT AI Impact Bot: Atualizando e instalando apenas as dependências necessárias
RUN apt-get update && \
    apt-get install -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Copiando arquivos como usuário não root
COPY --chown=myuser:myuser . .

CMD ["mvn", "spring-boot:run"]