-- human + ОБЩЕНИЕ
CREATE TABLE IF NOT EXISTS country (
	country_id serial PRIMARY KEY,
	name text NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS chat (
	chat_id serial PRIMARY KEY,
	name text NOT NULL CHECK (name != ''),
	description text NOT NULL DEFAULT '',
	avatar_small bytea,
	avatar_full bytea,
	member_num int CHECK (member_num > 2)
);

CREATE TABLE IF NOT EXISTS human (
	human_id serial PRIMARY KEY,
	login varchar(255) NOT NULL UNIQUE CHECK (login != ''),
	password text NOT NULL CHECK (password != ''), -- md5
	email text NOT NULL UNIQUE CHECK (email ~* '^([a-z0-9]\.?)*[a-z0-9]+@([a-z0-9]\.?)+[a-z0-9]$'),
	vk_id text UNIQUE,
	tg_nickname text UNIQUE,
	first_name varchar(50) NOT NULL CHECK (first_name != ''),
	second_name varchar(50) NOT NULL CHECK (second_name != ''),
	last_name varchar(50) NOT NULL DEFAULT '',
	bio text NOT NULL DEFAULT '',
	likes int NOT NULL DEFAULT 0 CHECK (likes >= 0),
	dislikes int NOT NULL DEFAULT 0 CHECK (dislikes >= 0),
	avatar_small bytea,
	avatar_full bytea,
	banned boolean NOT NULL DEFAULT FALSE,
	country_id int REFERENCES country ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(human_id, tg_nickname)
);

CREATE TABLE IF NOT EXISTS human_chat (
	chat_id int REFERENCES chat ON UPDATE CASCADE ON DELETE CASCADE,
	human_id int REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY (chat_id, human_id)
);

CREATE TABLE IF NOT EXISTS message (
	message_id serial PRIMARY KEY,
	chat_id int NOT NULL REFERENCES chat ON UPDATE CASCADE ON DELETE CASCADE,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	body text NOT NULL CHECK (trim(body) != ''),
	_date timestamp NOT NULL,
	UNIQUE(chat_id, message_id)
);

-- РОЛИ humanОВ
CREATE TABLE IF NOT EXISTS archaeologist (
	human_id int PRIMARY KEY REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS collector (
	human_id int PRIMARY KEY REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS moderator (
	human_id int PRIMARY KEY REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	tg_nickname text NOT NULL UNIQUE CHECK (tg_nickname != '')
);

CREATE TABLE IF NOT EXISTS researcher (
	human_id int PRIMARY KEY REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sponsor (
	human_id int PRIMARY KEY REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE
);

-- artifactЫ + auctionЫ
CREATE TABLE IF NOT EXISTS age (
	age_id serial PRIMARY KEY,
	description text NOT NULL UNIQUE CHECK ('' != description)
);

CREATE TABLE IF NOT EXISTS category (
	category_id serial PRIMARY KEY,
	name text NOT NULL UNIQUE CHECK ('' != name)
);

CREATE TABLE IF NOT EXISTS artifact (
	artifact_id serial PRIMARY KEY,
	name text NOT NULL CHECK ('' != name),
	approved boolean NOT NULL DEFAULT FALSE,
	description text NOT NULL DEFAULT '',
	age_id int REFERENCES age ON UPDATE CASCADE ON DELETE CASCADE,
	avatar_small bytea,
	avatar_full bytea,
	owner int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	approver int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	origin int REFERENCES country ON UPDATE CASCADE ON DELETE CASCADE,
	country_id int REFERENCES country ON UPDATE CASCADE ON DELETE CASCADE,
	category_id int REFERENCES category ON UPDATE CASCADE ON DELETE CASCADE,
	banned boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS auction (
	auction_id serial PRIMARY KEY,
	artifact_id int NOT NULL REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE,
	price_old numeric,
	price_new numeric NOT NULL CHECK (price_old < price_new),
	raiser int REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	bet_time timestamp,
	start_time timestamp NOT NULL,
	end_time timestamp NOT NULL,
	CONSTRAINT хронология_auctionа CHECK (start_time < end_time AND bet_time BETWEEN start_time AND end_time)
);

CREATE TABLE IF NOT EXISTS subscription_auction (
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	auction_id int NOT NULL REFERENCES auction ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(human_id, auction_id)
);


-- ЭКСПЕДИЦИИ
CREATE TABLE IF NOT EXISTS expedition_stage (
	stage_id serial PRIMARY KEY,
	name text NOT NULL UNIQUE CHECK ('' != name)
);

CREATE TABLE IF NOT EXISTS route (
	route_id serial PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS stay (
	stay_id serial PRIMARY KEY,
	route_id int NOT NULL REFERENCES route ON UPDATE CASCADE ON DELETE CASCADE,
	excavations boolean NOT NULL DEFAULT FALSE,
	start_date date NOT NULL,
	end_date date NOT NULL CHECK (end_date > start_date),
	latitude float NOT NULL CHECK (latitude BETWEEN -90 AND 90), -- широта
	longtitude float NOT NULL CHECK (longtitude BETWEEN -180 AND 180) -- долгота
);

CREATE TABLE IF NOT EXISTS expedition (
	expedition_id serial PRIMARY KEY,
	name text NOT NULL CHECK ('' != name),
	description text NOT NULL DEFAULT '',
	avatar_small bytea,
	avatar_full bytea,
	costs numeric NOT NULL,
	current_sum numeric NOT NULL DEFAULT 0,
	stage_id int NOT NULL REFERENCES expedition_stage ON UPDATE CASCADE ON DELETE CASCADE,
	banned boolean NOT NULL DEFAULT FALSE,
	route_plan int REFERENCES route ON UPDATE CASCADE ON DELETE CASCADE,
	route_current int REFERENCES route ON UPDATE CASCADE ON DELETE CASCADE,
	head int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS excavation_result (
	artifact_id int PRIMARY KEY REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE,
	excavations int NOT NULL REFERENCES stay ON UPDATE CASCADE ON DELETE CASCADE,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	_date date
);

CREATE TABLE IF NOT EXISTS participation_expedition (
	participation_expedition_id serial PRIMARY KEY,
	expedition_id int NOT NULL REFERENCES expedition ON UPDATE CASCADE ON DELETE CASCADE,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	_date date
);

CREATE TABLE IF NOT EXISTS subscription_expedition (
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	expedition_id int NOT NULL REFERENCES expedition ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(human_id, expedition_id)
);

CREATE TABLE IF NOT EXISTS donation (
	donation_id serial PRIMARY KEY,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	expedition_id int NOT NULL REFERENCES expedition ON UPDATE CASCADE ON DELETE CASCADE,
	time timestamp NOT NULL,
	amount numeric NOT NULL CHECK (amount > 0::numeric)
);


-- ЗАПИСИ В ИСТОРИИ humanА В ЛИЧНОМ КАБИНЕТЕ
CREATE TYPE record_type AS ENUM ('purchased', 'sold', 'participation', 'donation');

CREATE TABLE IF NOT EXISTS record (
	record_id serial PRIMARY KEY,
	type record_type NOT NULL,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	time timestamp NOT NULL
);

CREATE OR REPLACE FUNCTION consistent_record_type(record_type, int)
RETURNS boolean AS $$ SELECT type = $1 FROM record WHERE record_id = $2 $$ LANGUAGE SQL;

CREATE TABLE IF NOT EXISTS record_purchased (
	record_id serial PRIMARY KEY REFERENCES record ON UPDATE CASCADE ON DELETE CASCADE,
	price numeric NOT NULL CHECK (price >= 0::numeric),
	artifact_id int NOT NULL REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT record_type_purchased CHECK (consistent_record_type('purchased', record_id))
);

CREATE TABLE IF NOT EXISTS record_sold (
	record_id serial PRIMARY KEY REFERENCES record ON UPDATE CASCADE ON DELETE CASCADE,
	price numeric NOT NULL CHECK (price >= 0::numeric),
	artifact_id int NOT NULL REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT record_type_sold CHECK (consistent_record_type('sold', record_id))
);

CREATE TABLE IF NOT EXISTS record_participation (
	record_id serial PRIMARY KEY REFERENCES record ON UPDATE CASCADE ON DELETE CASCADE,
	participation_expedition_id int NOT NULL REFERENCES participation_expedition ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT record_type_participation CHECK (consistent_record_type('participation', record_id))
);

CREATE TABLE IF NOT EXISTS record_donation (
	record_id serial PRIMARY KEY REFERENCES record ON UPDATE CASCADE ON DELETE CASCADE,
	donation_id int NOT NULL REFERENCES donation ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT record_type_donation CHECK (consistent_record_type('donation', record_id))
);


-- ЖАЛОБЫ
CREATE TYPE complaint_type AS ENUM ('human', 'artifact', 'expedition');

CREATE TABLE IF NOT EXISTS complaint (
	complaint_id serial PRIMARY KEY,
	type complaint_type NOT NULL,
	message_id int NOT NULL REFERENCES message ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION consistent_complaint_type(complaint_type, int)
RETURNS boolean AS $$ SELECT type = $1 FROM complaint WHERE complaint_id = $2 $$ LANGUAGE SQL;

CREATE TABLE IF NOT EXISTS complaint_human (
	complaint_id int PRIMARY KEY REFERENCES complaint ON UPDATE CASCADE ON DELETE CASCADE,
	human_id int NOT NULL REFERENCES human ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT complaint_type_human CHECK (consistent_complaint_type('human', complaint_id))
);

CREATE TABLE IF NOT EXISTS complaint_artifact (
	complaint_id int PRIMARY KEY REFERENCES complaint ON UPDATE CASCADE ON DELETE CASCADE,
	artifact_id int NOT NULL REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE
	CONSTRAINT complaint_type_human CHECK (consistent_complaint_type('artifact', complaint_id))
);

CREATE TABLE IF NOT EXISTS complaint_expedition (
	complaint_id int PRIMARY KEY REFERENCES complaint ON UPDATE CASCADE ON DELETE CASCADE,
	expedition_id int NOT NULL REFERENCES expedition ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT complaint_type_human CHECK (consistent_complaint_type('expedition', complaint_id))
);

-- изнутри бд запустить этот файл можно как \! psql -d имя_базы_данных -f путь/к/файлу/ddl.pgsql