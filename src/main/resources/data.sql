INSERT IGNORE INTO `member` (id, userid, userpw, username, activated) values (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
INSERT IGNORE INTO `member` (id, userid, userpw, username, activated) values (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

INSERT IGNORE INTO authority (authority_name) values ('ROLE_ADMIN');
INSERT IGNORE INTO authority (authority_name) values ('ROLE_USER');

INSERT IGNORE INTO user_authority (id, authority_name) values (1, 'ROLE_ADMIN');
INSERT IGNORE INTO user_authority (id, authority_name) values (2, 'ROLE_USER');