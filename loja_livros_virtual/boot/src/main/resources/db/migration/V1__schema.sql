CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    authority varchar(255) NOT NULL
);
CREATE TABLE "user" (
    id BIGSERIAL primary key NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    account_expired BOOLEAN NOT NULL,
    account_locked BOOLEAN NOT NULL,
    password_expired BOOLEAN NOT NULL
);
CREATE TABLE user_role(
    id_role_id BIGINT NOT NULL,
    id_user_id BIGINT NOT NULL,
    FOREIGN KEY (id_role_id) REFERENCES role(id),
    FOREIGN KEY (id_user_id) REFERENCES "user"(id),
    PRIMARY KEY (id_role_id, id_user_id)
);


CREATE TABLE  refresh_token(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username varchar(255) NOT NULL,
    refresh_token varchar(255) NOT NULL,
    revoked BOOLEAN NOT NULL,
    date_created TIMESTAMP NOT NULL
);


-- criar usuario admin

INSERT INTO role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO role (authority) VALUES ('ROLE_USER');

-- usuario admin
INSERT INTO "user" (username, password, enabled, account_expired, account_locked, password_expired)
VALUES ('admin', '$2a$12$rvawqe7dKFe3g.gW.E6rmerryIaJSM45h6/g4yKqLxWdB6nXiJUlC', true, false, false, false);
INSERT INTO user_role (id_role_id, id_user_id)
VALUES (1, 1);

-- usuario padrao
INSERT INTO "user" (username, password, enabled, account_expired, account_locked, password_expired)
VALUES ('user', '$2a$12$7EynTDvqS3kPXbjhzpmIEOgde/cCcIbVsUz0zG/eOHwo/UAnd5Fre', true, false, false, false);
INSERT INTO user_role (id_role_id, id_user_id)
VALUES (2, 2);