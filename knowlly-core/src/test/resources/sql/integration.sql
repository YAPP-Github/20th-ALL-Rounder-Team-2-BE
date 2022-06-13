INSERT INTO user (id, username, ball_cnt, intro, is_coach, is_push_active, is_active, created_at, updated_at, kakao_id, portfolio, identifier, email) VALUES (1, '테스트1', 1, '안녕하세요. 저는 테스트1이라고 합니다.', true, true, true, '2022-06-10 21:18:58', '2022-06-10 21:19:00', 'kakao_test1', null, 'identifier1', 'test1@email.com');
INSERT INTO user (id, username, ball_cnt, intro, is_coach, is_push_active, is_active, created_at, updated_at, kakao_id, portfolio, identifier, email) VALUES (2, '테스트2', 2, '안녕하세요. 저는 테스트2이라고 합니다.', false, true, true, '2022-06-11 21:18:58', '2022-06-12 21:19:00', 'kakao_test2', null, 'identifier2', 'test2@email.com');
INSERT INTO user (id, username, ball_cnt, intro, is_coach, is_push_active, is_active, created_at, updated_at, kakao_id, portfolio, identifier, email) VALUES (3, '테스트3', 3, '안녕하세요. 저는 테스트3이라고 합니다.', true, false, true, '2022-06-13 21:18:58', '2022-06-13 21:19:00', 'kakao_test3', null, 'identifier3', 'test3@email.com');
INSERT INTO user (id, username, ball_cnt, intro, is_coach, is_push_active, is_active, created_at, updated_at, kakao_id, portfolio, identifier, email) VALUES (4, '테스트4', 5, '안녕하세요. 저는 테스트4이라고 합니다.', true, true, true, '2022-06-05 21:18:58', '2022-06-11 21:19:00', 'kakao_test4', null, 'identifier4', 'test4@email.com');
INSERT INTO user (id, username, ball_cnt, intro, is_coach, is_push_active, is_active, created_at, updated_at, kakao_id, portfolio, identifier, email) VALUES (5, '테스트5', 6, '안녕하세요. 저는 테스트5이라고 합니다.', true, true, false, '2022-06-08 21:18:58', '2022-06-10 21:19:00', 'kakao_test5', null, 'identifier5', 'test5@email.com');

INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (1, 1, 'http://test1.img.url', true, '2022-06-13 21:26:58', '2022-06-13 21:27:01');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (2, 1, 'http://test1.img2.url', false, '2022-06-13 21:26:59', '2022-06-13 21:27:00');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (3, 2, 'http://test2.img.url', false, '2022-06-13 21:27:02', '2022-06-13 21:27:01');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (4, 3, 'http://test3.img.url', true, '2022-06-13 21:27:03', '2022-06-13 21:27:03');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (5, 4, 'http://test4.img.url', true, '2022-06-13 21:27:04', '2022-06-13 21:27:04');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (6, 5, 'http://test5.img.url', true, '2022-06-13 21:27:05', '2022-06-13 21:27:05');
INSERT INTO user_image (id, user_id, user_img_url, is_active, created_at, updated_at) VALUES (7, 2, 'http://test2.img2.url', true, '2022-06-13 21:27:06', '2022-06-13 21:27:06');

INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (1, 1, '운영 클래스', '프랑스어 수업', 1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (2, 1, '수강 클래스', '영어 수업', -1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (3, 2, '온보딩', '온보딩', 1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (4, 2, '수강 클래스', '자바 수업', -1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (5, 3, '운영 클래스', '프랑스어 수업', 1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (6, 4, '온보딩', '온보딩', 1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');
INSERT INTO ball_history (id, user_id, title, content, count, is_active, created_at, updated_at) VALUES (7, 5, '운영 클래스', '프랑스어 수업', 1, true, '2022-06-13 21:52:34', '2022-06-13 21:52:35');

INSERT INTO coach (id, user_id, introduce, is_active, created_at, updated_at) VALUES (1, 1, '안녕하세요. 테스트1 코치입니다.', true, '2022-06-13 21:57:17', '2022-06-13 21:57:17');
INSERT INTO coach (id, user_id, introduce, is_active, created_at, updated_at) VALUES (2, 3, '안녕하세요. 테스트3 코치입니다.', true, '2022-06-13 21:57:18', '2022-06-13 21:57:18');
INSERT INTO coach (id, user_id, introduce, is_active, created_at, updated_at) VALUES (3, 4, '안녕하세요. 테스트4 코치입니다.', true, '2022-06-13 21:57:18', '2022-06-13 21:57:19');
INSERT INTO coach (id, user_id, introduce, is_active, created_at, updated_at) VALUES (4, 5, '안녕하세요. 테스트5 코치입니다.', false, '2022-06-13 21:57:20', '2022-06-13 21:57:19');

INSERT INTO notification (id, user_id, coach_id, content, noti_type, is_read, is_active, created_at, updated_at) VALUES (1, 1, 3, '알림 내용', 'PLAYER', false, true, '2022-06-13 22:17:08', '2022-06-13 22:17:09');
INSERT INTO notification (id, user_id, coach_id, content, noti_type, is_read, is_active, created_at, updated_at) VALUES (2, 3, 1, '알림 내용2', 'COACH', true, true, '2022-06-13 22:17:08', '2022-06-13 22:17:09');
INSERT INTO notification (id, user_id, coach_id, content, noti_type, is_read, is_active, created_at, updated_at) VALUES (3, 1, 2, '알림 내용', 'PLAYER', false, true, '2022-06-13 22:17:08', '2022-06-13 22:17:09');
INSERT INTO notification (id, user_id, coach_id, content, noti_type, is_read, is_active, created_at, updated_at) VALUES (4, 4, 3, '알림 내용', 'PLAYER', true, true, '2022-06-13 22:17:08', '2022-06-13 22:17:09');

INSERT INTO review (id, user_id, coach_id, content, is_active, created_at, updated_at) VALUES (1, 1, 3, '테스트3은 좋은 코치입니다!', true, '2022-06-13 22:19:14', '2022-06-13 22:19:15');
INSERT INTO review (id, user_id, coach_id, content, is_active, created_at, updated_at) VALUES (2, 2, 1, '테스트1 코치는 친절했어요~', true, '2022-06-13 22:19:14', '2022-06-13 22:19:15');
INSERT INTO review (id, user_id, coach_id, content, is_active, created_at, updated_at) VALUES (3, 4, 3, '테스트3 코치는 좀 별로였습니다', true, '2022-06-13 22:19:16', '2022-06-13 22:19:16');
INSERT INTO review (id, user_id, coach_id, content, is_active, created_at, updated_at) VALUES (4, 3, 1, '테스트1 코치가 최고에요~', true, '2022-06-13 22:19:18', '2022-06-13 22:19:17');

INSERT INTO category (id, category_name, is_active) VALUES (1, '기획 / PM', true);
INSERT INTO category (id, category_name, is_active) VALUES (2, '디자인', true);
INSERT INTO category (id, category_name, is_active) VALUES (3, '개발', true);
INSERT INTO category (id, category_name, is_active) VALUES (4, '마케팅', true);
INSERT INTO category (id, category_name, is_active) VALUES (5, '외국어', true);
INSERT INTO category (id, category_name, is_active) VALUES (6, '기타', true);

INSERT INTO lecture_information (id, coach_id, category_id, topic, introduce, price, is_active, created_at, updated_at) VALUES (1, 1, 4, '마케팅 수업', '효과적인 마케팅에 대해 배웁니다', 1, true, '2022-06-13 22:39:40', '2022-06-13 22:39:54');
INSERT INTO lecture_information (id, coach_id, category_id, topic, introduce, price, is_active, created_at, updated_at) VALUES (2, 2, 3, '자바 개발', '자바를 자바라', 1, true, '2022-06-13 22:39:41', '2022-06-13 22:39:54');
INSERT INTO lecture_information (id, coach_id, category_id, topic, introduce, price, is_active, created_at, updated_at) VALUES (3, 3, 2, '그래픽 디자인', '그래픽을 그래그래', 1, true, '2022-06-13 22:39:42', '2022-06-13 22:39:55');
INSERT INTO lecture_information (id, coach_id, category_id, topic, introduce, price, is_active, created_at, updated_at) VALUES (4, 4, 1, '좋은 기획이란 무엇인가', '기획이 제일 쉬웠어요 사실 뻥이야', 1, false, '2022-06-13 22:39:42', '2022-06-13 22:39:55');
INSERT INTO lecture_information (id, coach_id, category_id, topic, introduce, price, is_active, created_at, updated_at) VALUES (5, 1, 6, '요리 클래스', '맛있는 요리 만들기', 1, true, '2022-06-13 22:39:42', '2022-06-13 22:39:55');

INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (1, 1, '2022-06-08 22:44:10', '2022-06-08 23:44:04', 'DONE', true, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');
INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (2, 1, '2022-06-09 22:44:10', '2022-06-09 23:44:04', 'ON_GOING', false, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');
INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (3, 1, '2022-06-10 22:44:10', '2022-06-10 23:44:04', 'ON_BOARD', false, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');
INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (4, 2, '2022-06-12 22:44:10', '2022-06-12 23:44:04', 'ON_BOARD', false, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');
INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (5, 3, '2022-06-08 22:44:10', '2022-06-13 23:44:04', 'DONE', true, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');
INSERT INTO lecture (id, lecture_info_id, start_at, end_at, state, is_review_written, is_active, created_at, updated_at) VALUES (6, 4, '2022-06-13 22:44:10', '2022-06-13 23:44:04', 'ON_BOARD', false, true, '2022-06-08 22:44:10', '2022-06-08 22:44:10');

INSERT INTO tag (id, lecture_info_id, content, is_active, created_at, updated_at) VALUES (1, 1, '마케팅', true, '2022-06-13 22:42:23', '2022-06-13 22:42:23');
INSERT INTO tag (id, lecture_info_id, content, is_active, created_at, updated_at) VALUES (2, 1, '쉬워요', true, '2022-06-13 22:42:24', '2022-06-13 22:42:24');
INSERT INTO tag (id, lecture_info_id, content, is_active, created_at, updated_at) VALUES (3, 2, '언어', true, '2022-06-13 22:42:25', '2022-06-13 22:42:25');
INSERT INTO tag (id, lecture_info_id, content, is_active, created_at, updated_at) VALUES (4, 2, '자바개발', true, '2022-06-13 22:42:26', '2022-06-13 22:42:26');
INSERT INTO tag (id, lecture_info_id, content, is_active, created_at, updated_at) VALUES (5, 3, '전문가', false, '2022-06-13 22:42:27', '2022-06-13 22:42:27');

INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (1, 1, 'http://lecture1.img.url', true, '2022-06-13 21:26:58', '2022-06-13 21:27:01');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (2, 1, 'http://lecture1.img2.url', false, '2022-06-13 21:26:59', '2022-06-13 21:27:00');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (3, 2, 'http://lecture2.img.url', false, '2022-06-13 21:27:02', '2022-06-13 21:27:01');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (4, 3, 'http://lecture3.img.url', true, '2022-06-13 21:27:03', '2022-06-13 21:27:03');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (5, 4, 'http://lecture4.img.url', true, '2022-06-13 21:27:04', '2022-06-13 21:27:04');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (6, 5, 'http://lecture5.img.url', true, '2022-06-13 21:27:05', '2022-06-13 21:27:05');
INSERT INTO lecture_image (id, lecture_info_id, lecture_img_url, is_active, created_at, updated_at) VALUES (7, 2, 'http://lecture2.img2.url', true, '2022-06-13 21:27:06', '2022-06-13 21:27:06');

INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (1, 1, 4, '신청서를 받아주세요!', 'ACCEPT', true, '2022-06-13 22:48:17', '2022-06-13 22:48:17');
INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (2, 2, 3, '제 신청서를 받아주세요!', 'ACCEPT', true, '2022-06-13 22:48:17', '2022-06-13 22:48:17');
INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (3, 1, 2, '안받아주셔도 되요', 'REJECT', true, '2022-06-13 22:48:17', '2022-06-13 22:48:17');
INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (4, 4, 1, '신청서를 받아주세요!', 'REQUEST', true, '2022-06-13 22:48:17', '2022-06-13 22:48:17');
INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (5, 3, 3, '신청 거절해주세요', 'REJECT', true, '2022-06-13 22:51:03', '2022-06-13 22:51:04');
INSERT INTO form (id, lecture_id, user_id, content, state, is_active, created_at, updated_at) VALUES (6, 3, 4, '신청합니다!', 'REQUEST', true, '2022-06-13 22:51:03', '2022-06-13 22:51:04');

