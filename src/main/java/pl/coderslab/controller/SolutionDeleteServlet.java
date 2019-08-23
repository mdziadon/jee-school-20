package pl.coderslab.controller;

import pl.coderslab.model.SolutionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/solutionDelete")
public class SolutionDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int solutionId = Integer.parseInt(id);
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.delete(solutionId);
        response.sendRedirect("/");
    }
}
