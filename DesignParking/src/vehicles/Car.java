package vehicles;

/**
 * מחלקה שמייצגת רכב מסוג רכב פרטי (Car).
 * <p>
 * יורשת מהמחלקה האבסטרקטית {@link Vehicle} ומגדירה את מחיר החניה הקבוע לרכב פרטי.
 * </p>
 */
public class Car extends Vehicle {

    /**
     * בונה מופע חדש של רכב פרטי עם מספר רישוי.
     *
     * @param licenseNumber מספר הרישוי של הרכב
     */
    public Car(String licenseNumber) {
        super(licenseNumber);
    }

    /**
     * מחשב את תשלום החניה לרכב פרטי.
     *
     * @return סכום קבוע של ₪20
     */
    @Override
    public double calculateFee() {
        return 20.0;
    }
}
