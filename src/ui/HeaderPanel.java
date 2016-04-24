package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.Admin;
import model.Member;
import queryUtils.SQLConnection;
import queryUtils.DBQueries;
import uiUtils.ButtonUtils;
import uiUtils.LabelUtils;
import utils.AppUtils;
import enumsProject.ButtonText;
import enumsProject.LabelText;
import enumsProject.ToolTipText;

public class HeaderPanel extends JPanel 
						implements ActionListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private MainFrame mainFrame;
	private boolean loggedIn;
	private JLabel appNameLabel;
	private LoginPanel loginPanel;
	private JButton actionBtn;
	private JPopupMenu userMenu;
	private JMenuItem settingsItem;
	private JMenuItem logoutItem;
	private Member member;
	private Admin admin;
	private SQLConnection dbConn;
	
	public HeaderPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		loggedIn = false;
		this.loginPanel = new LoginPanel(this);
		createUI();
		addListeners();
	}
	
	public JButton getActionBtn() {
		return actionBtn;
	}

	private void createUI(){
		appNameLabel = LabelUtils.createAppLabel(LabelText.APPNAME);
		actionBtn = ButtonUtils.createButton(ButtonText.LOGIN, ToolTipText.LOGIN);
		userMenu = ButtonUtils.createPopupMenu();
		settingsItem = ButtonUtils.createMenuItem(ButtonText.SETTINGS);
		logoutItem = ButtonUtils.createMenuItem(ButtonText.LOGOUT);

		userMenu.add(settingsItem);
		userMenu.add(logoutItem);
		
		BorderLayout layout = new BorderLayout(10, 10);
		this.setLayout(layout);
		this.add(appNameLabel, BorderLayout.WEST);
		this.add(loginPanel, BorderLayout.CENTER);
		this.add(actionBtn, BorderLayout.EAST);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setVisible(true);
	}
	
	private void addListeners(){
		actionBtn.addActionListener(this);
		settingsItem.addActionListener(this);
		logoutItem.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();
		
		if(eventSource.equals(actionBtn)){
			if(actionBtn.getText().equals(ButtonText.LOGIN.getButtonText())){
				dbConn = new SQLConnection(mainFrame);
				mainFrame.setDbConn(dbConn);
				if(dbConn == null){
					JOptionPane.showMessageDialog(null,"Connection Failed");
				}
				loggedIn = getMemberData(loginPanel.getUsernameTF().getText(),
						new String(loginPanel.getPasswordTF().getPassword()).getBytes());
				if(!loggedIn){
					loggedIn = getAdminData(loginPanel.getUsernameTF().getText(),
						new String(loginPanel.getPasswordTF().getPassword()).getBytes());
				}
				if(!loggedIn){
					logoutItem.doClick();
				}
				if(member != null){
					mainFrame.setMember(member);
					mainFrame.setAdmin(null);
					mainFrame.setRentalLayout(member);	
					setValidUserLayout(true);
					loginPanel.setValidUser(true, member.getFirstName());
				}else if (admin != null){
					mainFrame.setAdmin(admin);
					mainFrame.setMember(null);
					mainFrame.setAdminLayout();
					settingsItem.setEnabled(false);
					setValidUserLayout(true);
					loginPanel.setValidUser(true, admin.getEmail());
				}
			}else if(actionBtn.getText().equals(ButtonText.SETTINGS.getButtonText())){
				showPopup(e);
			}
		}else if(eventSource.equals(settingsItem)){
			logger.info("member: " + member);
			if(member != null){
				mainFrame.setMemberLayout(member);
			}
		}else if(eventSource.equals(logoutItem)){
			setValidUserLayout(false);
			mainFrame.setLoggedOutLayout();
			member = null;
			admin = null;
			loggedIn = false;
			loginPanel.reset();
		}
	}
	
	private boolean getMemberData(String email, byte[] password){
		String hashedPassword;
		hashedPassword = AppUtils.hashPassword(password);
		logger.info("email =" + email + " password = " + hashedPassword);
		return fetchUser(false, DBQueries.fetchMember(email, hashedPassword));
	}
	
	private boolean getAdminData(String email, byte[] password){
		String hashedPassword;
		boolean valid;
		hashedPassword = AppUtils.hashPassword(password);
		logger.info("email =" + email + " password = " + hashedPassword);
		valid = fetchUser(true, DBQueries.fetchAdmin(email, hashedPassword));
		if(!valid){
			JOptionPane.showMessageDialog(null,"Username/password mismatch!");
		}
		return valid;
	}
	
	private void setValidUserLayout(boolean validUser){
		if(validUser){
			actionBtn.setText(ButtonText.SETTINGS.getButtonText());
			actionBtn.setToolTipText(ToolTipText.SETTINGS.getToolTipText());
		}else{
			actionBtn.setText(ButtonText.LOGIN.getButtonText());
			actionBtn.setToolTipText(ToolTipText.LOGIN.getToolTipText());
			settingsItem.setEnabled(true);
		}
	}
	
    private void showPopup(ActionEvent ae)
    {
        Component component =(Component)ae.getSource();     
        Point location = component.getLocationOnScreen();

        userMenu.show(this,0,0);
        userMenu.setLocation(location.x, location.y+component.getHeight());
    }
    
	private boolean fetchUser(boolean isAdmin, String query){
		boolean isUser = false;
		ResultSet resultSet;
		Connection conn = mainFrame.getDbConn().getConnection();
		Statement statement = null;
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			
			if(!resultSet.next()){
				isUser = false;
			}else{
				Vector<Object> columnNames = new Vector<Object>(); 
				
				ResultSetMetaData metaData = resultSet.getMetaData();
				logger.info("Column Count= " + metaData.getColumnCount());
				
				for(int i = 1; i <= metaData.getColumnCount(); ++i){
					columnNames.addElement(metaData.getColumnName(i));
	//					logger.info(metaData.getColumnName(i).toString() + " = " + resultSet.getObject(i).toString());
				}
				
				int column = 1;
				if(!isAdmin){
					member = new Member();
					member.setId(resultSet.getInt(column++));
					member.setFirstName(resultSet.getString(column++));
					member.setSurName(resultSet.getString(column++));
					member.getAddress().setId(resultSet.getInt(column++));
					member.getAddress().setStreet(resultSet.getString(column++));
					member.getAddress().setApartment(resultSet.getString(column++));
					member.getAddress().setCity(resultSet.getString(column++));
					member.getAddress().setState(resultSet.getString(column++));
					member.getAddress().setPostal(resultSet.getInt(column++));
					member.getAddress().setType(resultSet.getString(column++));
					member.setId(resultSet.getInt(column++));
					member.setEmail(resultSet.getString(column++));
					member.setPassword(resultSet.getString(column++));
					member.setPasswordDate(resultSet.getDate(column++));
					member.getMembership().setId(resultSet.getInt(column++));
					member.getMembership().setContent(resultSet.getString(column++));
					member.getMembership().setQuantity(resultSet.getInt(column++));
					member.getMembership().setExpiration(resultSet.getDate(column++));
					resultSet = statement.executeQuery(DBQueries.fetchMemberRentedCount(member.getId()));
				    if (resultSet.next()) {
				    	member.setCurrentRentals(resultSet.getInt(1));
				    }
				    logger.info(member.toString());
				}else if(isAdmin){
					admin = new Admin();
					admin.setId(resultSet.getInt(column++));
					admin.setEmail(resultSet.getString(column++));
					admin.setPassword(resultSet.getString(column++));
				}
				isUser = true;
			}
			statement.close();
		} catch (SQLException e) {
			isUser = false;
			e.printStackTrace();
		}
		
		return isUser;
	}
}
