package movie.com.bookmovietickets;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnRegister;
    private EditText edtUserName;
    private EditText edtPassword;
    private SqlAdapter mySQLiteAdapter;

    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         initViews();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }


    private void initViews() {
        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();

        btnLogin  =(Button) findViewById(R.id.button_login);
        btnRegister =(Button) findViewById(R.id.button_register);
        edtPassword = (EditText) findViewById(R.id.editText_password);
        edtUserName = (EditText) findViewById(R.id.editText_user);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                cursor = mySQLiteAdapter.queueAll();
                if (cursor.moveToFirst()) {
                    do {
                        String name  = cursor.getString(1);
                        String pswd  = cursor.getString(2);
                        String roleType = cursor.getString(3);
                        if(!edtUserName.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty() ) {

                            if (roleType.equalsIgnoreCase("Admin") && edtUserName.getText().toString().equalsIgnoreCase(name)) {
                                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            } else {
                                Intent intent = new Intent(MainActivity.this, MoviesListActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }else{
                           Toast.makeText(MainActivity.this,"Invalid credentials",Toast.LENGTH_SHORT).show();
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();

                //Intent i = new Intent(MainActivity.this,HomeActivity.class);
               // startActivity(i);
                break;
            case R.id.button_register:
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
                break;
        }
    }
}
