package movie.com.bookmovietickets;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Registration extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnRegister;
    private EditText edtUserName,edtFastname,edtLastname;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtPhone;
    private Spinner  roleSpinner;

    private String[] roles = { "User", "Admin"};
    private SqlAdapter mySQLiteAdapter;
    Cursor cursor;
    String textType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_registration);

        initViews();
        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToWrite();

        btnRegister.setOnClickListener(this);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roles);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter_state);
        roleSpinner.setOnItemSelectedListener(this);



    }

    private void initViews() {
        btnRegister =(Button) findViewById(R.id.button_register);
        edtPassword = (EditText) findViewById(R.id.editText_password);
        edtUserName = (EditText) findViewById(R.id.editText_user);
        edtFastname = (EditText) findViewById(R.id.edt_fname);
        edtLastname = (EditText) findViewById(R.id.edit_lastname);

        edtEmail = (EditText) findViewById(R.id.edit_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        roleSpinner =(Spinner) findViewById(R.id.spinner);

    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){

                case R.id.button_register:

                    if(!edtUserName.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()
                            && !textType.isEmpty() && !edtFastname.getText().toString().isEmpty() && !edtLastname.getText().toString().isEmpty()){

                        String email = edtEmail.getText().toString().trim();


                        if (isValidMail(email))
                        {
                            if(isValidMobile(edtPhone.getText().toString().trim()) &&edtPhone.getText().length()==9){

                                mySQLiteAdapter.insert(edtUserName.getText().toString(), edtPassword.getText().toString(),textType);
                                Intent intent = new Intent(Registration.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"Invalid phone number",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                        Toast.makeText(Registration.this,"Please enter all values",Toast.LENGTH_SHORT).show();

            }

    }
    private boolean isValidMail(String email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidMobile(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roleSpinner.setSelection(position);
         textType = roleSpinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
