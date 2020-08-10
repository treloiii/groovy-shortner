create table url (
    id bigint primary key auto_increment not null,
    input_url mediumtext not null,
    redirect_postfix varchar(255) not null unique,
    lifetime datetime not null default CURRENT_TIMESTAMP
)