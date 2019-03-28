-- ЮЗЕР + ОБЩЕНИЕ
CREATE TABLE IF NOT EXISTS страна (
	страна_ид serial PRIMARY KEY,
	название text NOT NULL UNIQUE CHECK (название != '')
);

CREATE TABLE IF NOT EXISTS чат (
	чат_ид serial PRIMARY KEY,
	название text NOT NULL CHECK (название != ''),
	описание text NOT NULL DEFAULT '',
	аватар_мал bytea,
	аватар_полн bytea,
	кол_участников int CHECK (кол_участников > 2)
);

CREATE TABLE IF NOT EXISTS юзер (
	юзер_ид serial PRIMARY KEY,
	логин varchar(255) NOT NULL UNIQUE CHECK (логин != ''),
	пароль text NOT NULL CHECK (пароль != ''), -- md5
	почта text NOT NULL UNIQUE CHECK (почта ~* '^([a-z0-9]\.?)*[a-z0-9]+@([a-z0-9]\.?)+[a-z0-9]$'),
	вк_ид text UNIQUE,
	телеграм_имя text UNIQUE,
	имя varchar(50) NOT NULL CHECK (имя != ''),
	фамилия varchar(50) NOT NULL CHECK (фамилия != ''),
	отчество varchar(50) NOT NULL DEFAULT '',
	биография text NOT NULL DEFAULT '',
	лайки int NOT NULL DEFAULT 0 CHECK (лайки >= 0),
	дизлайки int NOT NULL DEFAULT 0 CHECK (дизлайки >= 0),
	аватар_мал bytea,
	аватар_полн bytea,
	забанен boolean NOT NULL DEFAULT FALSE,
	страна_ид int REFERENCES страна ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(юзер_ид, телеграм_имя)
);

CREATE TABLE IF NOT EXISTS юзер_чат (
	чат_ид int REFERENCES чат ON UPDATE CASCADE ON DELETE CASCADE,
	юзер_ид int REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY (чат_ид, юзер_ид)
);

CREATE TABLE IF NOT EXISTS сообщение (
	сообщ_ид serial PRIMARY KEY,
	чат_ид int NOT NULL REFERENCES чат ON UPDATE CASCADE ON DELETE CASCADE,
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	текст text NOT NULL CHECK (trim(текст) != ''),
	дата timestamp NOT NULL,
	UNIQUE(чат_ид, сообщ_ид)
);

CREATE TABLE IF NOT EXISTS знакомые (
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	чат_ид int NOT NULL,
	посл_сообщ_ид int NOT NULL,
	FOREIGN KEY (чат_ид, посл_сообщ_ид) REFERENCES сообщение (чат_ид, сообщ_ид) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY (юзер_ид, чат_ид)
);

