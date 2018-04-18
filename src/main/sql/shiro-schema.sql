drop table if exists sys_user;
drop table if exists sys_organization;
drop table if exists sys_resource;
drop table if exists sys_role;

create table sys_user (
  id bigint auto_increment comment '用户主键',
  organization_id bigint comment '公司id',
  username varchar(100) comment '登陆名 唯一约束',
  password varchar(100) comment '密码',
  salt varchar(100) comment '加密的盐',
  role_ids varchar(100) comment '角色列表 比如31,41 用户拥有两个角色 角色id分别为31和41',
  locked bool default false comment '用户状态',
  constraint pk_sys_user primary key(id)
) charset=utf8 ENGINE=InnoDB comment '用户表';
create unique index idx_sys_user_username on sys_user(username);
create index idx_sys_user_organization_id on sys_user(organization_id);

create table sys_organization (
  id bigint auto_increment,
  name varchar(100),
  parent_id bigint,
  parent_ids varchar(100),
  available bool default false,
  constraint pk_sys_organization primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_sys_organization_parent_id on sys_organization(parent_id);
create index idx_sys_organization_parent_ids on sys_organization(parent_ids);


create table sys_resource (
  id bigint auto_increment comment '资源主键',
  name varchar(100) comment '资源名称',
  type varchar(50) comment '资源类型',
  url varchar(200) comment 'url',
  parent_id bigint commnet '资源的直接上级',
  parent_ids varchar(100) commnet '资源所有上级 中间用/隔离',
  permission varchar(100) commnet '权限值',
  available bool default false comment '资源状态',
  constraint pk_sys_resource primary key(id)
) charset=utf8 ENGINE=InnoDB comment '资源表';
create index idx_sys_resource_parent_id on sys_resource(parent_id);
create index idx_sys_resource_parent_ids on sys_resource(parent_ids);

create table sys_role (
  id bigint auto_increment comment '角色主键',
  role varchar(100) comment '角色名称',
  description varchar(100) comment '角色描述',
  resource_ids varchar(100) comment '资源集合 比如:11,21 表示角色拥有资源id为11和21',
  available bool default false comment '角色状态',
  constraint pk_sys_role primary key(id)
) charset=utf8 ENGINE=InnoDB comment '角色表';
create index idx_sys_role_resource_ids on sys_role(resource_ids);