package movie.com.bookmovietickets;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nageshtelugunti on 17/09/16.
 */
public class TicketDetails  extends BaseActivity{
      int count =0;
    //static boolean isChkred,isBownchked,isyellowchecked,isCrimsonChekd,isindigoChecked;
    TextView txtMoviename;
    TextView txtLocation;
    TextView txtAmount;
TextView txtDate;
    TextView txtTime;
    EditText edtNumber;
    EditText edtCard;
    EditText edtCvv;
    Button btnSubmit;
    Button btnPay;
    CheckBox cb_red ;
    CheckBox cb_brown ;
    CheckBox cb_yellow;
    CheckBox cb_crimson;
    CheckBox cb_indigo ;
    int val;
    ArrayList<History> historyArrayList = new ArrayList<History>();
    SqlAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();

        initViews();
        if(getIntent().getSerializableExtra("Movie") != null){
           MovieBean bean =(MovieBean) getIntent().getSerializableExtra("Movie");
            txtMoviename.setText("Movie Name : " + bean.getMovieName());
            txtLocation.setText("Movie Place : " + bean.getPlace());
            txtDate.setText("Date : "+bean.getMovieDate());
            txtTime.setText("Time : "+bean.getMoviewTime());

        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>0) {
                     val = count*100;
                    Log.d("nagesh",val+"");
                    txtAmount.setText("Total Amount : " + val);
                    edtCvv.setVisibility(View.VISIBLE);
                    edtCard.setVisibility(View.VISIBLE);
                    btnPay.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(TicketDetails.this,"Please select tickets",Toast.LENGTH_SHORT).show();
                }

                Log.d("nag*************", count + "");

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCard.getText().toString() != null && edtCvv.getText().toString() != null){
                    //edtCvv.setVisibility(View.GONE);
                   // edtCard.setVisibility(View.GONE);
                    //btnPay.setVisibility(View.GONE);
                    //btnSubmit.setVisibility(View.GONE);
                    ///edtNumber.setEnabled(false);

                    History history = new History();
                    history.setName(txtMoviename.getText().toString());
                    history.setLocation(txtLocation.getText().toString());
                    history.setDate(txtDate.getText().toString());
                    history.setTime(txtTime.getText().toString());
                    history.setNumber(val+"");
                    mySQLiteAdapter.insertHistory(history);
                    //historyArrayList.add(history);
        // AppSingleTon.getInstance().getApplication().setHistoryArrayList(historyArrayList);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie TicketsDetails");
        emailIntent.putExtra(Intent.EXTRA_TEXT, history.getName()+"\n"+history.getDate()+"\n"+history.getTime());
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    Intent i = new Intent(TicketDetails.this,TicketsHistory.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(TicketDetails.this,"Payment Submitted successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        txtMoviename = (TextView) findViewById(R.id.txt_moviename);
        txtLocation  = (TextView) findViewById(R.id.txt_location);
        txtAmount    = (TextView) findViewById(R.id.txt_amount);
        edtNumber    = (EditText) findViewById(R.id.edt_number);
        edtCard      = (EditText) findViewById(R.id.edt_card);
        edtCvv       = (EditText) findViewById(R.id.edt_cvv);
        btnSubmit    = (Button)   findViewById(R.id.btn_submit);
        btnPay       = (Button)    findViewById(R.id.btn_payment);
        txtDate      = (TextView) findViewById(R.id.txt_date);
        txtTime      = (TextView) findViewById(R.id.txt_time);
         cb_red = (CheckBox) findViewById(R.id.checkbox_red);
         cb_brown = (CheckBox) findViewById(R.id.checkbox_brown);
         cb_yellow = (CheckBox) findViewById(R.id.checkbox_yellow);
         cb_crimson = (CheckBox) findViewById(R.id.checkbox_crimson);
         cb_indigo = (CheckBox) findViewById(R.id.checkbox_indigo);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onCheckBoxClicked(View v) {
        //Get reference of the CheckBoxes

        //Is the view (Clicked CheckBox) now checked
        boolean checked = ((CheckBox) v).isChecked();

        //So, check which CheckBox was Clicked and generated a Click event
        switch (v.getId()) { //get the id of clicked CheckBox
            case R.id.checkbox_red:
                if (checked) {
                   // TicketDetails.isChkred =true;
                    //if 'Red' CheckBox checked, changed its text to bold and italic
                    cb_red.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);
                    count++;
                } else {
                    //TicketDetails.isChkred =false;

                    //if 'Red' CheckBox unchecked, changed its text to normal state
                    cb_red.setTypeface(null, Typeface.NORMAL);
                    count--;
                }
                break;
            case R.id.checkbox_brown:
                if (checked) {
                    count++;
                   // TicketDetails.isBownchked =true;

                    cb_brown.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);
                } else {
                   // TicketDetails.isBownchked = false;
                    count--;
                    cb_brown.setTypeface(null, Typeface.NORMAL);
                }
                break;
            case R.id.checkbox_yellow:
                if (checked) {
                   // TicketDetails.isyellowchecked = true;
                    count++;
                    cb_yellow.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);
                } else {
                    //TicketDetails.isyellowchecked = false;
                    count--;
                    cb_yellow.setTypeface(null, Typeface.NORMAL);
                }
                break;
            case R.id.checkbox_crimson:
                if (checked) {
                   // TicketDetails.isCrimsonChekd = true;
                    count++;
                    cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);
                } else {
                   // TicketDetails.isCrimsonChekd = false;

                    count--;
                    cb_crimson.setTypeface(null, Typeface.NORMAL);
                }
                break;
            case R.id.checkbox_indigo:
                if (checked) {
                    count++;
                   // isindigoChecked =true;
                    cb_indigo.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);
                } else {
                    //isCrimsonChekd = false;
                    count--;
                    cb_indigo.setTypeface(null, Typeface.NORMAL);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        if(TicketDetails.isChkred){
            cb_red.setChecked(true);
            cb_red.setEnabled(false);
            cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);

        }
        if(TicketDetails.isBownchked){
            cb_brown.setChecked(true);
            cb_brown.setEnabled(false);
            cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);

        }
        if(TicketDetails.isyellowchecked){
            cb_yellow.setChecked(true);
            cb_yellow.setEnabled(false);
            cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);

        }
        if(TicketDetails.isCrimsonChekd){
            cb_crimson.setChecked(true);
            cb_crimson.setEnabled(false);
            cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);

        }
        if(TicketDetails.isindigoChecked){
            cb_indigo.setChecked(true);
            cb_indigo.setEnabled(false);
            cb_crimson.setTypeface(cb_red.getTypeface(), Typeface.BOLD_ITALIC);

        }*/
    }
}
