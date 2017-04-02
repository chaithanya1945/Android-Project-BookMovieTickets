package movie.com.bookmovietickets;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nageshtelugunti on 17/09/16.
 */
public class AdminMoviesList extends ListActivity {

    adapter adapt;
    ArrayList<MovieBean> movies;
    TextView txtAmount;
    TextView txtAvailable;
    private SqlAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_movies);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        txtAmount = (TextView) findViewById(R.id.txt_amount);
        txtAvailable = (TextView) findViewById(R.id.txt_remaining);
       mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();
        txtAvailable.setText("Avilable Tickets"+5);
        txtAmount.setText("Collected  amount"+ 0);
        //movies = getAllMovies();


         //adapt = new adapter(getApplicationContext(), R.layout.item_file, movies);
        //setListAdapter(adapt);

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                MovieBean value = (MovieBean) adapter.getItemAtPosition(position);
                ArrayList<History> list =    getHistory();
                for(int i=0;i<list.size();i++){
                    Log.d("nagesh",list.get(i).getName());
                    Log.d("nagesh",movies.get(position).getMovieName());
                    int total = 0;
                    total = total+ Integer.parseInt(list.get(i).getNumber());

                    if(list.get(i).getName().contains(movies.get(position).getMovieName())){
                        txtAvailable.setText("Avilable Tickets : "+(5-total/100));
                        txtAmount.setText("Collected  amount : "+ total);

                        break;

                    }

                }

                openDialog(position,value);
                }
        });



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
    @Override
    protected void onResume() {
        super.onResume();
        movies = getAllMovies();
        adapt = new adapter(getApplicationContext(), R.layout.item_file, movies);
        setListAdapter(adapt);

    }

    private void openDialog(final long pos, final MovieBean value) {
        new AlertDialog.Builder(AdminMoviesList.this)
                .setTitle("Options Dialog")
                .setItems(R.array.options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){

                            case 0:
                                Intent i = new Intent(AdminMoviesList.this,AddMovieActivity.class);
                                i.putExtra("Movie",value);
                                startActivity(i);
                                finish();
                                    break;
                            case 1:
                                mySQLiteAdapter.deleteMovie(value.getId());
                                Log.d("delete",value.getId()+"");
                                Toast.makeText(AdminMoviesList.this, "Delete movie successfully", Toast.LENGTH_SHORT).show();
                                movies = getAllMovies();

                                adapt = new adapter(getApplicationContext(), R.layout.item_file, movies);
                                setListAdapter(adapt);

                                adapt.notifyDataSetChanged();
                                break;
                            case 2:
                                Intent intent = new Intent(AdminMoviesList.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;

                        }

                    }
                }).show();
    }

    private ArrayList<MovieBean> getAllMovies() {
        ArrayList<MovieBean> moviesList = new ArrayList<MovieBean>();

        Cursor c =mySQLiteAdapter.queueMoviesAll();

        try{
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String movieName = c.getString(c.getColumnIndex("MovieName"));
                    byte[] movieImagePath = c.getBlob(c.getColumnIndex("MovieLocation"));
                    String place = c.getString(c.getColumnIndex("location"));
                    String date = c.getString(c.getColumnIndex("date"));
                    String time = c.getString(c.getColumnIndex("time"));

                    MovieBean bean = new MovieBean();
                    bean.setId(c.getLong(c.getColumnIndex("_id")));
                    bean.setMovieName(movieName);
                    bean.setImageArray(movieImagePath);
                    bean.setPlace(place);
                    bean.setMovieDate(date);
                    bean.setMoviewTime(time);

                    moviesList.add(bean);
                }while (c.moveToNext());
            }
        }
    } catch(SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");

        }
        return  moviesList;
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

}
