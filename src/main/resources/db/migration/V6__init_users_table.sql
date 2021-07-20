drop table if exists users;
create table users(
                      id int primary key auto_increment,
                      first_name varchar(25) not null ,
                      last_name varchar (25) not null ,
                      email varchar (100) not null ,
                      password varchar (200) not null,
                      role enum ('USER', 'ADMIN') not null
);

alter table discoveries add column user_id int null;
alter table discoveries add foreign key (user_id) references users(id);
