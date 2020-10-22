create table pics
(
    id          int auto_increment
        primary key,
    userId      int                  null,
    location    varchar(200)         null,
    type        int                  not null comment '1头像，2logo，3签章',
    add_time    datetime             null,
    update_time datetime             null,
    deleted     tinyint(1) default 0 null,
    constraint pics_user__fk
        foreign key (userId) references user (id)
            on update cascade on delete cascade
)
    comment '由于一开始设计没考虑到，因此在此说明此表存所有用户传上来的资源，如图片，doc文档...,1:头像;2:学校logo;3:校长签章;4:mou模板';

INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (20, 247, '80f634b9-050a-495b-82b0-925bc65b3894.jpg', 2, '2020-10-17 11:29:08', '2020-10-17 18:05:38', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (21, 247, '532c3abb-11ce-4437-aa9b-af44e868ebfc.jpg', 3, '2020-10-17 12:24:07', '2020-10-17 18:06:23', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (24, 331, 'd64a0ccd-31d8-4540-a99e-4d53c98d3fec.jpg', 2, '2020-10-17 18:11:07', '2020-10-18 11:13:13', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (25, 331, '2d15f556-400c-442f-bd87-96d2161b7d14.jpg', 3, '2020-10-17 18:11:09', '2020-10-18 11:14:58', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (26, 330, 'ee0b7a83-3178-4a07-9454-3cdc0c33ff75.jpg', 2, '2020-10-17 21:42:40', '2020-10-17 21:43:49', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (27, 330, '78dca10b-49cb-47e9-aca9-cd40f170b40d.jpg', 3, '2020-10-17 21:43:12', '2020-10-17 21:43:12', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (28, 325, 'a4f9a5ca-324b-4988-8738-abb764dd79f1.docx', 4, '2020-10-18 16:10:07', '2020-10-18 21:52:58', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (29, 325, '442579ef-1ed0-427e-919a-490a326c5c25.jpg', 2, '2020-10-21 21:41:36', '2020-10-21 21:41:36', 0);
INSERT INTO school.pics (id, userId, location, type, add_time, update_time, deleted) VALUES (30, 325, 'd7d26d51-7f9c-4999-b598-8d6df75912bc.jpg', 3, '2020-10-21 21:41:41', '2020-10-21 21:41:41', 0);