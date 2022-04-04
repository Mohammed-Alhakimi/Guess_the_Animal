import java.time.LocalTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] range1 = scanner.nextLine().split(" ");
        String[] range2 = scanner.nextLine().split(" ");

        LocalTime border1 = LocalTime.parse(range1[0]);
        LocalTime border2 = LocalTime.parse(range1[1]);
        LocalTime border3 = LocalTime.parse(range2[0]);
        LocalTime border4 = LocalTime.parse(range2[1]);

        System.out.println((border1.isBefore(border4) || border1.equals(border4))
                && (border2.isAfter(border3) || border2.equals(border3)));
    }
}