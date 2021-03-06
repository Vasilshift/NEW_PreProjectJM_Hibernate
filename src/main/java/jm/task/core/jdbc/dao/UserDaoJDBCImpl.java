package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Statement statement;
    private Connection connection;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {

        try (Connection connection = Util.getConnection()) {

            String query = "CREATE TABLE IF NOT EXISTS test.User (\n" +
                    " `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    " `name` VARCHAR(45) NULL,\n" +
                    " `lastName` VARCHAR(45) NULL,\n" +
                    " `age` TINYINT NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            statement = connection.createStatement();
            statement.execute(query);
            //ResultSet resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {

            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS test.User;");
            //statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        try (Connection connection = Util.getConnection()) {

            //statement = connection.createStatement();
            //preparedStatement.getConnection();
            String query = "INSERT INTO test.User (name, lastName, age  ) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.printf("???????????????????????? ?? ???????????? %s ???????????????? ?? ???????? ????????????.", name);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id)  {
        try (Connection connection = Util.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from test.User where id = ?;");
            //preparedStatement.execute();
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.printf("???????????????????????? ?? id %d ???????????? ???? ???????? ????????????", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List <User> getAllUsers ()  {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM test.User;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
    }

    public void cleanUsersTable () throws SQLException {
        try (Connection connection = Util.getConnection()) {
            statement = connection.createStatement();
            statement.execute("DELETE FROM test.User;");
            System.out.println("?????????????? ??????????????.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}