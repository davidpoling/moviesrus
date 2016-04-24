package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import uiUtils.InputUtils;
import uiUtils.LabelUtils;
import enumsDB.DBColumn;
import enumsProject.LabelText;
import enumsProject.ToolTipText;
import enumsProject.UISize;

public class LoginPanel extends JPanel
					implements ActionListener{
	private boolean validUser;
	private JLabel userNameLabel;
	private JLabel memberNameLabel;
	private JLabel passwordLabel;
	private JTextField usernameTF;
	private JPasswordField passwordTF;
	private HeaderPanel headerPanel;
	
	public LoginPanel(HeaderPanel headerPanel){
		this.headerPanel = headerPanel;
		validUser = false;
		createUI();
		addListeners();
	}
	
	public JTextField getUsernameTF() {
		return usernameTF;
	}
	public void setUsernameTF(JTextField usernameTF) {
		this.usernameTF = usernameTF;
	}

	public JPasswordField getPasswordTF() {
		return passwordTF;
	}
	public void setPasswordTF(JPasswordField passwordTF) {
		this.passwordTF = passwordTF;
	}
	
	public void reset(){
		usernameTF.setText("");
		passwordTF.setText("");
		setValidUser(false, "");
	}

	private void createUI(){
		FlowLayout layout = new FlowLayout(FlowLayout.TRAILING);
		layout.setHgap(10);
		this.setLayout(layout);
		
		memberNameLabel = LabelUtils.createRegularLabel(LabelText.EMPTY);
		memberNameLabel.setVisible(validUser);
		userNameLabel = LabelUtils.createRegularLabel(LabelText.USERNAME);
		usernameTF = InputUtils.createTextField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(), ToolTipText.USERNAME);
		passwordLabel = LabelUtils.createRegularLabel(LabelText.PASSWORD);
		passwordTF = InputUtils.createPasswordField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.PASSWORD.getDbColumnName(), ToolTipText.PASSWORD);

		this.add(userNameLabel);
		this.add(usernameTF);
		this.add(passwordLabel);
		this.add(passwordTF);
		this.add(memberNameLabel);
	}
	
	public void setValidUser(boolean validUser, String userID){
		this.validUser = validUser;
		this.memberNameLabel.setText(LabelText.WELCOME.getLabelText() + userID);
		this.userNameLabel.setVisible(!validUser);
		this.usernameTF.setVisible(!validUser);
		this.passwordLabel.setVisible(!validUser);
		this.passwordTF.setVisible(!validUser);
		this.memberNameLabel.setVisible(validUser);
	}

	private void addListeners(){
		passwordTF.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(passwordTF)){
			headerPanel.getActionBtn().doClick();
		}
		
	}
}
