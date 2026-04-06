package cpu;

import cpu.*;
import operation.*;
import memory.Memory;
import instruction.Instruction;

import java.util.Scanner;

public class CPU {
    /*-------------------------------------------------*/
    // CPU members
    /*-------------------------------------------------*/
    static public Register registers;
    static public Flag flags;
    static public Memory mem;


    /*-------------------------------------------------*/
    // constructor
    /*-------------------------------------------------*/
    CPU() {
        registers = new Register();
        flags = new Flag();
        mem = new Memory();
    }


    /*-------------------------------------------------*/
    // input method
    /*-------------------------------------------------*/
    private void input(Scanner sc) {
        int startAddress = sc.nextInt();
        sc.nextLine();
        
        this.mem.setCurrAddress(startAddress);
        
        while (true) {
            String input = sc.nextLine();
            this.mem.write(input);
            if (input.equals("HLT")) 
                { break; }
        }
    }


    /*-------------------------------------------------*/
    // excecute method
    /*-------------------------------------------------*/
    private void execute(Scanner sc) {
        int address = sc.nextInt();
        sc.nextLine();
        this.registers.setPtrs("PC", address);
        while (true) {
            String[] inst = Opcode.decode(this.mem.read(this.registers.getPtrs("PC")));
            this.registers.increment("PC");
            if (inst[0].equals("HLT")) break;

            int i = 1;
            while (i < inst.length && inst[i] != null) i++;
            while (i < inst.length) {
                inst[i] = this.mem.read(this.registers.getPtrs("PC"));
                this.registers.increment("PC");
                i++;
            }
            
            Instruction.call(inst);
        }
    }


    /*-------------------------------------------------*/
    // memory access
    /*-------------------------------------------------*/
    private void memoryAccess(Scanner sc) {
        int address = sc.nextInt();
        sc.nextLine();
        String s = "";
        while (true) {
            System.out.print(address + " - " + mem.read(address) + " - ");
            s = sc.nextLine();
            if (s.length() == 2) this.mem.write(address, s);
            else if (s.length() == 1) break;
            address++;
        }
    }

    public static void main(String[] args) {
        CPU cpu = new CPU();
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        boolean run = true;

        while (run) {
            System.out.println("1. Input");
            System.out.println("2. Memory Access");
            System.out.println("3. Execute");
            System.out.print("Choice : ");
            choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    cpu.input(sc);
                    break;
                case 2:
                    cpu.memoryAccess(sc);
                    break;
                case 3:
                    cpu.execute(sc);
                    break;
                case 4:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    run = false;
                    break;
            }
        }
        sc.close();
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