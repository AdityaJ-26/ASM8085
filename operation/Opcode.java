package operation;

// import memory.Memory;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;

public class Opcode {
    public static String fetch(String inst) {
        try {
            File f = new File("data\\opcodes.txt");
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] list = s.split("\t");

                if (list[0].equals(inst)) {
                    return list[1];
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "00";
    }

    public static String[] decode(String opcode) {
        try {
            File f = new File("data\\opcodes.txt");
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] list = s.split("\t");
                int size = Integer.parseInt(list[list.length-1]);
                if (list[1].equals(opcode)) {
                    String[] part =  list[0].split("[,\\s]+");
                    String inst[] = new String[size + part.length-1];
                    for (int i=0; i<part.length; i++) {
                        inst[i] = part[i];
                    }
                    return inst;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{"NOP"};
    }
}