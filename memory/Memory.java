package memory;

import operation.*;
import instruction.Instruction;
import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
    private String mem[];
    int currAddress;
    
    public Memory() {
        mem = new String[65536];
        Arrays.fill(mem, "00h");
    }

    public void setCurrAddress(int address) {
        this.currAddress = address;
    }

    public void write(String inst) {
        String[] d = Instruction.process(inst);
        mem[currAddress++] = Opcode.fetch(d[0]);
        for (int i=d.length-1; i>=1; i--) {
            mem[currAddress++] = d[i];
        }
    }

    public void write(int address, String data) {
        // if (Utility.checkFormat8Bit(data) == false) {
        //     Utility.error("_wrong_format_input");
        // }
        mem[address] = data;
    }

    public String read(int address) {
        return mem[address];
    }
}