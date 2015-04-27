package logic;


import java.util.ArrayDeque;
import java.util.Deque;

public class Register {

	private int[] speicher= new int[256];
	private int wRegister;
	private int programmcounter;
	private Deque<Integer> myStack = new ArrayDeque<Integer>();
	private static boolean t0CKLChanged;
	private int psa_counter;
	
	public Register(){
		
	//init Speicher---------------- Alex meinte hier gibt es noch ein Paar Register die sind zu Anfang nicht null
	//----------------------------- müssen hier dann noch extra deklariert werdenenn
	for (int j=0;j<256;j++){
		this.speicher[j]=0;
	}
	this.speicher[0x03]=0x18;
	this.speicher[0x83]=0x18;
	this.speicher[0x81]=0xFF;
	this.speicher[0x85]= 0xFF;
	this.speicher[0x86]= 0xFF;
	
	//init programcounter---------- Zwar in den Registern enthalten, hier ist jezz die Frage ob wir es so Redundant lassen sollen
	//----------------------------- oder ob wir nur mit Speicher-Array arbeiten sollen
	this.wRegister=0;
	this.programmcounter=0;
	
	this.psa_counter=0;
	}
	
	public void setProgrammcounterByCallAndGoto(int givenPC){
		int pclath = this.speicher[10];
		pclath = (Integer.rotateRight(pclath, 3)) & 0b11;
		pclath = Integer.rotateLeft(pclath, 11);
		this.programmcounter= pclath | givenPC;
		int pcl = programmcounter & 0b11111111;
		this.speicher[0x2]= pcl;
		this.speicher[0x82]= pcl;
	}
	
	public int getTrisA(){
		return this.speicher[0x85];
	}
	
	public int getTrisB(){
		return this.speicher[0x86];
	}
	
	public int getPortA(){
		return this.speicher[0x5];
	}
	
	public int getPortB(){
		return this.speicher[0x6];
	
	}
			
	public void setPortA(int result){
		this.speicher[0x5] = result;
	}
		
	public void setPortB(int wert){
		this.speicher[0x6] = wert;
	}
	
	public int getProgrammcounter(){
		return this.programmcounter;
	}
	public int getWRegister(){
		return this.wRegister;
	}
	public void setWRegister(int result){
		this.wRegister=result;
	}
	public void setRegister(int f, int result){
		f= checkBank(f);
		boolean doppeltvorhanden = checkRedundanz(f);
		if (doppeltvorhanden == true){
			if(f<0x80){ //Bank0 Adresse
				this.speicher[f]= result;
				this.speicher[(f+0x80)]=result;
			}
			if(f>=0x80){ //Bank 1 Adresse
				this.speicher[f]= result;
				this.speicher[(f-0x80)]=result;
			}
		}
		else{
			this.speicher[f]= result;
		}
		checkPCLmanipulated(f);
	}
	public int getRegisterValue(int f){
		f= checkBank(f);
		return this.speicher[f];
	}
	public void increaseProgrammcounter(){
		this.programmcounter++;
		int pcl = programmcounter & 0b11111111;
		this.speicher[0x2]= pcl;
		this.speicher[0x82]= pcl;
		}

