package movie.com.bookmovietickets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by nageshtelugunti on 11/09/16.
 */
public class AddMovieActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    byte[] imageName;
    Button btnSelectDate,btnSelectTime;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;

    // variables to save user selected date and time
    public  int year,month,day,hour,minute;
    // declare  the variables to Show/Set the date and time when Time and  Date Picker Dialog first appears
    private int mYear, mMonth, mDay,mHour,mMinute;



    byte[] imgDecodableString;

    private EditText edtAddMovieName,edtLocation,edtSeats;
    private ImageView imgMovie;
    private Button btnAdd;
    private SqlAdapter mySQLiteAdapter;
    long position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movies_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();
        edtAddMovieName = (EditText) findViewById(R.id.edt_movie);
        edtLocation = (EditText) findViewById(R.id.edt_location);
        edtSeats = (EditText) findViewById(R.id.edt_seats);
        btnSelectDate=(Button)findViewById(R.id.buttonSelectDate);
        btnSelectTime=(Button)findViewById(R.id.buttonSelectTime);

        imgMovie = (ImageView) findViewById(R.id.imgImage);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        if(getIntent().getSerializableExtra("Movie") != null){
            MovieBean movie = (MovieBean) getIntent().getSerializableExtra("Movie");
            edtAddMovieName.setText(movie.getMovieName());
            edtLocation.setText(movie.getPlace());
            imgDecodableString =movie.getImageArray();
            btnSelectDate.setText(movie.getMovieDate());
            btnSelectTime.setText(movie.getMoviewTime());
            edtSeats.setText(movie.getSeats());
            edtSeats.setVisibility(View.GONE);
            btnAdd.setText("Update");
            position= movie.getId();

            Log.d("nagesh",position+"");

            ByteArrayInputStream imageStream = new ByteArrayInputStream(imgDecodableString);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imgMovie.setImageBitmap(theImage);

        }


        // Set ClickListener on btnSelectDate
        btnSelectDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Show the DatePickerDialog
                showDialog(DATE_DIALOG_ID);
            }
        });

        // Set ClickListener on btnSelectTime
        btnSelectTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Show the TimePickerDialog
                showDialog(TIME_DIALOG_ID);
            }
        });


        imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showCustomDialog();
            }

            
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // imgDecodableString="image";
                if(btnAdd.getText().toString().equalsIgnoreCase("Update")){
                  boolean x=  mySQLiteAdapter.updateMovie(position,edtAddMovieName.getText().toString(),imgDecodableString,edtLocation.getText().toString(),btnSelectTime.getText().toString(),btnSelectDate.getText().toString(),edtSeats.getText().toString());
                   Log.d("nagesh db",x+"");
                    Intent i = new Intent(AddMovieActivity.this, AdminMoviesList.class);
                    startActivity(i);
                    finish();
                }else {
                    if (edtAddMovieName.getText().toString() != null && imgDecodableString != null) {
                        Log.d("nagesh insert",imgDecodableString+"");

                        long id = mySQLiteAdapter.insertMovie(edtAddMovieName.getText().toString(), imgDecodableString, edtLocation.getText().toString(),btnSelectDate.getText().toString(),btnSelectTime.getText().toString(),edtSeats.getText().toString());
                        Log.d("nagesh",id+"");

                        if (id > 0)
                            Toast.makeText(AddMovieActivity.this, "Add Movie SuccessFully", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddMovieActivity.this, AdminMoviesList.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(AddMovieActivity.this, "Please add all fields", Toast.LENGTH_SHORT).show();
                    }
                }
      }
        });

    }
    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    btnSelectDate.setText(day+"-"+month+"-"+year);
                }
            };

    // Register  TimePickerDialog listener
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hour = hourOfDay;
                    minute = min;
                    // Set the Selected Date in Select date Button
                    btnSelectTime.setText(hour+"-"+minute);
                }
            };


    // Method automatically gets Called when you call showDialog()  method
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // create a new DatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TimePickerDialog with values you want to show
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);

        }
        return null;
    }




    private void showCustomDialog() {
        final String[] option = new String[] { "Take from Camera",
                "Select from Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();


    }

    /**
     * open camera method
     */
    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    /**
     * open gallery method
     */

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
    switch (id){
    case R.id.action_settings:
        Intent i = new Intent(AddMovieActivity.this,AdminMoviesList.class);
        startActivity(i);
        break;
    case R.id.action_logout:
        Intent intent = new Intent(AddMovieActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        break;
}
        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
       //     return true;
      //  }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

/*        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
               // ImageView imgView = (ImageView) findViewById(R.id.imgImage);
                imgMovie.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                }else{
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }*/

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    imgDecodableString = imageInByte;
                    Log.e("output before ", imageInByte.toString());
                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
                    Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                    imgMovie.setImageBitmap(theImage);


                   // imgMovie.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    // mySQLiteAdapter.addContact(new Contact("Android", imageInByte));
                   // Intent i = new Intent(SQLiteDemoActivity.this,SQLiteDemoActivity.class);
                    //startActivity(i);
                   // finish();

                }
                break;
            case PICK_FROM_GALLERY:
                Bundle extras2 = data.getExtras();

                if (extras2 != null) {
                    Bitmap yourImage = extras2.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    Log.e("output before", imageInByte.toString());
                    imgDecodableString = imageInByte;
                    //imgMovie.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
                    Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                    imgMovie.setImageBitmap(theImage);

                    // Inserting Contacts
                   // mySQLiteAdapter.addContact(new Contact("Android", imageInByte));
                    //Intent i = new Intent(SQLiteDemoActivity.this, SQLiteDemoActivity.class);
                    //startActivity(i);
                   // finish();
                }

                break;
        }


    }

}
