package queryUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ui.MainFrame;
import enumsProject.WarningText;

public class SQLConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	static final String DATABASE_URL = "jdbc:mysql://falcon-cs.fairmontstate.edu/DB00";
	static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mydb";
	
//	TODO add login details
	static final String USER_NAME = "root";
	static final String PASSWORD = "admin";
	MainFrame mainFrame;
	Connection connection = null;

	public SQLConnection(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, WarningText.DB_CONN.getText(),
					WarningText.DATABASE.getText(), JOptionPane.ERROR_MESSAGE);
			mainFrame.setLoggedOutLayout();
			e.printStackTrace();
		}
	}
	
	public SQLConnection(String username, String password){
		try {
			connection = DriverManager.getConnection(DATABASE_URL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
