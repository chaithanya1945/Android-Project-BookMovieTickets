package movie.com.bookmovietickets;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by nageshtelugunti on 23/10/16.
 */
public class TicketsHistory  extends Activity{
    ArrayList<History> list;
    SqlAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();


/*        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        */
        list = getHistory();
 Log.d("list size=====",list.size()+"");
        final RecyclerView recList = (RecyclerView) findViewById(R.id.historyList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(new HistoryAdapter(list));

        recList.addOnItemTouchListener(
                new RecyclerItemClickListener(TicketsHistory.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );
    }
    private ArrayList<History> getHistory() {
        ArrayList<History> moviesList = new ArrayList<History>();

      Cursor c =mySQLiteAdapter.queueHistoryAll();
Log.d("Nagesh",c.getCount()+"");
        try{
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String movieName = c.getString(c.getColumnIndex("MovieName"));
                        String place = c.getString(c.getColumnIndex("location"));
                        String date = c.getString(c.getColumnIndex("date"));
                        String time = c.getString(c.getColumnIndex("time"));
                        String amount = c.getString(c.getColumnIndex("amount"));


                        History bean = new History();
                        bean.setName(movieName);
                        bean.setLocation(place);
                        bean.setDate(date);
                        bean.setTime(time);
                        bean.setNumber(amount);

                        moviesList.add(bean);
                    }while (c.moveToNext());
                }
            }
        } catch(SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");

        }
        return  moviesList;
    }
}
