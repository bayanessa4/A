package observers;

import system.ParkingLot;

/**
 * מחלקה זו מייצגת לוח תצוגה בחניון, אשר מציג את מספר המקומות הפנויים.
 * <p>
 * המחלקה מממשת את דפוס העיצוב Observer, כך שכל שינוי במצב החניון
 * (כמו כניסה או יציאה של רכב) מפעיל את מתודת העדכון בלוח התצוגה.
 * </p>
 */
public class DisplayBoard implements ParkingObserver {

    /**
     * מזהה ייחודי של לוח התצוגה – מאפשר להבדיל בין מספר לוחות.
     */
    private String boardId;

    /**
     * בונה לוח תצוגה חדש עם מזהה מסוים.
     *
     * @param boardId מזהה הלוח (למשל: "Board A")
     */
    public DisplayBoard(String boardId) {
        this.boardId = boardId;
    }

    /**
     * מתודת העדכון שמופעלת כאשר החניון משתנה.
     * מציגה את מספר המקומות הפנויים בלוח זה.
     *
     * @param lot מופע של החניון
     */
    @Override
    public void update(ParkingLot lot) {
        System.out.println("[" + boardId + "] מקומות פנויים: " + lot.getAvailableSpots());
    }

    /**
     * מחזיר את מזהה לוח התצוגה.
     *
     * @return מזהה הלוח
     */
    public String getBoardId() {
        return boardId;
    }

    /**
     * מגדיר מזהה חדש ללוח התצוגה.
     *
     * @param boardId מזהה חדש
     */
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
