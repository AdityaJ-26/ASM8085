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

    public static void CALL(String data) {
        int address = CPU.registers.getPtrs("SP");

        address--;       
        String retAddress = String.valueOf(CPU.registers.getPtrs("PC"));
        CPU.registers.decrement("SP");
        CPU.mem.write(address, retAddress.substring(2) + "h");
        
        address--;
        CPU.registers.decrement("SP");
        CPU.mem.write(address, retAddress.substring(0, 2) + "h");

        CPU.registers.setPtrs("PC", Integer.parseInt(data));
    }

    public static void RET() {
        int address = CPU.registers.getPtrs("SP");

        String data = CPU.mem.read(address).replace("h", "");
        address++;
        CPU.registers.increment("SP");

        data = data + CPU.mem.read(address).replace("h", "");
        CPU.registers.increment("SP");

        CPU.registers.setPtrs("PC", Integer.parseInt(data));
    }

    public static void PUSH(String reg) {
        String data = CPU.registers.get(reg);
        int address = CPU.registers.getPtrs("SP");

        address--;       
        CPU.registers.decrement("SP");
        CPU.mem.write(address, data.substring(2));
        
        address--;
        CPU.registers.decrement("SP");
        CPU.mem.write(address, data.substring(0, 2) + "h");
    }

    public static void POP(String reg) {
        int address = CPU.registers.getPtrs("SP");
        String data = CPU.mem.read(address).replace("h", "");

        address++;
        CPU.registers.increment("SP");

        data = data + CPU.mem.read(address).replace("h", "");
        CPU.registers.increment("SP");

        CPU.registers.set(reg, data);
    }
}