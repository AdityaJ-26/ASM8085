package instruction;

import cpu.CPU;
import operation.Utility;
import operation.StringConversion;

public class Arithmetic {
    public static void ADD(char reg) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));
        
        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        StringBuffer resBin = new StringBuffer(op1.length());
        int len = op1.length();
        
        int carry = 0;  
        for (int i=0; i<len; i++) {
            if (i == 4) {
                CPU.flags.setAC(carry == 1);
            }

            int s = (op1.charAt(len-i-1) - '0') + (op2.charAt(len-i-1) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(i, s);
        }
        resBin.reverse();
        CPU.flags.setC(carry == 1);

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }

    public static void ADD(char reg, int carry) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));
        
        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        StringBuffer resBin = new StringBuffer(op1.length());
        int len = op1.length() - 1;

        for (int i=0; i<len; i++) {
            if (i == 4) {
                CPU.flags.setAC(carry == 1);
            }

            int s = (op1.charAt(len-i-1) - '0') + (op2.charAt(len-i-1) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(i, s);
        }
        resBin.reverse();
        CPU.flags.setC(carry == 1);

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }

    public static void ADI(String data) {
        CPU.registers.set('W', data);
        Arithmetic.ADD('W');
    }

    public static void SUB(char reg) {
        String op1 = StringConversion.hexaToBinary(CPU.registers.get('A'));
        String op2 = StringConversion.hexaToBinary(CPU.registers.get(reg));

        if (op1.length() != op2.length()) {
            Utility.error("_incompatible_operands");
        }

        op2 = StringConversion.twosCompliment(op2);

        StringBuffer resBin = new StringBuffer(op1.length());
        int len = op1.length();

        int carry = 0;
        for (int i=0; i<len; i++) {
            if (i == 4) {
                CPU.flags.setAC(carry == 1);
            }

            int s = (op1.charAt(len-i-1) - '0') + (op2.charAt(len-i-1) - '0') + carry;
            carry = s/2;
            s %= 2;
            resBin.insert(i, s);
        }
        resBin.reverse();
        CPU.flags.setC( carry != 1 );

        String res = StringConversion.binaryToHexa(resBin);
        CPU.flags.update(res);

        CPU.registers.set('A', res);
    }
}
