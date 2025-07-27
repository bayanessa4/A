package vehicles;

import java.time.LocalDateTime;

/**
 * מחלקה אבסטרקטית שמייצגת רכב כללי במערכת החניה.
 * <p>
 * כוללת את הנתונים המשותפים לכל סוגי הרכבים:
 * מספר רישוי וזמן כניסה לחניון.
 * <br>
 * מחלקות יורשות (Car, Truck, Motorcycle) נדרשות לממש את פעולת {@code calculateFee}.
 * </p>
 */
public abstract class Vehicle {

    /**
     * מספר הרישוי של הרכב
     */
    private String licenseNumber;

    /**
     * זמן הכניסה של הרכב לחניון
     */
    private LocalDateTime entryTime;

    /**
     * בונה רכב חדש ומאתחל את זמן הכניסה לעכשיו.
     *
     * @param licenseNumber מספר רישוי של הרכב
     */
    public Vehicle(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        this.entryTime = LocalDateTime.now();
    }

    /**
     * מחזיר את מספר הרישוי של הרכב.
     *
     * @return מספר רישוי
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * מגדיר מספר רישוי חדש לרכב.
     *
     * @param licenseNumber מספר רישוי
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * מחזיר את זמן הכניסה של הרכב לחניון.
     *
     * @return זמן כניסה
     */
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    /**
     * מגדיר את זמן הכניסה של הרכב (לשימוש פנימי/בדיקות).
     *
     * @param entryTime זמן כניסה חדש
     */
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * פעולה אבסטרקטית לחישוב תשלום עבור חניה – כל סוג רכב מממש בעצמו.
     *
     * @return הסכום לתשלום
     */
    public abstract double calculateFee();
}
