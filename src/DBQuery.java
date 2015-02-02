import com.sun.org.apache.bcel.internal.generic.DREM;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
  Created by Evgeny Baskakov on 26.01.2015.
**/
public class DBQuery {
    private static String dbName = "climate";
    private static String DBUrl = "jdbc:mysql://localhost/" + dbName;
    private static String DBUser = "root";
    private static String DBPassword = "qweqwe!23";
    private static String toolsTableName = "tools";
    private String timeTableName = "timetable";

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

        String query = "SELECT * FROM `" + timeTableName + "` WHERE `camera_id` = " + cameraId + " ORDER BY `start_time`";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Experiment experiment = new Experiment();
                experiment.setId(resultSet.getString(1));
                experiment.setCameraId(resultSet.getString(2));

                //date and time format 'HH:MM DD-MM-YYYY'
                String sqlDateTime[] = resultSet.getString(3).split("\\s");
                String[] date = sqlDateTime[0].split("-");
                String[] time = sqlDateTime[1].split(":");
                experiment.setStartTime(time[0] + ":" + time[1] + " " + date[2] + "-" + date[1] + "-" + date[0]);
                sqlDateTime = resultSet.getString(4).split("\\s");
                date = sqlDateTime[0].split("-");
                time = sqlDateTime[1].split(":");
                experiment.setEndTime(time[0] + ":" + time[1] + " " + date[2] + "-" + date[1] + "-" + date[0]);

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

    public String[][] getCurrentExperiments(int size) {
        String[][] currentExperiments = new String[size][9];
        Connection connection = null;
        Statement statement = null;

        java.util.Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String currentDateTime = formatter.format(now);
        String currentDateTime = "2014-01-29 13:00:00";

        String query = "SELECT * FROM `" + timeTableName + "` WHERE" +
                " \'" + currentDateTime + "\' >= `start_time` " +
                " AND \'" + currentDateTime + "\' < `end_time`" +
                " ORDER BY `camera_id`";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                //date and time format 'HH:MM DD-MM-YYYY'
                String sqlDateTime[] = resultSet.getString("start_time").split("\\s");
                String[] date = sqlDateTime[0].split("-");
                String[] time = sqlDateTime[1].split(":");
                String experimentStartTime = time[0] + ":" + time[1] + " " + date[2] + "-" + date[1] + "-" + date[0];
                sqlDateTime = resultSet.getString("end_time").split("\\s");
                date = sqlDateTime[0].split("-");
                time = sqlDateTime[1].split(":");
                String experimentEndTime = time[0] + ":" + time[1] + " " + date[2] + "-" + date[1] + "-" + date[0];

                currentExperiments[resultSet.getInt("camera_id")][0] = resultSet.getString("id");
                currentExperiments[resultSet.getInt("camera_id")][1] = resultSet.getString("camera_id");
                currentExperiments[resultSet.getInt("camera_id")][2] = experimentStartTime;
                currentExperiments[resultSet.getInt("camera_id")][3] = experimentEndTime;
                currentExperiments[resultSet.getInt("camera_id")][4] = resultSet.getString("dec_number");
                currentExperiments[resultSet.getInt("camera_id")][5] = resultSet.getString("name");
                currentExperiments[resultSet.getInt("camera_id")][6] = resultSet.getString("serial_number");
                currentExperiments[resultSet.getInt("camera_id")][7] = resultSet.getString("order");
                currentExperiments[resultSet.getInt("camera_id")][8] = resultSet.getString("description");
            }
            //int i = 0;
            //while (i < currentExperiments.length) {
            //    System.out.print(i + ": ");
            //    for (int j = 0; j < currentExperiments[i].length; j++){
            //        System.out.print(currentExperiments[i][j] + " ");
            //    }
            //    System.out.println("");
            //    i++;
            //}

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
        return currentExperiments;
    }

    public String addCamera(ArrayList<String> values) {
        String result;
        String checkQuery =
                "SELECT * FROM" +
                " `" + toolsTableName + "`" +
                " WHERE" +
                " `name` = \'" + values.get(0) + "\' AND" +
                " `tool_type` = \'" + values.get(2) + "\' AND" +
                " `placement` = \'" + values.get(3) + "\'";

        String addQuery =
                "INSERT INTO" +
                " `" + toolsTableName + "`" +
                " (`name`, `description`, `tool_type`, `placement`)" +
                " VALUES (";
                for (int i = 0; i < values.size(); i++) {
                    if (i < values.size() - 1) {
                        addQuery += "\'" + values.get(i) + "\', ";
                    } else {
                        addQuery += "\'" + values.get(i) + "\'";
                    }
                }
                addQuery += ")";

        result = checkQuery;

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(checkQuery);
            if (resultSet.next()) {
                System.out.println(resultSet.getString("id") + " " +
                        resultSet.getString("name") + " " +
                        resultSet.getString("description") + " " +
                        resultSet.getString("tool_type") + " " +
                        resultSet.getString("placement")
                );
            } else {
                statement.executeUpdate(addQuery);
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
        return result;
    }
}
