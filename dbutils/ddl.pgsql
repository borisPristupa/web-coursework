-- human + ОБЩЕНИЕ
CREATE TABLE IF NOT EXISTS country (
	country_id serial PRIMARY KEY,
	name text NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS chat (
	chat_id serial PRIMARY KEY,
	name text NOT NULL CHECK (name != ''),
	description text NOT NULL DEFAULT ''
);

CREATE TABLE IF NOT EXISTS human (
	human_id serial PRIMARY KEY,
	login varchar(255) NOT NULL UNIQUE CHECK (login != ''),
	password text NOT NULL CHECK (password != ''), -- md5
	email text NOT NULL UNIQUE CHECK (email ~* '^[^@]+@([^@]\.?)+[^@]$'),
	vk_id text UNIQUE,
	tg_nickname text UNIQUE,
	first_name varchar(50) NOT NULL CHECK (first_name != ''),
	second_name varchar(50) NOT NULL CHECK (second_name != ''),
	last_name varchar(50) DEFAULT '',
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

CREATE TABLE IF NOT EXISTS expedition_result (
	artifact_id int PRIMARY KEY REFERENCES artifact ON UPDATE CASCADE ON DELETE CASCADE,
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

-- изнутри бд запустить этот файл можно как \! psql -d имя_базы_данных -f путь/к/файлу/ddl.pgsql