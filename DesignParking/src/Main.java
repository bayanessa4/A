import system.ParkingSystem;
import system.ParkingLot;
import observers.DisplayBoard;

import java.util.Scanner;

/**
 * הקלאס הראשי של מערכת החניון החכם.
 * <p>
 * מפעיל תפריט קונסולי אינטראקטיבי המאפשר למשתמש לבצע פעולות:
 * הוספת רכב, הוצאת רכב, הצגת מצב, בדיקת היסטוריה, וסטטיסטיקה.
 * <br>
 * מבוסס על המחלקה {@link ParkingSystem} שהיא מחלקת ה־Facade של המערכת.
 * <br>
 * בתחילת התוכנית יודפס מספר המקומות הפנויים ההתחלתי (ברירת מחדל – 100).
 * </p>
 */
public class Main {

    /**
     * נקודת הכניסה הראשית למערכת.
     *
     * @param args לא בשימוש
     */
    public static void main(String[] args) {
        ParkingSystem system = new ParkingSystem();
        DisplayBoard board = new DisplayBoard("Main Board");
        ParkingLot.getInstance().registerObserver(board);

        // ✅ הצגת מצב חניון התחלתי
        System.out.println("🅿️ מספר מקומות פנויים בתחילת המערכת: " + ParkingLot.getInstance().getAvailableSpots());

        Scanner scanner = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\n===== מערכת חניון חכם =====");
            System.out.println("1. הוספת רכב");
            System.out.println("2. הוצאת רכב");
            System.out.println("3. הצגת סטטוס");
            System.out.println("4. צפייה בהיסטוריית רכב");
            System.out.println("5. סטטיסטיקה");
            System.out.println("6. יציאה");
            System.out.print("בחר פעולה: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // הוספת רכב חדש לפי סוג
                    System.out.print("סוג רכב (car/truck/motorcycle): ");
                    String type = scanner.nextLine().toLowerCase();

                    if (!type.equals("car") && !type.equals("truck") && !type.equals("motorcycle")) {
                        System.out.println("❌ סוג רכב לא תקין. נסי שוב עם: car / truck / motorcycle");
                        break;
                    }

                    System.out.print("מספר רישוי: ");
                    String license = scanner.nextLine();
                    system.enterVehicle(type, license);
                    break;

                case "2":
                    // הוצאת רכב לפי מספר רישוי
                    System.out.print("מספר רישוי להוצאה: ");
                    license = scanner.nextLine();
                    system.exitVehicle(license);
                    break;

                case "3":
                    // הצגת מצב החניון
                    system.displayStatus();
                    break;

                case "4":
                    // בדיקת היסטוריית רכב
                    System.out.print("מספר רישוי לבדיקה: ");
                    license = scanner.nextLine();
                    system.printVehicleHistory(license);
                    break;

                case "5":
                    // סטטיסטיקה כללית
                    system.printStatistics();
                    break;

                case "6":
                    // יציאה מהמערכת
                    run = false;
                    break;

                default:
                    System.out.println("❌ בחירה לא חוקית");
            }
        }

        System.out.println("תודה ולהתראות!");
        scanner.close();
    }
}
