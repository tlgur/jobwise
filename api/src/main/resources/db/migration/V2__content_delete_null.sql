alter table content
    drop foreign key fk_content_comment;
alter table content
    drop foreign key fk_root_comment;

alter table content
    modify column parent_id bigint null;
alter table content
    modify column root_post_id bigint null;
alter table content
    modify column title varchar(255) null;

alter table content
    add constraint fk_root_comment
    foreign key (root_post_id)
    references content (content_id);

alter table content
    add constraint fk_content_comment
    foreign key (parent_id)
    references content (content_id);