CREATE TABLE meta 
(
	file_id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  	file_name varchar(50) NOT NULL,
  	file_size bigint NOT NULL,
  	upload_time timestamp NOT NULL,
  	upload_user varchar(30),
  	description varchar(300),
  	download_link varchar(100) NOT NULL,
  	PRIMARY KEY (file_id)
);