package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM public.\"patikaUser\"";

        User user;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
                userList.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static ArrayList<User> getListOnlyEducator() {
        ArrayList<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM public.\"patikaUser\" WHERE type='educator'";

        User user;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
                userList.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static boolean add(String name, String username, String password, String type) {
        String query = "INSERT INTO public.\"patikaUser\" (name,username,password,type) VALUES (?,?,?,?)";
        boolean isAdd;

        if (User.getFetch(username) != null) {
            Helper.showMessage("usernameInUse");
            isAdd = false;
        } else {
            try {
                PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
                preparedStatement.setString(1, name.trim());
                preparedStatement.setString(2, username.trim());
                preparedStatement.setString(3, password.trim());
                preparedStatement.setString(4, type);

                isAdd = preparedStatement.executeUpdate() != -1;
                if (!isAdd) {
                    Helper.showMessage("error");
                }
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return isAdd;
    }

    public static User getFetch(String username) {
        User user = null;

        String query = "SELECT * FROM public.\"patikaUser\" WHERE username = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static User getFetch(String username, String password) {
        User user = null;

        String query = "SELECT * FROM public.\"patikaUser\" WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
    public static User getFetch(int id) {
        User user = null;

        String query = "SELECT * FROM public.\"patikaUser\" WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM public.\"patikaUser\" WHERE id = ?";
        boolean isDelete;

        ArrayList<Course> courseList = Course.getListByUser(id);
        for (Course course : courseList) {
            Course.delete(course.getId());
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            isDelete = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDelete;
    }

    public static boolean update(int id, String name, String username, String password, String type) {
        String query = "UPDATE public.\"patikaUser\" SET name=?, username=?, password=?, type=? WHERE id=?";
        boolean isUpdate;

        if (getFetch(username) != null && getFetch(username).getId() != id) {
            Helper.showMessage("usernameInUse");
            return false;
        }

        if (name.equals("") || username.equals("") || password.equals("") || type.equals("")) {
            Helper.showMessage("fill");
            return false;
        }

        if (type.equals("operator") || type.equals("student") || type.equals("educator")) {
            try {
                PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
                preparedStatement.setString(1, name.trim());
                preparedStatement.setString(2, username.trim());
                preparedStatement.setString(3, password.trim());
                preparedStatement.setString(4, type.trim());
                preparedStatement.setInt(5, id);
                isUpdate = preparedStatement.executeUpdate() != -1;
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Helper.showMessage("User type cannot be " + type);
            isUpdate = false;
        }
        return isUpdate;
    }

    public static ArrayList<User> search(String query) {
        ArrayList<User> userList = new ArrayList<>();

        User user;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
                userList.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public static String searchQuery(String id, String name, String username, String type) {
        String query = "SELECT * FROM public.\"patikaUser\" WHERE id = {{id}} AND name ILIKE '%{{name}}%' AND username ILIKE '%{{username}}%' AND type LIKE '%{{type}}%'";
        if (id.trim().length() != 0) {
            query = query.replace("{{id}}", id);
        } else {
            query = query.replace("id = {{id}} AND ", "");
        }
        query = query.replace("{{name}}", name);
        query = query.replace("{{username}}", username);
        query = query.replace("{{type}}", type);

        return query;
    }
}
