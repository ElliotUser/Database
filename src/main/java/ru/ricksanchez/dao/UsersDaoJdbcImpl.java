package ru.ricksanchez.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ru.ricksanchez.model.User;

public class UsersDaoJdbcImpl implements UsersDao{
    private UsersDao usersDao;

    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM fix_user_database";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM fix_user_database WHERE id = ?";

    private Connection connection;

    public UsersDaoJdbcImpl (DataSource dataSource){
        try {
            this.connection = dataSource.getConnection();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    @Override
    public List<User> findAllByName(String firstName) {
        return null;
    }

    @Override
    public Optional<User> find(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                return Optional.of(new User(id,firstName,lastName));
            }
            return Optional.empty();
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User user = new User(id,firstName,lastName);
                users.add(user);
            }
            return users;
        } catch(SQLException e) {
            throw new IllegalStateException(e);
        }
    }


}