-- РОЛИ ЮЗЕРОВ
CREATE TABLE IF NOT EXISTS археолог (
	юзер_ид int PRIMARY KEY REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS коллекционер (
	юзер_ид int PRIMARY KEY REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS модератор (
	юзер_ид int PRIMARY KEY REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	телеграм_имя text NOT NULL UNIQUE CHECK (телеграм_имя != ''),
	FOREIGN KEY (юзер_ид, телеграм_имя) REFERENCES юзер (юзер_ид, телеграм_имя) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS исследователь (
	юзер_ид int PRIMARY KEY REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS спонсор (
	юзер_ид int PRIMARY KEY REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE
);

-- АРТЕФАКТЫ + АУКЦИОНЫ
CREATE TABLE IF NOT EXISTS возраст (
	возраст_ид serial PRIMARY KEY,
	описание text NOT NULL UNIQUE CHECK ('' != описание)
);

CREATE TABLE IF NOT EXISTS категория (
	категория_ид serial PRIMARY KEY,
	название text NOT NULL UNIQUE CHECK ('' != название)
);

CREATE TABLE IF NOT EXISTS артефакт (
	арт_ид serial PRIMARY KEY,
	подтверждён boolean NOT NULL DEFAULT FALSE,
	описание text NOT NULL DEFAULT '',
	возраст_ид int REFERENCES возраст ON UPDATE CASCADE ON DELETE CASCADE,
	аватар_мал bytea,
	аватар_полн bytea,
	владелец int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	подтвердивший int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	родина int REFERENCES страна ON UPDATE CASCADE ON DELETE CASCADE,
	страна_ид int REFERENCES страна ON UPDATE CASCADE ON DELETE CASCADE,
	категория_ид int REFERENCES категория ON UPDATE CASCADE ON DELETE CASCADE,
	забанен boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS аукцион (
	аукцион_ид serial PRIMARY KEY,
	арт_ид int NOT NULL REFERENCES артефакт ON UPDATE CASCADE ON DELETE CASCADE,
	цена_старая money,
	цена_новая money NOT NULL CHECK (цена_старая < цена_новая),
	поднявший int REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	время_ставки timestamp,
	время_начала timestamp NOT NULL,
	время_конца timestamp NOT NULL,
	CONSTRAINT хронология_аукциона CHECK (время_начала < время_конца AND время_ставки BETWEEN время_начала AND время_конца)
);

CREATE TABLE IF NOT EXISTS подписка_аукцион (
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	аукцион_ид int NOT NULL REFERENCES аукцион ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(юзер_ид, аукцион_ид)
);


-- ЭКСПЕДИЦИИ
CREATE TABLE IF NOT EXISTS этап_экспедиции (
	этап_ид serial PRIMARY KEY,
	название text NOT NULL UNIQUE CHECK ('' != название)
);

CREATE TABLE IF NOT EXISTS маршрут (
	маршрут_ид serial PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS остановка (
	остановка_ид serial PRIMARY KEY,
	маршрут_ид int NOT NULL REFERENCES маршрут ON UPDATE CASCADE ON DELETE CASCADE,
	раскопки boolean NOT NULL DEFAULT FALSE,
	начало date NOT NULL,
	конец date NOT NULL CHECK (конец > начало),
	широта float NOT NULL CHECK (широта BETWEEN -90 AND 90),
	долгота float NOT NULL CHECK (долгота BETWEEN -180 AND 180)
);

CREATE TABLE IF NOT EXISTS экспедиция (
	экспед_ид serial PRIMARY KEY,
	название text NOT NULL CHECK ('' != название),
	описание text NOT NULL DEFAULT '',
	аватар_мал bytea,
	аватар_полн bytea,
	стоимость money NOT NULL,
	текущая_сумма money NOT NULL DEFAULT 0,
	этап_ид int NOT NULL REFERENCES этап_экспедиции ON UPDATE CASCADE ON DELETE CASCADE,
	забанена boolean NOT NULL DEFAULT FALSE,
	маршрут_план int REFERENCES маршрут ON UPDATE CASCADE ON DELETE CASCADE,
	маршрут_текущий int REFERENCES маршрут ON UPDATE CASCADE ON DELETE CASCADE,
	глава int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS результат_раскопок (
	арт_ид int PRIMARY KEY REFERENCES артефакт ON UPDATE CASCADE ON DELETE CASCADE,
	раскопки int NOT NULL REFERENCES остановка ON UPDATE CASCADE ON DELETE CASCADE,
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	дата date
);

CREATE TABLE IF NOT EXISTS участие_экспедиция (
	экспед_ид int NOT NULL REFERENCES экспедиция ON UPDATE CASCADE ON DELETE CASCADE,
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(экспед_ид, юзер_ид)
);

CREATE TABLE IF NOT EXISTS подписка_экспедиция (
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	экспед_ид int NOT NULL REFERENCES экспедиция ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(юзер_ид, экспед_ид)
);

CREATE TABLE IF NOT EXISTS донат (
	донат_ид serial PRIMARY KEY,
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	сумма money NOT NULL CHECK (сумма > 0::money)
);


-- ЗАПИСИ В ИСТОРИИ ЮЗЕРА В ЛИЧНОМ КАБИНЕТЕ
CREATE TYPE тип_записи AS ENUM ('куплено', 'продано', 'участие', 'донат');

CREATE TABLE IF NOT EXISTS запись (
	запись_ид serial PRIMARY KEY,
	тип тип_записи NOT NULL,
	юзер_ид int NOT NULL REFERENCES юзер ON UPDATE CASCADE ON DELETE CASCADE,
	дата timestamp NOT NULL
);

CREATE OR REPLACE FUNCTION соблюдён_тип_записи(тип_записи, int)
RETURNS boolean AS $$ SELECT тип = $1 FROM запись WHERE запись_ид = $2 $$ LANGUAGE SQL;

CREATE TABLE IF NOT EXISTS запись_куплено (
	запись_ид serial PRIMARY KEY REFERENCES запись ON UPDATE CASCADE ON DELETE CASCADE,
	цена money NOT NULL CHECK (цена >= 0::money),
	арт_ид int NOT NULL REFERENCES артефакт ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_записи_куплено CHECK (соблюдён_тип_записи('куплено', запись_ид))
);

CREATE TABLE IF NOT EXISTS запись_продано (
	запись_ид serial PRIMARY KEY REFERENCES запись ON UPDATE CASCADE ON DELETE CASCADE,
	цена money NOT NULL CHECK (цена >= 0::money),
	арт_ид int NOT NULL REFERENCES артефакт ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_записи_продано CHECK (соблюдён_тип_записи('продано', запись_ид))
);

CREATE TABLE IF NOT EXISTS запись_участие (
	запись_ид serial PRIMARY KEY REFERENCES запись ON UPDATE CASCADE ON DELETE CASCADE,
	экспед_ид int NOT NULL REFERENCES экспедиция ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_записи_участие CHECK (соблюдён_тип_записи('участие', запись_ид))
);

CREATE TABLE IF NOT EXISTS запись_донат (
	запись_ид serial PRIMARY KEY REFERENCES запись ON UPDATE CASCADE ON DELETE CASCADE,
	цена money NOT NULL CHECK (цена >= 0::money),
	экспед_ид int NOT NULL REFERENCES экспедиция ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_записи_донат CHECK (соблюдён_тип_записи('донат', запись_ид))
);


-- ЖАЛОБЫ
CREATE TYPE тип_жалобы AS ENUM ('юзер', 'артефакт', 'экспедиция');

CREATE TABLE IF NOT EXISTS жалоба (
	жалоба_ид serial PRIMARY KEY,
	тип тип_жалобы NOT NULL,
	сообщ_ид int NOT NULL REFERENCES сообщение ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION соблюдён_тип_жалобы(тип_жалобы, int)
RETURNS boolean AS $$ SELECT тип = $1 FROM жалоба WHERE жалоба_ид = $2 $$ LANGUAGE SQL;

CREATE TABLE IF NOT EXISTS жалоба_юзер (
	жалоба_ид int PRIMARY KEY REFERENCES жалоба ON UPDATE CASCADE ON DELETE CASCADE,
	юзер_ид int NOT NULL REFERENCES жалоба ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_жалобы_юзер CHECK (соблюдён_тип_жалобы('юзер', жалоба_ид))
);

CREATE TABLE IF NOT EXISTS жалоба_артефакт (
	жалоба_ид int PRIMARY KEY REFERENCES жалоба ON UPDATE CASCADE ON DELETE CASCADE,
	арт_ид int NOT NULL REFERENCES артефакт ON UPDATE CASCADE ON DELETE CASCADE
	CONSTRAINT тип_жалобы_юзер CHECK (соблюдён_тип_жалобы('артефакт', жалоба_ид))
);

CREATE TABLE IF NOT EXISTS жалоба_экспедиция (
	жалоба_ид int PRIMARY KEY REFERENCES жалоба ON UPDATE CASCADE ON DELETE CASCADE,
	экспед_ид int NOT NULL REFERENCES экспедиция ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT тип_жалобы_юзер CHECK (соблюдён_тип_жалобы('экспедиция', жалоба_ид))
);

-- изнутри бд запустить этот файл можно как \! psql -d имя_базы_данных -f путь/к/файлу/ddl.sql
