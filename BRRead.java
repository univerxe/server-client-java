package stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BRRead {
    public static void main(String[] args) throws IOException {
        char c;
        String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, System.console().charset()));
        System.out.println("Enter characters, 'q' to quit.");

        do {
            c = (char) br.read();
            System.out.println(c);
        } while (c != 'q');

        System.out.println("Enter 'stop' to quit.");

        do {
            str = br.readLine();
            System.out.println(str);
        } while (!str.equals("stop"));
    }
}
