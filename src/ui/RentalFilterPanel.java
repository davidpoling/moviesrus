package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Member;
import queryUtils.DBQueries;
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

public class RentalFilterPanel extends JPanel
								implements ActionListener, DocumentListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private final static boolean IS_EDITABLE = true;
	private JRadioButton moviesBtn;
	private JRadioButton gamesBtn;
	private JRadioButton bothBtn;
	private ButtonGroup mediaButtonGrp;
	private JLabel mediaLabel;
	private JLabel searchLabel;
	private JLabel actorLabel;
	private JLabel directorLabel;
	private JLabel keywordLabel;
	private JLabel genreLabel;
	private JLabel platformLabel;
	private JLabel versionLabel;
	private JLabel limitLabel;
	private ButtonGroup filterButtonGrp;
	private JRadioButton awardRB;
	private JRadioButton sequelRB;
	private JRadioButton unrentedRB;
	private JRadioButton rentedRB;
	private JButton resetFilterBtn;
	private JTextField actorTF;
	private JTextField directorTF;
	private JTextField keywordTF;
	private JComboBox<String> genreCombo;
	private JComboBox<String> platformCombo;
	private JComboBox<String> versionCombo;
	private JButton resetBtn;
	private JButton searchBtn;
		
	private MainFrame mainFrame;
	Member member;
	private Map<DBTable,String> searchPairs;
	
	public RentalFilterPanel(MainFrame mainFrame, Member member) {
		this.mainFrame = mainFrame;
		this.member = member;
		logger.info("Getting FilterPanel panel");
		createUI();
		enableFilters(false);
		enableSearchFields(false);
		enableMovieFields(false);
		enableDependantFields(false);
		groupButtons();
		addListeners();
		setContentType(member.getMembership().getContent());
	}
	
	private void createUI(){
		mediaLabel = LabelUtils.createHeadingLabel(LabelText.RENTALTYPE);
		moviesBtn = ButtonUtils.createRadioButton(LabelText.MOVIES, ToolTipText.MOVIE);
		gamesBtn = ButtonUtils.createRadioButton(LabelText.GAMES, ToolTipText.GAMES);
		bothBtn = ButtonUtils.createRadioButton(LabelText.BOTH, ToolTipText.BOTH);
		
		limitLabel = LabelUtils.createHeadingLabel(LabelText.LIMIT);
		awardRB = ButtonUtils.createRadioButton(LabelText.AWARDS, ToolTipText.AWARD);
		sequelRB = ButtonUtils.createRadioButton(LabelText.SEQUEL, ToolTipText.SEQUEL);
		unrentedRB = ButtonUtils.createRadioButton(LabelText.UNRENTED, ToolTipText.UNRENTED);
		rentedRB = ButtonUtils.createRadioButton(LabelText.RENTED, ToolTipText.RENTED);
		resetFilterBtn = ButtonUtils.createButton(ButtonText.RESET, ToolTipText.RESET);
		
		searchLabel = LabelUtils.createHeadingLabel(LabelText.SEARCH);
		actorLabel = LabelUtils.createRegularLabel(LabelText.ACTOR);
		directorLabel = LabelUtils.createRegularLabel(LabelText.DIRECTOR);
		keywordLabel = LabelUtils.createRegularLabel(LabelText.KEYWORDS);
		genreLabel = LabelUtils.createRegularLabel(LabelText.GENRE);
		platformLabel = LabelUtils.createRegularLabel(LabelText.PLATFORM);
		versionLabel = LabelUtils.createRegularLabel(LabelText.VERSION);
		
		keywordTF = InputUtils.createSearchField(UISize.FIELDWIDTH_MD.getSize(), 
				UISize.FIELDHEIGHT.getSize(), DBTable.ALL.getDbTableName(), ToolTipText.SEARCH_KEYWORD);
		actorTF = InputUtils.createSearchField(UISize.FIELDWIDTH_MD.getSize(), 
				UISize.FIELDHEIGHT.getSize(), DBTable.ACTOR.getDbTableName(), ToolTipText.SEARCH_ACTOR);
		directorTF = InputUtils.createSearchField(UISize.FIELDWIDTH_MD.getSize(), 
				UISize.FIELDHEIGHT.getSize(), DBTable.DIRECTOR.getDbTableName(), ToolTipText.SEARCH_DIRECTOR);
		genreCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_MD.getSize(),UISize.FIELDHEIGHT.getSize(), 
				ToolTipText.SEARCH_GENRE, mainFrame.getDbConn(), 
				DBTable.GENRE, DBColumn.GENRE, !IS_EDITABLE, null, null);
		platformCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_MD.getSize(),UISize.FIELDHEIGHT.getSize(), 
				ToolTipText.SEARCH_PLATFORM, mainFrame.getDbConn(), 
				DBTable.PLATFORM, DBColumn.CONSOLE, !IS_EDITABLE, null, null);
		versionCombo = InputUtils.createDBComboBox(UISize.FIELDWIDTH_MD.getSize(),UISize.FIELDHEIGHT.getSize(), 
				ToolTipText.SEARCH_PLATFORM, mainFrame.getDbConn(), 
				DBTable.PLATFORM, DBColumn.VERSION, !IS_EDITABLE, null, null);
		searchBtn = ButtonUtils.createButton(ButtonText.SEARCH, ToolTipText.SEARCH);
		resetBtn = ButtonUtils.createButton(ButtonText.CLEAR, ToolTipText.CLEAR);
		resetBtn.setEnabled(false);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(mediaLabel)
						.addComponent(moviesBtn)
						.addComponent(gamesBtn)
						.addComponent(bothBtn)
						.addComponent(limitLabel)
						.addComponent(awardRB)
						.addComponent(unrentedRB)
						.addComponent(searchLabel)
						.addComponent(keywordLabel)
						.addComponent(directorLabel)
						.addComponent(actorLabel)
						.addComponent(genreLabel)
						.addComponent(platformLabel)
						.addComponent(versionLabel)
						.addComponent(searchBtn))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(sequelRB)
						.addComponent(rentedRB)
						.addComponent(resetFilterBtn)
						.addComponent(keywordTF, UISize.FIELDWIDTH_MD.getSize(),
								UISize.FIELDWIDTH_MD.getSize(), UISize.FIELDWIDTH_MD.getSize())
						.addComponent(directorTF)
						.addComponent(actorTF)
						.addComponent(genreCombo)
						.addComponent(platformCombo)
						.addComponent(versionCombo)
						.addComponent(resetBtn))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mediaLabel)
				.addComponent(moviesBtn)
				.addComponent(gamesBtn)
				.addComponent(bothBtn)
				.addComponent(limitLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(awardRB)
						.addComponent(sequelRB))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(unrentedRB)
						.addComponent(rentedRB))
				.addComponent(resetFilterBtn)
				.addComponent(searchLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(keywordLabel)
						.addComponent(keywordTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(actorLabel)
						.addComponent(actorTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(directorLabel)
						.addComponent(directorTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(genreLabel)
						.addComponent(genreCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(platformLabel)
						.addComponent(platformCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(versionLabel)
						.addComponent(versionCombo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(searchBtn)
						.addComponent(resetBtn))
				);
		
		this.setLayout(layout);
	}
	
	private void groupButtons(){
		mediaButtonGrp = new ButtonGroup();
		mediaButtonGrp.add(moviesBtn);
		mediaButtonGrp.add(gamesBtn);
		mediaButtonGrp.add(bothBtn);
		
		filterButtonGrp = new ButtonGroup();
		filterButtonGrp.add(awardRB);
		filterButtonGrp.add(sequelRB);
		filterButtonGrp.add(unrentedRB);
		filterButtonGrp.add(rentedRB);
	}
	
	private void addListeners(){
		moviesBtn.addActionListener(this);
		gamesBtn.addActionListener(this);
		bothBtn.addActionListener(this);
		awardRB.addActionListener(this);
		sequelRB.addActionListener(this);
		unrentedRB.addActionListener(this);
		rentedRB.addActionListener(this);
		resetFilterBtn.addActionListener(this);
		keywordTF.getDocument().addDocumentListener(this);
		actorTF.getDocument().addDocumentListener(this);
		directorTF.getDocument().addDocumentListener(this);
		genreCombo.addActionListener(this);
		platformCombo.addActionListener(this);
		versionCombo.addActionListener(this);
		searchBtn.addActionListener(this);
		resetBtn.addActionListener(this);
	}
	
	private void enableFilters(boolean enabled){
		awardRB.setEnabled(enabled);
		sequelRB.setEnabled(enabled);
		unrentedRB.setEnabled(enabled);
		rentedRB.setEnabled(enabled);
	}
	
	private void enableMovieFields(boolean enabled){
		actorLabel.setEnabled(enabled);
		actorTF.setEnabled(enabled);
		directorLabel.setEnabled(enabled);
		directorTF.setEnabled(enabled);
	}
	
	private void clearMovieFields(){
		actorTF.setText("");
		directorTF.setText("");
	}
	
	public void clearSelections(){
		mediaButtonGrp.clearSelection();
		filterButtonGrp.clearSelection();
	}
	
	private void enableSearchFields(boolean enabled){
		keywordLabel.setEnabled(enabled);
		keywordTF.setEnabled(enabled);
		genreLabel.setEnabled(enabled);
		genreCombo.setEnabled(enabled);
		platformLabel.setEnabled(enabled);
		platformCombo.setEnabled(enabled);
		searchBtn.setEnabled(enabled);
	}
	
	private void enableDependantFields(boolean enabled){
		versionLabel.setEnabled(enabled);
		versionCombo.setEnabled(enabled);
	}
	
	public void setContentType(String content){
		logger.info("Setting Content: " + content);
		if(("Movie").equals(content)){
			gamesBtn.setEnabled(false);
			bothBtn.setEnabled(false);
		}else if(("Game").equals(content)){
			moviesBtn.setEnabled(false);
			bothBtn.setEnabled(false);
		}
	}
	
	public DBTable getSelectedMedia(){	
		if(moviesBtn.isSelected()){
			return DBTable.MOVIE;
		}else if(gamesBtn.isSelected()){
			return DBTable.GAME;
		}
		return DBTable.MEDIA;
	}
	
	private void getSelectedFilterMedia(DBTable media, Map<DBTable, String> searchPairs){	
		if(awardRB.isSelected()){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAwards(media));
		}else if(sequelRB.isSelected()){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchSequels(media));
		}else if(unrentedRB.isSelected()){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchUserNotRentedInfo(media, mainFrame.getMember().getEmail()));
		}else if(rentedRB.isSelected()){
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchUserRentedInfo(media, mainFrame.getMember().getEmail()));
		}else{
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAllType(media, null));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();
		
		if(eventSource.equals(searchBtn)){
			doSearch(createSearchMap());
		}else if(eventSource.equals(resetFilterBtn)){
			filterButtonGrp.clearSelection();
			return;
		}else if(eventSource.equals(resetBtn)){
			keywordTF.setText("");
			clearMovieFields();
			genreCombo.setSelectedIndex(0);
			platformCombo.setSelectedIndex(0);
			versionCombo.setSelectedIndex(0);
			getSelectedMedia();
			enableDependantFields(false);
			resetBtn.setEnabled(false);
			return;
		}
		if(eventSource.equals(moviesBtn) || eventSource.equals(gamesBtn) || eventSource.equals(bothBtn)){
			enableFilters(true);
			enableSearchFields(true);
			if(eventSource.equals(moviesBtn)){
				enableSearchFields(true);
				enableMovieFields(true);
			}else{
				clearMovieFields();
				enableMovieFields(false);
			}
			getSelectedFilterMedia(getSelectedMedia(), null);
		}
		if(eventSource.equals(awardRB) || eventSource.equals(sequelRB) ||
			eventSource.equals(unrentedRB) || eventSource.equals(rentedRB)){
			getSelectedFilterMedia(getSelectedMedia(), null);
			enableSearchFields(true);
		}
		if(eventSource.equals(genreCombo) || eventSource.equals(platformCombo)){
			resetBtn.setEnabled(true);
			if(platformCombo.getSelectedIndex() != 0){
				enableDependantFields(true);
			}
		}

	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		logger.info("Inserting");
		filterButtonGrp.clearSelection();
		resetBtn.setEnabled(true);
		enableFilters(false);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		logger.info("Removing");
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("Changing");
	}
	
	private Map<DBTable, String> createSearchMap(){
		
		searchPairs = new HashMap<DBTable,String>();
		
		searchPairs.put(getSelectedMedia(), getSelectedMedia().getDbTableName());
		
		if(!resetBtn.isEnabled()){
			resetBtn.setEnabled(true);
		}
		if(keywordTF.getText().trim().length() != 0){
			searchPairs.put(DBTable.ALL, keywordTF.getText().trim());
		}
		if(actorTF.getText().trim().length() != 0){
			searchPairs.put(DBTable.ACTOR, actorTF.getText().trim());
		}
		if(directorTF.getText().trim().length() != 0){
			searchPairs.put(DBTable.DIRECTOR, directorTF.getText().trim());
		}
		if(genreCombo.getSelectedIndex() != 0){
			searchPairs.put(DBTable.GENRE, genreCombo.getSelectedItem().toString());
		}
		if(platformCombo.getSelectedIndex() != 0){
			searchPairs.put(DBTable.PLATFORM, platformCombo.getSelectedItem().toString());
		}
		if(versionCombo.getSelectedIndex() != 0){
			searchPairs.put(DBTable.PLATFORM_VERSION, versionCombo.getSelectedItem().toString());
		}

		for (Map.Entry<DBTable, String> pair : searchPairs.entrySet())
		{
			logger.info(pair.getKey() + ": " + pair.getValue());
		}
		return searchPairs;
	}
	
	private void doSearch(Map<DBTable, String> values){
		if(values.size() < 2){
			if(values.containsKey(DBTable.ALL)){
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAllType(getSelectedMedia(), keywordTF.getText().trim()));
			}else if(values.containsKey(DBTable.ACTOR)){
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchPersonByMedia(DBTable.ACTOR, getSelectedMedia(), actorTF.getText().trim()));
			}else if(values.containsKey(DBTable.DIRECTOR)){		
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchPersonByMedia(DBTable.DIRECTOR, getSelectedMedia(), directorTF.getText().trim()));
			}else if(values.containsKey(DBTable.GENRE)){
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMediaByColumn(getSelectedMedia(), DBColumn.GENRE, genreCombo.getSelectedItem().toString()));
			}else if(values.containsKey(DBTable.PLATFORM)){
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMediaByColumn(getSelectedMedia(), DBColumn.PLATFORM_CONSOLE, platformCombo.getSelectedItem().toString()));
			}else if(values.containsKey(DBTable.PLATFORM)){
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMediaByColumn(getSelectedMedia(), DBColumn.PLATFORM_VERSION, versionCombo.getSelectedItem().toString()));
			}
		}else{
			logger.info("Fetch multi: ");
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMultiKeyword(getSelectedMedia(), createSearchMap()));
		}
	}
}
