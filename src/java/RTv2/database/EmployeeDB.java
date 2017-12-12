package RTv2.database;

import RTv2.objects.TimeClock;
import RTv2.objects.Employee;
import RTv2.utilities.ConnectionPool;
import RTv2.utilities.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EmployeeDB {

    public static int insert(Employee employee){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO cs_employees (employeeID, firstName, lastName,"
                + " authLevel, status, payRate, password) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, employee.getEmployeeID());
            ps.setString(2, employee.getFirstName());
            ps.setString(3, employee.getLastName());
            ps.setInt(3, employee.getAuthLevel());
            ps.setBoolean(4, employee.getStatus());
            ps.setDouble(6, employee.getPayRate());
            ps.setString(6, employee.getPassword());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int update(Employee employee){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE cs_employees SET "
                + "FirstName = ?, LastName = ?, "
                + "PayRate =?, AuthLevel = ?, Status = ?, Password = ? "
                + "WHERE employeeID = ?";
        try {
           ps = connection.prepareStatement(query);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setDouble(3, employee.getPayRate());
            ps.setInt(4, employee.getAuthLevel());
            ps.setBoolean(5, employee.getStatus());
            ps.setInt(6, employee.getEmployeeID());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    // EmployeeServlet will get ID and Password from login.jsp 
    // & Validate if Employee Exists
   public static Employee verifyLogin(int verifyID, String verifyPassword){
       
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        JOptionPane.showMessageDialog(null, "Password and id: " + verifyPassword + " " + verifyID);

        String query = "SELECT * FROM cs_employees "
                + "WHERE EmployeeID = ? AND Password = ?";
      try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, verifyID);
            ps.setString(2, verifyPassword);
            rs = ps.executeQuery();
            Employee employee = null;
            if (rs.next()) {
                    employee = new Employee();
                    employee.setEmployeeID(rs.getInt("EmployeeID"));
                    employee.setAuthLevel(rs.getInt("AuthLevel"));
                    employee.setPayRate(rs.getDouble("PayRate"));
                    employee.setFirstName(rs.getString("FirstName"));
                    employee.setLastName(rs.getString("LastName"));
                    employee.setStatus(rs.getBoolean("Status"));
            }
            JOptionPane.showMessageDialog(null, "Employee: " + employee.getFirstName());
            return employee;
        } catch (SQLException | NullPointerException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "wrong ID or password");
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
   }
    
    // Delete 1 Employee using Employee ID
    public static int delete(Employee employee){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM cs_employees "
                + "WHERE EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, employee.getEmployeeID());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    // Select 1 employee using Employee ID
    public static Employee selectEmployee(int employeeID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
           
        ResultSet rs = null;

        String query = "SELECT * FROM cs_employees "
                + "WHERE EmployeeID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, employeeID);
            rs = ps.executeQuery();
            Employee employee = null;
            if (rs.next()) {
                    employee = new Employee();
                    employee.setEmployeeID(rs.getInt("EmployeeID"));
                    employee.setAuthLevel(rs.getInt("AuthLevel"));
                    employee.setPayRate(rs.getDouble("PayRate"));
                    employee.setFirstName(rs.getString("FirstName"));
                    employee.setLastName(rs.getString("LastName"));
                    employee.setStatus(rs.getBoolean("Status"));
            }
            return employee;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Employee> selectEmployees(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM cs_employees";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Employee> employees = new ArrayList<>();
                while (rs.next())
                {
                    Employee employee = new Employee();
                    employee.setEmployeeID(rs.getInt("employeeID"));
                    employee.setAuthLevel(rs.getInt("authLevel"));
                    employee.setPayRate(rs.getDouble("payRate"));
                    employee.setFirstName(rs.getString("firstName"));
                    employee.setLastName(rs.getString("lastName"));
                    employee.setStatus(rs.getBoolean("status"));
                    employees.add(employee);
                }
            return employees;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<TimeClock> selectTimeClocks(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM cs_workhours";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<TimeClock> timeClocks = new ArrayList<TimeClock>();
            while (rs.next())
            {
//                TimeClock timeClock = new TimeClock();
//                timeClock.setDay(rs.getDate("day"));
//                timeClock.setStartTime(rs.getDate("startTime"));
//                timeClock.setLunchOut(rs.getDate("lunchOut"));
//                timeClock.setLunchIn(rs.getDate("lunchIn"));
//                timeClock.setEndTime(rs.getDate("endTime"));
//                
//                timeClocks.add(timeClock);
            }
            return timeClocks;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
                
    }
    
     public static int changePassword(Employee employee){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE cs_employees SET "
                + "Password = ? "
                + "WHERE employeeID = ?";
        try {
           ps = connection.prepareStatement(query);
            ps.setString(1, employee.getPassword());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}