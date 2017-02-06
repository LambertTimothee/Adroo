package timothee.lambert.channelmessaging;

/**
 * Created by lambetim on 23/01/2017.
 */
public class CallbackConnect {

    private String username;
    private  String password;
    int code;
    String accesstoken;


    @Override
    public String toString(){
        return "Usurname :"+this.username+" Code :"+this.code+" accestoken : "+this.accesstoken;
    }
}