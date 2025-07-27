package vehicles;

/**
 * מחלקה שמייצגת רכב מסוג משאית (Truck).
 * <p>
 * יורשת מהמחלקה {@link Vehicle} ומגדירה תשלום חניה גבוה יותר עבור משאיות.
 * </p>
 */
public class Truck extends Vehicle {

    /**
     * בונה מופע חדש של משאית עם מספר רישוי.
     *
     * @param licenseNumber מספר הרישוי של המשאית
     */
    public Truck(String licenseNumber) {
        super(licenseNumber);
    }

    /**
     * מחשב את תשלום החניה עבור משאית.
     *
     * @return סכום קבוע של ₪35
     */
    @Override
    public double calculateFee() {
        return 35.0;
    }
}
