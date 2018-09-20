# PSQL Queries: #

13\.
MISSING ID's:

- SELECT generate_series(
(SELECT MIN(dept_id) FROM dept),
(SELECT MAX(dept_id) FROM dept)
) AS MissingID
except SELECT dept_id FROM dept;

14\.

Manager Name, Reportee who joined first (Reportee Name - doj), Reportee who draws less sal (Reportee Name - salary)

- SELECT a.name,a.doj AS "Reportee Name - doj",b.sal AS "Reportee Name - salary"
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


