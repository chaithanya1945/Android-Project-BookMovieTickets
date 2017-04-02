package movie.com.bookmovietickets;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nageshtelugunti on 11/09/16.
 */
public class MoviesListActivity extends AppCompatActivity {

    private SqlAdapter mySQLiteAdapter;
    ArrayList<MovieBean> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySQLiteAdapter = new SqlAdapter(this);
        mySQLiteAdapter.openToRead();

        movies = getAllMovies();
        final RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setAdapter(new MoviesAdapter(movies));

        recList.addOnItemTouchListener(
                new RecyclerItemClickListener(MoviesListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(MoviesListActivity.this,TicketDetails.class);
                        i.putExtra("Movie",movies.get(position));
                        startActivity(i);

                    }
                })
        );

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
