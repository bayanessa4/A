package vehicles;

/**
 * מחלקה שמייצגת רכב מסוג אופנוע (Motorcycle).
 * <p>
 * יורשת מהמחלקה {@link Vehicle} ומגדירה תשלום מוזל לחניה עבור אופנועים.
 * </p>
 */
public class Motorcycle extends Vehicle {

    /**
     * בונה מופע חדש של אופנוע עם מספר רישוי.
     *
     * @param licenseNumber מספר הרישוי של האופנוע
     */
    public Motorcycle(String licenseNumber) {
        super(licenseNumber);
    }

    /**
     * מחשב את תשלום החניה עבור אופנוע.
     *
     * @return סכום קבוע של ₪10
     */
    @Override
    public double calculateFee() {
        return 10.0;
    }
}
