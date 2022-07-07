CREATE KEYSPACE result WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE KEYSPACE example WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE IF NOT EXISTS example.tbasic1 (
   	tconst text,
	titleType text,
	primaryTitle text,
	originalTitle text,
	adult boolean,
	startYear int,
   	endYear text,
	runtimeMinutes int,
	genres text,
   	PRIMARY KEY((titleType,genres),startYear,primaryTitle)
	);

CREATE TABLE IF NOT EXISTS example.trating1 (
   	tconst text,
	averageRating double,
	numVotes bigint,   				
   	PRIMARY KEY(averageRating)
	);


CREATE TABLE result.basicwithrating1 (
	titleType text,
	primaryTitle text,
	adult boolean,
	startYear int,
	genres text,
	averageRating double,
	numVotes bigint,
 	PRIMARY KEY ((titleType, startYear,averageRating),genres)
);