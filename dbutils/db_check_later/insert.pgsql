-- country
insert into country (name) values ('Казахстан');
insert into country (name) values ('Россия');
insert into country (name) values ('США');
insert into country (name) values ('Япония');
insert into country (name) values ('Украина');


-- chat
insert into chat (chat_id,name, description, avatar_small, avatar_full, member_num)
values (1,'one','btw 2p','','',3);
insert into chat (chat_id,name, description, avatar_small, avatar_full, member_num)
values (2,'two','btw 2p','','',4);
insert into chat (chat_id,name, description, avatar_small, avatar_full, member_num)
values (3,'three','btw 2p','','',5);

-- human
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('nursat','555','nursat@icloud.con','nursat.baigenzheev','nursat','Nursat','Baigenzheyev','','blablabla',28,3,'','',false,1);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('boris','666','boris@icloud.con','null','boris_pristupa','Boris','Pristupa','Не_сегодня','blablabla1',30,5,'','',false,2);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('nursat1','777','nursat1@icloud.com','nursat.baigenzheeb','Nursat1','Nursat1','Baigenzheyev1','','blabla2',29,6,'','',false,3);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('boris1','888','boris1@icloud.com','boris.pristup1','boris_pristupa1','Boris1','Pristupa1','Не_сегодня1','blabla3',22,1,'','',false,4);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('nursat2','999','nursat2@icloud.co','nursat.baigenzheed','Nursat2','Nursat2','Baigenzheyev2','','blabla4',28,3,'','',false,5);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('boris2','101','boris2@icloud.co','boris.pristup2','boris_pristupa2','Boris2','Pristupa2','Не_сегодня2','blabla5',33,1,'','',true ,2);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('nursat3','102','nursat3@icloud.cd','nursat.baigenzheef','Nursat3','Nursat3','Baigenzheyev3','','blabla6',40,3,'','',true ,5);
insert into human (login,password,email,vk_id,tg_nickname,first_name,second_name,last_name,bio,likes,dislikes,avatar_small,avatar_full,banned,country_id)
          values ('boris3','103','boris3@icloud.cd','boris.pristup3','boris_pristupa3','Boris3','Pristupa3','Не_сегодня3','blabla7',32,2,'','',true ,1);


--human_chat
insert into human_chat (chat_id, human_id) VALUES (1,1);
insert into human_chat (chat_id, human_id) VALUES (1,2);
insert into human_chat (chat_id, human_id) VALUES (2,2);
insert into human_chat (chat_id, human_id) VALUES (2,3);
insert into human_chat (chat_id, human_id) VALUES (2,4);
insert into human_chat (chat_id, human_id) VALUES (3,1);
insert into human_chat (chat_id, human_id) VALUES (3,2);
insert into human_chat (chat_id, human_id) VALUES (3,3);
insert into human_chat (chat_id, human_id) VALUES (3,4);


--message
insert into message (chat_id,human_id,body,_date) values (1,1,'hello world!1','2018-09-28 01:00');
insert into message (chat_id,human_id,body,_date) values (1,2,'hello world!2','2018-09-28 02:00');
insert into message (chat_id,human_id,body,_date) values (2,2,'hello world!3','2018-09-28 03:00');
insert into message (chat_id,human_id,body,_date) values (2,2,'for human','2018-09-28 04:00');
insert into message (chat_id,human_id,body,_date) values (2,3,'for art','2018-09-28 05:00');
insert into message (chat_id,human_id,body,_date) values (2,4,'for exp','2018-09-28 06:00');
insert into message (chat_id,human_id,body,_date) values (3,2,'222','2018-09-28 06:01');
insert into message (chat_id,human_id,body,_date) values (3,3,'333','2018-09-28 06:02');
insert into message (chat_id,human_id,body,_date) values (3,1,'444','2018-09-28 06:03');


----------------------------------------------------------------------------------------------------------------------------------------


--archeaologist
insert into archaeologist (human_id) values (1);
insert into archaeologist (human_id) values (2);
insert into archaeologist (human_id) values (3);
insert into archaeologist (human_id) values (5);


--collector
insert into collector (human_id) values (1);
insert into collector (human_id) values (2);
insert into collector (human_id) values (4);

--moderator
insert into moderator values (3, 'Nursat1');
insert into moderator values (1, 'nursat');

--researcher
insert into researcher values (4);
insert into researcher values (1);

--sponsor
insert into sponsor values (5);
insert into sponsor values (1);


-----------------------------------------------------------------------------------------------------------------------------------------

--age
insert into age (description) values ('1000усл');
insert into age (description) values ('2000усл');
insert into age (description) values ('3000усл');


--category
insert into category (name) values ('органич');
insert into category (name) values ('неорганич');
insert into category (name) values ('чел ост');
--insert into category (category_id,name) values (4,'');


--artifact
insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('spoon',true,'this is anсient artefact like a spoon',1,'','',1,4,2,1,2,false);

insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('нож',true,'this is anсient artefact like a knife',3,'','',8,1,2,2,3,false);

insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('чашко-кружка',FALSE,'this is anсient artefact like a cup',2,'','',7,4,2,4,1,false);

insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('жрец',TRUE,'this is anсient artefact like a жрец',2,'','',4,1,2,5,3,false);

insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('дерево',TRUE,'this is anсient artefact like a дерево',2,'','',3,4,2,2,1,false);

insert into artifact (name,approved,description,age_id,avatar_small,avatar_full,owner,approver,origin,country_id,category_id,banned)
values ('Дизлайк из ВК',FALSE,'this is anсient artefact like a dislike',2,'','',7,1,2,2,1,true);
--?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????



--auction
insert into auction (artifact_id,price_old,price_new,raiser,bet_time,start_time,end_time)
values (1,555,600,1,now(),'2018-10-15'::timestamp,'2018-12-17'::timestamp);
insert into auction (artifact_id,price_old,price_new,raiser,bet_time,start_time,end_time)
values (2,666,700,2,now(),'2018-11-15'::timestamp,'2018-12-17'::timestamp);
insert into auction (artifact_id,price_old,price_new,raiser,bet_time,start_time,end_time)
values (4,10000,20000,3,'2018-11-15'::timestamp,'2018-11-15'::timestamp,'2018-11-17'::timestamp);
insert into auction (artifact_id,price_old,price_new,raiser,bet_time,start_time,end_time)
values (3,NULL,800,NULL,NULL,now(),'2018-12-17'::timestamp);

--subscription_auction
insert into subscription_auction (human_id, auction_id) values (1,2);
insert into subscription_auction (human_id, auction_id) values (1,3);
insert into subscription_auction (human_id, auction_id) values (2,1);
insert into subscription_auction (human_id, auction_id) values (2,2);
insert into subscription_auction (human_id, auction_id) values (2,3);
insert into subscription_auction (human_id, auction_id) values (3,2);

---------------------------------------------------------------------------------------------------------------------------------------------

--expedition_stage
insert into expedition_stage (stage_id,name) values (1,'начало');
insert into expedition_stage (stage_id,name) values (2,'2/4');
insert into expedition_stage (stage_id,name) values (3,'3/4');
insert into expedition_stage (stage_id,name) values (4,'конец');

--route
insert into route (route_id) values (1);
insert into route (route_id) values (2);
insert into route (route_id) values (3);
insert into route (route_id) values (4);

--stay, то есть остановка, превал
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (1, FALSE, now(), now() + '1 day', 50, -100);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (1, TRUE, now() + '2 days', now() + '4 days', 52, -100);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (1, FALSE, now() + '5 days', now() + '9 days', 50, -100);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (2, TRUE, now() + '1 day', now() + '3 days', 51, -100);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (2, TRUE, now() + '5 days', now() + '6 days', 52, -100);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (2, FALSE, now() + '6 days', now() + '7 days', 53, -101);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (2, TRUE, now() + '7 days', now() + '9 days', 54, -101);
insert into stay (route_id,excavations,start_date,end_date,latitude,longtitude) values (2, FALSE, now() + '11 days', now() + '15 days', 54, -99);
--?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????


--expedition
insert into expedition (name,description,avatar_small,avatar_full,costs,current_sum,stage_id,banned,route_plan,route_current,head)
values ('поиск Йети','в горы в поисках Йети','\x20D5','\x1193',100000,3000,2,false,1,2,3);
insert into expedition (name,description,avatar_small,avatar_full,costs,current_sum,stage_id,banned,route_plan,route_current,head)
values ('какая-то забаненная','в горы в поисках Йети','\x0001','\x11EE',100000,3000,2,TRUE,1,2,3);
--?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????


--excavation_result
insert into excavation_result (artifact_id, excavations, human_id, _date) VALUES (1,1,3,'2018-02-10');


--participation_expedition
insert into participation_expedition (expedition_id, human_id) values (1,4);


--subscription_expedition
insert into subscription_expedition (human_id, expedition_id) VALUES (6,1);


--donation
insert into donation (human_id,amount) values (6,200);
insert into donation (human_id,amount) values (6,400);

------------------------------------------------------------------------------------------------------------------------

--record
insert into record (type,human_id,_date) values ('purchased',1,'2018-02-12');
insert into record (type,human_id,_date) values ('sold',2,'2018-03-12');
insert into record (type,human_id,_date) values ('participation',3,'2018-04-12');
insert into record (type,human_id,_date) values ('donation',4,'2018-05-12');

--records
insert into record_purchased (record_id,price,artifact_id) values (1,2000,1);


insert into record_sold (record_id,price,artifact_id) values (2,3000,1);


insert into record_participation (record_id,expedition_id) values (3,1);


insert into record_donation (record_id,price,expedition_id) values (4,50000,1);

--???????????????????????????????????????????
------------------------------------------------------------------------------------------------------------------------

--complaint

insert into complaint (type,message_id) values ('human',3);
insert into complaint (type,message_id) values ('artifact',2);
insert into complaint (type,message_id) values ('expedition',4);

--complaints

insert into complaint_human (complaint_id, human_id) VALUES (1,6);


insert into complaint_artifact (complaint_id, artifact_id) values (2,2);


insert into complaint_expedition (complaint_id, expedition_id) VALUES (3,1);










