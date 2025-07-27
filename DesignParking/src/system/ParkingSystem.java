package system;

import storage.DataManager;
import factory.VehicleFactory;
import vehicles.Vehicle;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * מחלקת Facade של מערכת ניהול החניון.
 * <p>
 * מספקת ממשק פשוט ומרכזי לביצוע פעולות כמו הכנסת רכב, הוצאה, סטטוס, סטטיסטיקות והיסטוריה.
 * המשתמש (או המפעיל) מתקשר רק עם מחלקה זו, מבלי לדעת איך יתר המחלקות עובדות מאחורי הקלעים.
 * </p>
 *
 * דפוס עיצוב: <b>Facade</b>
 */
public class ParkingSystem {

    /**
     * מופע החניון המרכזי, מנוהל לפי Singleton
     */
    private ParkingLot lot;

    /**
     * בנאי שמאתחל את מערכת הניהול ומקשר לחניון.
     */
    public ParkingSystem() {
        this.lot = ParkingLot.getInstance();
    }

    /**
     * מוסיף רכב חדש לחניון לפי סוג ומספר רישוי.
     * שומר את הרכב בקובץ הרכבים הפעילים.
     *
     * @param type סוג הרכב (car/truck/motorcycle)
     * @param licenseNumber מספר רישוי
     */
    public void enterVehicle(String type, String licenseNumber) {
        Vehicle vehicle = VehicleFactory.createVehicle(type, licenseNumber);
        lot.addVehicle(vehicle);
        System.out.println("✅ רכב עם מספר " + licenseNumber + " נכנס למערכת.");

        // שמירת הרכב לקובץ הרכבים הפעילים
        DataManager.saveActiveVehicle(vehicle);
    }

    /**
     * מוציא רכב מהחניון לפי מספר רישוי,
     * שומר את תשלום הרכב לקובץ נתונים,
     * ומסיר אותו מקובץ הרכבים הפעילים.
     *
     * @param licenseNumber מספר רישוי
     */
    public void exitVehicle(String licenseNumber) {
        Vehicle vehicle = lot.removeVehicle(licenseNumber);
        if (vehicle != null) {
            double fee = vehicle.calculateFee();
            System.out.println("🚗 רכב " + licenseNumber + " יצא. תשלום: ₪" + fee);

            // שמירת פרטי התשלום
            DataManager.saveVehicleData(vehicle, fee);

            // הסרת הרכב מקובץ הפעילים
            DataManager.removeActiveVehicle(licenseNumber);
        } else {
            System.out.println("❌ לא נמצא רכב עם מספר: " + licenseNumber);
        }
    }

    /**
     * מציג את מצב החניון הנוכחי – מספר מקומות פנויים ורשימת הרכבים שנמצאים.
     */
    public void displayStatus() {
        System.out.println("📊 מקומות פנויים: " + lot.getAvailableSpots());
        System.out.println("📋 רכבים בחניון:");
        for (Vehicle v : lot.getAllVehicles()) {
            System.out.println("- " + v.getLicenseNumber() + " (" + v.getClass().getSimpleName() + ")");
        }
    }

    /**
     * מציג את ההיסטוריה של רכב לפי מספר רישוי:
     * כולל זמן כניסה, משך שהייה, וסכום לתשלום אם ייצא כעת.
     *
     * @param licenseNumber מספר הרכב לבדיקה
     */
    public void printVehicleHistory(String licenseNumber) {
        for (Vehicle v : lot.getAllVehicles()) {
            if (v.getLicenseNumber().equals(licenseNumber)) {
                System.out.println("מספר רכב: " + v.getLicenseNumber());
                System.out.println("זמן כניסה: " + v.getEntryTime());

                // חישוב משך שהייה
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(v.getEntryTime(), now);
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;

                System.out.println("⏱ משך שהייה: " + hours + " שעות ו־" + minutes + " דקות");
                System.out.println("סכום לתשלום אם יצא עכשיו: ₪" + v.calculateFee());
                return;
            }
        }
        System.out.println("הרכב לא נמצא במערכת.");
    }

    /**
     * מדפיס סטטיסטיקה כללית של החניון:
     * מספר רכבים וסכום כללי של תשלומים פוטנציאליים.
     */
    public void printStatistics() {
        int total = lot.getAllVehicles().size();
        double totalFee = 0;
        for (Vehicle v : lot.getAllVehicles()) {
            totalFee += v.calculateFee();
        }
        System.out.println("📈 מספר רכבים בחניון: " + total);
        System.out.println("💰 סך כל תשלומים (אם היו יוצאים עכשיו): ₪" + totalFee);
    }
}
