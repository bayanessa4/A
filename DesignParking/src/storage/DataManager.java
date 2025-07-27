package storage;

import vehicles.Vehicle;
import factory.VehicleFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * מחלקה זו אחראית על שמירת וטעינת נתוני רכבים לקבצים חיצוניים.
 * <p>
 * כוללת:
 * <ul>
 *     <li>שמירת נתוני רכבים שיצאו (parking_data.txt)</li>
 *     <li>שמירת רכבים פעילים (active_vehicles.txt)</li>
 *     <li>טעינת רכבים פעילים עם עליית המערכת</li>
 *     <li>הסרת רכבים פעילים כשהם יוצאים</li>
 * </ul>
 */
public class DataManager {

    private static final String FILE_PATH = "parking_data.txt";
    private static final String ACTIVE_VEHICLES_FILE = "active_vehicles.txt";

    /**
     * שומר את פרטי הרכב שיצא מהחניון – כולל מספר רישוי, זמן כניסה וסכום לתשלום.
     *
     * @param vehicle מופע הרכב שיצא
     * @param fee     הסכום אותו היה צריך לשלם
     */
    public static void saveVehicleData(Vehicle vehicle, double fee) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            writer.write("רכב: " + vehicle.getLicenseNumber() + "\n");
            writer.write("זמן כניסה: " + vehicle.getEntryTime().format(formatter) + "\n");
            writer.write("תשלום: ₪" + fee + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            System.err.println("❌ שגיאה בשמירת הנתונים: " + e.getMessage());
        }
    }

    /**
     * שומר רכב פעיל (שנכנס) לקובץ הרכבים הפעילים.
     *
     * @param vehicle רכב שנכנס לחניון
     */
    public static void saveActiveVehicle(Vehicle vehicle) {
        try (FileWriter writer = new FileWriter(ACTIVE_VEHICLES_FILE, true)) {
            writer.write(vehicle.getClass().getSimpleName() + "," + vehicle.getLicenseNumber() + "," + vehicle.getEntryTime() + "\n");
        } catch (IOException e) {
            System.err.println("❌ שגיאה בשמירת רכב פעיל: " + e.getMessage());
        }
    }

    /**
     * טוען את כל הרכבים הפעילים (שנשמרו בקובץ) ומחזיר אותם לרשימה.
     *
     * @return רשימת רכבים שהיו בחניון לפני ההפעלה הנוכחית
     */
    public static List<Vehicle> loadActiveVehicles() {
        List<Vehicle> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACTIVE_VEHICLES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String type = parts[0].toLowerCase();
                    String license = parts[1];
                    LocalDateTime entry = LocalDateTime.parse(parts[2]);

                    Vehicle vehicle = VehicleFactory.createVehicle(type, license);
                    vehicle.setEntryTime(entry);
                    list.add(vehicle);
                }
            }
        } catch (IOException e) {
            System.err.println("ℹ️ קובץ רכבים פעילים לא נמצא – ייווצר חדש אם יירשמו רכבים.");
        }
        return list;
    }

    /**
     * מסיר רכב מקובץ הרכבים הפעילים לפי מספר רישוי.
     *
     * @param licenseNumber מספר רישוי של הרכב להסרה
     */
    public static void removeActiveVehicle(String licenseNumber) {
        File inputFile = new File(ACTIVE_VEHICLES_FILE);
        File tempFile = new File("temp_active_vehicles.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    if (!parts[1].equals(licenseNumber)) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }

            writer.flush();
        } catch (IOException e) {
            System.err.println("❌ שגיאה בהסרת רכב מקובץ פעילים: " + e.getMessage());
            return;
        }

        // החלפת קובץ זמני לקובץ המקורי
        if (!inputFile.delete()) {
            System.err.println("⚠️ לא ניתן למחוק את קובץ המקור.");
        } else if (!tempFile.renameTo(inputFile)) {
            System.err.println("⚠️ לא ניתן להחליף את קובץ הפעילים.");
        }
    }

    /**
     * מנקה את קובץ הרכבים הפעילים (למשל כאשר סוגרים את המערכת בצורה נקייה).
     */
    public static void clearActiveVehiclesFile() {
        try (FileWriter writer = new FileWriter(ACTIVE_VEHICLES_FILE)) {
            // כתיבה ריקה מוחקת את התוכן
        } catch (IOException e) {
            System.err.println("❌ שגיאה בניקוי קובץ רכבים פעילים: " + e.getMessage());
        }
    }
}
