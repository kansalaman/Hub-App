package app.outlab.outlab9;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UserDisplay extends AppCompatActivity {
    String[] names;
    String[] description;
    String[] age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udis);
        Intent intent=getIntent();
        String uname=intent.getStringExtra("uname");

        ListView lv=(ListView) findViewById(R.id.listview);

        UserInfo unf = new UserInfo(uname,UserDisplay.this, names, description, age);
        unf.execute();
        customAdapter adapter = new customAdapter();
        lv.setAdapter(adapter);

    }
    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
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

            repoName.setText(names[position]);
            repoAge.setText(age[position]);
            repoDesc.setText(description[position]);
            return convertView;
        }
    }

}
