package pl.coderslab.controller;

import pl.coderslab.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/solutionAdd")
public class SolutionAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exerciseId = request.getParameter("exerciseId");
        String userId = request.getParameter("userId");
        String description = request.getParameter("description");

        int exerciseIdInt = Integer.parseInt(exerciseId);
        int userIdInt = Integer.parseInt(userId);

        Exercise exercise = new Exercise();
        exercise.setId(exerciseIdInt);

        User user = new User();
        user.setId(userIdInt);

        Solution solution = new Solution();
        solution.setDescription(description);
        solution.setExcercise(exercise);
        solution.setUser(user);

        SolutionDao solutionDao = new SolutionDao();
        solutionDao.create(solution);

        response.sendRedirect("/");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        User[] users = userDao.findAll();

        ExerciseDao exerciseDao = new ExerciseDao();
        Exercise[] exercises = exerciseDao.findAll();

        request.setAttribute("users", users);
        request.setAttribute("exercises", exercises);

        getServletContext().getRequestDispatcher("/solutionAdd.jsp")
                .forward(request, response);


    }
}
