-- ddl for table borrowing
-- Version: 1.0
-- Change Log: Initial version
-- Date: 2021-07-25
CREATE TABLE borrowing (
    id bigserial not null,
    book_id bigint,
    user_id bigint,
    borrow_date timestamp(6),
    return_date timestamp(6),
    created_at timestamp(6),
    modified_at timestamp(6),
    created_by varchar(255),
    last_modified_by varchar(255),
    version bigint not null,
    primary key (id)
);