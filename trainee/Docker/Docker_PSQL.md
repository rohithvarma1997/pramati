# DOCKER: #

**What is Docker:**

Docker is a computer program that performs operating-system-level
virtualization, also known as "containerization". Docker is used to run
software packages called "containers".

Containers are basically run much faster than virtual machines as there
uill be no unnecessary overheads for the containers to process like
graphic cards etc.The image of the docker can be deployed and
implemented easily and managed efficiently.

# How to install docker: #

*uninstall if old version of docker is installed using this command:*

- **sudo apt-get remove docker docker-engine docker.io**

**step1:**

*Install docker using the following command-*

- **\$ sudo apt-get -y install docker-ce**

**Step2:**

*Start the docker service in the installed machine*

- **\$ sudo systemctl start docker.service**

- **\$ sudo systemctl enable docker.service**

**Step3:**

*Check the status of installation by*

- **\$ sudo docker run hello-world**

- **\$ sudo docker --version**

# NOTE: #

by default docker requires root privilege to run docker commands like
using sudo before every commands.

**To give root access to non-root users to run docker:**

1\. sudo groupadd docker

2\. sudo useradd \$USER

3\. sudo usermod -aG docker \$USER

Now we are able to run docker command without prefix sudo.

**docker commands:**

sudo docker -v : gives docker version

sudo docker images -a : list all images with tag name.

sudo docker ps : list all containers in machine.

docker inspect container1 (to get ip address of the docker container)


**To stop and remove docker container:**

- sudo docker stop container1

- sudo docker rm container1

# Installing Postgresql in docker: #

**step1:**

- \$ pull postgres image from docker(hub.docker.com).

- \$ sudo docker pull postgres.

**Step2:**

*now we can see what are all the images present in our system by using*

- sudo docker images

*we able to see postgres image ,to use postgres create postgres instance
by*

- sudo docker run --name container1 -d postgres.

**Step3:**

- \$ sudo docker ps : list the containers list and we can able to see our
postgres\_container1 .

**Step4:**

- \$ sudo docker exec -it container1 bash

- psql -U postgres

**psql:**

- postgres=# CREATE USER rohithv SUPERUSER PASSWORD 'varma'; 
CREATE ROLE

- postgres=# CREATE DATABASE resume;
CREATE DATABASE

- postgres=# GRANT ALL PRIVILEGES ON DATABASE resume to rohithv;
GRANT

- postgres=# \c resume
You are now connected to database "resume" as user "postgres".

- resume=# create table resume_collect( id SERIAL primary key , filename varchar not null , email varchar not null ,phone varchar not null);CREATE TABLE

- resume=# GRANT ALL PRIVILEGES ON TABLE resume_collect to rohithv;
GRANT

- resume=# GRANT ALL PRIVILEGES ON SEQUENCE resume_collect_id_seq to rohithv;

GRANT


- resume=# insert into resume_collect values (1,'rohithvarma','krohithvarma1997@gmail.com','8328124562');

INSERT 0 1

- resume=# select * from resume_collect;

 id |  filename   |           email            |   phone    
 
----+-------------+----------------------------+------------

  1 | rohithvarma | krohithvarma1997@gmail.com | 8328124562
  
(1 row)

