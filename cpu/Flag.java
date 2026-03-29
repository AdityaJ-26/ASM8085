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


/*
| Instruction | Description                    | Flags Affected                    |
| ----------- | ------------------------------ | --------------------------------- |
| ADD r / M   | Add register/memory to A       | S, Z, AC, P, CY                   |
| ADC r / M   | Add with carry                 | S, Z, AC, P, CY                   |
| ADI data    | Add immediate                  | S, Z, AC, P, CY                   |
| ACI data    | Add immediate with carry       | S, Z, AC, P, CY                   |
| SUB r / M   | Subtract                       | S, Z, AC, P, CY                   |
| SBB r / M   | Subtract with borrow           | S, Z, AC, P, CY                   |
| SUI data    | Subtract immediate             | S, Z, AC, P, CY                   |
| SBI data    | Subtract immediate with borrow | S, Z, AC, P, CY                   |
| INR r / M   | Increment                      | S, Z, AC, P (**CY not affected**) |
| DCR r / M   | Decrement                      | S, Z, AC, P (**CY not affected**) |
| INX rp      | Increment register pair        | **No flags affected**             |
| DCX rp      | Decrement register pair        | **No flags affected**             |
| DAD rp      | Add register pair to HL        | **CY only**                       |

| Instruction | Description       | Flags Affected          |
| ----------- | ----------------- | ----------------------- |
| ANA r / M   | AND               | S, Z, P, **AC=1**, CY=0 |
| ANI data    | AND immediate     | S, Z, P, AC=1, CY=0     |
| ORA r / M   | OR                | S, Z, P, AC=0, CY=0     |
| ORI data    | OR immediate      | S, Z, P, AC=0, CY=0     |
| XRA r / M   | XOR               | S, Z, P, AC=0, CY=0     |
| XRI data    | XOR immediate     | S, Z, P, AC=0, CY=0     |
| CMP r / M   | Compare           | S, Z, AC, P, CY         |
| CPI data    | Compare immediate | S, Z, AC, P, CY         |

| Instruction | Description                | Flags Affected |
| ----------- | -------------------------- | -------------- |
| RLC         | Rotate left                | CY             |
| RRC         | Rotate right               | CY             |
| RAL         | Rotate left through carry  | CY             |
| RAR         | Rotate right through carry | CY             |

| Instruction | Description                | Flags Affected         |
| ----------- | -------------------------- | ---------------------- |
| CMA         | Complement accumulator     | No flags               |
| CMC         | Complement carry           | CY                     |
| STC         | Set carry                  | CY                     |
| DAA         | Decimal adjust accumulator | **All flags affected** |
| Instruction | Description                | Flags Affected         |
| ----------- | -------------------------- | ---------------------- |
| CMA         | Complement accumulator     | No flags               |
| CMC         | Complement carry           | CY                     |
| STC         | Set carry                  | CY                     |
| DAA         | Decimal adjust accumulator | **All flags affected** |

| Instruction | Condition   |
| ----------- | ----------- |
| JZ / JNZ    | Zero flag   |
| JC / JNC    | Carry flag  |
| JP / JM     | Sign flag   |
| JPE / JPO   | Parity flag |

*/