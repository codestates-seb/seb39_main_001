INSERT INTO TAGS (id, name, type)
VALUES (1, 'java', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(2, 'spring', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(3, 'nodejs', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(4, 'nestjs', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(5, 'go', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(6, 'kotlin', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(7, 'express', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(8, 'mysql', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(9, 'monggodb', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(10, 'python', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(11, 'django', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(12, 'php', 'BACKEND');

INSERT INTO TAGS(id, name, type)
VALUES(13, 'graphql', 'BACKEND');

INSERT INTO TAGS (id, name, type)
VALUES (14, 'javascript', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (15, 'typescript', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (16, 'react', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (17, 'vue', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (18, 'svelte', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (19, 'next', 'FRONTEND');

INSERT INTO TAGS (id, name, type)
VALUES (20, 'flutter', 'MOBILE');

INSERT INTO TAGS (id, name, type)
VALUES (21, 'swift', 'MOBILE');

INSERT INTO TAGS (id, name, type)
VALUES (22, 'reactnative', 'MOBILE');

INSERT INTO TAGS (id, name, type)
VALUES (23, 'unity', 'MOBILE');

INSERT INTO TAGS (id, name, type)
VALUES (24, 'aws', 'ETC');

INSERT INTO TAGS (id, name, type)
VALUES (25, 'kubernetes', 'ETC');

INSERT INTO TAGS (id, name, type)
VALUES (26, 'docker', 'ETC');

INSERT INTO TAGS (id, name, type)
VALUES (27, 'git', 'ETC');

INSERT INTO TAGS (id, name, type)
VALUES (28, 'jest', 'ETC');
--

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(1, 'img', 'hsbang.thom@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(2, 'img2', 'gatzby3645@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO SOCIAL_USERS (id, img, email, name, role, provider, provider_id)
VALUES(3, 'img3', 'tayloredwings@gmail.com','Hyeonsu Bang', 'ROLE_USER', 'provider', 'providerId');

INSERT INTO USERS (id, img, introduction, email, portfolio, liked, nickname, social_user_id)
VALUES(1, 'img', 'user1', 'hsbang.thom@gmail.com', 'test', 0, 'nickname', 1);

INSERT INTO USERS (id, img, introduction, email, portfolio, liked, nickname, social_user_id)
VALUES(2, 'img2', 'user2', 'gatzby3645@gmail.com', 'test2', 0,'nickname2', 2);
--
--INSERT INTO USERS (id, img, introduction, email, portfolio, liked, nickname, social_user_id)
--VALUES(3, 'img3', 'user3', 'tayloredwings@gmail.com', 'test3', 0, 'nickname3', 3);


INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(1, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '1', '2022-10-10', 'OPENING', '', 'STUDY', 0, 1, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(2, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '2', '2022-10-10', 'OPENING', '', 'PROJECT', 0, 1, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(3, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '1', '2022-10-10', 'OPENING', '', 'STUDY', 0, 1, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(4, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '3', '2022-10-10', 'OPENING', '', 'PROJECT', 0, 1, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(5, current_timestamp, current_timestamp, 1, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '5', '2022-10-10', 'OPENING', '', 'STUDY', 0, 2, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(6, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, 'short', '2022-10-10', 'OPENING', '', 'PROJECT', 0, 2, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(7, current_timestamp, current_timestamp, 1, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, 'short', '2022-10-10', 'OPENING', '', 'PROJECT', 0, 2, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(8, current_timestamp, current_timestamp, 1, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, 'long', '2022-10-10', 'CLOSED', '', 'STUDY', 0, 2, 0);

INSERT INTO BOARDS
(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id, cur_people)
VALUES(9, current_timestamp, current_timestamp, 2, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '6', '2022-10-10', 'CLOSED', '', 'STUDY', 0, 2, 0);

--INSERT INTO BOARDS
--(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id)
--VALUES(10, current_timestamp, current_timestamp, 0, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '1', '2022-10-10', 'CLOSED', '', 'PROJECT', 0, 3);
--
--INSERT INTO BOARDS
--(id, created_at, modified_at, backend, bookmarks, contact, content, cur_backend, cur_designer, cur_etc, cur_frontend, designer, due_date, etc, frontend, on_offline, people, period, starting_date, status, title, type, views, user_id)
--VALUES(11, current_timestamp, current_timestamp, 0, 0, '', '', 0, 0, 0, 0, 0, '2022-10-10', 0, 0, '', 0, '1', '2022-10-10', 'CLOSED', '', 'PROJECT', 0, 3);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(1, 1, 1);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(2, 1, 2);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(3, 1, 3);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(4, 2, 1);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(5, 2, 2);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(6, 3, 1);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(7, 4, 1);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(8, 5, 2);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(9, 6, 2);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(10, 7, 3);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(11, 7, 4);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(12, 8, 1);

INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
VALUES(13, 9, 2);
--
--INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
--VALUES(14, 10, 1);
--
--INSERT INTO BOARDS_TAGS (id, board_id, tag_id)
--VALUES(15, 11, 4);
--

