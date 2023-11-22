# Alterado por GFT AI Impact Bot: Usando uma imagem mais segura e atualizada
FROM openjdk:11-jdk-slim

# Alterado por GFT AI Impact Bot: Adicionando um usuário não root para evitar privilégios excessivos
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Alterado por GFT AI Impact Bot: Atualizando e instalando dependências necessárias
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Alterado por GFT AI Impact Bot: Mudando para o usuário não root
USER spring:spring

# Alterado por GFT AI Impact Bot: Copiando arquivos como o usuário não root
COPY --chown=spring:spring . .

# Alterado por GFT AI Impact Bot: Executando o comando como o usuário não root
CMD ["mvn", "spring-boot:run"]