package logic;

public class Interpreter {
	
	public static void befehlserkennung(int befehlscode){
	
	
	int kategorie = befehlscode & 0x3000; //00xx entscheiden, 
	kategorie = kategorie >> 12;
	
	int f = befehlscode & 0x007F; // hier ist kein Shiften nötig; steht am Ende
	
	int d = befehlscode & 0x0080;
	d = d >> 7;   // shiften, d steht an 8. Stelle im Bitmuster, es ergäbe sich ein falscher Wert
	
	int b = befehlscode & 0x0380;
	b = b >> 7;  // shiften, b steht an 8. Stelle im Bitmuster, es ergäbe sich ein falscher Wert

	int kshort = befehlscode & 0x00FF; // hier ist kein Shiften nötig; steht am Ende
	
	int klong = befehlscode & 0x07FF; // hier ist kein Shiften nötig; steht am Ende
	
	/*
	System.out.printf("Kategorie: %x \n", kategorie);
	System.out.printf("f: %x \n", f);
	System.out.printf("d: %x \n", d);
	System.out.printf("b: %x \n", b);
	System.out.printf("kshort: %x \n", kshort);
	System.out.printf("klong: %x \n", klong);
	*/
	
	
	/*
	{
	        
	        case 0:    System.out.println("Byte-oriented");
	                                 break;
	        
	        case 1:    System.out.println("Bit-oriented");
	                                 break;
	         
	        default:                 System.out.println("LC-Operations");; 
	}
    */
	
	
	// ------------------------------------------------ BYTE-Oriented
	
	if (befehlscode >= 0x0700 && befehlscode <= 0x07FF)
	{
		Execution.startBefehlADDWF(f,d);
	}
	
	else if (befehlscode >= 0x0500 && befehlscode <= 0x05FF)
	{
		Execution.startBefehlANDWF(f,d);
	}
	
	else if (befehlscode >= 0x0180 && befehlscode <= 0x01FF)
	{
		Execution.startBefehlCLRF(f);	
	}
	
	else if (befehlscode >= 0x0100 && befehlscode <= 0x017F)
	{
		Execution.startBefehlCLRW();	
	}
	
	else if (befehlscode >= 0x0900 && befehlscode <= 0x09FF)
	{
		Execution.startBefehlCOMF(f,d);	
	}
	
	else if (befehlscode >= 0x0300 && befehlscode <= 0x03FF)
	{
		Execution.startBefehlDECF(f,d);	
	}
	
	else if (befehlscode >= 0x0B00 && befehlscode <= 0x0BFF)
	{
		Execution.startBefehlDECFSZ(f,d);	
	}
	
	else if (befehlscode >= 0x0A00 && befehlscode <= 0x0AFF)
	{
		Execution.startBefehlINCF(f,d);	
	}
	
	else if (befehlscode >= 0x0F00 && befehlscode <= 0x0FFF)
	{
		Execution.startBefehlINCFSZ(f,d);	
	}
	
	else if (befehlscode >= 0x0400 && befehlscode <= 0x04FF)
	{
		Execution.startBefehlIORWF(f,d);	
	}
	
	else if (befehlscode >= 0x0800 && befehlscode <= 0x08FF)
	{
		Execution.startBefehlMOVF(f,d);	
	}
	
	else if (befehlscode >= 0x0080 && befehlscode <= 0x00FF)
	{
		Execution.startBefehlMOVWF(f);	
	}
	
	else if (befehlscode == 0x000 || befehlscode == 0x0020 || befehlscode == 0x0040 || befehlscode == 0x0060)
	{
		Execution.startBefehlNOP();	
	}
	
	else if (befehlscode >= 0x0D00 && befehlscode <= 0x0DFF)
	{
		Execution.startBefehlRLF(f,d);	
	}
	
	else if (befehlscode >= 0x0C00 && befehlscode <= 0x0CFF)
	{
		Execution.startBefehlRRF(f,d);	
	}
	
	else if (befehlscode >= 0x0200 && befehlscode <= 0x02FF)
	{
		Execution.startBefehlSUBWF(f,d);	
	}
	
	else if (befehlscode >= 0x0E00 && befehlscode <= 0x0EFF)
	{
		Execution.startBefehlSWAPF(f,d);	
	}
	
	else if (befehlscode >= 0x0600 && befehlscode <= 0x06FF)
	{
		Execution.startBefehlXORWF(f,d);	
	}
	
	// ------------------------------------------------ BIT-Oriented
	
	else if (befehlscode >= 0x1000 && befehlscode <= 0x13FF)
	{
		Execution.startBefehlBCF(f,b);	
	}
	
	else if (befehlscode >= 0x1400 && befehlscode <= 0x17FF)
	{
		Execution.startBefehlBSF(f,b);	
	}
	
	else if (befehlscode >= 0x1800 && befehlscode <= 0x1BFF)
	{
		Execution.startBefehlBTFSC(f,b);	
	}
	
	else if (befehlscode >= 0x1C00 && befehlscode <= 0x1FFF)
	{
		Execution.startBefehlBTFSS(f,b);	
	}
	
	// ------------------------------------------------ Literal-/Control-Operations
	
	else if (befehlscode >= 0x3E00 && befehlscode <= 0x3FFF)
	{
		Execution.startBefehlADDLW(kshort);	
	}
	
	else if (befehlscode >= 0x3900 && befehlscode <= 0x39FF)
	{
		Execution.startBefehlANDLW(kshort);	
	}
	
	else if (befehlscode >= 0x2000 && befehlscode <= 0x27FF)
	{
		Execution.startBefehlCALL(klong);	
	}
	
	else if (befehlscode == 0x0064)
	{
		Execution.startBefehlCLRWDT();	
	}
	
	else if (befehlscode >= 0x2800 && befehlscode <= 0x2FFF)
	{
		Execution.startBefehlGOTO(klong);	
	}
	
	else if (befehlscode >= 0x3800 && befehlscode <= 0x38FF)
	{
		Execution.startBefehlIORLW(kshort);	
	}
	
	else if (befehlscode >= 0x3000 && befehlscode <= 0x33FF)
	{
		Execution.startBefehlMOVLW(kshort);	
	}
	
	else if (befehlscode == 0x0009)
	{
		Execution.startBefehlRETFIE();	
	}
	
	else if (befehlscode >= 0x3400 && befehlscode <= 0x37FF)
	{
		Execution.startBefehlRETLW(kshort);	
	}
	
	else if (befehlscode == 0x0008)
	{
		Execution.startBefehlRETURN();	
	}
	
	else if (befehlscode == 0x0063)
	{
		Execution.startBefehlSLEEP();	
	}
	
	else if (befehlscode >= 0x3C00 && befehlscode <= 0x3DFF)
	{
		Execution.startBefehlSUBLW(kshort);	
	}
	
	else if (befehlscode >= 0x3A00 && befehlscode <= 0x3AFF)
	{
		Execution.startBefehlXORLW(kshort);	
	}
	
}
	
}
