package cpu;

import operation.Utility;
import cpu.CPU;
import operation.StringConversion;

public class Register {
    private String A, F;
    private String B, C;
    private String D, E;
    private String H, L;
    private int SP, PC;
    private String W;

    public Register() {
        this.A = this.F = this.B = this.B = this.C = this.D = this.E = this.H = this.L = " ";
        this.SP = this.PC = 0000;
    }

    public String get(char reg) {
        switch (reg) {
            case 'A':
                return this.A;
            case 'B':
                return this.B;
            case 'C':
                return this.C;
            case 'D':
                return this.D;
            case 'E':
                return this.E;
            case 'H':
                return this.H;
            case 'L':
                return this.L;
            case 'M':
                return this.get("M");
            case 'W':
                return this.W;
        }
        return "00";
    }
    public String get(String reg) {
        int address;
        switch (reg) {
            case "M":
                address = StringConversion.hexaToDecimal(this.H + this.L); 
                return CPU.mem.read(address);
            case "HL": case "H": 
                return (this.H + this.L);
            case "BC": case "B":
                return (this.B + this.C);
            case "DE": case "D":
                return (this.D + this.E);
        }
        return "00";
    }

    public void set(char reg, String data) {
        switch (reg) {
            case 'A':
                this.A = data;
                CPU.flags.update(data);
                break;
            case 'B':
                this.B = data;
                break;
            case 'C':
                this.C = data;
                break;
            case 'D':
                this.D = data;
                break;
            case 'E':
                this.E = data;
                break;
            case 'H':
                this.H = data;
                break;
            case 'L':
                this.L = data;
                break;
            case 'M':
                set("M", data);
                break;
            case 'W':
                this.W = data;
                break;
        }
    }
    public void set(String reg, String data) {
        int address;
        switch (reg) {
            case "M":
                address = StringConversion.hexaToDecimal(this.H + this.L);
                CPU.mem.write(address, data);
                break; 
            case "HL": case "H":
                this.H = data.substring(0, 2);
                this.L = data.substring(2);
                break;
            case "BC": case "B":
                this.B = data.substring(0, 2);
                this.C = data.substring(2);
                break;
            case "DE": case "D":
                this.D = data.substring(0, 2);
                this.E = data.substring(2);
                break;
        }
    }
    public void setPtrs(String reg, int data) {
        switch (reg) {
            case "PC":
                this.PC = data;
                break;
            case "SP":
                this.SP = data;
                break;
        }
    }
    public int getPtrs(String reg) {
        switch (reg) {
            case "PC":
                return this.PC;
            case "SP":
                return this.SP;
        }
        return 0;
    }
    public void increment(String reg) {
        switch (reg) {
            case "PC":
                this.PC++;
                break;
            case "SP":
                this.SP++;
                break;
        }
    }
}