create table comments(
                            id int primary key auto_increment,
                            date_added datetime null,
                            description varchar (200) not null
);

alter table comments add column discovery_id int null;
alter table comments add foreign key (discovery_id) references discoveries (id);

alter table comments add column user_id int null;
alter table comments add foreign key (user_id) references users (id) ;

alter table discoveries add column comment_id int null ;
alter table discoveries add foreign  key (comment_id) references comments (id);