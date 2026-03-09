create table topicos (

    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensaje text not null,
    fecha_creacion datetime not null,
    status varchar(50) not null,
    autor varchar(100) not null,
    curso varchar(100) not null,
    activo boolean not null default true,

    primary key (id)
    
);