	public void printRegister() {
		for  (int j=0; j <128;j++){
			System.out.println("Register: " + Integer.toHexString(j) + " : " +  Integer.toHexString(speicher[j]));
			}
		System.out.println("WRegister: " + wRegister);
		System.out.println("Programmcounter: " + programmcounter);
		System.out.println("Stack: " + myStack.toString());
	}
	public void setZFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister|0b00000100;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;

	}
	public void clearZFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister & 0b11111011;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;
	}
	public void setCFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister|0b00000001;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;
	}
	public int getCFlag(){
		int statusReg =this.speicher[3];
		int result= statusReg & 0b00000001;
		
		if(result == 0){
			return 0;	
		}
		else{
		return 1;
		}		
	}
	public int getZFlag(){
		int statusReg =this.speicher[3];
		int result= statusReg & 0b00000100;
		
		if(result == 0){
			return 0;	
		}
		else{
		return 1;
		}		
	}
	
	public int getDCFlag(){
		int statusReg =this.speicher[3];
		int result= statusReg & 0b00000010;
		
		if(result == 0){
			return 0;	
		}
		else{
		return 1;
		}		
	}
	
	
	
	
	public void clearCFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister & 0b11111110;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;
		
	}
	public void setCDFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister|0b00000010;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;
	}
	public void clearCDFlag(){
			int statusregister= this.speicher[3];
			int result = statusregister & 0b11111101;
			this.speicher[0x3]= result;
			this.speicher[0x83]= result;
	}
	public void programmCounterToStack(){
		this.myStack.push(programmcounter+1);
	}
	public void setProgrammCounterfromStack(){
		int jumpbackadress =  (int) this.myStack.pop();
		setProgrammcounterByCallAndGoto(jumpbackadress);
	}
	public int[] getRegisters(){
		return this.speicher;
	}
	private int checkBank(int f){
		if((this.speicher[3]&0b00100000) <= 0) //Bank 0
		{
			if(f<0x80){		//Adresse ist auf Bank 0
				return f;
			}
			if(f>=0x80){	//Adresse ist auf Bank 1 musss aber auf Bank 0 geändert werden da Bank 0 aktiv
				return (f-0x80);
			}
		}
		if((this.speicher[3]&0b00100000) > 0) //Bank 1
		{
			if(f<0x80){		//Adresse ist auf Bank 0 muss aber auf Bank 1 geändert werden da Bank 1 aktiv
				return (f+0x80);
			}
			if(f>=0x80){	//Adresse ist auf Bank 1 
				return (f);
			}
		}
	return 0;
	}
	private boolean checkRedundanz(int f){
	if(f==0x0|f==0x2|f==0x3|f==0x4|f==0xa|f==0xb|f==0x80|f==0x82|f==0x83|f==0x84|f==0x8a|f==0x8b){
		return true;// doppelt vorhanden
	}
	else{
		return false; // einfach vorhanden
		}
	}
	
	public void fromTableToRegister(int inhalt, int adresse){
		{
			
			this.speicher[adresse] = inhalt;
			//TODO: Programmcounter beachten
					}
		}
	
	public Object[] getStack(){
		
		return (Object[]) myStack.toArray();
		
	}

	private void checkPCLmanipulated(int f){
		if (f== 2){
		int pcl = this.speicher[0x2];
		int pclath =this.speicher[0xa];
	int temp =(Integer.rotateLeft(pclath, 8));
	this.programmcounter= temp + pcl;
		}
	}

	public void checkInterrupt() {
		// Check den GolbalenInteruptEnabledBit
		if((this.speicher[0xb] & 0b10000000)!= 0){
			checkIfTimer0Interrupt();
			return;
		}
		else{
			// Interputs nicht erlaubt
			return;
		}
	}
	// 
	private void checkIfTimer0Interrupt() {
		// Check Timer0InteruptEnabledBit
		if((this.speicher[0xb] & 0b01000000)!= 0){
		
			//Check T0CS (Timer-clock Input source => 1= Transition on RA4/T0CKI pin; 0= Internal instruction cycle clock (CLKOUT))
			if((this.speicher[0x81] & 0b00100000)== 0){
				
					// Check T0SE ( +1 Timer bei Fallender Flanke = 1; Bei Steigender Flanke = 0
					if((this.speicher[0x81] & 0b00010000)== 0){
						// T0SE = 0
						if (t0CKLChanged == true){
							//TODO: Implementierung Takt an Ra4/T0CKI
						}
						else{
							//TODO: Implementierung Takt an Ra4/T0CKI
						}	
					}
					else{
						// T0SE = 1	
						if (t0CKLChanged == true){
							//TODO: Implementierung Takt an Ra4/T0CKI
						}
						else{
							//TODO: Implementierung Takt an Ra4/T0CKI
						}
					}
			}
			else{
				// T0CS = 1 // jedenBefehlstakt
				
				// Check PSA (1 = Prescaler für WDT (kein TMR0); 0 = Prescaler für TMR0)
				if((this.speicher[0x81] & 0b00001000)== 0){
					// PSA = 0
					//increase With PSA
					int psa_bits = this.speicher[0x81] & 0b111;
					switch (psa_bits){
					case 0:
						psa_counter++;
						if (psa_counter>=2){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 1:
						psa_counter++;
						if (psa_counter>=4){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 2:
						psa_counter++;
						if (psa_counter>=8){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 3:
						psa_counter++;
						if (psa_counter>=16){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 4:
						psa_counter++;
						if (psa_counter>=32){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 5:
						psa_counter++;
						if (psa_counter>=64){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 6:
						psa_counter++;
						if (psa_counter>=128){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					case 7:
						psa_counter++;
						if (psa_counter>=256){
							erhöheTimer0counter();
							psa_counter=0;
						}
						break;
					
					default:
						break;
				
					}
				}
				else{
					// PSA = 1
					erhöheTimer0counter();
				}	
			}
		
			
		
			
			return;
		}
		else{
			// Timer0 Interupt nicht enabled
			return;
		}
	}

	private void erhöheTimer0counter() {
		this.speicher[0x1]++;
		
		if(this.speicher[0x1]>255){
			//Set T0IF
			this.speicher[0x1]=0;
			this.speicher[0xB]=(this.speicher[0xB]| 0b00000100);
			this.speicher[0x8B]=this.speicher[0xB];
		}
		
	}
}

	


