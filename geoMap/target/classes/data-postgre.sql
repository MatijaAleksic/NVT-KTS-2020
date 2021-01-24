INSERT INTO user_table (id, user_type, user_name, email,pasword) VALUES (1, 'ADMINISTRATOR', 'markomarkovic','markoMarkovic@maildrop.cc','MarkoMarkovic12');
INSERT INTO user_table (id, user_type, user_name, email,pasword) VALUES (2, 'USER', 'peroperic', 'peroPerovic@maildrop.cc','PeroPerovic12');

INSERT INTO cultural_offer(cultural_offer_id, name, creation_date, longitude, latitude) values (1,'Cultural offer 1', DATE '2021-01-23', 40.5, 20.5);

INSERT INTO rating(rating_id, value, cultural_offer_cultural_offer_id, user_id) values (1, 1, (SELECT cultural_offer_id FROM cultural_offer WHERE cultural_offer_id = '1'),(SELECT id FROM user_table WHERE id = '1'));

INSERT INTO image(image_id, name) values (1, 'image123');

INSERT INTO comment(comment_id, text, user_id) values (1, 'text123', (SELECT id FROM user_table WHERE id = '1'));

INSERT INTO authority (name) VALUES ('ADMIN');
INSERT INTO authority (name) VALUES ('USER');

insert into user_authority (user_id, authority_id) values (1, 1); -- admin has ROLE_ADMIN
insert into user_authority (user_id, authority_id) values (2, 2); -- user has ROLE_GUEST

INSERT INTO culture_type (culture_type_id, name) values (1, 'Institucija');
INSERT INTO culture_type (culture_type_id, name) values (2, 'Kulturno Dobro');
INSERT INTO culture_type (culture_type_id, name) values (3, 'Manifestacija');

INSERT INTO culture_subtype (culture_subtype_id, name) values (1, 'Muzej');

INSERT INTO news (news_id, title, text, creation_date) values (1, 'News 1', 'random text', DATE '2021-01-23');