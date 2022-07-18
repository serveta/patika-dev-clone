package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.SortedMap;

public class StudentCourse {
    private int id;
    private int userId;
    private int pathId;
    private int courseId;

    public StudentCourse(int id, int userId, int pathId, int courseId) {
        this.id = id;
        this.userId = userId;
        this.pathId = pathId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public static ArrayList<String> getMyPathName(int userId) {
        ArrayList<String> pathNameList = new ArrayList<>();
        String query = "SELECT DISTINCT public.path.name " +
                "FROM public.student_course " +
                "LEFT JOIN public.path ON public.student_course.path_id = public.path.id " +
                "WHERE public.student_course.user_id = " + userId;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                pathNameList.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pathNameList;
    }

    public static ArrayList<String> getMyCourseName(int userId, String pathName) {
        ArrayList<String> courseNameList = new ArrayList<>();
        String query = "SELECT DISTINCT public.course.name " +
                "FROM public.student_course " +
                "LEFT JOIN public.course ON public.student_course.course_id = public.course.id " +
                "WHERE public.student_course.user_id = " + userId + " AND " +
                "(SELECT name FROM public.path WHERE id = public.student_course.path_id) = '" + pathName + "'";

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                courseNameList.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseNameList;
    }

    public static ArrayList<String> getMyContentTitle(int userId, String courseName) {
        ArrayList<String> contentTitleList = new ArrayList<>();
        String query = "SELECT DISTINCT public.content.title " +
                "FROM public.student_course " +
                "LEFT JOIN public.content ON public.student_course.course_id = public.content.course_id " +
                "WHERE public.student_course.user_id = " + userId + " AND " +
                "(SELECT name FROM public.course WHERE id = public.student_course.course_id) = '" + courseName + "'";

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                contentTitleList.add(resultSet.getString("title"));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return contentTitleList;
    }


    public static boolean add(int userId, String path, String course) {
        String isThereQuery = "SELECT COUNT(*) FROM public.student_course " +
                "WHERE user_id = "+userId+" AND " +
                "path_id = (SELECT id FROM public.path WHERE name = '"+path+"') AND " +
                "course_id = (SELECT id FROM public.course WHERE name = '"+course+"')";



        String pathIdQuery = "(SELECT id FROM public.path WHERE name = '" + path + "')";
        String courseIdQuery = "(SELECT id FROM public.course WHERE name = '" + course + "')";
        String query = "INSERT INTO public.student_course (user_id,path_id,course_id) VALUES (?,"+pathIdQuery+","+courseIdQuery+")";
        boolean isAdd;


        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(isThereQuery);
            if (resultSet.next()){
                if(resultSet.getInt("count")>0){
                    isAdd = false;
                } else {
                    PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
                    preparedStatement.setInt(1, userId);
                    isAdd = preparedStatement.executeUpdate() != -1;
                    preparedStatement.close();
                }
            } else {
                isAdd = false;
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isAdd;
    }
}
