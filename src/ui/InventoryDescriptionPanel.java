package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Actor;
import model.Award;
import model.Director;
import model.Media;
import uiUtils.ButtonUtils;
import uiUtils.InputUtils;
import uiUtils.LabelUtils;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsDB.MediaType;
import enumsProject.ButtonText;
import enumsProject.LabelText;
import enumsProject.ToolTipText;
import enumsProject.UISize;

public class InventoryDescriptionPanel extends JPanel
									implements ActionListener, ChangeListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private final static boolean IS_EDITABLE = true;

	private JLabel headingLabel;
	private JLabel titleLabel;
	private JLabel actorLabel;
	private JLabel directorLabel;
	private JLabel platformLabel;
	private JLabel versionLabel;
	private JLabel genreLabel;
	private JLabel awardLabel;
	private JLabel honorLabel;
	private JLabel releaseDateLabel;
	private JLabel sequelLabel;
	private JLabel requiredLabel;
	private JLabel requiredDetailsLabel;

	private JComboBox<String> titleCombo;
	private JComboBox<String> platformCombo;
	private JComboBox<String> versionCombo;
	private JComboBox<String> genreCombo;
	private JComboBox<String> awardCombo;
	private JComboBox<String> categoryCombo;
	private JComboBox<String> releaseYearCombo;
	private JComboBox<String> sequelCombo;
	
	private JSpinner stockSpinner;
	
	private JButton addDirectorBtn;
	private JButton addActorBtn;
	private JButton addAwardBtn;
	private JButton addCastBtn;
	private JButton saveMediaBtn;
	private JButton saveAddBtn;
	
	private JLabel castHeadingLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel roleLabel;
	private JLabel stockLabel;

	private JTextField firstNameTF;
	private JTextField lastNameTF;
	private JComboBox<String> roleCombo;
		
	private MainFrame mainFrame;
	private boolean detailsActive;
	Connection dbConn;
	Media media;
	
	public InventoryDescriptionPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.media = new Media();
		logger.info("Getting InventoryTypePanel panel");
		createUI();
		addListeners();
		addTraversal();
		detailsActive = false;
		toggleNameFieldsVisible(detailsActive);
	}
	
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	
	private void createUI(){
		headingLabel = LabelUtils.createHeadingLabel(LabelText.DESCRIPTION);
		titleLabel = LabelUtils.createRegularLabel(LabelText.TITLE);
		actorLabel = LabelUtils.createRegularLabel(LabelText.ACTOR);
		directorLabel = LabelUtils.createRegularLabel(LabelText.DIRECTOR);
		platformLabel = LabelUtils.createRegularLabel(LabelText.PLATFORM);
		versionLabel = LabelUtils.createRegularLabel(LabelText.VERSION);
		genreLabel = LabelUtils.createRegularLabel(LabelText.GENRE);
		awardLabel = LabelUtils.createRegularLabel(LabelText.AWARDS);
		honorLabel = LabelUtils.createRegularLabel(LabelText.HONOR);
		releaseDateLabel = LabelUtils.createRegularLabel(LabelText.RELEASEDATE);
		sequelLabel = LabelUtils.createRegularLabel(LabelText.SEQUEL_TITLE);
		requiredLabel = LabelUtils.createRegularLabel(LabelText.EMPTY);
		requiredDetailsLabel = LabelUtils.createRegularLabel(LabelText.EMPTY);

		titleCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_XL.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_TITLE, mainFrame.getDbConn(),
				DBTable.MEDIA, DBColumn.TITLE, IS_EDITABLE, null, null);
		platformCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_PLATFORM, mainFrame.getDbConn(),
				DBTable.PLATFORM, DBColumn.CONSOLE, IS_EDITABLE, null, null);
		versionCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_VERSION, mainFrame.getDbConn(),
				DBTable.PLATFORM, DBColumn.VERSION, IS_EDITABLE, null, null);
		genreCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_XL.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_GENRE, mainFrame.getDbConn(),
				DBTable.GENRE, DBColumn.GENRE, IS_EDITABLE, null, null);
		awardCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_AWARD, mainFrame.getDbConn(),
				DBTable.AWARD, DBColumn.HONOR, IS_EDITABLE, null, null);
		categoryCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_XL.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_AWARD, mainFrame.getDbConn(),
				DBTable.AWARD, DBColumn.CATEGORY, IS_EDITABLE, null, null);
		releaseYearCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_AWARD, mainFrame.getDbConn(),
				DBTable.MEDIA, DBColumn.DATE, IS_EDITABLE, null, null);
		sequelCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_XL.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_SEQUEL, mainFrame.getDbConn(),
				DBTable.MEDIA, DBColumn.TITLE, !IS_EDITABLE, null, null);
		saveMediaBtn = ButtonUtils.createButton(ButtonText.SAVE, ToolTipText.SAVE_TITLE);
		saveAddBtn = ButtonUtils.createButton(ButtonText.SAVE_ADD, ToolTipText.SAVE_ADD);
		
		addActorBtn = ButtonUtils.createButton(ButtonText.ADD_ACTOR, ToolTipText.ADD_ACTOR);
		addDirectorBtn = ButtonUtils.createButton(ButtonText.ADD_DIRECTOR, ToolTipText.ADD_DIRECTOR);
		addAwardBtn = ButtonUtils.createButton(ButtonText.ADD_AWARD, ToolTipText.ADD_AWARD);

		castHeadingLabel = LabelUtils.createHeadingLabel(LabelText.EMPTY);
		firstNameLabel = LabelUtils.createRegularLabel(LabelText.FIRSTNAME);
		lastNameLabel = LabelUtils.createRegularLabel(LabelText.LASTNAME);
		
		roleLabel = LabelUtils.createRegularLabel(LabelText.ROLE);
		roleCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_SM.getSize(), UISize.FIELDHEIGHT.getSize(),
				ToolTipText.ADD_ROLE, mainFrame.getDbConn(),
				DBTable.MEDIA_ACTOR, DBColumn.ROLE, IS_EDITABLE, null, null);

		firstNameTF = InputUtils.createInputField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.FIRSTNAME.getDbColumnName(), ToolTipText.FIRST_NAME);
		lastNameTF = InputUtils.createInputField(UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDHEIGHT.getSize(),
				DBColumn.SURNAME.getDbColumnName(), ToolTipText.SURNAME);
		
		addCastBtn = ButtonUtils.createButton(ButtonText.ADD_CAST, ToolTipText.ADD_CAST);
		stockSpinner = new JSpinner();
		stockSpinner.setToolTipText(ToolTipText.STOCK.getToolTipText());
		stockSpinner.setMaximumSize(new Dimension(UISize.FIELDWIDTH_MD.getSize(),UISize.FIELDHEIGHT.getSize()));		
		SpinnerNumberModel numberModel = new SpinnerNumberModel();
		numberModel.setMinimum(0);
		numberModel.setMaximum(999);
		stockSpinner.setModel(numberModel);
		
		stockLabel = LabelUtils.createRegularLabel(LabelText.STOCK);


		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(titleLabel)
						.addComponent(directorLabel)
						.addComponent(actorLabel)
						.addComponent(platformLabel)
						.addComponent(versionLabel)
						.addComponent(genreLabel)
						.addComponent(stockLabel)
						.addComponent(awardLabel)
						.addComponent(honorLabel)
						.addComponent(releaseDateLabel)
						.addComponent(sequelLabel)
						.addComponent(requiredLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(headingLabel)
						.addComponent(titleCombo)
						.addComponent(addDirectorBtn)
						.addComponent(addActorBtn)
						.addComponent(platformCombo)
						.addComponent(versionCombo)
						.addComponent(genreCombo)
						.addComponent(stockSpinner)
						.addComponent(awardCombo)
						.addComponent(categoryCombo)
						.addComponent(releaseYearCombo)
						.addComponent(sequelCombo)
						.addComponent(saveMediaBtn)
						.addComponent(saveAddBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(addAwardBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(firstNameLabel)
						.addComponent(lastNameLabel)
						.addComponent(roleLabel)
						.addComponent(requiredDetailsLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(castHeadingLabel)
						.addComponent(firstNameTF)
						.addComponent(lastNameTF)
						.addComponent(roleCombo)
						.addComponent(addCastBtn))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(headingLabel)
						.addComponent(castHeadingLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(titleLabel)
						.addComponent(titleCombo)
						.addComponent(firstNameLabel)
						.addComponent(firstNameTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(directorLabel)
						.addComponent(addDirectorBtn)
						.addComponent(lastNameLabel)
						.addComponent(lastNameTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(actorLabel)
						.addComponent(addActorBtn)
						.addComponent(roleLabel)
						.addComponent(roleCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(platformLabel)
						.addComponent(platformCombo)
						.addComponent(requiredDetailsLabel)
						.addComponent(addCastBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(versionLabel)
						.addComponent(versionCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(genreLabel)
						.addComponent(genreCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(stockLabel)
						.addComponent(stockSpinner))				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(awardLabel)
						.addComponent(awardCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(honorLabel)
						.addComponent(categoryCombo)
						.addComponent(addAwardBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(releaseDateLabel)
						.addComponent(releaseYearCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(sequelLabel)
						.addComponent(sequelCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(requiredLabel)
						.addComponent(saveMediaBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(saveAddBtn))
				);
		
		categoryCombo.setEnabled(false);
		addAwardBtn.setEnabled(false);
		this.setVisible(true);
	}
	
	private void addListeners(){
		titleCombo.addActionListener(this);
		addDirectorBtn.addActionListener(this);
		addActorBtn.addActionListener(this);
		platformCombo.addActionListener(this);
		versionCombo.addActionListener(this);
		genreCombo.addActionListener(this);
		stockSpinner.addChangeListener(this);
		awardCombo.addActionListener(this);
		categoryCombo.addActionListener(this);
		releaseYearCombo.addActionListener(this);
		sequelCombo.addActionListener(this);
		addCastBtn.addActionListener(this);
		addAwardBtn.addActionListener(this);
		saveMediaBtn.addActionListener(this);
		saveAddBtn.addActionListener(this);
	}
	
	private void addTraversal(){
		List<Component> components = new ArrayList<Component>();
		components.add(titleCombo);
		components.add(addDirectorBtn);
		components.add(addActorBtn);
		components.add(platformCombo);
		components.add(versionCombo);
		components.add(genreCombo);
		components.add(awardCombo);
		components.add(categoryCombo);
		components.add(releaseYearCombo);
		components.add(sequelCombo);
		components.add(firstNameTF);
		components.add(lastNameTF);
		components.add(roleCombo);
		components.add(addCastBtn);
		components.add(saveMediaBtn);
		components.add(saveAddBtn);
		setFocusTraversalPolicyProvider(true);
		setFocusCycleRoot(true);
		setFocusTraversalPolicy(new InfoTraversalPolicy(this, components));
	}
	
	private void toggleNameFieldsVisible(boolean isVisible){
		castHeadingLabel.setVisible(isVisible);
		firstNameLabel.setVisible(isVisible);
		firstNameTF.setVisible(isVisible);
		lastNameLabel.setVisible(isVisible);
		lastNameTF.setVisible(isVisible);
		if(castHeadingLabel.getText().startsWith(LabelText.DIRECTOR.getLabelText())){
			roleLabel.setVisible(!isVisible);
			roleCombo.setVisible(!isVisible);
		}else{
			roleLabel.setVisible(isVisible);
			roleCombo.setVisible(isVisible);
		}
		requiredDetailsLabel.setVisible(isVisible);
		addCastBtn.setVisible(isVisible);
		clearDetailsFields();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object component;
		component = e.getSource();
		
		if(component instanceof JComboBox){
			JComboBox<?> comboBox = (JComboBox<?>)e.getSource();
			String value = (String) comboBox.getSelectedItem();
			logger.info("Combo " + value);
			if(comboBox.equals(titleCombo)){
				if(value.trim().length() > 2){
					media.setTitle(value);
				}
			}else if(comboBox.equals(platformCombo)){
				if(value.trim().length() >= 2){
					media.getPlatform().setConsole(value);
				}
			}else if(comboBox.equals(versionCombo)){
				if(value.trim().length() > 2){
					media.getPlatform().setVersion(value);
				}
			}else if(comboBox.equals(genreCombo)){
				if(value.trim().length() > 2){
					media.setGenre(value);
				}
			}else if(comboBox.equals(awardCombo)){
				if(value.trim().length() > 2){
					categoryCombo.setEnabled(true);
				}
			}else if(comboBox.equals(categoryCombo)){
				addAwardBtn.setEnabled(true);
			}else if(comboBox.equals(releaseYearCombo)){
				try{
					int year = Integer.parseInt(value);
					if(year > 1900){
						media.setYear(year);
					}
				}catch(NumberFormatException nfe){
					nfe.printStackTrace();
				}
			}
		}else{
			if(component.equals(addActorBtn)){
				detailsActive = true;
				castHeadingLabel.setText(LabelText.ACTOR_DETAILS.getLabelText());
				toggleNameFieldsVisible(detailsActive);
			}else if(component.equals(addDirectorBtn)){
				detailsActive = true;
				castHeadingLabel.setText(LabelText.DIRECTOR_DETAILS.getLabelText());
				
				toggleNameFieldsVisible(detailsActive);
			}else if(component.equals(addAwardBtn)){
				Award award = new Award(awardCombo.getSelectedItem().toString(), categoryCombo.getSelectedItem().toString());
				media.addAward(award);
				categoryCombo.setSelectedIndex(0);
				awardCombo.setSelectedIndex(0);
				resetAward();
			}else if(component.equals(addCastBtn)){
				if(isValidDetails()){
					if(castHeadingLabel.getText().startsWith(LabelText.ACTOR.getLabelText())){
						Actor actor = new Actor(firstNameTF.getText(), lastNameTF.getText(), roleCombo.getSelectedItem().toString());
						media.addActor(actor);
					}else{
						Director director = new Director(firstNameTF.getText(), lastNameTF.getText());
						media.setDirector(director);
						addDirectorBtn.setEnabled(false);
					}
					detailsActive = false;
					castHeadingLabel.setText("");
					roleCombo.setSelectedIndex(0);
					toggleNameFieldsVisible(detailsActive);
				}
			}else if(component.equals(saveMediaBtn)){
				if(isValidInput()){
					setMediaType();
					media.createMedia(mainFrame.getDbConn());
					mainFrame.getActionPanel().getHomeBtn().doClick();
				}
			}else if(component.equals(saveAddBtn)){
				if(isValidInput()){
					setMediaType();
					media.createMedia(mainFrame.getDbConn());
					clearFields();
					this.media = new Media();
				}
			}
		}
		logger.info(media.toString());
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object component;
		component = e.getSource();
		
		if(component.equals(stockSpinner)){
			media.setInStock((int)stockSpinner.getValue());
		}
		logger.info(media.toString());
	}
	
	private void setMediaType(){
		if(mainFrame.getAdminFilterPanel().getSelectedMedia().equals(DBTable.MOVIE)){
			media.setMediaType(MediaType.MOVIE);
		}else if(mainFrame.getAdminFilterPanel().getSelectedMedia().equals(DBTable.GAME)){
			media.setMediaType(MediaType.GAME);
		}else{
			media.setMediaType(MediaType.MEDIA);
		}
	}
	
	private void clearFields(){
		titleCombo.setSelectedIndex(0);
		platformCombo.setSelectedIndex(0);
		versionCombo.setSelectedIndex(0);
		genreCombo.setSelectedIndex(0);
		awardCombo.setSelectedIndex(0);
		categoryCombo.setSelectedIndex(0);
		categoryCombo.setEditable(false);
		releaseYearCombo.setSelectedIndex(0);
		sequelCombo.setSelectedIndex(0);
		stockSpinner.setValue(0);
	}
	
	private boolean isValidInput(){
		boolean isValid = true;
		
		if(!mainFrame.getAdminFilterPanel().hasRequired(media)){
			isValid = false;
		}
		if(media.getTitle().isEmpty()){
			isValid = false;
			toggleValidColors(titleLabel, isValid);
		}else{
			toggleValidColors(titleLabel, true);
		}
		if(MediaType.MOVIE.equals(media.getMediaType()) && media.getActors().isEmpty()){
			isValid = false;
			toggleValidColors(actorLabel, isValid);
		}else{
			toggleValidColors(actorLabel, true);
		}
		if(MediaType.MOVIE.equals(media.getMediaType()) && media.getDirector() == null){
			isValid = false;
			toggleValidColors(directorLabel, isValid);
		}else{
			toggleValidColors(directorLabel, true);
		}
		if(media.getPlatform().getConsole().isEmpty()){
			isValid = false;
			toggleValidColors(platformLabel, isValid);
		}else{
			toggleValidColors(platformLabel, true);
		}
		if(media.getPlatform().getVersion().isEmpty()){
			isValid = false;
			toggleValidColors(versionLabel, isValid);
		}else{
			toggleValidColors(versionLabel, true);
		}
		if(media.getGenre().isEmpty()){
			isValid = false;
			toggleValidColors(genreLabel, isValid);
		}else{
			toggleValidColors(genreLabel, true);
		}
		if(media.getYear() == 0){
			isValid = false;
			toggleValidColors(releaseDateLabel, isValid);
		}else{
			toggleValidColors(releaseDateLabel, true);
		}
		if(!isValid){
			requiredLabel.setText(LabelText.REQUIRED.getLabelText());
			toggleValidColors(requiredLabel, isValid);
		}else{
			requiredLabel.setText("");
		}
		return isValid;
	}
	
	private boolean isValidDetails(){
		boolean isValid = true;
		if(detailsActive){
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
			if(castHeadingLabel.getText().startsWith(LabelText.ACTOR.getLabelText())){
				if(roleCombo.getSelectedIndex() == 0){
					isValid = false;
					toggleValidColors(requiredDetailsLabel, isValid);
					toggleValidColors(roleLabel, isValid);
				}else{
					toggleValidColors(requiredDetailsLabel, true);
					toggleValidColors(roleLabel, true);
				}
			}
			if(!isValid){
				requiredDetailsLabel.setText(LabelText.REQUIRED.getLabelText());
				toggleValidColors(requiredDetailsLabel, isValid);
			}else{
				requiredDetailsLabel.setText("");
			}
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
	
	private void clearDetailsFields(){
		firstNameTF.setText("");
		lastNameTF.setText("");
		roleCombo.setSelectedIndex(0);
	}
	
	private void resetAward(){
		awardCombo.setSelectedIndex(0);;
		categoryCombo.setSelectedIndex(0);
		categoryCombo.setEnabled(false);
		addAwardBtn.setEnabled(false);
	}

}
