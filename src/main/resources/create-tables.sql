CREATE TABLE Account (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,username VARCHAR(100),password VARCHAR(100) not null,firstName VARCHAR(100) not null, lastName VARCHAR(100) not null,address VARCHAR(100) not null,skills VARCHAR(100) not null);
create table UserConnection (userId varchar(100) not null,
	providerId varchar(100) not null,
	providerUserId varchar(100),
	rank int not null,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(255) not null,					
	secret varchar(255),
	refreshToken varchar(255),
	expireTime bigint,
	primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);