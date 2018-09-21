# PSQL Local installation: #

- sudo apt install postgresql-client-common

- sudo apt-get install postgresql-client

- krohithvarma@IMCHLT089:~$ psql -d training -h 172.17.10.109 -U python_app (to connect to remote machine)

Password for user python_app: 
psql (9.5.14)
SSL connection (protocol: TLSv1.2, cipher: ECDHE-RSA-AES256-GCM-SHA384, bits: 256, compression: off)
Type "help" for help.
```
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
```


- sudo su -  (to use docker inside without sudo keyword)[this means giving access to root su means super user]

- root@IMCHLT089:~# docker inspect container1 (to get ip address of the docker container)

- root@IMCHLT089:~# psql -d resume -U rohithv -h 172.18.0.2 (to connect to remote host postgres database in docker )



