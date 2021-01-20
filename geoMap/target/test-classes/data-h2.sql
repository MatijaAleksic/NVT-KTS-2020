INSERT INTO user_table (id, user_type, user_name, email,pasword) VALUES (nextval('user_sequence'), 'ADMINISTRATOR', 'markomarkovic','markoMarkovic@maildrop.cc','MarkoMarkovic12');
INSERT INTO user_table (id, user_type, user_name, email,pasword) VALUES (nextval('user_sequence'), 'USER', 'peroperic', 'peroPerovic@maildrop.cc','PeroPerovic12');

INSERT INTO cultural_offer(name) values ('Institucija');

INSERT INTO rating(rating_id, value, cultural_offer_cultural_offer_id) values ((SELECT id FROM user_table WHERE email = 'markoMarkovic@maildrop.cc'), 1, (SELECT cultural_offer_id FROM cultural_offer WHERE name = 'Institucija'));

INSERT INTO image(image_id, name) values ((SELECT id FROM user_table WHERE email = 'markoMarkovic@maildrop.cc'), 'image123');

INSERT INTO comment(comment_id, text) values ((SELECT id FROM user_table WHERE email = 'markoMarkovic@maildrop.cc'), 'text123');

