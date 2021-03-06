package fiuba.mensajero;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.ArrayList;

public class NetworkService extends IntentService {

    public NetworkService() {
        super("NetworkService");
    }

    public static final int RUNNING = 0;
    public static final int OK = 1;
    public static final int ERROR = 2;


    protected void onHandleIntent(Intent intent) {


        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String command = intent .getStringExtra("command");
        Bundle b = new Bundle();
        Context context= getApplicationContext();
        ServerRequest serverRequest = new ServerRequest(context);

        if(command.equals("getListaConectados")) {
            receiver.send(RUNNING, Bundle.EMPTY);
            try {
                String token = intent.getStringExtra("token");
                String user = intent.getStringExtra("user");
                ArrayList<String> res = serverRequest.getUsersOnline(user, token);
                b.putStringArrayList("result", res);
                receiver.send(OK, b);
            } catch(Exception e) {
                b.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(ERROR, b);
            }
        }

        if (command.equals("register")) {
            receiver.send(RUNNING, Bundle.EMPTY);
            try {
                String user = intent.getStringExtra("user");
                String password = intent.getStringExtra("password");
                String name = intent.getStringExtra("name");
                String res = serverRequest.register(user, password, name);
                b.putString("result", res);
                receiver.send(OK, b);
            } catch(Exception e) {
                b.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(ERROR, b);
            }
        }

        if (command.equals("logIn")) {
            receiver.send(RUNNING, Bundle.EMPTY);
            try {
                String user = intent.getStringExtra("user");
                String password = intent.getStringExtra("password");
                String res = serverRequest.logIn(user, password);
                b.putString("result", res);
                receiver.send(OK, b);
            } catch(Exception e) {
                b.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(ERROR, b);
            }
        }

    }


}
