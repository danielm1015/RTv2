/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RTv2.servlets;

import RTv2.database.TimeClockDB;
import RTv2.objects.TimeClock;

import java.io.IOException;
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
 * @author Admin
 */
@WebServlet(name = "TimeClocksServlet", urlPatterns = {"/timeclock"})

public class TimeClocksServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
    
        String url = "/viewTimeClocks.jsp";
        
        // get current action
        String action = request.getParameter("action");
        JOptionPane.showMessageDialog(null, action);
        if(action == null){
            action = "display_timeClocks";
        }
        
        
        if (action.equals("clockIn")) {
            TimeClock timeClock = (TimeClock) session.getAttribute("dayID");
            //if dayID.equals("")
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
                JOptionPane.showMessageDialog(null, "Please Clock In.");
        }
        
        if (action.equals("display_timeClocks")) {            
            // get list of users
            ArrayList<TimeClock> timeClocks = TimeClockDB.selectTimeClocks();            
            request.setAttribute("timeClocks", timeClocks);
            url = "/viewTimeClocks.jsp";
        } 
        
        else if (action.equals("update_timeClock")) {
            // get parameters from the request
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            String clockIn = request.getParameter("clockIn");
            String lunchOut = request.getParameter("lunchOut");
            String lunchIn = request.getParameter("lunchIn");
            String clockOut = request.getParameter("clockOut");

            // get and update user
            TimeClock timeClock = (TimeClock) session.getAttribute("employeeID"); 
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
            TimeClock timeClock = TimeClockDB.selectTimeClock(employeeID);
            session.setAttribute("timeClock",timeClock);
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