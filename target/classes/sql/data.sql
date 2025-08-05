select * from users;
delete from users where avatar is null;

update users set role = 'ADMIN' where email = 'hamza.kheiri@gmail.com';


INSERT INTO halls (serial_number, seats)
VALUES
    ('HALL001', 150),
    ('HALL002', 200),
    ('HALL003', 120);



INSERT INTO films (age_restrictions, description, title, year)
VALUES (13, 'Thriller about dreams', 'Inception', 2010),
       (18, 'Crime drama', 'The Departed', 2006),
       (0, 'Family adventure', 'The Incredibles', 2004),
       (0, 'Animated classic', 'Toy Story', 1995),
       (13, 'Superhero action', 'The Avengers', 2012);