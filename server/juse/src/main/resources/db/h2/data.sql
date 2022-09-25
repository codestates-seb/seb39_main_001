INSERT INTO TAGS (id, name, type)
VALUES (1, 'java', 'BACKEND');

INSERT INTO TAGS (id, name, type)
VALUES (2, 'react', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (3, 'kotlin', 'MOBILE');

INSERT INTO TAGS (id, name, type)
VALUES (4, 'figma', 'ETC');

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(1, 'img', 'hsbang.thom@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(2, 'img2', 'gatzby3645@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(3, 'img3', 'tayloredwings@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO USERS (id, img, introduction, email, portfolio, liked, role, provider, provider_id, nickname, social_user_id)
VALUES(1, 'img', 'user1', 'hsbang.thom@gmail.com', 'test', 0, 'ROLE_USER', 'provider1', 'providerId', 'nickname', 1);

INSERT INTO USERS (id, img, introduction, email, portfolio, liked, role, provider, provider_id, nickname, social_user_id)
VALUES(2, 'img2', 'user2', 'gatzby3645@gmail.com', 'test2', 0, 'ROLE_USER', 'provider1', 'providerId', 'nickname2', 2);

INSERT INTO USERS (id, img, introduction, email, portfolio, liked, role, provider, provider_id, nickname, social_user_id)
VALUES(3, 'img3', 'user3', 'tayloredwings@gmail.com', 'test3', 0, 'ROLE_USER', 'provider1', 'providerId', 'nickname3', 3);

