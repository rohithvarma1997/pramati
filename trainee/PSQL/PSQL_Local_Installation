# PSQL Local installation: #

- sudo apt install postgresql-client-common

- sudo apt-get install postgresql-client

[Not necessary for local installation and usage(just for ref to get ip address of container)

- sudo su -  (to use docker inside without sudo keyword)[this means giving access to root su means super user][when docker conainer is initialised, it provides root access to container directly and only used for the container saperately]

- root@IMCHLT089:~# docker ps -a[to see 
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                    PORTS                    NAMES
3220c8d2e53a        postgres            "docker-entrypoint.s…"   15 minutes ago      Up 15 minutes             0.0.0.0:5432->5432/tcp   container2
1426fb52db91        postgres            "docker-entrypoint.s…"   2 hours ago         Up 2 hours                5432/tcp                 container1
6eac32c7507c        hello-world         "/hello"                 26 hours ago        Exited (0) 26 hours ago                            goofy_sammet
00e9e33c5dc4        hello-world         "/hello"                 26 hours ago        Exited (0) 26 hours ago                            compassionate_brown

docker inspect container1 (to get ip address of the docker container)

]

- krohithvarma@IMCHLT089:~$ psql -d training -h 172.17.10.109 -U python_app (to connect to remote machine)

Password for user python_app: 
psql (9.5.14)
SSL connection (protocol: TLSv1.2, cipher: ECDHE-RSA-AES256-GCM-SHA384, bits: 256, compression: off)
Type "help" for help.

training=> \d
No relations found.
training=> \dt
No relations found.
training=> \dn
  List of schemas
  Name   |  Owner   
---------+----------
 company | aneesh
 public  | postgres
(2 rows)



- sudo su -  (to use docker inside without sudo keyword)[this means giving access to root su means super user][when docker conainer is initialised, it provides root access to container directly and only used for the container saperately]


- root@IMCHLT089:~# docker inspect container1 (to get ip address of the docker container)

- root@IMCHLT089:~# psql -d resume -U rohithv -h 172.18.0.2 (to connect to remote host postgres database in docker )



