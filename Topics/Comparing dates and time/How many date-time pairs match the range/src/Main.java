import java.time.LocalDateTime;
import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime border1 = LocalDateTime.parse(scanner.nextLine());
        LocalDateTime border2 = LocalDateTime.parse(scanner.nextLine());
        if (border1.isAfter(border2)) {
            LocalDateTime temp = border1;
            border1 = border2;
            border2 = temp;
        }
        int numbersCount = scanner.nextInt();
        scanner.nextLine();
        int counter = 0;
        for (int i = 0; i < numbersCount; i++) {
            LocalDateTime check = LocalDateTime.parse(scanner.nextLine());
            if ((check.isEqual(border1) || check.isAfter(border1) && check.isBefore(border2))) {
                counter++;
            }
        }
        System.out.println(counter);
    }
}