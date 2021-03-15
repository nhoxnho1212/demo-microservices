CREATE TABLE IF NOT EXISTS demo_microservice.product (
    id int not null auto_increment,
    name varchar(255) not null,
    price bigint,
    category varcharacter(255),
    primary key(id),
    foreign key(category) references demo_microservice.category(id)
);