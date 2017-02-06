package timothee.lambert.channelmessaging;

/**
 * Created by lambetim on 27/01/2017.
 */
public class Channel {
    public int channelID;
    public String name;
    public int connectedusers;

    public Channel(int channelId, int connectedusers, String name) {
        this.channelID = channelID;
        this.connectedusers = connectedusers;
        this.name = name;
    }
}
