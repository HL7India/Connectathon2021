alter table sequences add column encounter_id bigint;
alter table sequences add column pid bigint;

update sequences set pid = '1500' where id =1;
update sequences set encounter_id = '1500' where id =1;