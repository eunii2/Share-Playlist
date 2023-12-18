INSERT IGNORE INTO `member` (id, userid, userpw, username, activated) values (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
INSERT IGNORE INTO `member` (id, userid, userpw, username, activated) values (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

INSERT IGNORE INTO authority (authority_name) values ('ROLE_ADMIN');
INSERT IGNORE INTO authority (authority_name) values ('ROLE_USER');

INSERT IGNORE INTO user_authority (id, authority_name) values (1, 'ROLE_ADMIN');
INSERT IGNORE INTO user_authority (id, authority_name) values (2, 'ROLE_USER');

INSERT INTO genre (name) values ("Gpop");
INSERT INTO genre (name) values ("Kpop");
INSERT INTO genre (name) values ("Ballad");
INSERT INTO genre (name) values ("Dance");
INSERT INTO genre (name) values ("Rock");
INSERT INTO genre (name) values ("Jazz");
INSERT INTO genre (name) values ("Classical");
INSERT INTO genre (name) values ("Hiphop");
INSERT INTO genre (name) values ("Blues");
INSERT INTO genre (name) values ("R&B");

INSERT INTO mood (name) values ("Happiness");
INSERT INTO mood (name) values ("Sadness");
INSERT INTO mood (name) values ("Anger");
INSERT INTO mood (name) values ("Peace");
INSERT INTO mood (name) values ("Love");
INSERT INTO mood (name) values ("Emotion");
INSERT INTO mood (name) values ("Excitement");
INSERT INTO mood (name) values ("Consolation");
INSERT INTO mood (name) values ("Dreamy");
INSERT INTO mood (name) values ("Loneliness");