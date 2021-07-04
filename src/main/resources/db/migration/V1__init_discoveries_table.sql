drop  table if exists discoveries;
create table discoveries(
    id int primary key auto_increment,
    title varchar (100) not null ,
    description varchar (200) not null ,
    url varchar (100),
    done bit
)