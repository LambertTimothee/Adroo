package timothee.lambert.channelmessaging;

import java.util.Date;

/**
 * Created by lambetim on 06/02/2017.
 */
public class Message {
    public int userID;
    public String message;
    public String date;
    public String imageUrl;
    public String username;

    public Message(int userID, String message, String date, String imageUrl) {
        this.userID = userID;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
        this.username = username;
    }
}
