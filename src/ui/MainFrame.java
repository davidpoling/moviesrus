package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;

import javax.swing.JFrame;

import model.Admin;
import model.Member;
import queryUtils.SQLConnection;

public class MainFrame extends JFrame{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private final static boolean IS_ADMIN = true;
	private ActionPanel actionPanel;
	private AdminFilterPanel adminFilterPanel;
	private RentalFilterPanel rentalFilterPanel;
	private InventoryDescriptionPanel inventoryInfoPanel;
	private StartUpPanel startUpPanel;
	private SubscriptionPanel subscriptionPanel;
	private TablePanel tablePanel;
	private UserInfoPanel userInfoPanel;
	private BorderLayout layout;
	private Admin admin;
	private Member member;
	
	private SQLConnection dbConn;
	
	public MainFrame() {
		admin = null;
		member = null;
		createUI();
	}

	public SQLConnection getDbConn() {
		return dbConn;
	}
	public void setDbConn(SQLConnection dbConn) {
		this.dbConn = dbConn;
	}

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public SubscriptionPanel getSubscriptionPanel() {
		return subscriptionPanel;
	}
	public void setSubscriptionPanel(SubscriptionPanel subscriptionPanel) {
		this.subscriptionPanel = subscriptionPanel;
	}

	public AdminFilterPanel getAdminFilterPanel() {
		return adminFilterPanel;
	}
	
	public RentalFilterPanel getRentalFilterPanel() {
		return rentalFilterPanel;
	}

	public InventoryDescriptionPanel getInventoryInfoPanel() {
		return inventoryInfoPanel;
	}

	public void setAdminFilterPanel(AdminFilterPanel adminFilterPanel) {
		this.adminFilterPanel = adminFilterPanel;
	}
	
	public ActionPanel getActionPanel(){
		return this.actionPanel;
	}
	
	public Component getCenterPanel(){
		Component panel;
		
		logger.info("Getting CENTER " + layout.getLayoutComponent(this, BorderLayout.CENTER).toString());
		panel = layout.getLayoutComponent(this, BorderLayout.CENTER);
		return panel;
	}

	public TablePanel getTablePanel() {
		return tablePanel;
	}
	public void setTablePanel(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
		revalidate();
		repaint();
	}

	private void createUI(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Dimension frameSize = new Dimension(tk.getScreenSize().width/3*2,
											tk.getScreenSize().height/3*2);
		
		layout = new BorderLayout();
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);
		actionPanel = new ActionPanel(this, false);
		startUpPanel = new StartUpPanel();
		actionPanel.setVisible(false);
		
		this.add(new HeaderPanel(this), BorderLayout.NORTH);
		this.add(startUpPanel, BorderLayout.CENTER);
		this.add(actionPanel, BorderLayout.SOUTH);
		
		this.setMinimumSize(frameSize);
		this.setLocation(tk.getScreenSize().width/5, tk.getScreenSize().height/5);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	private void clearContentArea(){
		if(layout.getLayoutComponent(this, BorderLayout.WEST) != null){
			logger.info("Clearing WEST " + layout.getLayoutComponent(this, BorderLayout.WEST).toString());
			this.remove(layout.getLayoutComponent(this, BorderLayout.WEST));
		}
		if(layout.getLayoutComponent(this, BorderLayout.CENTER) != null){
			if(layout.getLayoutComponent(this, BorderLayout.CENTER).equals(userInfoPanel)){
				userInfoPanel.setFocusTraversalPolicy(null);
			}
			logger.info("Clearing CENTER " + layout.getLayoutComponent(this, BorderLayout.CENTER).toString());
			this.remove(layout.getLayoutComponent(this, BorderLayout.CENTER));
		}
		if(layout.getLayoutComponent(this, BorderLayout.EAST) != null){
			logger.info("Clearing EAST " + layout.getLayoutComponent(this, BorderLayout.EAST).toString());
			this.remove(layout.getLayoutComponent(this, BorderLayout.EAST));
		}
	}
	
	public void setLoggedOutLayout(){
		clearContentArea();
		this.add(startUpPanel, BorderLayout.CENTER);
		actionPanel.setVisible(false);
		adminFilterPanel = null;
		inventoryInfoPanel = null;
		subscriptionPanel = null;
		userInfoPanel = null;
		rentalFilterPanel = null;
		tablePanel = null;
		userInfoPanel = null;
		updatePanel();
	}
	
	public void setMemberLayout(Member member){
		this.member = member;
		clearContentArea();
		subscriptionPanel = new SubscriptionPanel(member);
		userInfoPanel = new UserInfoPanel(this, member);

		this.add(subscriptionPanel, BorderLayout.WEST);
		this.add(userInfoPanel, BorderLayout.CENTER);
		setButtonActions(!IS_ADMIN);
		updatePanel();
		logger.info("member panel set" + subscriptionPanel.toString() + " " + userInfoPanel.toString());
	}
	
	public void addMemberLayout(Member member){
		setMemberLayout(member);
		setButtonActions(IS_ADMIN);
		updatePanel();
	}
	
	public void setRentalLayout(Member member){
		clearContentArea();
		if(rentalFilterPanel == null){
			rentalFilterPanel = new RentalFilterPanel(this, member);
		}else{
			rentalFilterPanel.clearSelections();
		}
		rentalFilterPanel.setContentType(member.getMembership().getContent());
		tablePanel = new TablePanel(this);
		this.add(rentalFilterPanel, BorderLayout.WEST);
		this.add(tablePanel, BorderLayout.CENTER);
		setButtonActions(!IS_ADMIN);
		updatePanel();
	}
	
	public void setAdminLayout(){
		clearContentArea();
		if(adminFilterPanel == null){
			adminFilterPanel = new AdminFilterPanel(this);
		}else{
			adminFilterPanel.toggleFiltersVisible(true);
			adminFilterPanel.clearSelections();
		}
		inventoryInfoPanel = null;
		tablePanel = new TablePanel(this);
		this.add(adminFilterPanel, BorderLayout.WEST);
		this.add(tablePanel, BorderLayout.CENTER);
		setButtonActions(IS_ADMIN);
		updatePanel();
	}
	
	public void setInventoryLayout(){
		clearContentArea();
		adminFilterPanel = new AdminFilterPanel(this);
		
		if(inventoryInfoPanel == null){
			inventoryInfoPanel = new InventoryDescriptionPanel(this);
		}
		
		this.add(adminFilterPanel, BorderLayout.WEST);
		this.add(inventoryInfoPanel, BorderLayout.CENTER);
		adminFilterPanel.clearSelections();
		adminFilterPanel.toggleFiltersVisible(false);
		setButtonActions(IS_ADMIN);
		updatePanel();
	}
	
	public void setButtonActions(boolean admin){
		actionPanel.setVisible(true);
		if(admin){
			actionPanel.setAdminButtonsVisible(admin);
		}else{
			actionPanel.setMemberButtonsVisible(!admin);
		}
	}
	
	private void updatePanel(){
		this.revalidate();
		this.repaint();
	}
}