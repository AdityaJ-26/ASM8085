package instruction;

import cpu.CPU;

public class Transfer {
    public static void MOV(char reg1, char reg2) {
        CPU.registers.set(reg1, CPU.registers.get(reg2));
        if (reg1 == 'A') {
            CPU.flags.update(CPU.registers.get('A'));
        }
    }

    public static void MVI(char reg, String data) {
        CPU.registers.set(reg, data);
        if (reg == 'A') {
            CPU.flags.update(data);
        }
    }

    public static void LDA(String data) {
        int address = Integer.parseInt(data);
        CPU.registers.set('A', CPU.mem.read(address));
        CPU.flags.update(CPU.registers.get('A'));
    }

    public static void STA(String data) {
        int address = Integer.parseInt(data);
        CPU.mem.write(address, CPU.registers.get('A'));
    }
}