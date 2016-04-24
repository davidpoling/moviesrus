package uiUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;

import enumsProject.ButtonText;
import enumsProject.LabelText;
import enumsProject.ToolTipText;

public class ButtonUtils {
	
	public static JRadioButton createRadioButton(LabelText label, ToolTipText tooltip){
		JRadioButton button = new JRadioButton(label.getLabelText());
		addFontandTooltip(button, tooltip.getToolTipText());
		return button;
	}
	
	public static JCheckBox createCheckbox(LabelText label, ToolTipText tooltip){
		JCheckBox button = new JCheckBox(label.getLabelText());
		addFontandTooltip(button, tooltip.getToolTipText());
		return button;
	}
	
	public static JButton createButton(String text, String string) {
		JButton button = new JButton(text);
		button.setForeground(ColorUtils.getButtonForeground());
		button.setBackground(ColorUtils.getButtonBackground());
		button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		addFontandTooltip(button, string);
		return button;
	}
	
	public static JButton createButton(ButtonText label, ToolTipText tooltip){
		JButton button = new JButton(label.getButtonText());
		button.setForeground(ColorUtils.getButtonForeground());
		button.setBackground(ColorUtils.getButtonBackground());
		button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		addFontandTooltip(button, tooltip.getToolTipText());
		return button;
	}
	
	public static JPopupMenu createPopupMenu(){
		JPopupMenu menu = new JPopupMenu();
		return menu;
	}
	
	public static JMenuItem createMenuItem(ButtonText label){
		JMenuItem item = new JMenuItem(label.getButtonText());
		return item;
	}
	
	private static void addFontandTooltip(JComponent button, String tooltip){
		if(button instanceof JRadioButton){
			button.setFont(FontUtils.createMainFont());
		}else if(button instanceof JButton){
			button.setFont(FontUtils.createButtonFont());
		}else{
			button.setFont(FontUtils.createMainFont());
		}
		button.setToolTipText(tooltip);
	}

}
