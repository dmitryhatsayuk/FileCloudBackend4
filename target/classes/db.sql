create table "users"
(
    id        int primary key,
    login     varchar(255),
    password  varchar(255),
    authority varchar(255)
);

insert into "users" (id, login, password, authority)
values (1, 'user', 'user', 'USER'),
       (2, 'user2', 'user2', 'USER');

create table "user_files"
(
    id           int primary key,
    file_name    varchar(255),
    file_content oid,
    user_id int references "users" (id)
);

insert into "user_files"(id, file_name, file_content, user_id)
values (201, 'TestFile1.jpg', lo_from_bytea(0, '71,77,65,72,74,79'), 1),
       (202, 'TestFile2.jpg', lo_from_bytea(0, '61,73,64,66,67'), 1),
       (203, 'TestFile3.jpg', lo_from_bytea(0, '64,66,67,68,6a'), 2);


