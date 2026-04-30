package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import service.CrashLogService;

public class DisplayServlet extends HttpServlet {
    private CrashLogService service = new CrashLogService();
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (service.getAllCrashLogs().isEmpty())
            req.setAttribute("popup", "No Crash Logs Found.");
        else
            req.setAttribute("logs", service.getAllCrashLogs());
        req.getRequestDispatcher("index.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }
}