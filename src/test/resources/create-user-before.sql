delete from user_role;
delete from usr;

insert into usr(id, username, password, is_active, email, activation_code) values
(1, 'inginiir', '$2a$08$dRP9RbHc7J0vMUZtmYPIjOfiH6MxBlR1h46GBl3pk/iAc6CyVLjUu', true, 'email@email.com', null),
(2, 'elena', '$2a$08$dRP9RbHc7J0vMUZtmYPIjOfiH6MxBlR1h46GBl3pk/iAc6CyVLjUu', true, 'email@email.com', null);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');