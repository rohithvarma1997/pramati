# PSQL Local installation: #

- sudo apt install postgresql-client-common

- sudo apt-get install postgresql-client

# psql connecting to remote machine #
```
krohithvarma@IMCHLT089:~$ psql -d training -h 172.17.10.109 -U python_app
Password for user python_app: 
psql (9.5.14)
SSL connection (protocol: TLSv1.2, cipher: ECDHE-RSA-AES256-GCM-SHA384, bits: 256, compression: off)
Type "help" for help.
```
- training=> select * from employee;

# PSQL QUERIES SET 1: #

1\. Employee_Name and Manager_Name
```
SELECT e.name as "Employee name",m.name as "Manager name" 
FROM employee e ,employee m 
WHERE e.mgr_id=m.emp_id;
```
2\. Employee name, emp doj, manager name, manger doj
```
SELECT e.name as "Employee name",e.joining_date as "Employee joining date",m.name as "Manager name",m.joining_date as "Manager joining date" 
FROM employee e ,employee 
WHERE e.mgr_id=m.emp_id;
```
3\. Employee name, Emp Dept, Mgr Dept
```
SELECT a.name Emp_Name,ad.name Employee_dept ,bd.name Manager_dept 
FROM employee a, employee b ,dept ad,dept bd 
WHERE b.emp_id = a.mgr_id
AND a.dept_id=ad.dept_id and b.dept_id=bd.dept_id;
```
4\. List of employees without a manager
```
SELECT name from employee WHERE mgr_id IS NULL;
```
5\. List of terminated manager names
```
SELECT DISTINCT b.name AS manager_names 
FROM employee a,employee b 
WHERE a.mgr_id=b.emp_id
AND b.termination_date<now()::date;
```
6\. List of department names where we have a terminated employee
```
SELECT distinct ad.name Dept_name 
FROM employee a,employee b,dept ad,dept bd 
WHERE a.mgr_id=b.emp_id 
AND b.termination_date<now()::date 
AND ad.dept_id=a.dept_id 
AND bd.dept_id=b.dept_id;
```
7\. List of department names where we have a terminated Manager
```
SELECT DISTINCT bd.name Dept_name 
FROM employee a,employee b,dept ad,dept bd 
WHERE a.mgr_id=b.emp_id 
AND b.termination_date<now()::date 
AND ad.dept_id=a.dept_id 
AND bd.dept_id=b.dept_id;
```
8\. List of employees whose manager's salary is less then employee salary
```
SELECT a.name AS "Emp_Name" 
FROM employee a, employee b 
WHERE b.emp_id = a.mgr_id 
AND b.salary<a.salary;
```
9\. List of employees whose doj is earlier than manager
```
SELECT a.name AS "Emp_Name" 
FROM employee a, employee b 
WHERE a.mgr_id=b.emp_id 
AND a.joining_date<b.joining_date;
```
10\. List of employees whose name has a vowel
```
SELECT name FROM employee 
WHERE name 
LIKE '%A%' OR name LIKE '%E%' OR name LIKE '%I%' OR name LIKE '%O%' OR name LIKE '%U%'OR name LIKE '%a%' OR name LIKE '%e%' OR name LIKE '%i%' OR name LIKE '%o%' OR name LIKE '%u%';
```
11\. List of employees whose's manager name has a vowel and employee salary is less than 20000
```
SELECT b.name AS "Emp_Name" 
FROM employee a, employee b 
WHERE a.emp_id = b.mgr_id 
AND b.salary < 20000 
AND (a.name LIKE '%A%' OR a.name LIKE '%E%' OR a.name LIKE '%I%' OR a.name LIKE '%O%' OR a.name LIKE '%U%' OR a.name LIKE '%a%' OR a.name LIKE '%e%' OR a.name LIKE '%i%' OR a.name LIKE '%o%' OR a.name LIKE '%u%');
```
12\. List of employees who has joined in Jan/Feb and Nov
```
SELECT name 
FROM employee 
WHERE to_char(joining_date, 'mm') = '02' 
OR to_char(joining_date, 'mm') = '01' 
OR to_char(joining_date, 'mm') = '11';
```
