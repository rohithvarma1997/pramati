# PSQL Queries: #

13\.

MISSING DEPT ID's:
```
SELECT generate_series(
(SELECT MIN(dept_id) FROM dept),
(SELECT MAX(dept_id) FROM dept)
) AS MissingID
EXCEPT SELECT dept_id FROM dept;
```
Without generate series function:

```

WITH RECURSIVE series AS (
	SELECT
		1 AS rn
	UNION ALL
		SELECT
			rn + 1 AS rn
		FROM
			series
		WHERE
			rn < 10
) SELECT
	*
FROM
	series
```
```
DO $$
BEGIN
   FOR i IN (SELECT MIN(dept_id) FROM dept)..(SELECT MAX(dept_id) FROM dept) LOOP
 insert into miss values (i);
   END LOOP;
END; $$ LANGUAGE plpgsql;
```
14\.

Manager Name, Reportee who joined first (Reportee Name - doj), Reportee who draws less sal (Reportee Name - salary)
```
SELECT a.name,a.doj AS "Reportee Name - doj",b.sal AS "Reportee Name - salary"
FROM
(SELECT b.name,CONCAT(a.name,'-',a.joining_date) AS doj 
FROM employee a,employee b
WHERE b.emp_id=a.mgr_id 
AND EXISTS(
SELECT 1
FROM employee a_inr
WHERE a_inr.mgr_id=b.emp_id
HAVING min(a_inr.joining_date)=a.joining_date
) 
) a
INNER JOIN (
SELECT b.name,CONCAT(a.name,'-',a.salary) AS sal	
FROM employee a,employee b
WHERE b.emp_id=a.mgr_id
AND EXISTS(
SELECT 1
FROM employee a_inr
WHERE a_inr.mgr_id=b.emp_id
HAVING min(a_inr.salary)=a.salary
)
)b
ON a.name=b.name;
```
```
SELECT DISTINCT
	M . NAME AS mgr_name,
	FIRST_VALUE (e. NAME) OVER (
		PARTITION BY M .emp_id
		ORDER BY
			e.joining_date
	) AS first_emp_name,
	FIRST_VALUE (e.joining_date) OVER (
		PARTITION BY M .emp_id
		ORDER BY
			e.joining_date
	) AS first_doj,
	FIRST_VALUE (e. NAME) OVER (
		PARTITION BY M .emp_id
		ORDER BY
			e.salary
	) AS least_sal_emp_name,
	FIRST_VALUE (e.salary) OVER (
		PARTITION BY M .emp_id
		ORDER BY
			e.salary
	) AS least_salary
FROM
	employee e
INNER JOIN employee M ON e.mgr_id = M .emp_id

```      
16\.

Find the list of employee records where salary data is missing

```
SELECT CONCAT(a.i,'-',b.i) AS Missing_Data
FROM
(
SELECT i 
FROM generate_series(
(SELECT MIN(start_date) 
FROM sh),
(SELECT MAX(start_date) 
FROM sh)) AS t(i)
WHERE NOT EXISTS(
SELECT 1 
FROM sh
WHERE sh.start_date=t.i)
) a

INNER JOIN (
SELECT i 
FROM generate_series(
(SELECT MIN(end_date) 
FROM sh),
(SELECT MAX(end_date) 
FROM sh)) AS t(i)
WHERE NOT EXISTS(
SELECT 1 
FROM sh
WHERE sh.end_date=t.i)
)b

ON b.i-a.i>1;
```
```
SELECT * FROM (
SELECT
	sh.start_date,
	sh.end_date,
	LEAD (start_date, 1) OVER (ORDER BY start_date) AS next_start_date
FROM
	sh
ORDER BY
	start_date)t where next_start_date-end_date > 0
```
