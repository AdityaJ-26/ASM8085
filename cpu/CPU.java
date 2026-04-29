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
    public CPU() {
        registers = new Register();
        flags = new Flag();
        mem = new Memory();
    }


    /*-------------------------------------------------*/
    // access method
    /*-------------------------------------------------*/
    private void regAccess(Scanner sc) {
        String reg;
        System.out.print("Enter Register : ");
        reg = sc.next();

        System.out.print(reg.charAt(0) + " - " + this.registers.get(reg.charAt(0)) + " - ");
        sc.nextLine();
        String input = sc.nextLine();
        if (input.length() == 0 || input.length() == 1) {
            return;
        }
        else {
            this.registers.set(reg.charAt(0), input);
        }
    }

    public String flagStatus() {
        return new String(
            "Sign      : " + this.flags.getS() + "\n" +
            "Carry     : " + this.flags.getC() + "\n" +
            "Aux Carry : " + this.flags.getAC() + "\n" +
            "Parity    : " + this.flags.getP() + "\n" +
            "Zero      : " + this.flags.getZ() + "\n"
        );
    }

    public String registerStatus() {
        return new String(
            "A  : " + this.registers.get('A') + "\n" +
            "B  : " + this.registers.get('B') + "\n" +
            "C  : " + this.registers.get('C') + "\n" +
            "D  : " + this.registers.get('D') + "\n" +
            "E  : " + this.registers.get('E') + "\n" +
            "H  : " + this.registers.get('H') + "\n" +
            "L  : " + this.registers.get('L') + "\n" +
            "PC : " + this.registers.getPtrs("PC") + "\n" +
            "SP : " + this.registers.getPtrs("SP") + "\n"
        );
    }


    /*-------------------------------------------------*/
    // input method
    /*-------------------------------------------------*/
    public void inputAddress(int address) {
        this.mem.setCurrAddress(address);
    }

    public int input(String instruction) {
        int nextAddress = this.mem.writeInst(instruction);
        return nextAddress;
    }

    /*-------------------------------------------------*/
    // excecute method
    /*-------------------------------------------------*/
    public void execute(int address) {
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
    public String memRead(int address) {
        return this.mem.read(address);
    }

    public void memWrite(int address, String data) {
        this.mem.write(address, data);
    }

    private void memoryAccess(Scanner sc) {
        System.out.print("Enter Access Address : ");
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
}


    /*-------------------------------------------------*/
    // main()
    /*-------------------------------------------------*/
//     public static void main(String[] args) {
//         CPU cpu = new CPU();
//         Scanner sc = new Scanner(System.in);
//         int choice = -1;
//         boolean run = true;

//         while (run) {
//             System.out.println();
//             System.out.println("1. Input");
//             System.out.println("2. Memory Access");
//             System.out.println("3. Execute");
//             System.out.println("4. Register Access");
//             System.out.print("Choice : ");
//             choice = sc.nextInt();
            
//             switch (choice) {
//                 case 1:
//                     System.out.print("Enter Starting Address : ");
//                     sc.nextLine();
//                     String startAddress = sc.nextLine();
//                     cpu.input(sc, startAddress);
//                     break;
//                 case 2:
//                     cpu.memoryAccess(sc);
//                     break;
//                 case 3:
//                     cpu.execute(sc);
//                     System.out.println();
//                     System.out.println("--- Flags State ---");
//                     System.out.println("Sign Flag : " + cpu.flags.getS());
//                     System.out.println("Carry Flag : " + cpu.flags.getC());
//                     System.out.println("Auxillary Carry Flag : " + cpu.flags.getAC());
//                     System.out.println("Parity Flag : " + cpu.flags.getP());
//                     System.out.println("Zero Flag : " + cpu.flags.getZ());
//                     break;
//                 case 4:
//                     cpu.regAccess(sc);
//                     break;
//                 case 5:
//                     System.out.print("\033[H\033[2J");
//                     System.out.flush();
//                     break;
//                 case 0:
//                     System.out.println("Exiting...");
//                     run = false;
//                     break;
//             }
//         }
//         sc.close();
//     }
// }

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