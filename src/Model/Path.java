package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Path {
    private int id;
    private String name;

    public Path() {
    }

    public Path(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static ArrayList<Path> getList() {
        ArrayList<Path> pathList = new ArrayList<>();

        String sql = "SELECT * FROM public.path";

        Path path;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));
                pathList.add(path);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pathList;
    }

    public static boolean add(String name) {
        String query = "INSERT INTO public.path (name) VALUES (?)";
        boolean isAdd;
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name.trim());
            isAdd = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isAdd;
    }

    public static boolean update(int id, String name) {
        String query = "UPDATE public.path SET name = ? WHERE id = ?";
        boolean isUpdate;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name.trim());
            preparedStatement.setInt(2, id);
            isUpdate = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdate;
    }

    public static Path getFetch(int id) {
        Path path = null;

        String query = "SELECT * FROM public.path WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                path = new Path();
                path.setId(resultSet.getInt("id"));
                path.setName(resultSet.getString("name"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return path;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM public.path WHERE id = ?";

        boolean isDelete;

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
}
