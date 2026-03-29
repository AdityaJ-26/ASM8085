package instruction;

import cpu.CPU;
import operation.Utility;
import operation.StringConversion;

class Arithmetic {
    static void ADD(char reg) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));
        
        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        StringBuffer resBin = new StringBuffer(op1.length());
        
        int carry = 0;  
        int i = op1.length() - 1;
        for (; i >= 0; i--) {
            if (i == 3) {
                CPU.flags.setAC(carry == 1);
            }
            int s = (op1.charAt(i) - '0') + (op2.charAt(i) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(0, s);
        }
        CPU.flags.setC(carry == 1);

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }

    static void ADD(char reg, int carry) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));
        
        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        StringBuffer resBin = new StringBuffer(op1.length());

        int i = op1.length() - 1;
        for (; i >= 0; i--) {
            if (i == 3) {
                CPU.flags.setAC(carry == 1);
            }
            int s = (op1.charAt(i) - '0') + (op2.charAt(i) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(0, s);
        }
        CPU.flags.setC(carry == 1);

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }

    static void ADI(String data) {
        CPU.registers.set('W', data);
        Arithmetic.ADD('W');
    }

    static void SUB(char reg) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));

        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        op2 = StringConversion.twosCompliment(op2);

        StringBuffer resBin = new StringBuffer();

        int carry = 0;
        int i = op1.length()-1;
        for (; i >= 0; i--) {
            if (i == 3) {
                CPU.flags.setAC(carry == 1);
            }
            int s = (op1.charAt(i) - '0') + (op2.charAt(i) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(0, s);
        }
        CPU.flags.setC( carry != 1 );

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }

    static void INR(char reg) {
        String op = StringConversion.hexaToBinary(CPU.registers.get(reg));

        StringBuffer resBin = new StringBuffer();

        int carry = 1;  
        int i = op.length()-1;
        for (; i >= 0 ; i--) {
            if (i == 3) {
                CPU.flags.setAC(carry == 1);
            }
            int s = (op.charAt(i) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(0, s);
        }

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set(reg, res);
    }
}

// MVI A 05h
// MVI B 06h
// ADD B
// STA 2050
// MOV B,A
// INR B
// MOV A,B
// STA 2051
// ADI 16h
// STA 2052
// SUB B
// STA 2053
// CMA
// STA 2054
// HLT