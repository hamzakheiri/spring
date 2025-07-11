select * from users;
delete from users where avatar is null;

update users set role = 'ADMIN' where email = 'hamza.kheiri@gmail.com';