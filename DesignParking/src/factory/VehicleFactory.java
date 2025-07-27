package factory;

import vehicles.*;

/**
 * דפוס עיצוב Factory Method:
 * מחלקה שאחראית על יצירת מופעים של רכבים שונים (Car, Truck, Motorcycle)
 * לפי סוג שהוזן על ידי המשתמש.
 * <p>
 * היתרון בשימוש בדפוס זה הוא שהקוד שקורא ל־createVehicle לא צריך לדעת
 * איזו מחלקה ספציפית נוצרת – הוא מקבל אובייקט מתאים מסוג Vehicle.
 * </p>
 */
public class VehicleFactory {

    /**
     * יוצר רכב חדש לפי הסוג שהוזן.
     *
     * @param type סוג הרכב: "car", "truck", או "motorcycle"
     * @param licenseNumber מספר הרישוי של הרכב
     * @return מופע חדש של Car / Truck / Motorcycle בהתאם לסוג
     * @throws IllegalArgumentException אם הוזן סוג רכב לא חוקי
     */
    public static Vehicle createVehicle(String type, String licenseNumber) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car(licenseNumber);
            case "truck":
                return new Truck(licenseNumber);
            case "motorcycle":
                return new Motorcycle(licenseNumber);
            default:
                throw new IllegalArgumentException("סוג רכב לא מוכר: " + type);
        }
    }
}
