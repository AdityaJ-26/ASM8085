package instruction;

import cpu.CPU;
import operation.StringConversion;

class Transfer {

    /*-------------------------------------------------*/
    // data read, write, load and copy instructions
    /*-------------------------------------------------*/
    static void MOV(char reg1, char reg2) {
        CPU.registers.set(reg1, CPU.registers.get(reg2));
        if (reg1 == 'A') {
            CPU.flags.update(CPU.registers.get('A'));
        }
    }

    static void MVI(char reg, String data) {
        CPU.registers.set(reg, data);
        if (reg == 'A') {
            CPU.flags.update(data);
        }
    }

    static void LDA(String data) {
        int address = StringConversion.hexaToDecimal(data);
        CPU.registers.set('A', CPU.mem.read(address));
        CPU.flags.update(CPU.registers.get('A'));
    }

    static void STA(String data) {
        int address = StringConversion.hexaToDecimal(data);
        CPU.mem.write(address, CPU.registers.get('A'));
    }

    static void LXI(String reg, String data) {
        if (reg.equals("PC") || reg.equals("SP")) {
            data = data.substring(0, data.length() - 1);
            CPU.registers.setPtrs(reg, Integer.parseInt(data));
        }
        else {
            CPU.registers.set(reg, data);
        }  
    }
}