package instruction;

import cpu.CPU;

/*-------------------------------------------------*/
// control instructions 
/*-------------------------------------------------*/
public class Control {
    public static void JMP(String data) {
        int address = Integer.parseInt(data);
        CPU.registers.setPtrs("PC", address);
    }
}