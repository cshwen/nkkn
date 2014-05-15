# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                        bigint not null,
  name                      varchar(255),
  author                    varchar(255),
  publisher                 varchar(255),
  isbn                      varchar(255),
  price                     double,
  pubtime                   timestamp,
  pages                     integer,
  summary                   varchar(255),
  score                     double,
  stock                     integer,
  img_path                  varchar(255),
  constraint pk_book primary key (id))
;

create table comment (
  id                        bigint not null,
  content                   varchar(255),
  time                      varchar(255),
  user_id                   bigint,
  book_id                   bigint,
  constraint pk_comment primary key (id))
;

create table user (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  role                      integer,
  phone                     varchar(255),
  regtime                   timestamp,
  constraint pk_user primary key (id))
;

create sequence book_seq;

create sequence comment_seq;

create sequence user_seq;

alter table comment add constraint fk_comment_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_1 on comment (user_id);
alter table comment add constraint fk_comment_book_2 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_comment_book_2 on comment (book_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists comment;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

drop sequence if exists comment_seq;

drop sequence if exists user_seq;

