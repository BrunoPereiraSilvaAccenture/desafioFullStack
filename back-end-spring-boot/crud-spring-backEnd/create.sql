create table empresa (id_empresa bigint not null, cep varchar(8) not null, cnpj varchar(14) not null, endereco varchar(255) not null, estado varchar(2) not null, nomefantasia varchar(255) not null, primary key (id_empresa)) engine=InnoDB
create table fornecedor (id bigint not null, cep varchar(8) not null, cnpjcpf varchar(14) not null, datanascimento datetime, email varchar(255), endereco varchar(255) not null, estado varchar(2) not null, nome varchar(255) not null, rg varchar(8), tipodocumento varchar(1) not null, empresa_id_empresa bigint not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
alter table fornecedor add constraint FKf5mbxhx4ol9g3rist52d4uhre foreign key (empresa_id_empresa) references empresa (id_empresa) on delete cascade
create table empresa (id_empresa bigint not null, cep varchar(8) not null, cnpj varchar(14) not null, endereco varchar(255) not null, estado varchar(2) not null, nomefantasia varchar(255) not null, primary key (id_empresa)) engine=InnoDB
create table fornecedor (id bigint not null, cep varchar(8) not null, cnpjcpf varchar(14) not null, datanascimento datetime, email varchar(255), endereco varchar(255) not null, estado varchar(2) not null, nome varchar(255) not null, rg varchar(8), tipodocumento varchar(1) not null, empresa_id_empresa bigint not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
alter table fornecedor add constraint FKf5mbxhx4ol9g3rist52d4uhre foreign key (empresa_id_empresa) references empresa (id_empresa) on delete cascade
