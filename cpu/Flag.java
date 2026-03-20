package cpu;

import operation.StringConversion;

public class Flag {
    private boolean sign;
    private boolean zero;
    private boolean auxillaryCarry;
    private boolean parity;
    private boolean carry;

    public Flag() {
        this.sign = this.zero = this.auxillaryCarry = this.parity = this.carry = false;
    }

    public boolean getZ() { return this.zero; };
    public void setZ(boolean zero) { this.zero = zero; }

    public boolean getS() { return this.sign; };
    public void setS(boolean sign) { this.sign = sign; }

    public boolean getAC() { return this.auxillaryCarry; };
    public void setAC(boolean auxillaryCarry) { this.auxillaryCarry = auxillaryCarry; }
    
    public boolean getP() { return this.parity; };
    public void setP(boolean parity) { this.parity = parity; }

    public boolean getC() { return this.carry; };
    public void setC(boolean carry) { this.carry = carry; }

    public void update(String data) {
        String hexa = StringConversion.hexaToBinary(data);
        int oneCount = 0;
        for (int i=0; i<hexa.length(); i++) {
            if (hexa.charAt(i) == '1') oneCount++;
        }
        this.parity = (oneCount%2 == 0);
        this.zero = (oneCount == 0);
    }
}