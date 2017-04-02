package movie.com.bookmovietickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private TextView txtPatrons;
    private TextView txtShows;
    private TextView txtReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtPatrons = (TextView) findViewById(R.id.txt_patrons);
        txtShows = (TextView) findViewById(R.id.txt_shows);
        txtReports = (TextView) findViewById(R.id.txt_reports);

        txtPatrons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        txtShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MoviesListActivity.class);
                startActivity(intent);
            }
        });

        txtReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
  }
        });
    }

}
