package servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import service.CrashLogService;

public class SortServlet extends HttpServlet {
    private CrashLogService service = new CrashLogService();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (service.getAllCrashLogs().isEmpty())
            req.setAttribute("popup", "No Crash Logs Found to Sort.");
        else
            req.setAttribute("logs", service.getSortedCrashLogs());
        req.getRequestDispatcher("index.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }
}