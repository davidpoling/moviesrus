package uiUtils;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import queryUtils.DBQueries;
import queryUtils.SQLConnection;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsProject.ToolTipText;

public class InputUtils {
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	
	public static JTextField createTextField(int width, int height, ToolTipText toolTipText){
		JTextField tf = new JTextField();
		addSizeandTooltip(tf, width, height, toolTipText);
		return tf;
	}
	
	public static JTextField createInputField(int width, int height, String value, ToolTipText toolTipText){
		JTextField tf = new JTextField();
		addSizeandTooltip(tf, width, height, toolTipText);
		tf.getDocument().putProperty("name", value);
		tf.setToolTipText(toolTipText.getToolTipText());
		return tf;
	}
	
	public static JPasswordField createPasswordField(int width, int height, String value, ToolTipText toolTipText){
		JPasswordField pwf = new JPasswordField();
		addSizeandTooltip(pwf, width, height, toolTipText);
		pwf.getDocument().putProperty("name", value);
		return pwf;
	}
	
	public static JTextField createSearchField(int width, int height, String value, ToolTipText toolTipText){
		JTextField tf = new JTextField();
		addSizeandTooltip(tf, width, height, toolTipText);
		tf.getDocument().putProperty("name", value);
		tf.setToolTipText(toolTipText.getToolTipText());
		return tf;
	}
	
	public static JComboBox<String> createSearchBox(int width, int height, ToolTipText tooltip, ResultSet resultSet){
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
		JComboBox<String> cb = new JComboBox<String>();
		comboBoxModel.addElement("select");
		if(resultSet != null){
			 try {
				while(resultSet.next()){
				        comboBoxModel.addElement(resultSet.getString("OBJECT_NAME"));
				    }
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		cb.setEditable(true);
		addSizeandTooltip(cb, width, height, tooltip);
		return cb;
	}
	
	public static JComboBox<String> createDBComboBox(int width, int height, ToolTipText tooltip, SQLConnection conn,
			DBTable table, DBColumn column, boolean editable, DBColumn source, String value){
		String query;
		Statement statement;
		ResultSet resultSet = null;
		DefaultComboBoxModel<String> comboBoxModel = null;
		JComboBox<String> cb;
		
		query = "SELECT DISTINCT " + column.getDbColumnName()
				+ " FROM ";
		if(table.equals(DBTable.MEDIA) && !column.equals(DBColumn.DATE)){
			query += DBQueries.getMediaTable(table, "titles");
		}else{
			query += table.getDbTableName();
		}
		if(source != null){
			query += " WHERE " + source.getDbColumnName() + " = '" + value + "'";
		}
		query += " ORDER BY " + column.getDbColumnName() + " ASC;";
		logger.info("Combo box: " + query);
		try {
			statement = conn.getConnection().createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet == null){
				JOptionPane.showMessageDialog(null,"No records found!");
			}else{
				ResultSetMetaData metaData = resultSet.getMetaData();
//				logger.info(metaData.toString());
				comboBoxModel = new DefaultComboBoxModel<String>();
				resultSet.next();
					comboBoxModel.addElement("select");
				do{
            		comboBoxModel.addElement(resultSet.getString(1));
	            }while(resultSet.next());
			} 
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cb = new JComboBox<String>(comboBoxModel);
		cb.setEditable(editable);
		addSizeandTooltip(cb, width, height, tooltip);
		return cb;
	}
	
	private static void addSizeandTooltip(JComponent field, int width, int height, ToolTipText tooltip){
		field.setPreferredSize(new Dimension(width, height));
		field.setMaximumSize(new Dimension(width, height));
		field.setToolTipText(tooltip.getToolTipText());
	}
}
