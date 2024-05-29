-- add data to book table
insert into books (id, title, price, author_id, category_id, language, description, image_url, created_at, modified_at, created_by, last_modified_by, version) values
(1, 'Book 1', 10, 1, 1, 1, 'Description 1', 'http://image1', '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'admin', 'admin', 1);

insert into authors (id, name, email, description, bio, image_url, created_at, modified_at, created_by, last_modified_by, version) values
(1, 'Admin', 'admin@localhost', 'Admin', 'Admin', 'http://image1', '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'admin', 'admin', 1);

INSERT into categories (id, name, description, created_at, modified_at, created_by, last_modified_by, version) values
(1, 'Category 1', 'Description 1', '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'admin', 'admin', 1);