package studying;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string:");
        String input = scanner.nextLine();
        System.out.println("Echo: " + input);
        scanner.close();
    }
}