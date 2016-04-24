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
import model.Member;
import uiUtils.ButtonUtils;
import uiUtils.LabelUtils;
import enumsProject.LabelText;
import enumsProject.ToolTipText;

public class SubscriptionPanel extends JPanel
							implements ActionListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private JLabel subTypeLbl;
	private JLabel requiredTypeLbl;
	private JLabel subLevelLbl;
	private JLabel requiredLevelLbl;
	private JRadioButton moviesBtn;
	private JRadioButton gamesBtn;
	private JRadioButton bothBtn;
	private JRadioButton levelOneBtn;
	private JRadioButton levelTwoBtn;
	private JRadioButton levelThreeBtn;
	private ButtonGroup typeButtonGrp;
	private ButtonGroup levelButtonGrp;
	
	private Member member;
	private Media media;
	
	public SubscriptionPanel(Member member) {
		logger.info("Getting Subscription panel");
		createUI();
		groupButtons();
		addListeners();
		createOrSetMember(member);
	}
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}

	public JLabel getRequiredTypeLbl() {
		return requiredTypeLbl;
	}
	
	public JLabel getRequiredLevelLbl() {
		return requiredLevelLbl;
	}
	
	public JRadioButton getMoviesBtn() {
		return moviesBtn;
	}

	public JRadioButton getGamesBtn() {
		return gamesBtn;
	}
	
	public JRadioButton getBothBtn() {
		return bothBtn;
	}

	public JRadioButton getLevelOneBtn() {
		return levelOneBtn;
	}

	public JRadioButton getLevelTwoBtn() {
		return levelTwoBtn;
	}

	public JRadioButton getLevelThreeBtn() {
		return levelThreeBtn;
	}

	private void createUI(){
		subTypeLbl = LabelUtils.createHeadingLabel(LabelText.SUBTYPE);
		moviesBtn = ButtonUtils.createRadioButton(LabelText.MOVIES, ToolTipText.MOVIE);
		gamesBtn = ButtonUtils.createRadioButton(LabelText.GAMES, ToolTipText.GAMES);
		bothBtn = ButtonUtils.createRadioButton(LabelText.BOTH, ToolTipText.BOTH);
		requiredTypeLbl = LabelUtils.createRegularLabel(LabelText.REQUIRED);
		
		subLevelLbl = LabelUtils.createHeadingLabel(LabelText.SUBLEVEL);
		levelOneBtn = ButtonUtils.createRadioButton(LabelText.LEVELONE, ToolTipText.TITLE_ONE);
		levelTwoBtn = ButtonUtils.createRadioButton(LabelText.LEVELTWO, ToolTipText.TITLE_TWO);
		levelThreeBtn = ButtonUtils.createRadioButton(LabelText.LEVELTHREE, ToolTipText.TITLE_THREE);
		requiredLevelLbl = LabelUtils.createRegularLabel(LabelText.REQUIRED);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);

		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(subTypeLbl);
		this.add(moviesBtn);
		this.add(gamesBtn);
		this.add(bothBtn);
		this.add(requiredTypeLbl);
		this.add(Box.createRigidArea(new Dimension(40,40)));
		this.add(subLevelLbl);
		this.add(levelOneBtn);
		this.add(levelTwoBtn);
		this.add(levelThreeBtn);
		this.add(requiredLevelLbl);
		
		requiredTypeLbl.setVisible(false);
		requiredLevelLbl.setVisible(false);
		this.setVisible(true);
	}
	
	private void groupButtons(){
		typeButtonGrp = new ButtonGroup();
		typeButtonGrp.add(moviesBtn);
		typeButtonGrp.add(gamesBtn);
		typeButtonGrp.add(bothBtn);
		
		levelButtonGrp = new ButtonGroup();
		levelButtonGrp.add(levelOneBtn);
		levelButtonGrp.add(levelTwoBtn);
		levelButtonGrp.add(levelThreeBtn);
	}
	
	private void addListeners(){
		moviesBtn.addActionListener(this);
		gamesBtn.addActionListener(this);
		bothBtn.addActionListener(this);
		levelOneBtn.addActionListener(this);
		levelTwoBtn.addActionListener(this);
		levelThreeBtn.addActionListener(this);
	}
	
	private void createOrSetMember(Member member){
		if(member == null){
			this.member = new Member();
		}else{
			this.member = member;
		}
	}
	
	public boolean hasRequired(){
		boolean hasRequired = true;
		
		if(member.getMembership().getContent() == null){
			hasRequired = false;
			toggleValidColors(requiredTypeLbl, hasRequired);
		}else{
			toggleValidColors(requiredTypeLbl, hasRequired);
		}
		if (member.getMembership().getQuantity() == 0){
			hasRequired = false;
			toggleValidColors(requiredLevelLbl, hasRequired);
		}else{
			toggleValidColors(requiredLevelLbl, hasRequired);
		}
		return hasRequired;
	}
	
	private void toggleValidColors(JLabel label, boolean hasRequired){
		if(!hasRequired){
			label.setForeground(Color.RED);
			label.setVisible(!hasRequired);
		}else{
			label.setVisible(!hasRequired);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource;
		eventSource = e.getSource();

		if(eventSource.equals(moviesBtn) || eventSource.equals(gamesBtn) || eventSource.equals(bothBtn)){
			if(moviesBtn.isSelected()){
				member.getMembership().setContent("Movie");
			}else if(gamesBtn.isSelected()){
				member.getMembership().setContent("Game");
			}else{
				member.getMembership().setContent("Both");
			}
			logger.info("Content type =" + member.getMembership().getContent());
		}
	
		if(eventSource.equals(levelOneBtn) || eventSource.equals(levelTwoBtn) || eventSource.equals(levelThreeBtn)){
			if(levelOneBtn.isSelected()){
				member.getMembership().setQuantity(1);
			}else if(levelTwoBtn.isSelected()){
				member.getMembership().setQuantity(2);
			}else{
				member.getMembership().setQuantity(3);
			}
			logger.info("Quantity =" + member.getMembership().getQuantity());
		}
	}	
}
