/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RTv2.servlets;

import RTv2.database.EmployeeDB;
import RTv2.database.TimeClockDB;
import RTv2.objects.Employee;
import RTv2.objects.TimeClock;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexander
 */
@WebServlet(name = "ManagerPageServlet", urlPatterns = {"/ManagerPageServlet"})
public class ManagerPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        String url = "/managerPage.jsp";
        
        // get current action
        String action = request.getParameter("action");
        
        if(action == null){
            action = "showMyHours";
        }
        
        
        if (action.equals("clockIn")) {
            TimeClock timeClock = (TimeClock) session.getAttribute("dayID");
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            Employee employee = EmployeeDB.selectEmployee(employeeID);
            JOptionPane.showMessageDialog(null, employeeID);
            
            
            TimeClockDB.insertTimeClock(employeeID);
            /*if dayID.equals("")
            //setAttribute for dayID
            if (timeClock.getStartTime().equals("")){
                timeClock.setStartTime(LocalTime.now().toString());
            } else if (timeClock.getLunchIn().equals("")){
                timeClock.setLunchIn(LocalTime.now().toString());
            } else
                JOptionPane.showMessageDialog(null, "Please Clock Out.");
        } else if (action.equals("clockOut")) {
            TimeClock timeClock = (TimeClock) session.getAttribute("dayID");
            if (timeClock.getLunchOut().equals("")){
                timeClock.setLunchOut(LocalTime.now().toString());
            } else if (timeClock.getEndTime().equals("")){
                timeClock.setEndTime(LocalTime.now().toString());
            } else
                JOptionPane.showMessageDialog(null, "Please Clock In.");*/
            
            request.setAttribute("employee",employee);
            
            url = "/managerPage.jsp";
        }
        
        else if (action.equals("update_timeClock")) {
            // get parameters from the request
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            String dayID = request.getParameter("dayID");
            String clockIn = request.getParameter("clockIn");
            String lunchOut = request.getParameter("lunchOut");
            String lunchIn = request.getParameter("lunchIn");
            String clockOut = request.getParameter("clockOut");

            
            // get and update user
            TimeClock timeClock = (TimeClock) session.getAttribute("employeeID");
            timeClock.setDayID(dayID);
            timeClock.setEmployeeID(employeeID);
            timeClock.setStartTime(clockIn);
            timeClock.setLunchOut(lunchOut);
            timeClock.setLunchIn(lunchIn);
            timeClock.setEndTime(clockOut);
            TimeClockDB.updateTimeClock(timeClock);

            // get and set updated users
            ArrayList<TimeClock> timeClocks = TimeClockDB.selectTimeClocks();            
            request.setAttribute("timeClocks", timeClocks);            
        }
        
        else if (action.equals("showMyHours")){
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            
            JOptionPane.showMessageDialog(null, employeeID);
            
            ArrayList<TimeClock> timeClock = TimeClockDB.selectTimeClock(employeeID);
            
            Employee employee = EmployeeDB.selectEmployee(employeeID);
            
            request.setAttribute("timeClocks",timeClock);      
            request.setAttribute("employee",employee);
            //TODO: add if statment to check authLevel based on
            //authLevel choose either manager or employee.jsp...
            url = "/managerPage.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
     
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
