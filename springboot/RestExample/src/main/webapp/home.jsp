<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="addSuperhero">
	<h1>Add hero</h1>
	<input type="text" name="id"><br>
	<input type="text" name="name"><br>
	<input type="text" name="power"><br>
	<input type="submit"><br><br>
</form>

<form action="getSuperhero">
	<h1>fetch hero</h1>
	<input type="text" name="id"><br>
	<input type="submit">
</form>


</body>
</html>