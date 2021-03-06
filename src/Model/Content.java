package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int course_id;
    private String title;
    private String link;
    private String description;
    private Course course;


    public Content(int id, int course_id, String title, String link, String description) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.course = Course.getFetch(course_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static ArrayList<Content> getList() {
        ArrayList<Content> contentList = new ArrayList<>();

        String query = "SELECT * FROM public.content";

        Content content;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String link = resultSet.getString("link");
                String description = resultSet.getString("description");
                content = new Content(id, course_id, title, link, description);
                contentList.add(content);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contentList;
    }

    public static ArrayList<Content> getListByCourseId(int courseID) {
        ArrayList<Content> contentList = new ArrayList<>();

        String query = "SELECT * FROM public.content WHERE course_id = " + courseID;

        Content content;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String link = resultSet.getString("link");
                String description = resultSet.getString("description");
                content = new Content(id, course_id, title, link, description);
                contentList.add(content);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contentList;
    }

    public static ArrayList<Content> getListByTitle(int courseId, String searchTitle) {
        ArrayList<Content> contentList = new ArrayList<>();

        String query = "SELECT * FROM public.content WHERE course_id = " + courseId + " AND title ILIKE '%" + searchTitle + "%'";

        Content content;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String link = resultSet.getString("link");
                String description = resultSet.getString("description");
                content = new Content(id, course_id, title, link, description);
                contentList.add(content);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contentList;
    }

    public static Content getFetch(int contentID) {
        String query = "SELECT * FROM public.content WHERE id = " + contentID;

        Content content = null;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String link = resultSet.getString("link");
                String description = resultSet.getString("description");
                content = new Content(id, course_id, title, link, description);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    public static Content getFetch(String contentTitle) {
        String query = "SELECT * FROM public.content WHERE title = '" + contentTitle + "'";

        Content content = null;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String link = resultSet.getString("link");
                String description = resultSet.getString("description");
                content = new Content(id, course_id, title, link, description);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    public static boolean add(int course_id, String title, String link, String description) {
        String query = "INSERT INTO public.content (course_id, title, link, description) VALUES (?,?,?,?)";
        boolean isAdd;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, link);
            preparedStatement.setString(4, description);
            isAdd = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isAdd;
    }

    public static boolean update(int id, String title, String link, String description) {
        String query = "UPDATE public.content SET title = ?, link = ?, description = ? WHERE id = ?";
        boolean isUpdate;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, link);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, id);
            isUpdate = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdate;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM public.content WHERE id = ?";
        boolean isDelete;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            isDelete = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isDelete) {
            if (!Quiz.deleteByContentId(id)) {
                Helper.showMessage("error");
            }
        }

        return isDelete;
    }
}
