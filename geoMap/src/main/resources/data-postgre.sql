INSERT INTO user_table (id, user_type, email,pasword) VALUES (3,'ADMNISTRATOR','admin@gmail.cc', '$2y$12$36Iz3dtM10D8MXjOmOjdUeCh3KG5h9hZ0wGFUIVe.tIuJ7JGmeQjq');
INSERT INTO user_table (id, user_type, email,pasword) VALUES (4,'USER', 'user@gmail.cc', '$2y$12$MH4riFIZar/74dU4Z2jE/upVcKbcFjiX/UaY0PJMACB1MIOwp6vdy');

INSERT INTO authority(name) VALUES ('ROLE_ADMIN');
INSERT INTO authority(name) VALUES ('ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) values (3,1);
INSERT INTO user_authority (user_id, authority_id) values (4,2);



