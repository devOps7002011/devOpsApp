create sequence hibernate_sequence start with 1 increment by 1;


create table song (
  id bigint not null,
  genre varchar(256)not null,
  title varchar(256)not null,
  year integer not null,
  primary key (id)
);
