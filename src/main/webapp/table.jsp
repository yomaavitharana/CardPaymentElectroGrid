
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
#customers {
	font-family: Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#customers td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#customers tr:nth-child(even) {
	background-color: #f2f2f2;
}

#customers tr:hover {
	background-color: #ddd;
}

#customers th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	background-color: #23527c;
	color: white;
}

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #04AA6D;
}

li {
	float: left;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a:hover:not(.active) {
	background-color: #111;
}

.active {
	background-color: #04AA6D;
}

.alert {
	padding: 20px;
	background-color: #31b0d5;
	color: white;
}

.alert1 {
	padding: 20px;
	background-color: red;
	color: white;
}

.closebtn {
	margin-left: 15px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 22px;
	line-height: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}
</style>
</head>
<body>
	<div>
		<ul>
			<li><a href="index.jsp">Add Card</a></li>
			<li><a href="CardAPI">View Card Details</a></li>
		</ul>
	</div>

	<%
	String message = (String) request.getAttribute("alertMsg");
	if (message != null) {
		if (message == "Card Details Updated Successfully!") {
	%>
	<div class="alert">
		<span class="closebtn"
			onclick="this.parentElement.style.display = 'none';">&times;</span> <strong>Success!</strong>
		<%=message%>
	</div>
	<%
	} else {
	%>
	<div class="alert1">
		<span class="closebtn"
			onclick="this.parentElement.style.display = 'none';">&times;</span> <strong>Failed!</strong>
		<%=message%>
	</div>

	<%
	}
	}
	%>
	<%
	String msg = (String) request.getAttribute("alert");
	if (msg != null) {
		if (msg == "Card Details Deleted Successfully!") {
	%>
	<div class="alert">
		<span class="closebtn"
			onclick="this.parentElement.style.display = 'none';">&times;</span> <strong>Success!</strong>
		<%=msg%>
	</div>
	<%
	} else {
	%>
	<div class="alert1">
		<span class="closebtn"
			onclick="this.parentElement.style.display = 'none';">&times;</span> <strong>Failed!</strong>
		<%=msg%>
	</div>

	<%
	}
	}
	%>
	<div>
		<table id="customers">
			<tr>
				<th>ID</th>
				<th>Card Number</th>
				<th>Name on Card</th>
				<th>Expiry Date</th>
				<th>Security Code</th>
				<th>Update Action</th>
				<th>Delete Action</th>
			</tr>

			<%
			ResultSet resultSet = null;
			try {
				resultSet = (ResultSet) request.getAttribute("cardResult");
				while (resultSet.next()) {
			%>
			<tr>
				<td><input id="id" name="id" type="hidden"
					value="<%=resultSet.getString("id")%>" readonly><%=resultSet.getString("id")%></td>
				<td><input name="card" type="number"
					value="<%=resultSet.getString("card")%>"
					onKeyDown="if (this.value.length == 16 && event.keyCode != 8)
                                return false;"></td>
				<td><input name="name" type="text"
					value="<%=resultSet.getString("name")%>"></td>
				<td><input name="date" type="text"
					value="<%=resultSet.getString("date")%>"
					pattern="(?:0[1-9]|1[0-2])/[0-9]{2}"></td>
				<td><input name="cvc" type="text"
					value="<%=resultSet.getString("cvc")%>"
					onKeyDown="if (this.value.length == 3 && event.keyCode != 8)
                                return false;"
					)></td>
				<td><input class="btn-link btnUpdate" type="submit"
					value="Update"></td>
				<td><input id="<%=resultSet.getString("id")%>"
					class="btn-warning btnRemove" type="submit" name="delete_card"
					value="Delete""></td>

			</tr>

			<%
			}

			} catch (Exception e) {
			e.printStackTrace();
			}
			%>
		</table>
	</div>
</body>
<script>
	function check() {
		var result = confirm("Are you sure to Update details?");
		if (result == false) {
			event.preventdefault();
		}
	}

	$(document).ready(function() {
		$(document).on("click", ".btnRemove", function(event) {
			var id = +this.id;
		
			$.ajax({
				url : "/app/CardAPI",
				type : "DELETE",
				data : "cardId=" + id,
				dataType : "text",

				complete : function(response, status) {
					console.log(response)
				}
			});

		});

		$(document).on("click", ".btnUpdate", function(event) {
			var id = +this.id;
			$.ajax({
				url : "/app/CardAPI",
				type : "put",
				data : "cardId=" + id + "&card=",
				dataType : "text",

				complete : function(response, status) {
					console.log(response)
				}
			});

		});
	});
</script>
</html>
