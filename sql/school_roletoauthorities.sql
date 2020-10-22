create table roletoauthorities
(
    id        int auto_increment
        primary key,
    roleId    int          null,
    authority varchar(200) null,
    constraint roletoauthorities_role__fk
        foreign key (roleId) references role (id)
);

INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (1, 1, '/1');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (2, 2, '/2');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (3, 3, '/3');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (4, 4, '/4');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (16, 2, '/like');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (17, 2, 'user::like');
INSERT INTO school.roletoauthorities (id, roleId, authority) VALUES (18, 2, 'user::sign');