package app.outlab.outlab9;

import android.content.Context;
import android.os.AsyncTask;

public class UserInfo extends AsyncTask<Void,Void,Void> {
    String uname;
    Context context;
    UserInfo(String un,Context c){
        this.uname=un;
        this.context=c;

    }
    @Override
    protected Void doInBackground(Void... voids) {

    return null;
    }
}
