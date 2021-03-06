<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset=UTF-8">
        <link rel="stylesheet" href="styles/viewEmployees.css" type="text/css"/>
        <title>View Employees</title>
    </head>
    
    <body>
            
            <div id="employeeFormPanel">
                <form action="employee" method="post">
                    <input type="hidden" name="action" value="update_employee">
                    <label class="pad_top">Employee ID:</label>
                    <input type="text" name="employeeID" value="${employee.employeeID}"><br>
                    <label class="pad_top">First Name:</label>
                    <input type="text" name="firstName" value="${employee.firstName}"><br>
                    <label class="pad_top">Last Name:</label>
                    <input type="text" name="lastName" value="${employee.lastName}"><br>
                    <label class="pad_top">Auth Level:</label>
                    <input type="text" name="authLevel" value="${employee.authLevel}"><br>
                    <label class="pad_top">Pay Rate:</label>
                    <input type="text" name="payRate" value="${employee.payRate}"><br>
                    <input type="submit" value="Update Employee" action="update_employee">
                </form>

            </div>
            
            <div id="employeeTablePanel">
                <table id="employeeTable">
                    <tr>
                        <th>Employee ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Auth Level</th>
                        <th>Pay Rate</th>
                        <th colspan="2">Options</th>
                    </tr>

                    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                        <c:forEach var="employee" items="${employees}">
                        <tr>

                          <td>${employee.employeeID}</td>
                          <td>${employee.firstName}</td>
                          <td>${employee.lastName}</td>
                          <td>${employee.authLevel}</td>
                          <td>${employee.payRate}</td>
                          <td><a href="employee?action=display_employee&amp;employeeID=${employee.employeeID}">Edit</a></td>
                          <td><a href="employee?action=delete_employee&amp;employeeID=${employee.employeeID}">Delete</a></td>
                          
                          
                        </tr>
                        </c:forEach>

                </table>
                        <p>
                            <a href="employee"><button>Refresh Employees</button></a>
                            <a href="addEmployeePage.jsp"><button>Add Employee</button></a>
                        </p>
            </div>
               
    </body>
    
</html>
