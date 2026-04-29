package instruction;

import cpu.CPU;
import operation.Utility;
import instruction.*;
import java.util.Arrays;

public class Instruction {

    /*-------------------------------------------------*/
    // process instruction to split instruction and data
    /*-------------------------------------------------*/
    public static String[] process(String d) {
        if (d.length() == 0) return new String[]{"NOP"};
        
        String[] inst = d.split("[\\s]+");
        for (int i=1; i<inst.length; i++) {

            if ( inst[i].length() >= 4 && (Utility.isData(inst[i]) || Utility.isAddress(inst[i])) ) {
                return new String[] {
                    String.join(" ", Arrays.copyOfRange(inst, 0, i)),
                    inst[i].substring(0, 2),
                    inst[i].substring(2)
                };
            } 
         
            else if ( inst[i].length() == 3 && Utility.isData(inst[i]) ) {
                return new String[] {
                    String.join(" ", Arrays.copyOfRange(inst, 0, i)),
                    inst[i]
                };
            }
        }
        return new String[] {d};
    }


    /*-------------------------------------------------*/
    // instruction call
    /*-------------------------------------------------*/
    public static void call(String[] inst) {
        switch( inst[0] ) {
            case "MOV":
                Transfer.MOV(inst[1].charAt(0), inst[2].charAt(0));
                break;
            case "ADD":
                Arithmetic.ADD(inst[1].charAt(0));
                break;
            case "ADC":
                Arithmetic.ADD(inst[1].charAt(0), (CPU.flags.getC() ? 1 : 0));
                break;
            case "LDA":
                Transfer.LDA(inst[2]+inst[1]);
                break;
            case "STA":
                Transfer.STA(inst[2]+inst[1]);
                break;
            case "MVI":
                Transfer.MVI(inst[1].charAt(0), inst[2]);
                break;
            case "ADI":
                Arithmetic.ADI(inst[1]);
                break;
            case "SUB":
                Arithmetic.SUB(inst[1].charAt(0));
                break;
            case "CMA":
                Logic.CMA();
                break;
            case "INR":
                Arithmetic.INR(inst[1].charAt(0));
                break;
            // case "DCR":
            //     Arithmetic.DCR(inst[1].charAt(0));
            //     break;
            case "ANA":
                Logic.ANA(inst[1].charAt(0));
                break;
            case "ORA":
                Logic.ORA(inst[1].charAt(0));
                break;
            case "LXI":
                Transfer.LXI(inst[1], inst[3]+inst[2]);
                break;
            case "JMP":
                Control.JMP(inst[2] + inst[1]);
                break;
            case "CALL":
                Control.CALL(inst[2] + inst[1]);
                break;
            case "RET":
                Control.RET();
                break;
            case "PUSH":
                Control.PUSH(inst[1]);
                break;
            case "POP":
                Control.POP(inst[1]);
                break;
            case "HLT":
                break;
            case "NOP":
                break;
        }
    }
}


/*
MVI A 05h

MVI A | 05h

inst[]  MVI | A | 05h


*/