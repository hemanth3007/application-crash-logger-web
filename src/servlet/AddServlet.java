package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import service.CrashLogService;

public class AddServlet extends HttpServlet {

    private CrashLogService service = new CrashLogService();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String msg = req.getParameter("message");
        String result = service.addCrashLog(id, msg);
        if ("Success".equals(result))
            req.setAttribute("popup", "Crash Log Added Successfully.");
        else
            req.setAttribute("popup", result);
        req.getRequestDispatcher("index.jsp").forward(req, res);
    }
}