package uiUtils;

import javax.swing.JLabel;

import enumsProject.LabelText;

public class LabelUtils {

	public static JLabel createAppLabel(LabelText label){
		JLabel jLabel = new JLabel(label.getLabelText());
		jLabel.setFont(FontUtils.createAppFont());
		return jLabel;
	}
	
	public static JLabel createHeadingLabel(LabelText label){
		JLabel jLabel = new JLabel(label.getLabelText());
		jLabel.setFont(FontUtils.createHeadingFont());
		return jLabel;
	}
	
	public static JLabel createRegularLabel(LabelText label){
		JLabel jLabel = new JLabel(label.getLabelText());
		jLabel.setFont(FontUtils.createMainFont());
		return jLabel;
	}
}
