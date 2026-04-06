package instruction;

import operation.*;
import cpu.CPU; 

class Logic {

    /*-------------------------------------------------*/
    // logical operations
    /*-------------------------------------------------*/
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


    /*-------------------------------------------------*/
    // complement method
    /*-------------------------------------------------*/
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


    /*-------------------------------------------------*/
    // logical instructions
    /*-------------------------------------------------*/
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

    static void ORA(char reg) {
        String op1 = StringConversion.hexaToBinary( CPU.registers.get('A') );
        String op2 = StringConversion.hexaToBinary( CPU.registers.get(reg) );

        StringBuffer resBin = new StringBuffer();

        for (int i=op1.length()-1; i >= 0; i--) {
            resBin.insert( 0, OR(op1.charAt(i), op2.charAt(i)) );
        }

        CPU.flags.setC(false);
        String res = StringConversion.binaryToHexa(resBin);
        CPU.registers.set('A', res);
        CPU.flags.update(res);
    }


    /*-------------------------------------------------*/
    // compare instruction
    /*-------------------------------------------------*/
    void CMP(char reg) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));

        if (op1.equals(op2)) {
            CPU.flags.setZ( true );
            CPU.flags.setP( true );
            CPU.flags.setS( false );
            return;
        }

        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        op2 = StringConversion.twosCompliment(op2);
        StringBuffer resBin = new StringBuffer();

        int carry = 0;
        int i = op1.length()-1;
        for (; i >= 0; i--) {
            if (i == 3) {
                CPU.flags.setAC(carry != 1);
            }
            int s = (op1.charAt(i) - '0') + (op2.charAt(i) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(0, s);
        }

        if (carry != 1) {
            CPU.flags.setC( true );
            CPU.flags.setS( true );
        }
        else {
            CPU.flags.setC( false );
            CPU.flags.setS( false );
        }

        int count = 0;
        for (int j = 0; j<resBin.length(); j++) {
            if (resBin.charAt(j) == '1') {
                count++;
            }
        }

        CPU.flags.setP( count % 2 == 0 );
        CPU.flags.setZ( false );
    }
}