package uiUtils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableFieldRenderer extends JButton implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	private static final Color ALTERNATE_ROWS = new Color(230, 230, 230);
	public TableFieldRenderer() {
	}

	public TableFieldRenderer(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public TableFieldRenderer(String text) {
		super(text);
		this.setOpaque(true);
		// TODO Auto-generated constructor stub
	}

	public TableFieldRenderer(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public TableFieldRenderer(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, 
        														isSelected, hasFocus, row, column);
        if(!isSelected){
	        if (row % 2 == 0){
	            comp.setBackground(ALTERNATE_ROWS);
	        }else{
	            comp.setBackground(Color.WHITE);
	        }
        }

		return comp;
	}

}
