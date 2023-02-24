package data;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLHelper {
    public static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    //PostgreSQL local
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "pass");
    }
    //MySQL local
//    private static Connection getConn() throws SQLException {
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
//    }

    //MySQL remote
//    private static Connection getConn() throws SQLException {
//        return DriverManager.getConnection("url=jdbc:mysql://192.168.1.100:3306/app", "app", "pass");
//    }

    public static String getStatusCard() {
        var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return code;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getStatusCredit() {
        var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return code;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
