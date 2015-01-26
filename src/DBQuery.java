import java.sql.*;
import java.util.ArrayList;

/**
  Created by Evgeny Baskakov on 26.01.2015.
**/
public class DBQuery {
    private static String dbName = "climate";
    private static String DBUrl = "jdbc:mysql://localhost/" + dbName;
    private static String DBUser = "root";
    private static String DBPassword = "qweqwe!23";
    private static String toolsTableName = "tools";
    //private String timeTableName = "timetable";

    public ResultSet makeQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultSet;
    }

    public ArrayList<Tool> getTools() {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Tool> queryResult = new ArrayList<Tool>();

        String query = "SELECT * FROM `" + toolsTableName + "`";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Tool tool = new Tool();
                tool.setId(resultSet.getString(1));
                tool.setName(resultSet.getString(2));
                tool.setDescription(resultSet.getString(3));
                tool.setToolType(resultSet.getString(4));
                tool.setPlacement(resultSet.getString(5));
                queryResult.add(tool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return queryResult;
    }

    public ArrayList<Experiment> getExperiments(String cameraId) {
        Connection connection = null;
        Statement statement = null;

        ArrayList<Experiment> experiments = new ArrayList<Experiment>();

        String query = "SELECT * FROM `timetable` WHERE `camera_id` = " + cameraId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Experiment experiment = new Experiment();
                experiment.setId(resultSet.getString(1));
                experiment.setCameraId(resultSet.getString(2));
                experiment.setStartTime(resultSet.getString(3));
                experiment.setEndTime(resultSet.getString(4));
                experiment.setDecNumber(resultSet.getString(5));
                experiment.setName(resultSet.getString(6));
                experiment.setSerialNumber(resultSet.getString(7));
                experiment.setOrder(resultSet.getString(8));
                experiment.setDescription(resultSet.getString(9));
                experiments.add(experiment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return experiments;
    }
}
