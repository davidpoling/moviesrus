package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Member;
import uiUtils.ButtonUtils;
import uiUtils.InputUtils;
import uiUtils.LabelUtils;
import utils.AppUtils;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsProject.ButtonText;
import enumsProject.LabelText;
import enumsProject.ToolTipText;
import enumsProject.UISize;
import enumsProject.WarningText;

public class UserInfoPanel extends JPanel
							implements ActionListener, DocumentListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private final static boolean IS_EDITABLE = true;
	
	private JLabel headingLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel streetLabel;
	private JLabel apartmentNumberLabel;
	private JLabel cityLabel;
	private JLabel stateLabel;
	private JLabel postalLabel;
	private JLabel eMailLabel;
	private JLabel passwordLabel;
	private JLabel requiredLabel;
	private JTextField firstNameTF;
	private JTextField lastNameTF;
	private JTextField streetTF;
	private JTextField aptNumTF;
	private JTextField cityTF;
	private JComboBox<String> stateCombo;
	private JTextField postalTF;
	private JTextField eMailTF;
	private JPasswordField passwordTF;
	private JLabel addressLabel;
	private JRadioButton shippingRB;
	private JRadioButton billingRB;
	private JRadioButton bothRB;
	private JLabel requiredAddressLabel;
	private JButton saveBtn;
	private JButton saveAddBtn;
	
	private ButtonGroup addressButtonGrp;
	
	private MainFrame mainFrame;
	private Member member;
	private boolean existingMember;

	public UserInfoPanel(MainFrame mainFrame, Member member) {
		this.mainFrame = mainFrame;
		this.member = member;
		logger.info("Getting UserInfoPanel panel");
		createUI();
		groupButtons();
		addListeners();
		addTraversal();
		logger.info("member = " + member.toString());
		if(member.getEmail() != null){
			setCurrentMember(member);
			existingMember = true;
		}else{
			this.member = new Member();
			existingMember = false;
		}
	}
	
	private void createUI(){
		headingLabel = LabelUtils.createHeadingLabel(LabelText.USERINFO);
		firstNameLabel = LabelUtils.createRegularLabel(LabelText.FIRSTNAME);
		lastNameLabel = LabelUtils.createRegularLabel(LabelText.LASTNAME);
		streetLabel = LabelUtils.createRegularLabel(LabelText.STREET);
		apartmentNumberLabel = LabelUtils.createRegularLabel(LabelText.APTNUM);
		cityLabel = LabelUtils.createRegularLabel(LabelText.CITY);
		stateLabel = LabelUtils.createRegularLabel(LabelText.STATE);
		postalLabel = LabelUtils.createRegularLabel(LabelText.ZIPCODE);
		eMailLabel = LabelUtils.createRegularLabel(LabelText.EMAIL);
		passwordLabel = LabelUtils.createRegularLabel(LabelText.PASSWORD);
		requiredLabel = LabelUtils.createRegularLabel(LabelText.EMPTY);

		firstNameTF = InputUtils.createInputField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.FIRSTNAME.getDbColumnName(), ToolTipText.FIRST_NAME);
		lastNameTF = InputUtils.createInputField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.SURNAME.getDbColumnName(), ToolTipText.SURNAME);
		streetTF = InputUtils.createInputField(UISize.FIELDWIDTH_LG.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.STREET.getDbColumnName(), ToolTipText.STREET);
		aptNumTF = InputUtils.createInputField(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.APT_NUM.getDbColumnName(), ToolTipText.APT_NUM);
		cityTF = InputUtils.createInputField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.CITY.getDbColumnName(), ToolTipText.CITY);
		stateCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.STATE, mainFrame.getDbConn(),
				DBTable.STATE, DBColumn.TITLE, !IS_EDITABLE, null, null);
		postalTF = InputUtils.createInputField(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.POSTAL.getDbColumnName(), ToolTipText.POSTAL);
		eMailTF = InputUtils.createInputField(UISize.FIELDWIDTH_LG.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.EMAIL.getDbColumnName(), ToolTipText.EMAIL);
		passwordTF = InputUtils.createPasswordField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.PASSWORD.getDbColumnName(), ToolTipText.PASSWORD);
		
		addressLabel = LabelUtils.createHeadingLabel(LabelText.ADDRESS_TYPE);
		billingRB = ButtonUtils.createRadioButton(LabelText.BILLING, ToolTipText.ADDRESS_BILLING);
		shippingRB = ButtonUtils.createRadioButton(LabelText.SHIPPING, ToolTipText.ADDRESS_SHIPPING);
		bothRB = ButtonUtils.createRadioButton(LabelText.BOTH, ToolTipText.ADDRESS_BOTH);
		requiredAddressLabel = LabelUtils.createRegularLabel(LabelText.EMPTY);
		
		saveBtn = ButtonUtils.createButton(ButtonText.SAVE, ToolTipText.SAVE_MEMBER);
		saveAddBtn = ButtonUtils.createButton(ButtonText.SAVE_ADD, ToolTipText.SAVE_ADD);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(firstNameLabel)
						.addComponent(lastNameLabel)
						.addComponent(streetLabel)
						.addComponent(apartmentNumberLabel)
						.addComponent(cityLabel)
						.addComponent(stateLabel)
						.addComponent(postalLabel)
						.addComponent(eMailLabel)
						.addComponent(passwordLabel)
						.addComponent(requiredLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(headingLabel)
						.addComponent(firstNameTF)
						.addComponent(lastNameTF)
						.addComponent(streetTF)
						.addComponent(aptNumTF)
						.addComponent(cityTF)
						.addComponent(stateCombo)
						.addComponent(postalTF)
						.addComponent(eMailTF)
						.addComponent(passwordTF)
						.addComponent(saveBtn)
						.addComponent(saveAddBtn))
				.addGap(UISize.FIELDWIDTH_SM.getSize())
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(addressLabel)
						.addComponent(billingRB)
						.addComponent(shippingRB)
						.addComponent(bothRB)
						.addComponent(requiredAddressLabel))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(headingLabel)
						.addComponent(addressLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(firstNameLabel)
						.addComponent(firstNameTF)
						.addComponent(billingRB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lastNameLabel)
						.addComponent(lastNameTF)
						.addComponent(shippingRB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(streetLabel)
						.addComponent(streetTF)
						.addComponent(bothRB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(apartmentNumberLabel)
						.addComponent(aptNumTF)
						.addComponent(requiredAddressLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cityLabel)
						.addComponent(cityTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(stateLabel)
						.addComponent(stateCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(postalLabel)
						.addComponent(postalTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(eMailLabel)
						.addComponent(eMailTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(requiredLabel)
						.addComponent(saveBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(saveAddBtn))
				);
		
		this.setVisible(true);
	}

	private void groupButtons(){
		addressButtonGrp = new ButtonGroup();
		addressButtonGrp.add(billingRB);
		addressButtonGrp.add(shippingRB);
		addressButtonGrp.add(bothRB);
	}
	
	private void addListeners(){
		stateCombo.addActionListener(this);
		billingRB.addActionListener(this);
		shippingRB.addActionListener(this);
		bothRB.addActionListener(this);
		passwordTF.getDocument().addDocumentListener(this);
		saveBtn.addActionListener(this);
		saveAddBtn.addActionListener(this);
	}
	
	private void addTraversal(){
		List<Component> components = new ArrayList<Component>();
		components.add(firstNameTF);
		components.add(lastNameTF);
		components.add(streetTF);
		components.add(aptNumTF);
		components.add(cityTF);
		components.add(stateCombo);
		components.add(postalTF);
		components.add(eMailTF);
		components.add(passwordTF);
		components.add(billingRB);
		components.add(shippingRB);
		components.add(bothRB);
		components.add(saveBtn);
		components.add(saveAddBtn);
		setFocusCycleRoot(true);
		setFocusTraversalPolicy(new InfoTraversalPolicy(this, components));
	}
	
	public boolean isExistingMember() {
		return existingMember;
	}

	private void setCurrentMember(Member member){
		logger.info(member.getMembership().getContent());
		if(member.getMembership().getContent().equals("Movie")){
			mainFrame.getSubscriptionPanel().getMoviesBtn().setSelected(true);
		}else if(member.getMembership().getContent().equals("Game")){
			mainFrame.getSubscriptionPanel().getGamesBtn().setSelected(true);
		}else if(member.getMembership().getContent().equals("Both")){
			mainFrame.getSubscriptionPanel().getBothBtn().setSelected(true);	
		}
		logger.info(member.getMembership().getQuantity() + "");
		if(member.getMembership().getQuantity() == 1){
			mainFrame.getSubscriptionPanel().getLevelOneBtn().setSelected(true);
		}else if(member.getMembership().getQuantity() == 2){
			mainFrame.getSubscriptionPanel().getLevelTwoBtn().setSelected(true);
		}else if(member.getMembership().getQuantity() == 3){
			mainFrame.getSubscriptionPanel().getLevelThreeBtn().setSelected(true);
		}
		firstNameTF.setText(member.getFirstName());
		lastNameTF.setText(member.getSurName());
		streetTF.setText(member.getAddress().getStreet());
		aptNumTF.setText(member.getAddress().getApartment());
		cityTF.setText(member.getAddress().getCity());
		stateCombo.setSelectedItem(member.getAddress().getState());
		postalTF.setText(member.getAddress().getPostal() + "");
		eMailTF.setText(member.getEmail());
		
		logger.info(member.getMembership().getContent());
		if(member.getAddress().getType().equals("Billing")){
			shippingRB.setSelected(true);
		}else if(member.getAddress().getType().equals("Shipping")){
			billingRB.setSelected(true);
		}else if(member.getAddress().getType().equals("Both")){
			bothRB.setSelected(true);	
		}
		saveAddBtn.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();
		if(eventSource.equals(firstNameTF)){
		}
		if(eventSource.equals(stateCombo)){
			member.getAddress().setState(stateCombo.getSelectedItem().toString());
		}else if(eventSource.equals(saveBtn)){
			if(isValidInput()){
				if(!existingMember){
					setMemberDetails();
					member.createMember(mainFrame.getDbConn());
				}else{			
					setMemberDetails();
					member.updateMember(mainFrame.getDbConn());
				}
				this.mainFrame.getActionPanel().getHomeBtn().doClick();
			}
		}else if(eventSource.equals(saveAddBtn)){
			if(isValidInput()){
				setMemberDetails();
				member.createMember(mainFrame.getDbConn());
				resetFields();
				this.member = new Member();
			}
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		member.setPasswordUpdated(true);
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		member.setPasswordUpdated(true);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isValidInput(){
		boolean isValid = true;
		
		if(!mainFrame.getSubscriptionPanel().hasRequired()){
			isValid = false;
		}
		if(firstNameTF.getText().trim().length() < 2){
			isValid = false;
			toggleValidColors(firstNameLabel, isValid);
		}else{
			toggleValidColors(firstNameLabel, true);
		}
		if(lastNameTF.getText().trim().length() < 2){
			isValid = false;
			toggleValidColors(lastNameLabel, isValid);
		}else{
			toggleValidColors(lastNameLabel, true);
		}
		if(streetTF.getText().trim().length() < 8){
			isValid = false;
			toggleValidColors(streetLabel, isValid);
		}else{
			toggleValidColors(streetLabel, true);
		}
		if(cityTF.getText().trim().length() < 3){
			isValid = false;
			toggleValidColors(cityLabel, isValid);
		}else{
			toggleValidColors(cityLabel, true);
		}
		if(stateCombo.getSelectedIndex() == 0){
			isValid = false;
			toggleValidColors(stateLabel, isValid);
		}else{
			toggleValidColors(stateLabel, true);
		}
		if(postalTF.getText().trim().length() < 5 || postalTF.getText().trim().length() > 9){
			isValid = false;
			toggleValidColors(postalLabel, isValid);
		}else{
			toggleValidColors(postalLabel, true);
		}
		if(eMailTF.getText().trim().length() < 5 && !eMailTF.getText().contains("@")){
			isValid = false;
			toggleValidColors(eMailLabel, isValid);
		}else{
			toggleValidColors(eMailLabel, true);
		}
		if(passwordTF.getPassword().length < 8 && !existingMember){
			isValid = false;
			toggleValidColors(passwordLabel, isValid);
		}else{
			toggleValidColors(passwordLabel, true);
		}
		if(addressButtonGrp.getSelection() == null){
			isValid = false;
			requiredAddressLabel.setText(LabelText.REQUIRED.getLabelText());
			toggleValidColors(requiredAddressLabel, isValid);
		}else{
			toggleValidColors(requiredAddressLabel, true);
			requiredAddressLabel.setText("");
		}
		if(!isValid){
			requiredLabel.setText(LabelText.REQUIRED.getLabelText());
			toggleValidColors(requiredLabel, isValid);
		}else{
			requiredLabel.setText("");
		}
		return isValid;
	}
	
	private void toggleValidColors(JComponent comp, boolean isValid){
		if(isValid){
			comp.setForeground(Color.BLACK);
		}else{
			comp.setForeground(Color.RED);
		}
	}
	
	private void setMemberDetails(){
		String addressType = "Both";
		int zip = 0;
		
		member.getMembership().setContent(mainFrame.getSubscriptionPanel().getMember().getMembership().getContent());
		member.getMembership().setQuantity(mainFrame.getSubscriptionPanel().getMember().getMembership().getQuantity());
		member.setFirstName(firstNameTF.getText().trim());
		member.setSurName(lastNameTF.getText().trim());
		
		if(addressButtonGrp.getSelection().equals(billingRB)){
			addressType = new String("Billing");
		}else if(addressButtonGrp.getSelection().equals(shippingRB)){
			addressType = new String("Shipping");
		}
		
		member.getAddress().setType(addressType);
		member.getAddress().setStreet(streetTF.getText().trim());
		member.getAddress().setApartment(aptNumTF.getText().trim());
		member.getAddress().setCity(cityTF.getText().trim());
		try{
			zip = Integer.parseInt(postalTF.getText().trim());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null, WarningText.INPUT_NUM.getText(),
					WarningText.INPUT.getText(), JOptionPane.ERROR_MESSAGE);
		}
		member.getAddress().setPostal(zip);
		member.setEmail(eMailTF.getText().trim());
		char[] unhashed = passwordTF.getPassword();
		member.setPassword(AppUtils.hashPassword(new String(unhashed).getBytes()));

		logger.info(member.toString());
	}
	
	private void resetFields(){
		firstNameTF.setText("");
		lastNameTF.setText("");
		streetTF.setText("");
		aptNumTF.setText("");
		cityTF.setText("");
		stateCombo.setSelectedIndex(0);
		postalTF.setText("");
		eMailTF.setText("");
		passwordTF.setText("");
	}

}
