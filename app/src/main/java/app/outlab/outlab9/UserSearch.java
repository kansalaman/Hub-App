package app.outlab.outlab9;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UserSearch extends AsyncTask<Void,Void,String> {
    Context context;
    ListView listview;
    String name;
    TextView tev;

    UserSearch(Context c, String n, TextView t){
        this.context=c;
//        this.listview=l;
        this.name=n;
        this.tev=t;
    }
    @Override
    protected String doInBackground(Void... voids) {
        String urlname = "https://api.github.com/search/users?q=" + name + "&sort=repositories&order=desc";
        String strjson=new String("hello");
        try {
            URL urls = new URL(urlname);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            strjson = s.hasNext() ? s.next() : "";
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return strjson;
    }
    protected void onPostExecute(String names) {
        tev.setText(names);

    }
}
