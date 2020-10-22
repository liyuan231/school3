create table likes
(
    id              int auto_increment
        primary key,
    likeUserId      int                  null comment '代表“我”',
    likedUserId     int                  null comment '喜欢 她',
    update_time     datetime             null,
    add_time        datetime             null,
    deleted         tinyint(1) default 0 null,
    likeSchoolName  varchar(200)         null,
    likedSchoolName varchar(200)         null,
    constraint likes_user__fk
        foreign key (likeUserId) references user (id)
            on update cascade on delete cascade,
    constraint likes_user__fk_2
        foreign key (likedUserId) references user (id)
            on update cascade on delete cascade
);

INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (150, 288, 315, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校41', '学校68');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (151, 315, 302, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校68', '学校55');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (152, 256, 255, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校9', '学校8');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (153, 294, 320, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校47', '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (155, 292, 250, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校45', '学校3');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (158, 294, 302, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校47', '学校55');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (159, 280, 305, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校33', '学校58');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (160, 311, 303, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校64', '学校56');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (161, 290, 259, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校43', '学校12');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (162, 257, 287, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校10', '学校40');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (163, 304, 308, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校57', '学校61');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (165, 296, 267, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校49', '学校20');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (166, 274, 262, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校27', '学校15');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (167, 257, 304, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校10', '学校57');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (168, 295, 298, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校48', '学校51');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (170, 275, 295, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校28', '学校48');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (171, 274, 265, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校27', '学校18');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (172, 278, 253, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校31', '学校6');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (173, 279, 310, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校32', '学校63');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (174, 282, 313, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校35', '学校66');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (175, 265, 322, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校18', '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (176, 280, 317, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校33', '学校70');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (177, 283, 269, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校36', '学校22');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (178, 298, 279, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校51', '学校32');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (179, 297, 300, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校50', '学校53');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (180, 305, 302, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校58', '学校55');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (181, 263, 307, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校16', '学校60');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (182, 274, 304, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校27', '学校57');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (183, 258, 321, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校11', '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (184, 251, 257, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校4', '学校10');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (185, 251, 281, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校4', '学校34');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (186, 304, 289, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校57', '学校42');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (187, 250, 297, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校3', '学校50');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (188, 269, 282, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校22', '学校35');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (189, 292, 281, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校45', '学校34');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (190, 264, 273, '2020-10-13 13:01:05', '2020-10-13 13:01:05', 0, '学校17', '学校26');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (193, 281, 264, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校34', '学校17');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (194, 285, 275, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校38', '学校28');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (195, 313, 297, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校66', '学校50');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (196, 258, 279, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校11', '学校32');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (197, 287, 321, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校40', '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (198, 322, 260, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校75', '学校13');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (199, 317, 260, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校70', '学校13');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (200, 281, 262, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校34', '学校15');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (202, 291, 289, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校44', '学校42');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (203, 296, 247, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校49', '学校0');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (204, 253, 315, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校6', '学校68');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (205, 320, 312, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校73', '学校65');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (206, 280, 311, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校33', '学校64');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (207, 259, 278, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校12', '学校31');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (211, 313, 307, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校66', '学校60');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (212, 280, 318, '2020-10-13 13:02:08', '2020-10-13 13:02:08', 0, '学校33', '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (213, 288, 252, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校41', '学校5');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (215, 303, 252, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校56', '学校5');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (216, 278, 266, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校31', '学校19');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (218, 279, 276, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校32', '学校29');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (219, 270, 262, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校23', '学校15');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (220, 299, 298, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校52', '学校51');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (221, 301, 290, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校54', '学校43');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (222, 294, 285, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校47', '学校38');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (223, 298, 300, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校51', '学校53');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (224, 292, 308, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校45', '学校61');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (225, 258, 261, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校11', '学校14');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (226, 304, 296, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校57', '学校49');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (228, 281, 304, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校34', '学校57');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (229, 250, 276, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校3', '学校29');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (230, 289, 274, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校42', '学校27');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (232, 299, 313, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校52', '学校66');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (233, 264, 303, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校17', '学校56');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (235, 288, 261, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校41', '学校14');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (236, 286, 247, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校39', '学校0');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (237, 273, 274, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校26', '学校27');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (238, 300, 247, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校53', '学校0');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (239, 280, 271, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校33', '学校24');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (240, 321, 309, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校74', '学校62');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (241, 269, 267, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校22', '学校20');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (242, 279, 291, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校32', '学校44');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (243, 321, 267, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校74', '学校20');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (244, 316, 261, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校69', '学校14');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (245, 310, 275, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校63', '学校28');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (247, 259, 318, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校12', '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (248, 263, 287, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校16', '学校40');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (249, 247, 260, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校0', '学校13');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (250, 253, 286, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校6', '学校39');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (251, 310, 320, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校63', '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (252, 253, 318, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校6', '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (253, 303, 295, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校56', '学校48');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (254, 320, 281, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校73', '学校34');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (255, 291, 266, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校44', '学校19');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (257, 296, 290, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校49', '学校43');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (258, 264, 306, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校17', '学校59');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (259, 271, 285, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校24', '学校38');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (260, 288, 264, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校41', '学校17');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (261, 322, 255, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校75', '学校8');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (262, 291, 261, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校44', '学校14');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (263, 254, 251, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校7', '学校4');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (264, 296, 268, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校49', '学校21');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (265, 309, 311, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校62', '学校64');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (266, 313, 315, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校66', '学校68');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (267, 283, 298, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校36', '学校51');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (268, 283, 318, '2020-10-13 13:02:09', '2020-10-13 13:02:09', 0, '学校36', '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (269, 322, 297, '2020-10-17 11:11:56', '2020-10-17 11:03:38', 1, '学校50', null);
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (270, 310, 295, '2020-10-17 11:12:26', '2020-10-17 11:12:09', 1, '学校48', null);
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (271, 310, 281, '2020-10-17 11:17:29', '2020-10-17 11:14:29', 1, '学校34', null);
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (272, 310, 294, '2020-10-17 11:17:27', '2020-10-17 11:15:36', 1, '学校47', null);
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (273, 310, 294, '2020-10-17 11:17:25', '2020-10-17 11:17:19', 1, '学校63', '学校47');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (274, 310, 281, '2020-10-17 11:17:24', '2020-10-17 11:17:19', 1, '学校63', '学校34');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (275, 310, 262, '2020-10-17 11:17:23', '2020-10-17 11:17:19', 1, '学校63', '学校15');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (276, 322, 305, '2020-10-17 11:17:36', '2020-10-17 11:17:36', 0, '学校75', '学校58');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (277, 322, 303, '2020-10-17 18:14:35', '2020-10-17 18:14:35', 0, '学校75', '学校56');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (278, 330, 247, '2020-10-17 19:12:07', '2020-10-17 19:12:07', 0, 'GDUFS', '学校0');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (279, 330, 331, '2020-10-18 17:01:01', '2020-10-17 19:12:07', 1, 'GDUFS', null);
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (280, 331, 319, '2020-10-17 22:30:51', '2020-10-17 22:00:18', 1, null, '学校72');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (281, 331, 321, '2020-10-17 22:11:07', '2020-10-17 22:00:18', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (282, 331, 320, '2020-10-17 22:11:05', '2020-10-17 22:00:18', 1, null, '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (283, 331, 322, '2020-10-17 22:11:03', '2020-10-17 22:00:18', 1, null, '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (284, 331, 310, '2020-10-17 22:11:01', '2020-10-17 22:05:52', 1, null, '学校63');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (285, 331, 313, '2020-10-17 22:11:00', '2020-10-17 22:10:27', 1, null, '学校66');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (286, 331, 314, '2020-10-17 22:10:58', '2020-10-17 22:10:27', 1, null, '学校67');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (287, 331, 320, '2020-10-17 22:15:51', '2020-10-17 22:11:13', 1, null, '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (288, 331, 306, '2020-10-17 22:12:45', '2020-10-17 22:11:25', 1, null, '学校59');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (289, 331, 307, '2020-10-17 22:12:43', '2020-10-17 22:11:25', 1, null, '学校60');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (290, 331, 308, '2020-10-17 22:12:42', '2020-10-17 22:11:54', 1, null, '学校61');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (291, 331, 309, '2020-10-17 22:12:40', '2020-10-17 22:11:54', 1, null, '学校62');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (292, 331, 322, '2020-10-17 22:13:57', '2020-10-17 22:13:36', 1, null, '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (293, 331, 321, '2020-10-17 22:13:56', '2020-10-17 22:13:36', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (294, 331, 321, '2020-10-17 22:15:48', '2020-10-17 22:13:56', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (295, 331, 276, '2020-10-17 22:15:45', '2020-10-17 22:15:00', 1, null, '学校29');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (296, 331, 330, '2020-10-17 22:15:44', '2020-10-17 22:15:09', 1, null, 'GDUFS');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (297, 331, 308, '2020-10-17 22:15:42', '2020-10-17 22:15:22', 1, null, '学校61');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (298, 331, 309, '2020-10-17 22:15:40', '2020-10-17 22:15:22', 1, null, '学校62');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (299, 331, 318, '2020-10-17 22:30:49', '2020-10-17 22:16:54', 1, null, '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (300, 331, 320, '2020-10-17 22:28:24', '2020-10-17 22:16:54', 1, null, '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (301, 331, 321, '2020-10-17 22:28:22', '2020-10-17 22:16:54', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (302, 331, 306, '2020-10-17 22:28:21', '2020-10-17 22:17:36', 1, null, '学校59');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (303, 331, 322, '2020-10-17 22:28:20', '2020-10-17 22:19:54', 1, null, '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (304, 331, 320, '2020-10-17 22:30:48', '2020-10-17 22:28:30', 1, null, '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (305, 331, 330, '2020-10-17 22:30:46', '2020-10-17 22:29:44', 1, null, 'GDUFS');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (306, 331, 321, '2020-10-17 22:30:45', '2020-10-17 22:30:36', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (307, 331, 323, '2020-10-18 16:55:11', '2020-10-17 22:31:11', 1, null, '7676学校名');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (308, 331, 330, '2020-10-18 17:01:29', '2020-10-17 22:31:12', 1, null, 'GDUFS');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (309, 331, 318, '2020-10-18 12:43:09', '2020-10-17 22:31:12', 1, null, '学校71');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (310, 331, 319, '2020-10-18 12:43:08', '2020-10-18 10:33:57', 1, null, '学校72');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (311, 331, 320, '2020-10-18 12:43:06', '2020-10-18 10:33:57', 1, null, '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (312, 331, 321, '2020-10-18 12:43:05', '2020-10-18 10:33:57', 1, null, '学校74');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (313, 331, 308, '2020-10-18 12:43:03', '2020-10-18 10:33:57', 1, null, '学校61');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (314, 331, 322, '2020-10-18 12:43:02', '2020-10-18 10:53:33', 1, null, '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (315, 331, 306, '2020-10-18 12:43:00', '2020-10-18 10:55:31', 1, null, '学校59');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (316, 331, 309, '2020-10-18 16:53:34', '2020-10-18 14:06:34', 1, null, '学校62');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (317, 331, 322, '2020-10-18 16:53:32', '2020-10-18 14:18:42', 1, null, '学校75');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (318, 331, 319, '2020-10-18 16:53:31', '2020-10-18 14:30:12', 1, null, '学校72');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (319, 331, 320, '2020-10-18 16:59:46', '2020-10-18 16:59:46', 0, '广东外语外贸大学', '学校73');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (320, 331, 323, '2020-10-18 17:00:03', '2020-10-18 17:00:03', 0, '广东外语外贸大学', '7676学校名');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (321, 330, 331, '2020-10-18 17:01:14', '2020-10-18 17:01:14', 0, 'GDUFS', '广东外语外贸大学');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (322, 331, 330, '2020-10-18 17:02:11', '2020-10-18 17:02:11', 0, '广东外语外贸大学', 'GDUFS');
INSERT INTO school.likes (id, likeUserId, likedUserId, update_time, add_time, deleted, likeSchoolName, likedSchoolName) VALUES (323, 331, 300, '2020-10-18 17:04:45', '2020-10-18 17:04:45', 0, '广东外语外贸大学', '学校53');