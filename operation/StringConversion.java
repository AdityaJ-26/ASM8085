package operation;

public class StringConversion {
    public static String hexaToBinary(String data) {
        StringBuffer binary = new StringBuffer();
        for (char c : data.toCharArray()) {
            if (c == 'h') continue;
            int no = Character.digit(c, 16);
            String bin = Integer.toBinaryString(no);
            while (bin.length() < 4) {
                bin = "0" + bin;
            }
            binary.append(bin);
        }
        return binary.toString();
    }

    public static int hexaToDecimal(String data) {
        int number = 0, base = 1;
        int len = data.length();
        for (int i=len-2; i>=0; i--) {
            char c = data.charAt(i);
            if (c >= '0' && c <= '9') {
                number += (c - '0') * base;
                base *= 16;
            }
            else if (c >= 'A' && c <= 'F') {
                number += (c - 'A' + 10) * base;
                base *= 16;
            }
        }
        return number;
    }

    public static String binaryToHexa(StringBuffer binary) {
        String bin = binary.toString();
        while (bin.length() % 4 != 0) {
            bin = "0" + bin;
        }
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < bin.length(); i += 4) {
            String chunk = bin.substring(i, i + 4);
            int decimal = Integer.parseInt(chunk, 2);
            hex.append(Integer.toHexString(decimal));
        }
        return hex.toString().toUpperCase() + "h";
    }
}