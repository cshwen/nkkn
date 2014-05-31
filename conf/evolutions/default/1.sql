# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  author                    varchar(255),
  publisher                 varchar(255),
  isbn                      char(13),
  price                     char(32),
  pubtime                   datetime,
  pages                     varchar(255),
  summary                   TEXT,
  score                     float,
  stock                     integer,
  img_path                  varchar(255),
  sales                     integer,
  category_num              char(2),
  constraint pk_book primary key (id))
;

create table cart_item (
  id                        bigint auto_increment not null,
  user_id                   bigint not null,
  book_id                   bigint,
  num                       integer,
  price                     double,
  constraint pk_cart_item primary key (id))
;

create table category (
  num                       char(2) not null,
  name                      char(32),
  constraint pk_category primary key (num))
;

create table comment (
  id                        bigint auto_increment not null,
  content                   varchar(255),
  time                      datetime,
  user_id                   bigint,
  book_id                   bigint,
  constraint pk_comment primary key (id))
;

create table order_item (
  id                        bigint auto_increment not null,
  orders_id                 bigint not null,
  book_id                   bigint,
  num                       integer,
  price                     double,
  constraint pk_order_item primary key (id))
;

create table orders (
  id                        bigint auto_increment not null,
  user_id                   bigint not null,
  sum                       double,
  time                      datetime,
  state                     char(16),
  constraint pk_orders primary key (id))
;

create table role (
  id                        bigint auto_increment not null,
  name                      char(16),
  constraint pk_role primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  username                  char(32),
  password                  char(32),
  email                     varchar(255),
  phone                     varchar(255),
  regtime                   datetime,
  role_id                   bigint,
  constraint pk_user primary key (id))
;

alter table book add constraint fk_book_category_1 foreign key (category_num) references category (num) on delete restrict on update restrict;
create index ix_book_category_1 on book (category_num);
alter table cart_item add constraint fk_cart_item_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_cart_item_user_2 on cart_item (user_id);
alter table cart_item add constraint fk_cart_item_book_3 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_cart_item_book_3 on cart_item (book_id);
alter table comment add constraint fk_comment_user_4 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_4 on comment (user_id);
alter table comment add constraint fk_comment_book_5 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_comment_book_5 on comment (book_id);
alter table order_item add constraint fk_order_item_orders_6 foreign key (orders_id) references orders (id) on delete restrict on update restrict;
create index ix_order_item_orders_6 on order_item (orders_id);
alter table order_item add constraint fk_order_item_book_7 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_order_item_book_7 on order_item (book_id);
alter table orders add constraint fk_orders_user_8 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_orders_user_8 on orders (user_id);
alter table user add constraint fk_user_role_9 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_9 on user (role_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table book;

drop table cart_item;

drop table category;

drop table comment;

drop table order_item;

drop table orders;

drop table role;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

