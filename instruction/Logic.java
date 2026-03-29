package instruction;

import operation.*;
import cpu.CPU; 

class Logic {
    static char AND(char a, char b) {
        if (a == '1' && b == '1') {
            return '1';
        }
        return '0';
    }
    static char OR(char a, char b) {
        if (a == '0' && b == '0') {
            return '0';
        }
        return '1';
    }
    static char NOT(char a) {
        if (a == '1') return '0';
        return '1';
    }

    static void CMA() {
        StringBuffer binary;
        {
            String temp = StringConversion.hexaToBinary(CPU.registers.get('A'));
            binary = new StringBuffer(temp);
        }
        for (int i = 0; i<binary.length(); i++) {
            binary.setCharAt(i, NOT(binary.charAt(i)));
        }
        CPU.registers.set('A', StringConversion.binaryToHexa(binary));
    }

    static void ANA(char reg) {
        String op1 = StringConversion.hexaToBinary( CPU.registers.get('A') );
        String op2 = StringConversion.hexaToBinary( CPU.registers.get(reg) );

        StringBuffer resBin = new StringBuffer();

        for (int i=op1.length()-1; i >= 0; i--) {
            resBin.insert( 0, AND( op1.charAt(i), op2.charAt(i) ) );
        }

        CPU.flags.setAC(true);
        CPU.flags.setC(false);

        String res = StringConversion.binaryToHexa(resBin);
        CPU.registers.set('A', res);
        CPU.flags.update(res);
    }
}