/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Klaus Boeing
 */
public class ConnectionFactory {

    public Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/contatos", "admin", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
