create table role
(
    id     int auto_increment
        primary key,
    name   varchar(200) null,
    `desc` text         null
);

INSERT INTO school.role (id, name, `desc`) VALUES (1, 'ADMINISTRATOR', null);
INSERT INTO school.role (id, name, `desc`) VALUES (2, 'USER', null);
INSERT INTO school.role (id, name, `desc`) VALUES (3, '管理员', null);
INSERT INTO school.role (id, name, `desc`) VALUES (4, '管理员plus', null);