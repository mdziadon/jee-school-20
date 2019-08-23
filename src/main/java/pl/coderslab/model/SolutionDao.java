package pl.coderslab.model;

import java.sql.*;
import java.util.Arrays;

public class SolutionDao {

    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solutions(created, description, exercise_id, user_id) VALUES (current_timestamp(), ?, ?, ?)";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solutions where id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solutions SET updated = current_timestamp(), description = ?, exercise_id = ?, user_id = ? where id = ?";
    private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solutions WHERE id = ?";
    private static final String FIND_ALL_SOLUTIONS_QUERY =
            "SELECT * FROM solutions";
    private static final String FIND_ALL_SOLUTIONS_LIMIT_QUERY =
            "SELECT * FROM solutions order by id desc limit ?";
    private static final String FIND_ALL_BY_USER_ID =
            "SELECT * FROM solutions where user_id = ?";
    private static final String FIND_ALL_BY_EXERCISE_ID =
            "SELECT * FROM solutions where exercise_id = ? ORDER BY created ASC";

    public Solution create(Solution solution) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, solution.getDescription());
            if (solution.getExcercise().getId() > 0) {
                statement.setInt(2, solution.getExcercise().getId());
            } else {
                throw new IllegalArgumentException("exercise_id nie może być mniejsze niż 0");
            }
            if (solution.getUser().getId() > 0) {
                statement.setInt(3, solution.getUser().getId());
            } else {
                throw new IllegalArgumentException("user_id nie może być mniejsze niż 0");
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution read(int solutionId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                int exerciseId = resultSet.getInt("exercise_id");
                ExerciseDao exerciseDao = new ExerciseDao();
                solution.setExcercise(exerciseDao.read(exerciseId));
                int usersId = resultSet.getInt("user_id");
                UserDao userDao = new UserDao();
                solution.setUser(userDao.read(usersId));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Solution solution) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setString(1, solution.getDescription());
            if (solution.getExcercise().getId() > 0) {
                statement.setInt(2, solution.getExcercise().getId());
            } else {
                throw new IllegalArgumentException("exercise_id nie może być mniejsze niż 0");
            }
            if (solution.getUser().getId() > 0) {
                statement.setInt(3, solution.getUser().getId());
            } else {
                throw new IllegalArgumentException("user_id nie może być mniejsze niż 0");
            }
            statement.setInt(4, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int solutionId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Solution[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                int exerciseId = resultSet.getInt("exercise_id");
                ExerciseDao exerciseDao = new ExerciseDao();
                solution.setExcercise(exerciseDao.read(exerciseId));
                int usersId = resultSet.getInt("user_id");
                UserDao userDao = new UserDao();
                solution.setUser(userDao.read(usersId));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findRecent(int limit) {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_LIMIT_QUERY);
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                int exerciseId = resultSet.getInt("exercise_id");
                ExerciseDao exerciseDao = new ExerciseDao();
                solution.setExcercise(exerciseDao.read(exerciseId));
                int usersId = resultSet.getInt("user_id");
                UserDao userDao = new UserDao();
                solution.setUser(userDao.read(usersId));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution[] addToArray(Solution s, Solution[] solutions) {
        Solution[] tmpSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolutions[solutions.length] = s;
        return tmpSolutions;
    }

    public Solution[] findAllByUser(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                int exerciseId = resultSet.getInt("exercise_id");
                ExerciseDao exerciseDao = new ExerciseDao();
                solution.setExcercise(exerciseDao.read(exerciseId));
                int usersId = resultSet.getInt("user_id");
                UserDao userDao = new UserDao();
                solution.setUser(userDao.read(usersId));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByExercise(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_EXERCISE_ID);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("description"));
                int exerciseId2 = resultSet.getInt("exercise_id");
                ExerciseDao exerciseDao = new ExerciseDao();
                solution.setExcercise(exerciseDao.read(exerciseId2));
                int usersId = resultSet.getInt("user_id");
                UserDao userDao = new UserDao();
                solution.setUser(userDao.read(usersId));
                solutions = addToArray(solution, solutions);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
