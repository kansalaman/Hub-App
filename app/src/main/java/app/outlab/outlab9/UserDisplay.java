package app.outlab.outlab9;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UserDisplay extends AppCompatActivity {
    List<String> repo_names = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    List<String> ages = new ArrayList<String>();
    TextView nameblock;
    TextView companyblock;
    TextView locationblock;
    String pname = new String("default");
    String pcomp = new String("default company");
    String ploc = new String("def loc");


    String uname;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udis);
        nameblock=(TextView) findViewById(R.id.Name);
        companyblock=(TextView) findViewById(R.id.Company);
        locationblock=(TextView) findViewById(R.id.Location);

        Intent intent=getIntent();
        uname=intent.getStringExtra("uname");

        lv=(ListView) findViewById(R.id.listview);

        UserInfo unf = new UserInfo();
        unf.execute();


    }
    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return repo_names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView repoName = (TextView) convertView.findViewById(R.id.repoName);
            TextView repoAge = (TextView) convertView.findViewById(R.id.repoAge);
            TextView repoDesc = (TextView) convertView.findViewById(R.id.repoDesc);

            repoName.setText(repo_names.get(position));
            repoAge.setText(ages.get(position));
            repoDesc.setText(descriptions.get(position));
            return convertView;
        }
    }
    public class UserInfo extends AsyncTask<Void,Void,Void> {
//        String uname;
//        Context context;
        //    ListView lv;
//        String[] names;
//        String[] description;
//        String[] age;
//            this.uname=un;
//            this.context=c;
//            this.names = n;
//            this.description=d;
//            this.age=a;
//        }
        String strjsonfordata = new String("download this");
        String url2 = "https://api.github.com/users/" + uname;
        String strjson=new String("hello");
        @Override
        protected Void doInBackground(Void... voids) {
//            Toast.makeText(getApplicationContext(), uname, Toast.LENGTH_LONG).show();
//            SystemClock.sleep(5000);

            String urlname =  "https://api.github.com/search/repositories?q=user:" + uname;


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
                connection.disconnect();
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
            try {
                Log.d("connectiondebug","yo");
                URL urlinfo = new URL(url2);
                HttpURLConnection connection = (HttpURLConnection) urlinfo.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();
                strjsonfordata="";
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
                String tmp;
                while((tmp=bufferedReader.readLine())!=null){
                    strjsonfordata+=tmp;
                }
                Log.d("connectiondebug",strjsonfordata);
                connection.disconnect();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            try {
                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(strjsonfordata);
//                Log.d("connectiondebug","");
                pname = jsonRootObject.optString("login");
                Log.d("connectiondebug",pname);
                pcomp = jsonRootObject.optString("company");
                Log.d("connectiondebug","pcomp: "+ pcomp);
                ploc = jsonRootObject.optString("location");
                Log.d("connectiondebug",ploc);
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();
            try {
                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(strjson);
                Log.d("connectiondebug","reached");
                jsonArray = jsonRootObject.optJSONArray("items");

                for (int i = 0; i< jsonArray.length(); i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String cur_name = obj.optString("name").toString();
                    String cur_desc = obj.optString("description").toString();
                    if (cur_desc == "null")
                    {
                        cur_desc = "No desc available";
                    }
                    String cur_create = obj.optString("created_at").toString().substring(0, 10)+obj.optString("created_at").toString().substring(11, 19);
                    repo_names.add(cur_name);
                    descriptions.add(cur_desc);
//                    String final_date = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss", Locale.getDefault()).format(new Date());
//                    fd = cur_create;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    // LocalDate now = LocalDate.now();
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date in = new Date();
                    Date end = new Date();
                    String final_date = formatter.format(end);
                    try {
                        in = formatter.parse(cur_create);
                        end = formatter.parse(final_date);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    long diff = end.getTime() - in.getTime()-86400000;
                    Date d = new Date(diff);
                    String res_date;
                    if(diff<0){
                        res_date = "00 years, 00 months, 00 days";
                    }
                    else {
                        String years,months,days;
                        if(d.getYear()-70>9) {
                            years = Integer.toString(d.getYear() - 70);
                        }
                        else{
                            years = "0" + Integer.toString(d.getYear() - 70);
                        }

                        if(d.getMonth()>9) {
                            months = Integer.toString(d.getMonth());
                        }
                        else{
                            months = "0" + Integer.toString(d.getMonth());
                        }

                        if(d.getDate()>9) {
                            days = Integer.toString(d.getDate());
                        }
                        else{
                            days = "0" + Integer.toString(d.getDate());
                        }
                        res_date = years + " years, " + months + " months, " + days + " days";
                    }
                    ages.add(res_date);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            Log.d("connectiondebug","ansh ka sahi");
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            customAdapter adapter = new customAdapter();
            lv.setAdapter(adapter);
            Log.d("connectiondebug",pname);
            companyblock.setText(pcomp);
            nameblock.setText(pname);
            locationblock.setText(ploc);
            Log.d("connectiondebug","checkpoint2");
        }


    }


}
