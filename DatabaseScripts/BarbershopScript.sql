

   --  Create "barbershop_db"    original databse  ---
   --  Create "barbershop_test"    testing databse  ---

-- appt id    |    appt date  |  appt time |   userid    |   salon service id

-- userid  |  first name (50) | last name (50) |  phone (15) | email (256) | role | password

-- salon service id  | name (50)  | descip (4000) | duration int | price money

create table user_acc (

	user_id serial primary key,
	first_name varchar(40) not null,
	last_name varchar(40) not null,
	phone_number varchar(24) not null,
	email_address varchar(256) not null
);

alter table user_acc 
add column user_role varchar(50) not null;

alter table user_acc 
add column user_password varchar(50) not null;

create table salon_service (

	service_id serial primary key,
	service_name varchar(40) not null,
	description text not null,
	duration varchar(40) not null,
	price float not null
);

create table appointment (

	appointment_id serial primary key,
	appointment_date date not null,
	appointment_time time not null,
	user_id int references user_acc (user_id),
	service_id int references salon_service (service_id)
);


insert into user_acc (first_name, last_name, phone_number, email_address, user_role, user_password)
values ('Iyad', 'Shobaki', '333-333-9999', 'iyad@shobaki.com', 'Manager', 'Pwd12345@');


select * from user_acc

select * from appointment a 

select * from salon_service

select * from user_acc order by user_id asc

select * from appointment order by appointment_date, appointment_time desc 

select * from salon_service order by service_id  asc 

delete from appointment where user_id = 1

 delete from appointment where appointment_id != 1;

update user_acc set user_role = 'Manager' where user_id = 2

select a.appointment_id, ss.service_name, ss.description, ss.duration , ss.price, a.appointment_date , a.appointment_time 
from salon_service ss inner join appointment a on ss.service_id = a.service_id 
where user_id = 4
order by a.appointment_date , a.appointment_time desc

select a.appointment_id, ua.user_id, ua.first_name, ua.last_name, ua.email_address, ua.phone_number,
ua.user_role, ss.service_name, ss.duration , ss.price, a.appointment_date , a.appointment_time 
from salon_service ss inner join appointment a on ss.service_id = a.service_id 
inner join user_acc ua on ua.user_id = a.user_id  
order by ua.email_address, a.appointment_date , a.appointment_time desc

create or replace view all_appointments as select a.appointment_id, ua.user_id, ua.first_name, ua.last_name, ua.email_address, ua.phone_number,
ua.user_role, ss.service_name, ss.duration , ss.price, a.appointment_date , a.appointment_time 
from salon_service ss inner join appointment a on ss.service_id = a.service_id 
inner join user_acc ua on ua.user_id = a.user_id
order by ua.email_address, a.appointment_date , a.appointment_time desc

select * from all_appointments  -- view


create or replace function update_appointment(
	appt_date date,
	appt_time time,
	serv_id int,
    appt_id int)
returns void as $$
begin
  update appointment set appointment_date = appt_date, appointment_time = appt_time,
  service_id = serv_id where appointment_id = appt_id;
end;
$$ language plpgsql;

select update_appointment('2021-03-14','10:45:00',4,33);
------    Original database
select * from user_acc

select * from appointment

select * from salon_service

select * from all_appointments  -- view


































