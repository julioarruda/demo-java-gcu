# Alterado por GFT AI Impact Bot
# Usando um usuário não privilegiado para evitar a execução de processos com privilégios de root
FROM nginx:alpine
RUN addgroup -g 1001 -S nonroot && \
    adduser -u 1001 -D -S -G nonroot nonroot
USER nonroot
COPY . /usr/share/nginx/html