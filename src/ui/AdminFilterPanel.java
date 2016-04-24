package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Media;
import queryUtils.DBQueries;
import uiUtils.ButtonUtils;
import uiUtils.LabelUtils;
import utils.AppUtils;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsDB.MediaType;
import enumsProject.LabelText;
import enumsProject.ToolTipText;

public class AdminFilterPanel extends JPanel
							implements ActionListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private JLabel inventoryTypeLabel;
	private JRadioButton moviesBtn;
	private JRadioButton gamesBtn;
	private JRadioButton bothBtn;
	private ButtonGroup typeButtonGrp;
	private JLabel filterLabel;
	private JLabel requiredTypeLbl;
	private JRadioButton previousDayBtn;
	private JRadioButton topTenBtn;
	private JRadioButton needShippedBtn;
	private JRadioButton rentedBtn;
	private ButtonGroup filterButtonGrp;
	private JLabel membersLabel;
	private JRadioButton membersBtn;
	
	private MainFrame mainFrame;
	
	public AdminFilterPanel() {
		logger.info("Getting admin filter panel");
		createUI();
		addListeners();
		groupButtons();
	}
	
	public AdminFilterPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		logger.info("Getting admin filter panel");
		createUI();
		addListeners();
		groupButtons();
		mainFrame.getActionPanel().enableAdminActionButtons(false);
		requiredTypeLbl.setVisible(false);
	}
	
	public JLabel getRequiredTypeLbl() {
		return requiredTypeLbl;
	}

	public JRadioButton getNeedShippedBtn() {
		return needShippedBtn;
	}

	private void createUI(){
		inventoryTypeLabel = LabelUtils.createHeadingLabel(LabelText.INVENTORY);
		moviesBtn = ButtonUtils.createRadioButton(LabelText.MOVIES, ToolTipText.MOVIE);
		gamesBtn = ButtonUtils.createRadioButton(LabelText.GAMES, ToolTipText.GAMES);
		bothBtn = ButtonUtils.createRadioButton(LabelText.BOTH, ToolTipText.BOTH);
		requiredTypeLbl = LabelUtils.createRegularLabel(LabelText.REQUIRED);
		requiredTypeLbl.setForeground(Color.RED);
		
		filterLabel = LabelUtils.createHeadingLabel(LabelText.SEARCH);
		previousDayBtn = ButtonUtils.createRadioButton(LabelText.PREVIOUS, ToolTipText.PAST24);
		topTenBtn = ButtonUtils.createRadioButton(LabelText.TOPRENTALS, ToolTipText.TOP10);
		needShippedBtn = ButtonUtils.createRadioButton(LabelText.NEEDSHIPPED, ToolTipText.NEEDSHIPPED);
		rentedBtn = ButtonUtils.createRadioButton(LabelText.RENTED, ToolTipText.CURRENT);
		
		membersLabel = LabelUtils.createHeadingLabel(LabelText.MEMBERSHIP);
		membersBtn = ButtonUtils.createRadioButton(LabelText.MEMBERS, ToolTipText.MEMBERS);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		this.add(inventoryTypeLabel);
		this.add(moviesBtn);
		this.add(gamesBtn);
		this.add(bothBtn);
		this.add(requiredTypeLbl);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(filterLabel);
		this.add(previousDayBtn);
		this.add(topTenBtn);
		this.add(needShippedBtn);
		this.add(rentedBtn);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(membersLabel);
		this.add(membersBtn);
	}
	
	private void groupButtons(){
		typeButtonGrp = new ButtonGroup();
		typeButtonGrp.add(moviesBtn);
		typeButtonGrp.add(gamesBtn);
		typeButtonGrp.add(bothBtn);
		
		filterButtonGrp = new ButtonGroup();
		filterButtonGrp.add(previousDayBtn);
		filterButtonGrp.add(topTenBtn);
		filterButtonGrp.add(needShippedBtn);
		filterButtonGrp.add(rentedBtn);
		enableFilterButtons(false);
	}
	
	private void addListeners(){
		moviesBtn.addActionListener(this);
		gamesBtn.addActionListener(this);
		bothBtn.addActionListener(this);
		previousDayBtn.addActionListener(this);
		topTenBtn.addActionListener(this);
		needShippedBtn.addActionListener(this);
		rentedBtn.addActionListener(this);
		membersBtn.addActionListener(this);
	}
	
	public void clearSelections(){
		typeButtonGrp.clearSelection();
		filterButtonGrp.clearSelection();
	}
	
	private void enableFilterButtons(boolean enabled){
		previousDayBtn.setEnabled(enabled);
		topTenBtn.setEnabled(enabled);
		needShippedBtn.setEnabled(enabled);
		rentedBtn.setEnabled(enabled);
	}
	
	public void toggleFiltersVisible(boolean enabled){
		bothBtn.setVisible(enabled);
		filterLabel.setVisible(enabled);
		previousDayBtn.setVisible(enabled);
		topTenBtn.setVisible(enabled);
		needShippedBtn.setVisible(enabled);
		rentedBtn.setVisible(enabled);
		membersLabel.setVisible(enabled);
		membersBtn.setVisible(enabled);
	}
	
	public DBTable getSelectedMedia(){	
		if(moviesBtn.isSelected()){
			return DBTable.MOVIE;
		}else if(gamesBtn.isSelected()){
			return DBTable.GAME;
		}
		return DBTable.MEDIA;
	}
	
	public boolean hasRequired(Media media){
		boolean hasRequired = false;
		if(!media.getMediaType().equals(MediaType.MEDIA)){
			hasRequired = true;
		}
		requiredTypeLbl.setVisible(!hasRequired);
		return hasRequired;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();
		if(eventSource.equals(moviesBtn) || eventSource.equals(gamesBtn) || eventSource.equals(bothBtn)){
			if(mainFrame.getInventoryInfoPanel() == null){
				membersBtn.setSelected(false);
				enableFilterButtons(true);
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAllType(getSelectedMedia(), null));
				
				if(previousDayBtn.isSelected()){
					AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchLast24(getSelectedMedia()));
				}else if(topTenBtn.isSelected()){
					AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchTopTen(getSelectedMedia()));
				}else if(needShippedBtn.isSelected()){
					AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchRentedInfo(getSelectedMedia(), DBColumn.SHIPPED));
					enableShip(true);
				}else if(rentedBtn.isSelected()){
					AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchRentedInfo(getSelectedMedia(), DBColumn.RETURNED));
					enableShip(false);
				}
			}else{
				if(moviesBtn.isSelected()){
					mainFrame.getInventoryInfoPanel().getMedia().setMediaType(MediaType.MOVIE);
				}else{
					mainFrame.getInventoryInfoPanel().getMedia().setMediaType(MediaType.GAME);
				}
			}
		}
		if(eventSource.equals(membersBtn)){
			clearSelections();
			enableFilterButtons(false);
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMembers());
		}
		
		if (eventSource.equals(previousDayBtn)){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchLast24(getSelectedMedia()));
		}else if (eventSource.equals(topTenBtn)){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchTopTen(getSelectedMedia()));
		}else if (eventSource.equals(needShippedBtn)){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchRentedInfo(getSelectedMedia(), DBColumn.SHIPPED));
			enableShip(true);
		}else if (eventSource.equals(rentedBtn)){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchRentedInfo(getSelectedMedia(), DBColumn.RETURNED));
			enableShip(false);
		}
	}
	
	private void enableShip(boolean enable){
		mainFrame.getActionPanel().getShippingBtn().setEnabled(enable);
		mainFrame.getActionPanel().getReturnBtn().setEnabled(!enable);
	}
}
