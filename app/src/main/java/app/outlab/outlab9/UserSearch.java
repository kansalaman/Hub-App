package app.outlab.outlab9;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserSearch extends AsyncTask<Void,Void,List<String>> {
    Context context;
    ListView listview;
    String name;
    ListView lev;

    UserSearch(Context c, String n, ListView t){
        this.context=c;
//        this.listview=l;
        this.name=n;
        this.lev=t;
    }
    @Override
    protected List<String> doInBackground(Void... voids) {
        List<String> results = new ArrayList<String>();
        String urlname = "https://api.github.com/search/users?q=" + name + "&sort=repositories&order=desc";
        String strjson=new String("hello");
        try {
            URL urls = new URL(urlname);
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            strjson="yellow";
            connection.connect();
            strjson="purple";
            InputStream in = connection.getInputStream();
            strjson="";
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
//            Scanner s = new Scanner(in).useDelimiter("\\A");
            String tmp;
            while((tmp=bufferedReader.readLine())!=null){
                strjson+=tmp;
            }
//            strjson="green";
//            Toast.makeText(context,"here1",Toast.LENGTH_SHORT).show();
//            strjson = s.hasNext() ? s.next() : "";
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(strjson);
            jsonArray = jsonRootObject.optJSONArray("items");

            for (int i = 0; i< jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                String cur = obj.optString("login").toString();
                results.add(cur);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return results;
    }
    protected void onPostExecute(List<String> names) {
        String[] narr=new String[names.size()];
        narr=names.toArray(narr);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,narr);
        lev.setAdapter(adapter);
    }
}
