create table usertorole
(
    id     int auto_increment
        primary key,
    userId int null,
    roleId int null,
    constraint usertorole_role__fk
        foreign key (roleId) references role (id),
    constraint usertorole_user__fk
        foreign key (userId) references user (id)
            on update cascade on delete cascade
);

INSERT INTO school.usertorole (id, userId, roleId) VALUES (134, 247, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (137, 250, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (138, 251, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (139, 252, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (140, 253, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (141, 254, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (142, 255, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (143, 256, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (144, 257, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (145, 258, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (146, 259, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (147, 260, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (148, 261, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (149, 262, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (150, 263, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (151, 264, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (152, 265, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (153, 266, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (154, 267, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (155, 268, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (156, 269, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (157, 270, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (158, 271, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (159, 272, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (160, 273, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (161, 274, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (162, 275, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (163, 276, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (164, 277, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (165, 278, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (166, 279, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (167, 280, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (168, 281, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (169, 282, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (170, 283, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (171, 284, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (172, 285, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (173, 286, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (174, 287, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (175, 288, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (176, 289, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (177, 290, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (178, 291, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (179, 292, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (180, 293, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (181, 294, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (182, 295, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (183, 296, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (184, 297, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (185, 298, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (186, 299, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (187, 300, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (188, 301, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (189, 302, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (190, 303, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (191, 304, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (192, 305, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (193, 306, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (194, 307, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (195, 308, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (196, 309, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (197, 310, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (198, 311, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (199, 312, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (200, 313, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (201, 314, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (202, 315, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (203, 316, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (204, 317, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (205, 318, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (206, 319, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (207, 320, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (208, 321, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (209, 322, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (210, 323, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (211, 324, 2);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (212, 325, 1);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (217, 330, 1);
INSERT INTO school.usertorole (id, userId, roleId) VALUES (218, 331, 2);