package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Member;
import uiUtils.ButtonUtils;
import enumsProject.ButtonText;
import enumsProject.TableAction;
import enumsProject.ToolTipText;

public class ActionPanel extends JPanel
						implements ActionListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private MainFrame mainFrame;
	private JButton homeBtn;
	private JButton addUserBtn;
	private JButton addInventoryBtn;
	private JButton deleteBtn;
	private JButton rentBtn;
	private JButton returnBtn;
	private JButton shippingBtn;
	private boolean isAdmin;
	
	public ActionPanel(MainFrame mainFrame, boolean isAdmin){
		this.isAdmin = isAdmin;
		this.mainFrame = mainFrame;
		createUI();
		addListeners();
		buttonsVisible(false);
	}
	
	public JButton getHomeBtn() {
		return homeBtn;
	}
	
	public JButton getAddUserBtn() {
		return addUserBtn;
	}

	public JButton getAddInventoryBtn() {
		return addInventoryBtn;
	}

	public JButton getDeleteBtn() {
		return deleteBtn;
	}

	public JButton getRentBtn() {
		return rentBtn;
	}

	public JButton getReturnBtn() {
		return returnBtn;
	}

	public JButton getShippingBtn() {
		return shippingBtn;
	}

	private void createUI(){
		homeBtn = ButtonUtils.createButton(ButtonText.HOME, ToolTipText.HOME);
		addUserBtn = ButtonUtils.createButton(ButtonText.ADD_USER, ToolTipText.ADD_USER);
		addInventoryBtn = ButtonUtils.createButton(ButtonText.ADDINVENTORY, ToolTipText.ADD_INVENTORY);
		deleteBtn = ButtonUtils.createButton(ButtonText.DELETE, ToolTipText.DELETE);
		rentBtn = ButtonUtils.createButton(ButtonText.RENT, ToolTipText.RENT);
		returnBtn = ButtonUtils.createButton(ButtonText.RETURNED,ToolTipText.RETURN);
		shippingBtn = ButtonUtils.createButton(ButtonText.SHIP, ToolTipText.SHIPPING);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layout);
	
		this.add(Box.createRigidArea(new Dimension(20,10)));
		this.add(homeBtn);
		this.add(Box.createRigidArea(new Dimension(20,10)));
		this.add(addUserBtn);
		this.add(Box.createRigidArea(new Dimension(20,30)));
		this.add(rentBtn);
		this.add(Box.createRigidArea(new Dimension(20,10)));
		this.add(addInventoryBtn);
		this.add(Box.createRigidArea(new Dimension(20,10)));
		this.add(shippingBtn);
		this.add(Box.createRigidArea(new Dimension(40,10)));
		this.add(deleteBtn);
		this.add(Box.createRigidArea(new Dimension(20,10)));
		this.add(returnBtn);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setVisible(true);
	}
	
	private void buttonsVisible(boolean visible){
		homeBtn.setVisible(visible);
		addUserBtn.setVisible(visible);
		rentBtn.setVisible(visible);
		addInventoryBtn.setVisible(visible);
		shippingBtn.setVisible(visible);
		deleteBtn.setVisible(visible);
		returnBtn.setVisible(visible);
	}
	
	public void setAdminButtonsVisible(boolean visible){
		homeBtn.setVisible(visible);
		addUserBtn.setVisible(visible);
		rentBtn.setVisible(!visible);
		addInventoryBtn.setVisible(visible);
		shippingBtn.setVisible(visible);
		deleteBtn.setVisible(visible);
		returnBtn.setVisible(visible);
	}
	
	public void setMemberButtonsVisible(boolean visible){
		homeBtn.setVisible(visible);
		addUserBtn.setVisible(!visible);
		rentBtn.setVisible(visible);
		addInventoryBtn.setVisible(!visible);
		shippingBtn.setVisible(!visible);
		deleteBtn.setVisible(!visible);
		returnBtn.setVisible(!visible);
	}
	
	public void enableAdminActionButtons(boolean enable){
		shippingBtn.setEnabled(enable);
		returnBtn.setEnabled(enable);
	}
	
	private void addListeners(){
		homeBtn.addActionListener(this);
		addUserBtn.addActionListener(this);
		rentBtn.addActionListener(this);
		addInventoryBtn.addActionListener(this);
		shippingBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		returnBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();
		Member member;
		
		if(eventSource.equals(homeBtn)){
			if(mainFrame.getAdmin() != null){
				mainFrame.getAdminFilterPanel().getRequiredTypeLbl().setVisible(false);
				mainFrame.setAdminLayout();
			}else{
				mainFrame.setRentalLayout(mainFrame.getMember());
			}
		}else if(eventSource.equals(addUserBtn)){
			member = new Member();
			mainFrame.addMemberLayout(member);
		}else if (eventSource.equals(addInventoryBtn)){
			this.mainFrame.setInventoryLayout();
		}else if(mainFrame.getTablePanel() != null){
			if(hasSelection()){
				if(eventSource.equals(rentBtn)){
					mainFrame.getTablePanel().updateSelected(TableAction.RENT);
				}else if (eventSource.equals(shippingBtn)){	
					mainFrame.getTablePanel().updateSelected(TableAction.SHIP);
				}else if (eventSource.equals(deleteBtn)){
					mainFrame.getTablePanel().updateSelected(TableAction.DELETE);
				}else if (eventSource.equals(returnBtn)){
					mainFrame.getTablePanel().updateSelected(TableAction.RETURN);
				}
			}
		}
	}
	
	private boolean hasSelection(){
		if(mainFrame.getTablePanel().getSelectedRows() != null || !mainFrame.getTablePanel().getResultTable().isVisible()){
			return true;
		}
		JOptionPane.showMessageDialog(mainFrame, "Please select item(s).", "Selection Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}
}
