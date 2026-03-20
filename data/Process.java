import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class Process {

    public static void main(String[] args) {
        try {
            File f = new File("temp.txt");
            FileWriter w = new FileWriter("opcodes.txt");
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] words = s.split("\\s+");
                if (words.length == 4) {
                    w.write(words[1] + "\t" + words[2] + "\t" + words[3] + "\n");
                }
                else {
                    w.write(words[1] + " " + words[2] + "\t" + words[3] + "\t" + words[4] + "\n");
                } 
            }
            w.close();
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}