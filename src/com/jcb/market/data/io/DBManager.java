/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcb.market.data.io;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author gaocw
 */
public class DBManager {

	public static Connection getConnection() throws Exception {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		Connection connection = DriverManager.getConnection(
				"jdbc:derby://localhost:1527/sats", "admin", "12345");
		connection.setAutoCommit(true);
		return connection;
	}

}
