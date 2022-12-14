insert into oauth_authority(id, name) values (1, "SUPER_ADMIN");
insert into oauth_authority(id, name) values (2, "ADMIN");
insert into oauth_authority(id, name) values (3, "USER");


insert into oauth_user(username, PASSWORD, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, ENABLED)
    values('admin@gmail.com','$2a$08$OttNZyoF8Iil8BqpKp8aGemXC48ww9ULtmjY.bVw9bFfQjW7y9tIu', FALSE, FALSE, FALSE, TRUE);

insert into oauth_user_authorities(user_id, authority_id) values(1,1);
insert into oauth_user_authorities(user_id, authority_id) values(1,2);
insert into oauth_user_authorities(user_id, authority_id) values(1,3);


INSERT INTO oauth_client_details(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('test-webapp-r', 'service-api',
	/*test-webapp-r-1234*/'$2a$04$A9KJ3JREOrQWvTGGgbdCo.NKIWF9yJdXFckZ0VtIuV9O5KNJsId6S',
	'read', 'password,authorization_code,refresh_token,implicit', 'USER', 10800000, 2592000);

INSERT INTO oauth_client_details(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('test-webapp-rw', 'service-api',
	/*test-webapp-rw-1234*/'$2a$04$bjCtmXxNlp5cyuFDfu/Xs.Nzd/BcWCpgPq61dVOgJdP7lSX.eZztG',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 1080000, 2592000);


