create table content (
    content_id bigint not null auto_increment,

    depth integer not null default 0,
    title varchar(255) not null,
    body text not null ,
    dtype varchar(16) not null,

    parent_id bigint not null ,
    root_post_id bigint not null ,
    writer_id bigint not null ,

    primary key (content_id),
    check ( dtype in ('POST', 'COMMENT') )
) engine=InnoDB;

create table job_category (
    job_category_id bigint not null auto_increment,

    name varchar(255) not null unique ,

    primary key (job_category_id)
) engine=InnoDB;

create table post_job_category (
    post_job_category_id bigint not null auto_increment,

    job_category_id bigint not null ,
    post_id bigint not null ,

    primary key (post_job_category_id)
) engine=InnoDB;

create table user (
    user_id bigint not null auto_increment,

    username varchar(255) not null unique ,
    password varchar(255) not null ,
    real_name varchar(255) not null ,
    nickname varchar(255) not null unique ,

    primary key (user_id)
) engine=InnoDB;

create table user_job_category (
    user_job_category_id bigint not null auto_increment,

    job_category_id bigint not null ,
    user_id bigint not null ,

    primary key (user_job_category_id)
) engine=InnoDB;

alter table content
   add constraint fk_writer_content
   foreign key (writer_id)
   references user (user_id);

alter table content
   add constraint fk_content_comment
   foreign key (parent_id)
   references content (content_id);

alter table content
   add constraint fk_root_comment
   foreign key (root_post_id)
   references content (content_id);

alter table post_job_category
   add constraint fk_postjob_job
   foreign key (job_category_id)
   references job_category (job_category_id);

alter table post_job_category
   add constraint fk_postjob_post
   foreign key (post_id)
   references content (content_id);

alter table user_job_category
   add constraint fk_userjob_job
   foreign key (job_category_id)
   references job_category (job_category_id);

alter table user_job_category
   add constraint fk_userjob_user
   foreign key (user_id)
   references user (user_id);
