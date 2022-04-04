import java.time.LocalTime;
import java.util.*;

class Main {

    public static void main(String[] args) {
        List<String> placesYouCanGoTo = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        for (int i = 0; i < rows; i++) {
            scanner.nextLine();
            String place = scanner.next();
            LocalTime closing = LocalTime.parse(scanner.next());
            if (LocalTime.of(19, 30).plusMinutes(30).isBefore(closing)) {
                placesYouCanGoTo.add(place);
            }
        }
        placesYouCanGoTo.forEach(System.out::println);
    }
}