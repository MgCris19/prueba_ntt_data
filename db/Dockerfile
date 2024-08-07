FROM mysql:5.7

# Copiar el script SQL en el contenedor
COPY ./init.sql /docker-entrypoint-initdb.d/

# Variables de entorno necesarias
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_DATABASE=mydatabase

EXPOSE 3306