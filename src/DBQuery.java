import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ArrayList<String> getToolTypes() {
        ArrayList<String> toolTypes = new ArrayList<String>();
        String query = "SELECT * FROM `tooltype`";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                toolTypes.add(resultSet.getString("name"));
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
        return toolTypes;
    }

    public ArrayList<String> getToolPlacements() {
        ArrayList<String> toolPlacements = new ArrayList<String>();
        Connection connection = null;
        Statement statement = null;
        String query = "SELECT * FROM `toolplacement`";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                toolPlacements.add(resultSet.getString("name"));
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
        return toolPlacements;
    }

    public ArrayList<String> getExperimentTypes() {
        ArrayList<String> experimentTypes = new ArrayList<String>();
        String query = "SELECT * FROM `experimenttype`";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                experimentTypes.add(resultSet.getString("name"));
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

        return experimentTypes;
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
                tool.setId(resultSet.getString("id"));
                tool.setSerialNumber(resultSet.getString("serial_number"));
                tool.setName(resultSet.getString("name"));
                tool.setDescription(resultSet.getString("description"));
                tool.setToolType(resultSet.getString("tool_type"));
                tool.setPlacement(resultSet.getString("placement"));
                tool.setStatement(resultSet.getString("statement"));
                tool.setCertification(resultSet.getDate("certification"));
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
                experiment.setStartTime(SQL_DATE_FORMAT.parse(resultSet.getString("START_TIME")));
                experiment.setEndTime(SQL_DATE_FORMAT.parse(resultSet.getString("END_TIME")));
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

    public Experiment getCurrentExperiment(String toolId) {
        Experiment curExp = new Experiment();
        Connection connection = null;
        Statement statement = null;

        java.util.Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = formatter.format(now);

        String query = "SELECT * FROM `" + timeTableName + "` WHERE" +
                " \'" + currentDateTime + "\' >= `start_time`" +
                " AND \'" + currentDateTime + "\' < `end_time`" +
                " AND `camera_id` = " + toolId;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                curExp.setId(resultSet.getString("id"));
                curExp.setCameraId(resultSet.getString("camera_id"));
                curExp.setStartTime(resultSet.getDate("start_time"));
                curExp.setEndTime(resultSet.getDate("end_time"));
                curExp.setDecNumber(resultSet.getString("dec_number"));
                curExp.setName(resultSet.getString("name"));
                curExp.setSerialNumber(resultSet.getString("serial_number"));
                curExp.setOrder(resultSet.getString("order"));
                curExp.setDescription(resultSet.getString("description"));
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

        return curExp;
    }

    public Tool getToolData(String toolId) {
        String query = "SELECT *" +
                " FROM `" + toolsTableName + "`" +
                " WHERE id = " + toolId;
        Connection connection = null;
        Statement statement = null;
        Tool tool = new Tool();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tool.setName(resultSet.getString("name"));
                tool.setSerialNumber(resultSet.getString("serial_number"));
                tool.setToolType(resultSet.getString("tool_type"));
                tool.setPlacement(resultSet.getString("placement"));
                tool.setDescription(resultSet.getString("description"));
                tool.setStatement(resultSet.getString("statement"));
                tool.setCertification(resultSet.getDate("certification"));
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
        return tool;
    }

    public void setToolData(Tool tool) {
        String certificationDate = SQL_DATE_FORMAT.format(tool.getCertification());
        String toolStatement = tool.getStatement().equals("Ремонт") ? "broken" : "";

        String query = "UPDATE " + toolsTableName +
                " SET" +
                " `serial_number` = \'" + tool.getSerialNumber() + "\'," +
                " `name` = \'" + tool.getName() + "\'," +
                " `description` = \'" + tool.getDescription() + "\'," +
                " `tool_type` = \'" + tool.getToolType() + "\'," +
                " `placement` = \'" + tool.getPlacement() + "\'," +
                " `statement` = \'" + toolStatement + "\'," +
                " `certification` = \'" + certificationDate + "\'" +
                " WHERE `id` = \'" + tool.getId() + "\'";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);
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
    }

    public String addTool(Tool newToolData) {
        String result = "";
        String certificationDate = SQL_DATE_FORMAT.format(newToolData.getCertification());
                //newToolData.get("certificationYear") + "-" +
                //newToolData.get("certificationMonth") + "-" +
                //newToolData.get("certificationDay") + " 00:00:01";
        String checkQuery =
                "SELECT * FROM" +
                " `" + toolsTableName + "`" +
                " WHERE" +
                " `serial_number` = \'" + newToolData.getSerialNumber() + "\' AND" +
                " `name` = \'" + newToolData.getName() + "\' AND" +
                " `tool_type` = \'" + newToolData.getToolType() + "\' AND" +
                " `placement` = \'" + newToolData.getPlacement() + "\'";

        String addQuery =
                "INSERT INTO" +
                " `" + toolsTableName + "`" +
                " (`serial_number`, `name`, `description`, `tool_type`, `placement`, `statement`, `certification`)" +
                " VALUES (" +
                        "\'" + newToolData.getSerialNumber() + "\', " +
                        "\'" + newToolData.getName() + "\', " +
                        "\'" + newToolData.getDescription() + "\', " +
                        "\'" + newToolData.getToolType() + "\', " +
                        "\'" + newToolData.getPlacement() + "\', " +
                        "\'\', " +
                        "\'" + certificationDate + "\')";

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(checkQuery);
            if (resultSet.next()) {
                result = "Tool already exists";
            } else {
                statement.executeUpdate(addQuery);
                result = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
            }
        }
        return result;
    }

    public String removeTool(Tool tool) {
        String result = "";

        String getToolIdQuery = "SELECT id FROM `" + toolsTableName + "`" +
                " WHERE serial_number = \'" + tool.getSerialNumber() + "\'" +
                " AND placement = \'" + tool.getPlacement() + "\'";
        String toolId;

        Statement statement = null;
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getToolIdQuery);
            if (resultSet.next()) {
                toolId = resultSet.getString("id");
            } else {
                return "Оборудование не найдено";
            }

            String removeExperimentsQuery = "DELETE FROM `" + timeTableName + "`" +
                    " WHERE camera_id = \'" + toolId + "\'";

            String removeToolQuery = "DELETE" +
                    " FROM `" + toolsTableName + "`" +
                    " WHERE id = \'" + toolId + "\'";

            statement.executeUpdate(removeExperimentsQuery);
            statement.executeUpdate(removeToolQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    result = e.getMessage();
                }
            }
        }
        return result;
    }

    public String addExperiment(Experiment experiment) {
        String cameraId = experiment.getCameraId();

        String startTime = SQL_DATE_FORMAT.format(experiment.getStartTime());
        String endTime = SQL_DATE_FORMAT.format(experiment.getEndTime());

        try {
            Date startDate = experiment.getStartTime();
            Date endDate = experiment.getEndTime();
            if (startDate.after(endDate)) {
                return "Start date error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String decNumber = experiment.getDecNumber();
        String name = experiment.getName();
        String serialNumber = experiment.getSerialNumber();
        String order = experiment.getOrder();
        String description = experiment.getDescription();

        String checkQuery =
                "SELECT *" +
                    " FROM `" + timeTableName + "`" +
                " WHERE" +
                    " camera_id = \'" + cameraId + "\' AND" +
                    " (" +
                    " (start_time <= \'" + startTime + "\' AND" + " end_time >= \'" + startTime + "\') OR" +
                    " (start_time <= \'" + endTime + "\' AND" + " end_time >= \'" + endTime + "\') OR" +
                    " (start_time >= \'" + startTime + "\' AND" + " end_time <= \'" + endTime + "\')" +
                    ")";

        String addExperimentQuery = "INSERT" +
                " INTO `timetable`" +
                " (`camera_id`, `start_time`, `end_time`, `dec_number`, `name`, `serial_number`, `order`, `description`)" +
                " VALUES" +
                " (\'" + cameraId + "\', \'" + startTime + "\', \'" + endTime + "\', \'" +
                decNumber + "\', \'" + name + "\', \'" + serialNumber + "\', \'" +
                order + "\', \'" + description + "\')";

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkQuery);
            if (resultSet.next()) {
                return "Это время занято";
            } else {
                statement.executeUpdate(addExperimentQuery);
                return "OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Date format error";
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String removeExperiment(String cameraId, String experimentId) {
        String query = "DELETE" +
                " FROM `" + timeTableName + "`" +
                " WHERE id = \'" + experimentId + "\' AND camera_id = \'" + cameraId + "\'";

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);
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

        return "OK";
    }
}
