package Model;

import Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int path_id;
    private String name;
    private String programing_language;
    private Path path;
    private User educator;

    public Course(int id, int user_id, int path_id, String name, String programing_language) {
        this.id = id;
        this.user_id = user_id;
        this.path_id = path_id;
        this.name = name;
        this.programing_language = programing_language;
        this.path = Path.getFetch(path_id);
        this.educator = User.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPath_id() {
        return path_id;
    }

    public void setPath_id(int path_id) {
        this.path_id = path_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrograming_language() {
        return programing_language;
    }

    public void setPrograming_language(String programing_language) {
        this.programing_language = programing_language;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList = new ArrayList<>();

        String sql = "SELECT * FROM public.course";

        Course course;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int path_id = resultSet.getInt("path_id");
                String name = resultSet.getString("name");
                String programing_language = resultSet.getString("programing_language");
                course = new Course(id, user_id, path_id, name, programing_language);
                courseList.add(course);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courseList;
    }
}