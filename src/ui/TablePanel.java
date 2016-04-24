package ui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import queryUtils.DBDeletes;
import queryUtils.DBInserts;
import queryUtils.DBQueries;
import queryUtils.DBUpdates;
import model.Member;
import model.ObjectTableModel;
import model.Rental;
import uiUtils.LabelUtils;
import uiUtils.TableFieldRenderer;
import utils.AppUtils;
import enumsDB.DBColumn;
import enumsDB.DBTable;
import enumsProject.LabelText;
import enumsProject.TableAction;

public class TablePanel extends JPanel
						implements ListSelectionListener{
	private final static Logger logger = Logger.getLogger(Class.class.getName());
	private JScrollPane scrollPane;
	private JTable resultTable;
	private JLabel resultLabel;
	private JLabel countLabel;
	private JLabel titlesLabel;
	
	private MainFrame mainFrame;
	private Vector<Integer> selectedRows;
	private ObjectTableModel objectTableModel;

	public Vector<Integer> getSelectedRows() {
		return selectedRows;
	}

	public JTable getResultTable() {
		return resultTable;
	}

	public void setResultTable(JTable resultTable) {
		this.removeAll();
		this.resultTable = resultTable;
		createUI();
		setResultsVisible(true);
	}
	
	public void setResultTable(ObjectTableModel objectTableModel) {
		this.removeAll();
		if(selectedRows != null){
			selectedRows.clear();
		}
		logger.info("Setting ObjectTableModel");
		this.objectTableModel = objectTableModel;
		this.resultTable = new JTable(objectTableModel);
		createUI();
		setResultsVisible(true);
	}

	public TablePanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		resultTable = new JTable();
		logger.info("Getting TablePanel panel");
		createUI();
	}
	
	private void createUI(){
		resultLabel = LabelUtils.createHeadingLabel(LabelText.RESULT);
		countLabel = LabelUtils.createHeadingLabel(LabelText.COUNT);
		titlesLabel = LabelUtils.createHeadingLabel(LabelText.SEARCH_RESULT);
		
		if(resultTable == null){
			logger.info("Getting New Table");
			resultTable = new JTable();
		}else{
			countLabel.setText(resultTable.getRowCount() + "");
		}
		logger.info("Adding Listener");
		resultTable.getSelectionModel().addListSelectionListener(this);
		resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		resultTable.setShowGrid(false);
		resultTable.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(resultTable);
		resultTable.setFillsViewportHeight(true);
		TableColumn column = null;
		logger.info("Column Count: " + resultTable.getColumnCount());
		for(int i = 0; i < resultTable.getColumnCount(); i++){
			column = resultTable.getColumnModel().getColumn(i);
			column.setCellRenderer(new TableFieldRenderer());
		    if(resultTable.getColumnCount() > 13){
			    if (i == 1 || i == 9){
			        column.setPreferredWidth(150);
			    }else if(i == 0 || i == 12){
			        column.setPreferredWidth(30);
			    }else{
			    	column.setPreferredWidth(45);
			    }
		    }else if(resultTable.getColumnCount() == 13){
			    if (i == 3){
			        column.setPreferredWidth(150);
			    }else if(i == 0 || i == 7 || i == 10){
			        column.setPreferredWidth(30);
			    }else{
			    	column.setPreferredWidth(45);
			    }	
		    }else if(resultTable.getColumnCount() == 9){
			    if (i == 1){
			        column.setPreferredWidth(150);
			    }else if (i == 7){
			        	column.setPreferredWidth(300);
			    }else if(i == 0 || i == 4 || i == 6){
			        column.setPreferredWidth(25);
			    }else if(i == 8){
			    	column.setPreferredWidth(10);
			    }else{
			    	column.setPreferredWidth(45);
			    }	
			}else{
				if (i == 1 || i == 6){
					column.setPreferredWidth(150);
				}else{
					column.setPreferredWidth(50);
				}
			}
		}
		if(resultTable.getColumnCount() == 7){
			for(int col = 0; col < resultTable.getColumnCount() - 1; col++){
			}
		}
		scrollPane.setPreferredSize(new Dimension(600,350));
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.add(scrollPane, BorderLayout.CENTER);
		setResultsVisible(false);
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new FlowLayout());
		resultPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		resultPanel.add(resultLabel);
		resultPanel.add(countLabel);
		resultPanel.add(titlesLabel);
		this.add(resultPanel, BorderLayout.SOUTH);
		this.revalidate();
	}

	private void setResultsVisible(boolean isVisible){
		scrollPane.setVisible(isVisible);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel selectionModel = (ListSelectionModel)e.getSource();
        
        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting(); 
        logger.info("Event for indexes " + firstIndex + " - " + lastIndex
                      + "; isAdjusting is " + isAdjusting);

        if (selectionModel.isSelectionEmpty()) {
        	logger.info(" <none>");
        }else{
        	setSelected(selectionModel);
        }		
	}
	
	private void setSelected(ListSelectionModel selectionModel){
        int minIndex = selectionModel.getMinSelectionIndex();
        int maxIndex = selectionModel.getMaxSelectionIndex();
        selectedRows = new Vector<Integer>();
        for (int row = minIndex; row <= maxIndex; row++) {
            if (selectionModel.isSelectedIndex(row)) {
            	selectedRows.add(row);
            }
        }
        logger.info("Selected Rows: " + selectedRows.toString());
	}
	
	public void updateSelected(TableAction action){
		if(selectedRows.size() > 0){
	        if(objectTableModel.getMembers().size() > 0){
	        	Vector<Member> selectedItems;
	        	selectedItems = new Vector<Member>();
	        	for(int row: selectedRows){
	        		Member selectedMember = objectTableModel.getMembers().get(resultTable.convertRowIndexToModel(row));
	        		selectedItems.add(selectedMember);
	        	}
	        	updateMember(action, selectedItems);
	        }else if(objectTableModel.getTitles().size() > 0){
	        	Vector<Rental> selectedItems;
	        	selectedItems = new Vector<Rental>();
	        	for(int row: selectedRows){
	        		Rental selectedRental = objectTableModel.getTitles().get(resultTable.convertRowIndexToModel(row));
	        		selectedItems.add(selectedRental);
	        	}
	        	updateTitle(action, selectedItems);
	        }
		}
	}
	
	private void updateMember(TableAction action, Vector<Member> selectedItems){
		switch(action){
		case DELETE:
			int confirmed = JOptionPane.showConfirmDialog(mainFrame, "Confirm " + action.toString() + " a member.", "Caution", JOptionPane.OK_CANCEL_OPTION);
			if(confirmed == 0){
				for(Member member : selectedItems){
					if(!AppUtils.currentlyRented(mainFrame, DBQueries.fetchUserRentedInfo(DBTable.MEDIA, member.getEmail()))){
						AppUtils.updateTableResults(mainFrame, DBDeletes.deleteMember(member));
						AppUtils.updateTableResults(mainFrame, DBDeletes.resetPasswordAndDate(member));
					}else{
						JOptionPane.showMessageDialog(mainFrame, "Member " + member.getFirstName() + " " + member.getSurName() + " has current rentals.", "Invalid", JOptionPane.OK_OPTION);
					}
				}
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchMembers());
			}
			break;
		case RENT:
		case RETURN:
		case SHIP:
		case UPDATE:
			JOptionPane.showMessageDialog(mainFrame, "Cannot " + action.toString() + " a member.", "Invalid", JOptionPane.ERROR_MESSAGE);
		}
		logger.info(action.toString());
	}

	private void updateTitle(TableAction action, Vector<Rental> selectedItems){
		Vector<Integer> invalidItems = new Vector<Integer>();
		switch(action){
		case DELETE:
			int confirmed = JOptionPane.showConfirmDialog(mainFrame, "Confirm " + action.toString() + " "
					+ selectedItems.size() + " title(s).", "Caution", JOptionPane.OK_CANCEL_OPTION);
			if(confirmed == 0){
				Vector<Integer> actorIds = new Vector<Integer>();
				for(Rental rental : selectedItems){
					boolean rented = AppUtils.currentlyRented(mainFrame, DBDeletes.checkRented(rental));
					if(!rented){
						int mediaId = rental.getMedia().getId();
						AppUtils.updateTableResults(mainFrame, DBDeletes.deleteMedia(mediaId));
					}else{
						invalidItems.add(rental.getMedia().getId());
					}
				}
				if(invalidItems.size() > 0){
					JOptionPane.showMessageDialog(mainFrame, "Stock #(s) " + invalidItems.toString() + " are currently rented.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}
				AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAllType(mainFrame.getAdminFilterPanel().getSelectedMedia(), null));
			}
			break;
		case RENT:
			if(selectedItems.size() > 0){
				for(Rental rental : selectedItems){
					if(mainFrame.getMember().canRent()){
						Date now = AppUtils.getToday(mainFrame, DBQueries.fetchToday());
						if(AppUtils.updateTableResults(mainFrame, 
								DBInserts.rentTitle(rental.getMedia().getId(), mainFrame.getMember().getId(), now))){	
							mainFrame.getMember().rentedTitle();
						}
					}else{
						JOptionPane.showMessageDialog(mainFrame, "You have reached your rental limit.", "Invalid", JOptionPane.ERROR_MESSAGE);
						break;
					}
					if(!AppUtils.isAvailable(mainFrame, DBQueries.fetchInStock(rental.getMedia().getId()))){
						invalidItems.add(rental.getMedia().getId());
					}
				}
				if(invalidItems.size() > 0){
					JOptionPane.showMessageDialog(mainFrame, "Stock #(s) " + invalidItems.toString() + " are currently out of Stock.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}
			}
			AppUtils.createSingleTableResults(mainFrame, DBQueries.fetchAllType(mainFrame.getRentalFilterPanel().getSelectedMedia(), null));
			break;
		case RETURN:
			for(Rental rental : selectedItems){
				AppUtils.updateTableResults(mainFrame, DBUpdates.updateInventory(rental, action));
			}
			AppUtils.createSingleTableResults(mainFrame, 
					DBQueries.fetchRentedInfo(mainFrame.getAdminFilterPanel().getSelectedMedia(), DBColumn.RETURNED));
			break;
		case SHIP:
			for(Rental rental : selectedItems){
				AppUtils.updateTableResults(mainFrame, DBUpdates.updateInventory(rental, action));
			}
			AppUtils.createSingleTableResults(mainFrame, 
					DBQueries.fetchRentedInfo(mainFrame.getAdminFilterPanel().getSelectedMedia(), DBColumn.SHIPPED));
			break;
		case UPDATE:
			break;
		}
		logger.info(action.toString());
	}
}
