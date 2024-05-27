
create TABLE notification (
    id BIGSERIAL NOT NULL,

    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL,

    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    last_modified_by VARCHAR(255),
    version bigint not null,
    PRIMARY KEY (id)
);
