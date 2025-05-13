FROM postgres:latest
ENV POSTGRES_PASSWORD=fsm
ENV POSTGRES_USER=fsm
ENV POSTGRES_DB=fsm
COPY scripts/* /docker-entrypoint-initdb.d/
