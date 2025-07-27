package system;

import vehicles.Vehicle;
import observers.ParkingObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * מחלקת Singleton המייצגת את החניון הראשי במערכת.
 * <p>
 * מנהלת את רשימת הרכבים הנמצאים בחניון, את כמות המקומות הפנויים,
 * ואת רשימת הצופים (Observers) שצריכים להתעדכן כאשר המצב משתנה.
 * <br>
 * דפוסי עיצוב: <b>Singleton</b> + <b>Observer</b>
 * </p>
 */
public class ParkingLot {

    /**
     * מופע בודד של החניון – Singleton
     */
    private static ParkingLot instance = null;

    /**
     * רשימת רכבים שנמצאים כרגע בחניון
     */
    private List<Vehicle> vehicles;

    /**
     * רשימת הצופים (לוחות תצוגה וכו') שמעוניינים לקבל עדכונים
     */
    private List<ParkingObserver> observers;

    /**
     * כמות מקומות החניה המקסימלית
     */
    private int maxSpots;

    /**
     * בונה מופע חניון חדש עם כמות מקומות מוגדרת מראש.
     * בנאי פרטי לפי דפוס Singleton.
     *
     * @param maxSpots כמות מקומות החניה
     */
    private ParkingLot(int maxSpots) {
        this.maxSpots = maxSpots;
        this.vehicles = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * מחזיר את המופע היחיד של החניון (Singleton)
     *
     * @return מופע של ParkingLot
     */
    public static ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot(100); // ברירת מחדל: 100 מקומות
        }
        return instance;
    }

    /**
     * מוסיף רכב לחניון אם יש מקום, ומעדכן את כל הצופים.
     *
     * @param v רכב להוספה
     */
    public void addVehicle(Vehicle v) {
        if (vehicles.size() < maxSpots) {
            vehicles.add(v);
            notifyObservers();
        }
    }

    /**
     * מסיר רכב מהחניון לפי מספר רישוי, ומעדכן את הצופים.
     *
     * @param license מספר רישוי
     * @return הרכב שהוסר, או null אם לא נמצא
     */
    public Vehicle removeVehicle(String license) {
        for (Vehicle v : vehicles) {
            if (v.getLicenseNumber().equals(license)) {
                vehicles.remove(v);
                notifyObservers();
                return v;
            }
        }
        return null;
    }

    /**
     * מחזיר את מספר המקומות הפנויים כרגע.
     *
     * @return כמות מקומות חניה פנויים
     */
    public int getAvailableSpots() {
        return maxSpots - vehicles.size();
    }

    /**
     * מחזיר את רשימת כל הרכבים שנמצאים כרגע בחניון.
     *
     * @return רשימת רכבים
     */
    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // ==== תמיכה ב־Observer ====

    /**
     * רישום צופה חדש לעדכונים (למשל לוח תצוגה)
     *
     * @param o הצופה להוספה
     */
    public void registerObserver(ParkingObserver o) {
        observers.add(o);
    }

    /**
     * הסרת צופה מהרשימה
     *
     * @param o הצופה להסרה
     */
    public void removeObserver(ParkingObserver o) {
        observers.remove(o);
    }

    /**
     * הפעלת עדכון לכל הצופים הרשומים כאשר יש שינוי במצב החניון
     */
    private void notifyObservers() {
        for (ParkingObserver o : observers) {
            o.update(this);
        }
    }
}
