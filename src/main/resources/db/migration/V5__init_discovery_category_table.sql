create table categories(
                            id int primary key auto_increment,
                            name varchar (100) not null ,
                            description varchar (200) not null
);

alter table discoveries add column category_id int null;
alter table discoveries add foreign key (category_id) references categories (id);

