package Model;

import Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Comment {
    private int id;
    private int contentId;
    private int userId;
    private String comment;

    Content content;
    User user;

    public Comment(int id, int contentId, int userId, String comment) {
        this.id = id;
        this.contentId = contentId;
        this.userId = userId;
        this.comment = comment;
        this.content = Content.getFetch(contentId);
        this.user = User.getFetch(userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static boolean add(int contentId, int userId,String comment) {
        String query = "INSERT INTO public.comment (content_id, user_id, comment) VALUES (?,?,?)";
        boolean isAdd;
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,contentId);
            preparedStatement.setInt(2,userId);
            preparedStatement.setString(3, comment.trim());
            isAdd = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isAdd;
    }

    public static ArrayList<String[]> getCommentsOfContent(int contentId) {
        ArrayList<String[]> commentList = new ArrayList<>();
        String query = "SELECT public.\"patikaUser\".name, public.comment.comment " +
                "FROM public.comment " +
                "LEFT JOIN public.\"patikaUser\" ON public.\"patikaUser\".id = public.comment.user_id " +
                "WHERE content_id = " + contentId;

        String[] comment = new String[2];

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                comment[0] = resultSet.getString("name");
                comment[1] = resultSet.getString("comment");
                commentList.add(comment);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return commentList;
    }
}
