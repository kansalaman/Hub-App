package app.outlab.outlab9;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Button sbut=(Button) findViewById(R.id.Search);
        final EditText uname=(EditText) findViewById(R.id.User);
//        final TextView tv=(TextView) findViewById(R.id.temp);
        final ListView lv=(ListView) findViewById(R.id.listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=(String) lv.getItemAtPosition(position);
                Intent intent=new Intent(getApplicationContext(),UserDisplay.class);
                intent.putExtra("uname", item);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
            }
        });
        sbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                String usearch=uname.getText().toString();
                UserSearch usch=new UserSearch(SearchActivity.this,usearch,lv);
                usch.execute();

            }
        });

    }
}
