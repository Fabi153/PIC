package handler;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import view.ViewBox;
import logic.Interpreter;
import logic.Register;

//yolo alda fleischer is the best

public class Elementhandlers{
	public static Register myRegister = new Register();
	public static int var =0;
	public static int pointerTable=0;
	public static int[] befehlscodeInElementhandler;
	public static boolean startStop= true;
	public static Thread startThread = new Thread(new HauptStartThread());

	
	
	public static Thread buttonStart(int[] befehlscode, Thread aktuellerThread){
	
		
		try {							//Exception abfangen, falls Start gedrückt wird ohne ein File zu laden.
			
			if (aktuellerThread== null){		//Noch kein Thread offen
				Thread newThread = new Thread(new HauptStartThread());
				newThread.start();
				startStop=true;
				return newThread;
			}
			if(aktuellerThread.isAlive()== false){ // alter Thread finished
			Thread newThread = new Thread(new HauptStartThread());
			newThread.start();
			startStop=true;
			return newThread;
			}
			else{
				startStop=false;			// stop alten Thread
			return aktuellerThread;
			}
			}
			catch(NullPointerException e1) {System.out.println("Zuerst Befehlscode einlesen!"+ e1);}
		return null;
	}
	
	public static void buttonStep(Thread aktuellerThread){
	
	 //int zaehler = 0;	//sollte static sein, um Befehle hochzuzählen und beim nächsten Aufruf den aktuellen Befehl zu liefern
		if (aktuellerThread== null){		//Noch kein Thread offen
			Thread newThread = new Thread(new HauptNextStepThread());
			newThread.start();
		}
		if (aktuellerThread.isAlive()){
			return;
		}
		else{
		Thread newThread = new Thread(new HauptNextStepThread());
		newThread.start();
		
		
		
		}
	}	
	


	@SuppressWarnings("unchecked")
	public static void updateTable() {
		
		Object[][] rows = new Object [32][9];
		String[] columns = new String [9];
		int count=0;
		int[] registers =	Elementhandlers.myRegister.getRegisters(); 
		//Column nummerieren
		for (int i=0;i<32;i++){
			rows[i][0]= Integer.toHexString(i*8);
		}
		columns[0]= "X";
		for (int l=0;l<8;l++){
			columns[l+1]= Integer.toHexString(l);
		}
		//Füllen der Daten
		for(int k=0;k<32;k++){
			for (int j=1;j<9;j++){
				rows[k][j]=Integer.toHexString(registers[count]);
				count++;
			}
		}
		ViewBox.register_table.setModel(new DefaultTableModel(rows,columns));
		
		// WReg ausgeben
		String wReg= "W-Register :" + Integer.toHexString(Elementhandlers.myRegister.getWRegister());
		ViewBox.lblWregister.setText(wReg);
		
		//Flags ausgeben
		String zFlag= "Z-Flag :" + Elementhandlers.myRegister.getZFlag();
		ViewBox.lblZFlag.setText(zFlag);
		
		String cFlag= "C-Flag :" + Elementhandlers.myRegister.getCFlag();
		ViewBox.lblCFlag.setText(cFlag);
		
		String dcFlag= "DC-Flag :" + Elementhandlers.myRegister.getDCFlag();
		ViewBox.lblDCFlag.setText(dcFlag);
		
		// Stack ausgeben
		final Object[] stack = Elementhandlers.myRegister.getStack();
		@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
		DefaultListModel model = new DefaultListModel() {
	            {
	            	for (int i=0;i<stack.length;i++){
	                addElement(stack[i]);
	            	}
	            }
	        };
		ViewBox.stackList.setModel(model);		
		for (int i = 1; i <9; i++){
			TableColumn col = ViewBox.register_table.getColumnModel().getColumn(i);
			   col.setCellEditor(new Editor());
			   
			}
		
	}
	
	public static void updateTableA(){
		
		Object[][] rows = new Object [2][9];
		String[] columns = new String [9];
		int trisA = myRegister.getTrisA();
		int portA = myRegister.getPortA();
		columns[0] = "Port A";
		int j = 1;
		int index = 8;
		
		for (int i = 1; i<=8; i++)
			columns[i] = Integer.toString(8-i);
		
		rows[0][0] = "TrisA";
		
		while (j <=128)
		{
			if ((trisA & j) == j)
				rows[0][index] = "in";
			else
				rows[0][index] = "out";
			
			j = 2*j;
			index--;
		}
		j = 1;
		index = 8;
		
		while (j <=128)
		{
			if ((portA & j) == j)
				rows[1][index] = "1";
			else
				rows[1][index] = "0";
			
			j = 2*j;
			index--;
		}
		
		ViewBox.portA_table.setModel(new DefaultTableModel(rows,columns));
	}

	
	public static void updateTableB(){
		
		Object[][] rows = new Object [2][9];
		String[] columns = new String [9];
		int trisB = myRegister.getTrisB();
		int portB = myRegister.getPortB();
		columns[0] = "Port B";
		int j = 1;
		int index = 8;
		
		for (int i = 1; i<=8; i++)
			columns[i] = Integer.toString(8-i);
		
		rows[0][0] = "TrisB";
		
		while (j <=128)
		{
			if ((trisB & j) == j)
				rows[0][index] = "in";
			else
				rows[0][index] = "out";
			
			j = 2*j;
			index--;
		}
		j = 1;
		index = 8;
		
		while (j <=128)
		{
			if ((portB & j) == j)
				rows[1][index] = "1";
			else
				rows[1][index] = "0";
			
			j = 2*j;
			index--;
		}
		
		ViewBox.portB_table.setModel(new DefaultTableModel(rows,columns));
	}
	
	
	
	
	public static void buttonLoad(){
		
		
	}
	
