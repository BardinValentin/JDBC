import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection; // Используется для подключения к БД
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // Используется для выполнения SQL запросов

public class JobDB {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String USER = "postgres";
    static final String PASS = "1q2w3e4r5t";

    public static void main(String[] args){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String titleTable;
        String command;

        try {
            getDBConnection();
            System.out.println("Введите необходимую команду: create - создать таблицу \t delete - удалить таблицу");
            command = reader.readLine();
            System.out.println("Введите название таблицы");
            titleTable = reader.readLine();

            if(command.equals("create")){
                createDBTable(titleTable);
            }
            else if(command.equals("delete")){
                dropDBTable(titleTable);
            }
            else{
                System.out.println("Введена неверная команда");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            System.out.println("Подключаем JDBC драйвер");
            Class.forName(JDBC_DRIVER);
            System.out.println("Подключение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Подключаемся к базе данных");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Подключение установлено");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private static void createDBTable(String titleTable) throws SQLException{
        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Создаем таблицу в базе данных");
            connection = getDBConnection();
            statement = connection.createStatement();

            // Выполняем вставку таблицы в БД
            statement.executeUpdate("CREATE TABLE " + titleTable + "("+
                    "id INTEGER not NULL, " +
                    " name VARCHAR(50), " +
                    " PRIMARY KEY (id) " + ")");

            System.out.println("Таблица создана");
        }finally {
            if (connection != null) {
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
    }

    private static void dropDBTable(String titleTable) throws SQLException{
        Connection connection = null;
        Statement statement = null;

        try{
            connection = getDBConnection();
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE " + titleTable);
            System.out.println("Таблица удалена");
        }finally {
            if (connection != null) {
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
        }
    }
}
