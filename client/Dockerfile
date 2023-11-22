# Alterado por GFT AI Impact Bot
# Usando um usuário não privilegiado para evitar a execução de processos com privilégios de root
FROM nginx:alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
COPY . /usr/share/nginx/html