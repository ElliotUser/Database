package ru.ricksanchez.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.ricksanchez.dao.UsersDao;
import ru.ricksanchez.dao.UsersDaoJdbcImpl;
import ru.ricksanchez.dao.UsersDaoJdbcTempImpl;
import ru.ricksanchez.model.User;

@WebServlet("/users")
public class UsersServletWithDao extends HttpServlet {
    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUserName = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            dataSource.setUsername(dbUserName);
            dataSource.setUrl(dbUrl);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName(driverClassName);

            usersDao = new UsersDaoJdbcTempImpl(dataSource);
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = null;
        if(req.getParameter("firstName") != null){
            String firstName = req.getParameter("firstName");
            users = usersDao.findAllByName(firstName);
        }else {
            users = usersDao.findAll();
        }
        req.setAttribute("usersFromServer",users);
        req.getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(req,resp);
    }
}
