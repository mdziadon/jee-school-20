package pl.coderslab.controller;

import pl.coderslab.model.Solution;
import pl.coderslab.model.SolutionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SolutionDao solutionDao = new SolutionDao();
        Solution[] solutions = solutionDao.findRecent(5);

        request.setAttribute("solutions", solutions);

        getServletContext().getRequestDispatcher("/home.jsp")
                .forward(request, response);
    }
}
