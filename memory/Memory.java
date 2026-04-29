package memory;

import operation.*;
import instruction.Instruction;
import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
    private String mem[];
    int currAddress;
    
    /*-------------------------------------------------*/
    // constructor
    /*-------------------------------------------------*/
    public Memory() {
        mem = new String[65536];
        Arrays.fill(mem, "00h");
    }

    /*-------------------------------------------------*/
    // public function for input
    /*-------------------------------------------------*/
    public void setCurrAddress(int address) {
        this.currAddress = address;
    }
    public int getCurrAddress() {
        return this.currAddress;
    }

    /*-------------------------------------------------*/
    // read and write operations
    /*-------------------------------------------------*/
    public int writeInst(String inst) {
        String[] d = Instruction.process(inst);
        mem[currAddress++] = Opcode.fetch(d[0]);
        for (int i=d.length-1; i>=1; i--) {
            mem[currAddress++] = d[i];
        }
        return currAddress;
    }

    public void write(int address, String data) {
        mem[address] = data;
    }

    public String read(int address) {
        return mem[address];
    }
}