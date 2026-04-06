package instruction;

import cpu.CPU;

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
        int address = Integer.parseInt(data);
        CPU.registers.set('A', CPU.mem.read(address));
        CPU.flags.update(CPU.registers.get('A'));
    }

    static void STA(String data) {
        int address = Integer.parseInt(data);
        CPU.mem.write(address, CPU.registers.get('A'));
    }

    static void LXI(String reg, String data) {
        CPU.registers.set(reg, data);
    }
}