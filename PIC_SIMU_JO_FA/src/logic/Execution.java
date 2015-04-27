package logic;

import handler.*;
public class Execution{
	
	public static void	startBefehlADDWF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int wRegister =Elementhandlers.myRegister.getWRegister();
		int temp = wRegister + fRegister;
		// C ist Carry h�her als 8 Bit // CD ist Carry von 15 auf 16 also �bertrag von 4 nach 5 Bit
		check_C_DC_Flag(wRegister,fRegister);
		int result = temp & 0b0011111111;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlANDWF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = wRegister & fRegister;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlCLRF(int f){
		f= checkFsimilarZero(f);
		int result = 0;
		checkZFlag(result);
		insertInRegister(result,f,1);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlCLRW(){
		int result = 0;
		checkZFlag(result);
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlCOMF(int f,int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int result= ~fRegister;
		result = result & 0xFF;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlDECF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		if(fRegister==0){
			fRegister=256;
		}
		int result = fRegister- 1;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlDECFSZ(int f,int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		if(fRegister==0){
			fRegister=256;
		}
		int result = fRegister- 1;
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		if (result==0){
			startBefehlNOP();
		}
		return;
	}
	public static void	startBefehlINCF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int result= fRegister + 1;
		if (result>255){
		result =0;
		}
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlINCFSZ(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int result = fRegister +1;
		if (result>255){
			result=0;
			startBefehlNOP();
		}
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlIORWF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = fRegister|wRegister;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlMOVF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int result= fRegister;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlMOVWF(int f){
		f= checkFsimilarZero(f);
		int wRegister = Elementhandlers.myRegister.getWRegister();		
		int result= wRegister;
		insertInRegister(result,f,1);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlNOP(){
		// Nothing
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlRLF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);		
		int result=Integer.rotateLeft(fRegister, 1);
		//Aktuelles CFlag holen
		int zFlag = Elementhandlers.myRegister.getCFlag();
		//9tes Bit in CFlag schieben
		int temp = result & 0b100000000;
		if (temp==0){
			Elementhandlers.myRegister.clearCFlag();
		}
		else{
			Elementhandlers.myRegister.setCFlag();
		}
		// Entfernen 9tes Bit
		result = result & 0b011111111;
		//Altes Z-Flag hinten anschlie�en
		result = result | zFlag;
		
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlRRF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);		
		int zFlag = Elementhandlers.myRegister.getCFlag();
		// letzes Bit in C schieben
		int temp = fRegister & 1; 
		if (temp==0){
			Elementhandlers.myRegister.clearCFlag();
		}
		else{
			Elementhandlers.myRegister.setCFlag();
		}
		// Rotate Right Befehl
		int result=Integer.rotateRight(fRegister, 1);
		//64tes Bit l�schen
		result = result & 0b11111111;
		
		//Altes Z-Flag auf Position setzen
		if (zFlag>0){
		result = result | 0b10000000;
		}
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlSUBWF(int f, int d){
		//TODO: Effected C,DC,Z --> Check on Zweierkomplement stimmt
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int wRegister = Elementhandlers.myRegister.getWRegister();
		wRegister = (wRegister^0xFF) +1;// Zweierkomplement
		int result = fRegister + wRegister;
		check_C_DC_Flag(wRegister,fRegister);
		result = result & 0b11111111;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlSWAPF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		
		// Rotate Right Befehl
				int resultnewZerotoThree=Integer.rotateRight(fRegister, 4);
				//63-60tes Bit l�schen
				resultnewZerotoThree = resultnewZerotoThree & 0b11111111;
				int resultnewfourtoseven=Integer.rotateLeft(fRegister, 4);
				//12-9tes Bit l�schen
				resultnewfourtoseven = resultnewfourtoseven & 0b11111111;
				int result = resultnewfourtoseven+ resultnewZerotoThree;
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlXORWF(int f, int d){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = fRegister^wRegister;
		checkZFlag(result);
		insertInRegister(result,f,d);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlBCF(int f, int b){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int mask = checkBit(b);
		int result = fRegister & (~mask);
		insertInRegister(result,f,1);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlBSF(int f, int b){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int mask = checkBit(b);
		int result= fRegister | mask;
		insertInRegister(result,f,1);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlBTFSC(int f, int b){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int mask = checkBit(b);
		int result= fRegister & mask;
		Elementhandlers.myRegister.increaseProgrammcounter();
		if (result == 0){
			startBefehlNOP();
		}
		return;
	}
	public static void	startBefehlBTFSS(int f, int b){
		f= checkFsimilarZero(f);
		int fRegister = Elementhandlers.myRegister.getRegisterValue(f);
		int mask = checkBit(b);
		int result= fRegister & mask;
		Elementhandlers.myRegister.increaseProgrammcounter();
		if (result > 0){
			startBefehlNOP();
		}		
		return;
	}
	public static void	startBefehlADDLW(int k){
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = wRegister + k;
		check_C_DC_Flag(wRegister,k);
		result = result & 0b0011111111;
		insertInRegister(result,1337,0);
		checkZFlag(result);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlANDLW(int k){
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = wRegister & k;
		checkZFlag(result);
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlCALL(int klong){
		//TODO: Call 2 Cycle-instruction --> PCL ????
		Elementhandlers.myRegister.programmCounterToStack();
		Elementhandlers.myRegister.setProgrammcounterByCallAndGoto(klong);
		
		return;
	}
	public static void	startBefehlCLRWDT(){
		//TODO: Reset des Watchdogtimers
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlGOTO(int klong){
		//TODO: Goto 2 Cycle-instruction --> PCL ????
		Elementhandlers.myRegister.setProgrammcounterByCallAndGoto(klong);
		return;
	}
	public static void	startBefehlIORLW(int k){
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = wRegister|k;
		checkZFlag(result);
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlMOVLW(int k){
		int result =k;
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlRETFIE(){
		//TODO: Return from Interrupt
		return;
	}
	public static void	startBefehlRETLW(int k){
		//TODO: Implement ReturnLW (mit Verschiebung in w)
		Elementhandlers.myRegister.setWRegister(k);
		Elementhandlers.myRegister.setProgrammCounterfromStack();
		return;
	}
	public static void	startBefehlRETURN(){
		//TODO: Implement Return (ohne Verschiebung in w)
		Elementhandlers.myRegister.setProgrammCounterfromStack();
		return;
	}
	public static void	startBefehlSLEEP(){
		//TODO: Impelementieren Sleep-Befehl
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlSUBLW(int k){
		int wRegister = Elementhandlers.myRegister.getWRegister();
		wRegister = (wRegister^0xFF) +1;// Zweierkomplement
		int result = k + wRegister;
		check_C_DC_Flag(wRegister,k);
		result = result & 0b11111111; // h�herwertige Bits wegmaskieren
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	public static void	startBefehlXORLW(int k){
		int wRegister = Elementhandlers.myRegister.getWRegister();
		int result = wRegister^k;
		checkZFlag(result);
		insertInRegister(result,1337,0);
		Elementhandlers.myRegister.increaseProgrammcounter();
		return;
	}
	
	// Funktion zum Einf�gen von Werten abh�ngig von d in F oder W
	private static void insertInRegister(int result, int f, int d) {
		if (d == 0){
			Elementhandlers.myRegister.setWRegister(result);
		}
		if (d == 1){
			Elementhandlers.myRegister.setRegister(f,result);
		}
	return;
	}
	private static void checkZFlag(int result){
		if (result==0){
			Elementhandlers.myRegister.setZFlag();
		}
		if (result>0){
			Elementhandlers.myRegister.clearZFlag();
		}
	}
	private static void check_C_DC_Flag(int wRegister,int fRegister) {
		int fourBitF =  fRegister & 0b1111;
		int fourBitW =  wRegister & 0b1111;
		int fourBitResult = (fourBitW+fourBitF) & 0b10000;
		
		if (fourBitResult==0){
			Elementhandlers.myRegister.clearCDFlag();
		}
		else{
			Elementhandlers.myRegister.setCDFlag();
		}
		
		int cdFlag = (fRegister+wRegister)& 0b100000000;
		if (cdFlag==0){
			Elementhandlers.myRegister.clearCFlag();
		}
		else{
			Elementhandlers.myRegister.setCFlag();
		}
	}
	private static int checkFsimilarZero(int f){
		if (f == 0){
			return Elementhandlers.myRegister.getRegisterValue(4);			
		}
		else{
			return f;
		}
	}
	private static int checkBit (int b)
	{
		switch (b){
		case 0:
			return 0b00000001;
		case 1:
			return 0b00000010;
		case 2:
			return 0b00000100;
		case 3:
			return 0b00001000;
		case 4:
			return 0b00010000;
		case 5:
			return 0b00100000;
		case 6:
			return 0b01000000;
		case 7:
			return 0b10000000;
		default:
			break;
		}
		return 0;
	}
}
