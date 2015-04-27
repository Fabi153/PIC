package view;

import handler.Elementhandlers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



// Bibliotheken f�r Editor




import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ViewBox extends JFrame{

	public static JFrame frame;
	//private JTable quellcodeTable;
	private String[] quellcode;
	public static JTable table;
	public static JTable register_table;
	public int[] befehlscode;
	public static int[] positionInStringArray= new int[0xFFF];
	public static JLabel lblWregister = new JLabel("W-Register");
	public Thread aktuellerThread;
	@SuppressWarnings("rawtypes")
	public static JList stackList = new JList();

	@SuppressWarnings("rawtypes")

	public static JTable portA_table;
	public static JTable portB_table;
	
	
	//In/Outputbuttons
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@SuppressWarnings("static-access")
			public void run() {
				try {
					ViewBox window = new ViewBox();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewBox() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */	
	private void initialize() {
		 
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
//		***Hauptmen�***		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mainmenu = new JMenu("Men\u00FC");
		menuBar.add(mainmenu);
		
//		***Hauptmen�elemente***		
		
		JMenuItem buttonReadcode= new JMenuItem("Datei \u00F6ffnen");
		mainmenu.add(buttonReadcode);
		
		JMenuItem buttonClose = new JMenuItem("Programm schlie\u00DFen");
		mainmenu.add(buttonClose);
		
//		***Buttons***
		
		JButton btnStart = new JButton("Start"); // Start-Button
		btnStart.setBounds(276, 11, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JButton btnStepByStep = new JButton("Step by Step");
		btnStepByStep.setBounds(155, 10, 110, 25);
		frame.getContentPane().add(btnStepByStep);
		
//		***Anzeige f�r Code***		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(375, 10, 600, 400);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 58, 338, 289);
		frame.getContentPane().add(scrollPane_1);
		
	
		
		
		register_table = new JTable(){
			
		            @Override
			public Component prepareRenderer(
                    TableCellRenderer renderer, int row, int column) {
		            	if (column == 0) {
		                    return this.getTableHeader().getDefaultRenderer()
		                        .getTableCellRendererComponent(this, this.getValueAt(
		                            row, column), false, false, row, column);
		                } else {
		                    return super.prepareRenderer(renderer, row, column);
		                }
		            }
			};
		register_table.setBackground(Color.WHITE);
		register_table.setForeground(Color.BLACK);
		
		
		
		final JTableHeader header = register_table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(register_table));
        
       
		
		/*register_table.getColumnModel().getColumn(0).setPreferredWidth(30);
		register_table.getColumnModel().getColumn(0).setMinWidth(12);
		register_table.getColumnModel().getColumn(1).setPreferredWidth(30);
		register_table.getColumnModel().getColumn(2).setPreferredWidth(30);
		register_table.getColumnModel().getColumn(3).setPreferredWidth(30);
		register_table.getColumnModel().getColumn(4).setPreferredWidth(60);
		*/
		
        scrollPane_1.setViewportView(register_table);
		
		
		lblWregister.setBounds(12, 358, 174, 14);
		frame.getContentPane().add(lblWregister);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 393, 130, 171);
		frame.getContentPane().add(scrollPane_2);
		

		
		scrollPane_2.setViewportView(stackList);
		
		
		 

		
		scrollPane_2.setViewportView(stackList);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(302, 435, 417, 80);
		frame.getContentPane().add(scrollPane_3);
		
		portA_table = new JTable() {
			
		       @Override
				public Component prepareRenderer(
	                    TableCellRenderer renderer, int row, int column) {
			            	if (column == 0) {
			                    return this.getTableHeader().getDefaultRenderer()
			                        .getTableCellRendererComponent(this, this.getValueAt(
			                            row, column), false, false, row, column);
			                } else {
			                    return super.prepareRenderer(renderer, row, column);
			                }
			            }
			
		};
		
		scrollPane_3.setViewportView(portA_table);
		
					
		portA_table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				
				int row = portA_table.rowAtPoint(e.getPoint());
				int column = portA_table.columnAtPoint(e.getPoint()); // Zelle in Tabelle ermitteln
				int maske = 0;
				if (row == 1 && column > 0)
				maske = (int) Math.pow(2, (8-column));
				System.out.println(maske);
				if ((Elementhandlers.myRegister.getTrisA() & maske) == maske)
					Elementhandlers.myRegister.setPortA((Elementhandlers.myRegister.getPortA() ^ maske));
				Elementhandlers.updateTable();
				Elementhandlers.updateTableA();
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(302, 562, 417, 80);
		frame.getContentPane().add(scrollPane_4);
		
		portB_table = new JTable() {
			
		       @Override
				public Component prepareRenderer(
	                    TableCellRenderer renderer, int row, int column) {
			            	if (column == 0) {
			                    return this.getTableHeader().getDefaultRenderer()
			                        .getTableCellRendererComponent(this, this.getValueAt(
			                            row, column), false, false, row, column);
			                } else {
			                    return super.prepareRenderer(renderer, row, column);
			                }
			            }
			
		};
		
		
		scrollPane_4.setViewportView(portB_table);
		
				
		portB_table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = portB_table.rowAtPoint(e.getPoint());
				int column = portB_table.columnAtPoint(e.getPoint()); // Zelle in Tabelle ermitteln
				int maske = 0;
				if (row == 1 && column > 0)
				maske = (int) Math.pow(2, (8-column));
				System.out.println(maske);
				if ((Elementhandlers.myRegister.getTrisB() & maske) == maske)
					Elementhandlers.myRegister.setPortB((Elementhandlers.myRegister.getPortB() ^ maske));
				Elementhandlers.updateTable();
				Elementhandlers.updateTableB();
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		scrollPane_4.setViewportView(portB_table);
		
		
		
		
//		***Actionlisteners*** --> Schnittstellen zu Elementhandlers	
		
		btnStart.addActionListener(new ActionListener() { 			// Start-Button
			
			 public void actionPerformed(ActionEvent e){
				 
				 aktuellerThread = Elementhandlers.buttonStart(befehlscode,aktuellerThread);
				
			}
				
		});
		
		btnStepByStep.addActionListener(new ActionListener() { 		// Step-Button
			
			 public void actionPerformed(ActionEvent e){
				 
				 Elementhandlers.buttonStep(aktuellerThread);
				
			}
				
		});
		
		buttonReadcode.addActionListener(new ActionListener() {		//Load-Button
			 
            public void actionPerformed (ActionEvent e)
            {
            	String filename = null;
            	String dir = null;
            	FileFilter filter = new FileNameExtensionFilter("LST-Dateien", "LST"); //File-Filter
            	JFileChooser c = new JFileChooser();
            	c.removeChoosableFileFilter(c.getChoosableFileFilters()[0]); // Unterbindet Filter "Alle Dateien"
            	c.addChoosableFileFilter(filter);
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(ViewBox.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                  filename= c.getSelectedFile().getName();
                  dir = c.getCurrentDirectory().toString();
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                  filename= null;
                  dir= null;
                }
                if (filename != null || dir != null){
                quellcode = Elementhandlers.loadFile(filename,dir);
                befehlscode = Elementhandlers.convertStringFileToIntFile(quellcode); // In diesem Array werden die Befehlscodes gespeichert
                Object[][] data= new Object[quellcode.length][2];					// befehlscode oben deklariert
                Object[] columns={"BP","Quellcode"};
                for (int i=0;i<quellcode.length;i++){ // ??"<=" hinzugef�gt, ansonsten eine zeile zu wenig? syso(i)
                	data[i][0]=false;
                	data[i][1]=quellcode[i];
                    
                	}
                //updateTabelle(); // Databinding
                DefaultTableModel tablemodel = new DefaultTableModel(data,columns) {
                    @Override
					public
                    Class<?> getColumnClass(int columnIndex) {
                        return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
                    }
                };                
                table.setModel(tablemodel);
             // Erste Spalte auf 100 Pixel Breite setzen:
        		TableColumn col1 = table.getColumnModel().getColumn(0);
        		col1.setPreferredWidth(30);
        		TableColumn col2 = table.getColumnModel().getColumn(1);
        		col2.setPreferredWidth(552);
        		
        		//Update RegisterTable
                Elementhandlers.updateTable();
                Elementhandlers.updateTableA();
                Elementhandlers.updateTableB();
                
                //Update the Quellcode-table
                ViewBox.table.getColumn("Quellcode").setCellRenderer(new DefaultTableCellRenderer(){
    				Color originalColor = null;

    		        @Override
    		        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    		            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    		            if (originalColor == null) {
    		                originalColor = getBackground();
    		            }
    		            if (value == null) {
    		                renderer.setText("");
    		            } else {
    		                renderer.setText(value.toString());
    		            }

    		            if (row == ViewBox.positionInStringArray[0]) {
    		                renderer.setBackground(Color.RED);
    		            } else {
    		                renderer.setBackground(originalColor); // Retore original color
    		            }
    		            return renderer;
    		        }
    			});
    			ViewBox.table.setRowSelectionInterval(0,0);
    			ViewBox.table.clearSelection();
                
    			

    			
                }
            }
        });      
		
		
//		***Register-Objekt initialisieren ***
		
		
	}
}




class HeaderRenderer implements TableCellRenderer {

    TableCellRenderer renderer;

    public HeaderRenderer(JTable table) {
        renderer = table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, col);
    }
}



