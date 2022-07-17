package Model;

import Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int content_id;
    private String question;
    private String answer;
    private Content content;

    public Quiz(int id, int content_id, String question, String answer) {
        this.id = id;
        this.content_id = content_id;
        this.question = question;
        this.answer = answer;
        this.content = Content.getFetch(content_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static ArrayList<Quiz> getListByContentId(int contentID) {
        ArrayList<Quiz> quizList = new ArrayList<>();

        String query = "SELECT * FROM public.quiz WHERE content_id = " + contentID;

        Quiz quiz;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int content_id = resultSet.getInt("content_id");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                quiz = new Quiz(id, content_id, question, answer);
                quizList.add(quiz);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return quizList;
    }

    public static Quiz getFetch(int quizID) {
        String query = "SELECT * FROM public.quiz WHERE id = " + quizID;

        Quiz quiz = null;

        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int content_id = resultSet.getInt("content_id");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                quiz = new Quiz(id, content_id, question, answer);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return quiz;
    }

    public static boolean add(int content_id, String question, String answer) {
        String query = "INSERT INTO public.quiz (content_id, question, answer) VALUES (?,?,?)";
        boolean isAdd;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, content_id);
            preparedStatement.setString(2, question);
            preparedStatement.setString(3, answer);
            isAdd = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isAdd;
    }

    public static boolean delete(int content_id) {
        String query = "DELETE FROM public.quiz WHERE content_id = ?";
        boolean isDelete;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, content_id);
            isDelete = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDelete;
    }

    public static boolean update(int id, String question, String answer) {
        String query = "UPDATE public.quiz SET question = ?, answer = ? WHERE id = ?";
        boolean isUpdate;

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, answer);
            preparedStatement.setInt(3, id);
            isUpdate = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdate;
    }
}
