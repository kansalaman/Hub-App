package app.outlab.outlab9;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class UserDisplay extends AppCompatActivity {
    List<String> repo_names = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    List<String> ages = new ArrayList<String>();

    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udis);
        Intent intent=getIntent();
        uname=intent.getStringExtra("uname");

        ListView lv=(ListView) findViewById(R.id.listview);

        UserInfo unf = new UserInfo();
        unf.execute();
        customAdapter adapter = new customAdapter();
        lv.setAdapter(adapter);

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

        @Override
        protected Void doInBackground(Void... voids) {
//            Toast.makeText(getApplicationContext(), uname, Toast.LENGTH_LONG).show();
//            SystemClock.sleep(5000);

            String urlname =  "https://api.github.com/search/repositories?q=user:" + uname;
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
                    String cur_name = obj.optString("name").toString();
                    String cur_desc = obj.optString("description").toString();
                    if (cur_desc == null)
                    {
                        cur_desc = "No desc available";
                    }
                    String cur_create = obj.optString("created_at").toString().substring(0, 10);
                    repo_names.add(cur_name);
                    descriptions.add(cur_desc);
                    String final_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
                    // LocalDate now = LocalDate.now();
                    Date in = new Date();
                    Date end = new Date();
                    try {
                        in = formatter.parse(cur_create);
                        end = formatter.parse(final_date);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    long diff = end.getTime() - in.getTime();
                    Date d = new Date(diff);
                    String res_date = d.getYear() + "years" + d.getMonth()+"months" + d.getDay()+"days";
                    ages.add(res_date);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }


    }


}