	@SuppressWarnings("resource")
	public static String[] loadFile(String filename, String dir){
		try {
			
			File sourcecode = new File(dir + "\\" + filename);
			BufferedReader in = new BufferedReader(new FileReader(sourcecode));
			String zeile = null;
			ArrayList<String> quellCodeArrayList= new ArrayList<String>();
			while ((zeile = in.readLine()) != null) {
				quellCodeArrayList.add(zeile);
			}
			Object[] quellCodeObject= quellCodeArrayList.toArray();
			String[] quellcodeStringArray = new String[quellCodeObject.length];
			for (int i=0; i <quellCodeObject.length;i++){
			quellcodeStringArray[i] = quellCodeObject[i].toString();
			System.out.println(quellcodeStringArray[i]);
			}
			return quellcodeStringArray;	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int[] convertStringFileToIntFile(String[] stringquellcode){
		int index=0;
		for (int i=0;i<stringquellcode.length;i++){
			char erstesZeichen= stringquellcode[i].charAt(0);
			if(erstesZeichen != 32){
				index++;
				}
			}
		int[] befehlscode = new int[index];
		int befehlscodenummerierung = 0;
		for (int i=0;i<stringquellcode.length;i++){
			char erstesZeichen= stringquellcode[i].charAt(0);
			if(erstesZeichen != 32){
				ViewBox.positionInStringArray[befehlscodenummerierung]= i;
				String befehlsCodeinString= stringquellcode[i].substring(5, 9);
				int temp= Integer.parseInt(befehlsCodeinString,16);
				befehlscode[befehlscodenummerierung]= temp;
				befehlscodenummerierung++;
			} 
		}
	befehlscodeInElementhandler = befehlscode;	
	return befehlscode;
	}

	public static void startNextCommand(int[] befehlscode){
		
		try {
		//Exception abfangen, falls Start
		
		Interpreter.befehlserkennung(befehlscode[Elementhandlers.myRegister.getProgrammcounter()]);
		//myRegister.printRegister();
					
		updateTable();
		updateTableA();
		updateTableB();
		
		
		
		pointerTable = ViewBox.positionInStringArray[Elementhandlers.myRegister.getProgrammcounter()];
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

	            if (row == pointerTable) {
	                renderer.setBackground(Color.RED);
	            } else {
	                renderer.setBackground(originalColor); // Retore original color
	            }
	            return renderer;
	        }
		});
		ViewBox.table.setRowSelectionInterval(pointerTable,pointerTable);
		ViewBox.table.clearSelection();
		
		//überprüfe ob nächster Befehl mit Breakpoint
		int nextProgrammcounter = Elementhandlers.myRegister.getProgrammcounter();
		int lineInProgrammTable = ViewBox.positionInStringArray[nextProgrammcounter];
		boolean nextlineBP= (boolean) ViewBox.table.getValueAt(lineInProgrammTable, 0); 
		if(nextlineBP== true){
			startStop=false;
		}
		}
		catch(NullPointerException e1) { System.out.println("Zuerst Befehlscode einlesen!" + e1);}
	}
	
}

class Editor implements TableCellEditor{
	/** das Textfeld des Editors */
	private JTextField textField = new JTextField();
	/** Observer des Editors */
	private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();
 
	// Prepariert den Editor, so dass ein neuer Wert angezeigt wird.
	public Component getTableCellEditorComponent( 
			JTable table, Object value, boolean isSelected, int row, int column ){
 
		textField.setText( String.valueOf( value ) );
		return textField;
	}
 
	public void addCellEditorListener( CellEditorListener l ){
		listeners.add( l );
	}
 
	public void removeCellEditorListener( CellEditorListener l ){
		listeners.remove( l );
	}
 
	// Gibt den aktuellen Wert des Editors zurück.
	public Object getCellEditorValue(){
		return textField.getText();
	}
 
	public boolean isCellEditable( EventObject anEvent ){
		return true;
	}
 
	public boolean shouldSelectCell( EventObject anEvent ){
		return true;
	}
 
	// Informiert alle Observer, dass die Aktion abgebrochen wurde.
	public void cancelCellEditing(){
		ChangeEvent event = new ChangeEvent( this );
		for( CellEditorListener listener : 
			listeners.toArray( new CellEditorListener[ listeners.size() ] )){
 
			listener.editingCanceled( event );
			
		}
	}
 
	// Informiert alle Observer, dass der Wert gespeichert werden soll.
	public boolean stopCellEditing(){
		ChangeEvent event = new ChangeEvent( this );
		for( CellEditorListener listener : 
			listeners.toArray( new CellEditorListener[ listeners.size() ] )){
			listener.editingStopped( event );
			
			int column = ViewBox.register_table.getSelectedColumn();
			int row = ViewBox.register_table.getSelectedRow();
			int register = row * 8 + column -1;
			//listener.
			//System.out.println(test1);
			Object temp = ViewBox.register_table.getValueAt(row, column);
			String temp2 = temp.toString();
			
			int inhalt = Integer.parseInt(temp2, 16);
			
			Elementhandlers.myRegister.fromTableToRegister(inhalt, register);
		//	Elementhandlers.myRegister.printRegister();
		}
 
		// informiert den Aufrufer, dass der Input korrekt ist.
		return true;
	}
}

class HauptStartThread implements Runnable{
	@Override public void run()
	  {
		while (Elementhandlers.startStop == true){
			Elementhandlers.startNextCommand(Elementhandlers.befehlscodeInElementhandler);
			try {
				
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  }
}
class HauptNextStepThread implements Runnable{
	@Override public void run()
	  {
			Elementhandlers.startNextCommand(Elementhandlers.befehlscodeInElementhandler);
			try {
				
				Thread.sleep(200);
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	  }
}


