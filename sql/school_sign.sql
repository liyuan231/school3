create table sign
(
    id               int auto_increment
        primary key,
    signUserId       int                  null comment '签约的一方',
    signedUserId     int                  null comment '被签约的一方
',
    update_time      datetime             null,
    add_time         datetime             null,
    deleted          tinyint(1) default 0 null,
    status           int        default 1 null comment '该则签约的状态，公示状态或隐藏状态',
    signSchoolName   varchar(200)         null,
    signedSchoolName varchar(200)         null,
    constraint sign_user__fk
        foreign key (signUserId) references user (id)
            on update cascade on delete cascade,
    constraint sign_user__fk_2
        foreign key (signedUserId) references user (id)
            on update cascade on delete cascade
);

INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (292, 247, 286, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校0', '学校39');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (293, 300, 312, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校53', '学校65');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (294, 263, 318, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校16', '学校71');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (295, 267, 301, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校20', '学校54');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (296, 280, 273, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校33', '学校26');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (299, 263, 308, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校16', '学校61');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (301, 286, 282, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校39', '学校35');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (302, 301, 292, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校54', '学校45');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (303, 262, 247, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校15', '学校0');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (304, 297, 247, '2020-10-13 12:35:43', '2020-10-13 12:35:43', 0, 1, '学校50', '学校0');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (305, 253, 317, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校6', '学校70');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (307, 302, 255, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校55', '学校8');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (308, 319, 308, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校72', '学校61');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (309, 284, 310, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校37', '学校63');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (310, 280, 317, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校33', '学校70');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (311, 283, 306, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校36', '学校59');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (312, 299, 252, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校52', '学校5');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (314, 299, 259, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校52', '学校12');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (315, 313, 254, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校66', '学校7');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (316, 286, 275, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校39', '学校28');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (317, 319, 306, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校72', '学校59');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (318, 276, 309, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校29', '学校62');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (319, 285, 250, '2020-10-17 22:45:59', '2020-10-13 12:35:44', 1, 1, '学校38', '学校3');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (320, 259, 264, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校12', '学校17');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (321, 297, 262, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校50', '学校15');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (322, 295, 254, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校48', '学校7');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (323, 322, 257, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校75', '学校10');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (324, 275, 268, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校28', '学校21');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (325, 274, 254, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校27', '学校7');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (326, 309, 310, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校62', '学校63');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (327, 261, 290, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校14', '学校43');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (328, 291, 250, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校44', '学校3');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (329, 275, 258, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校28', '学校11');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (330, 294, 257, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校47', '学校10');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (331, 298, 256, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校51', '学校9');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (332, 301, 299, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校54', '学校52');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (335, 284, 267, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校37', '学校20');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (336, 256, 279, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校9', '学校32');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (337, 302, 294, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校55', '学校47');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (338, 307, 255, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校60', '学校8');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (339, 285, 301, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校38', '学校54');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (340, 284, 259, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校37', '学校12');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (341, 309, 314, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校62', '学校67');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (342, 287, 269, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校40', '学校22');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (343, 251, 256, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校4', '学校9');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (344, 295, 293, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校48', '学校46');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (345, 285, 250, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校38', '学校3');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (346, 290, 262, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校43', '学校15');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (347, 273, 294, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校26', '学校47');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (349, 292, 269, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校45', '学校22');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (350, 272, 297, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校25', '学校50');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (351, 264, 309, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校17', '学校62');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (352, 315, 281, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校68', '学校34');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (354, 276, 297, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校29', '学校50');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (356, 281, 312, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校34', '学校65');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (357, 255, 291, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校8', '学校44');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (358, 313, 292, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校66', '学校45');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (359, 286, 277, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校39', '学校30');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (360, 253, 298, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校6', '学校51');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (361, 300, 301, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校53', '学校54');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (363, 297, 300, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校50', '学校53');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (364, 318, 320, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校71', '学校73');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (366, 311, 277, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校64', '学校30');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (367, 267, 304, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校20', '学校57');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (368, 276, 316, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校29', '学校69');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (369, 298, 286, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校51', '学校39');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (370, 261, 313, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校14', '学校66');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (371, 290, 315, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校43', '学校68');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (372, 319, 276, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校72', '学校29');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (373, 268, 253, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校21', '学校6');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (374, 253, 281, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校6', '学校34');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (376, 298, 307, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校51', '学校60');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (377, 272, 307, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校25', '学校60');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (378, 260, 302, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校13', '学校55');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (379, 276, 320, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校29', '学校73');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (380, 266, 267, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校19', '学校20');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (381, 319, 301, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校72', '学校54');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (382, 293, 299, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校46', '学校52');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (383, 273, 251, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校26', '学校4');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (384, 287, 267, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校40', '学校20');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (385, 281, 306, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校34', '学校59');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (386, 278, 262, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校31', '学校15');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (387, 295, 309, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校48', '学校62');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (388, 278, 318, '2020-10-13 12:35:44', '2020-10-13 12:35:44', 0, 1, '学校31', '学校71');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (389, 330, 247, '2020-10-17 19:19:25', '2020-10-17 19:19:25', 0, 1, 'GDUFS', '学校0');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (390, 331, 320, '2020-10-17 22:50:05', '2020-10-17 22:21:55', 1, 1, null, '学校73');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (391, 331, 319, '2020-10-17 22:46:38', '2020-10-17 22:28:53', 1, 1, null, '学校72');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (392, 331, 318, '2020-10-17 22:46:38', '2020-10-17 22:28:53', 1, 1, null, '学校71');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (393, 331, 330, '2020-10-17 22:46:38', '2020-10-17 22:29:58', 1, 1, null, 'GDUFS');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (394, 331, 323, '2020-10-17 22:46:38', '2020-10-17 22:31:35', 1, 1, null, '7676学校名');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (395, 331, 323, '2020-10-18 12:42:11', '2020-10-18 10:34:31', 1, 1, null, '7676学校名');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (396, 331, 330, '2020-10-18 12:42:19', '2020-10-18 10:34:31', 1, 1, null, 'GDUFS');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (397, 331, 318, '2020-10-18 12:42:11', '2020-10-18 10:34:31', 1, 1, null, '学校71');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (398, 331, 319, '2020-10-18 12:42:11', '2020-10-18 10:34:31', 1, 1, null, '学校72');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (399, 331, 320, '2020-10-18 12:42:19', '2020-10-18 10:34:31', 1, 1, null, '学校73');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (400, 331, 321, '2020-10-18 12:42:19', '2020-10-18 10:34:31', 1, 1, null, '学校74');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (401, 331, 308, '2020-10-18 12:42:19', '2020-10-18 10:34:31', 1, 1, null, '学校61');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (402, 331, 322, '2020-10-18 12:42:11', '2020-10-18 11:15:09', 1, 1, null, '学校75');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (403, 331, 306, '2020-10-18 12:42:11', '2020-10-18 11:15:09', 1, 1, null, '学校59');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (404, 331, 323, '2020-10-18 16:59:36', '2020-10-18 14:18:46', 1, 1, null, '7676学校名');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (405, 331, 330, '2020-10-18 16:59:36', '2020-10-18 14:18:46', 1, 1, null, 'GDUFS');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (406, 331, 309, '2020-10-18 16:59:36', '2020-10-18 14:18:46', 1, 1, null, '学校62');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (407, 331, 322, '2020-10-18 16:59:36', '2020-10-18 14:18:46', 1, 1, null, '学校75');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (408, 331, 319, '2020-10-18 16:59:36', '2020-10-18 14:30:31', 1, 1, null, '学校72');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (409, 331, 320, '2020-10-21 21:42:02', '2020-10-18 17:04:48', 1, 1, '广东外语外贸大学', '学校73');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (410, 331, 323, '2020-10-18 17:04:48', '2020-10-18 17:04:48', 0, 1, '广东外语外贸大学', '7676学校名');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (411, 331, 330, '2020-10-18 17:04:48', '2020-10-18 17:04:48', 0, 1, '广东外语外贸大学', 'GDUFS');
INSERT INTO school.sign (id, signUserId, signedUserId, update_time, add_time, deleted, status, signSchoolName, signedSchoolName) VALUES (412, 331, 300, '2020-10-18 17:04:48', '2020-10-18 17:04:48', 0, 1, '广东外语外贸大学', '学校53');