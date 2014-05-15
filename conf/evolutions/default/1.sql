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

create table cart_item (
  id                        bigint not null,
  user_id                   bigint not null,
  book_id                   bigint,
  num                       integer,
  price                     double,
  constraint pk_cart_item primary key (id))
;

create table comment (
  id                        bigint not null,
  content                   varchar(255),
  time                      timestamp,
  user_id                   bigint,
  book_id                   bigint,
  constraint pk_comment primary key (id))
;

create table order_item (
  id                        bigint not null,
  orders_id                 bigint not null,
  book_id                   bigint,
  num                       integer,
  price                     double,
  constraint pk_order_item primary key (id))
;

create table orders (
  id                        bigint not null,
  user_id                   bigint not null,
  sum                       double,
  time                      timestamp,
  state                     varchar(255),
  constraint pk_orders primary key (id))
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

create sequence cart_item_seq;

create sequence comment_seq;

create sequence order_item_seq;

create sequence orders_seq;

create sequence user_seq;

alter table cart_item add constraint fk_cart_item_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_cart_item_user_1 on cart_item (user_id);
alter table cart_item add constraint fk_cart_item_book_2 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_cart_item_book_2 on cart_item (book_id);
alter table comment add constraint fk_comment_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_3 on comment (user_id);
alter table comment add constraint fk_comment_book_4 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_comment_book_4 on comment (book_id);
alter table order_item add constraint fk_order_item_orders_5 foreign key (orders_id) references orders (id) on delete restrict on update restrict;
create index ix_order_item_orders_5 on order_item (orders_id);
alter table order_item add constraint fk_order_item_book_6 foreign key (book_id) references book (id) on delete restrict on update restrict;
create index ix_order_item_book_6 on order_item (book_id);
alter table orders add constraint fk_orders_user_7 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_orders_user_7 on orders (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists book;

drop table if exists cart_item;

drop table if exists comment;

drop table if exists order_item;

drop table if exists orders;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists book_seq;

drop sequence if exists cart_item_seq;

drop sequence if exists comment_seq;

drop sequence if exists order_item_seq;

drop sequence if exists orders_seq;

drop sequence if exists user_seq;

