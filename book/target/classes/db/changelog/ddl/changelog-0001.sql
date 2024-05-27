-- Create Book table base on Book entity
CREATE TABLE books (
    id bigserial not null,
    title VARCHAR(255),
    price float(53),
    author_id bigint,
    category_id bigint,
    language smallint,
    description varchar(255),
    image_url varchar(255),
    -- Create and update time
    created_at timestamp(6),
    modified_at timestamp(6),
    -- Create and update by
    created_by varchar(255),
    last_modified_by varchar(255),
    version bigint not null,
    primary key (id)
);
create table authors (
    id bigserial not null,
    name varchar(255),
    email varchar(255),
    description varchar(255),
    bio varchar(255),
    image_url varchar(255),
    created_at timestamp(6),
    modified_at timestamp(6),
    created_by varchar(255),
    last_modified_by varchar(255),
    version bigint not null,
    primary key (id)
);
create table categories (
    id bigserial not null,
    name varchar(255),
    description varchar(255),
    created_at timestamp(6),
    modified_at timestamp(6),
    created_by varchar(255),
    last_modified_by varchar(255),
    version bigint not null,
    primary key (id)
);