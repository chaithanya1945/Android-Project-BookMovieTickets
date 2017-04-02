package movie.com.bookmovietickets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by nageshtelugunti on 23/10/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> moviesList;

    public HistoryAdapter(List<History> moviesList) {
        this.moviesList = moviesList;
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder contactViewHolder, int i) {
        History ci = moviesList.get(i);
        contactViewHolder.mName.setText("Movie Name   :" + ci.getName());
        contactViewHolder.mLocation.setText("Location  :" + ci.getLocation());
        contactViewHolder.date.setText("Date   :" + ci.getDate());
        contactViewHolder.time.setText("Time  :" + ci.getTime());
        contactViewHolder.amount.setText("Amount   :" + ci.getNumber());

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.history_row, viewGroup, false);

        return new HistoryViewHolder(itemView);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        protected TextView mName;
        protected TextView mLocation;
        protected TextView date;
        protected TextView time;
        protected  TextView amount;

        public HistoryViewHolder(View v) {
            super(v);
            mName =  (TextView) v.findViewById(R.id.txt_moviename);
            mLocation = (TextView)  v.findViewById(R.id.txt_location);
            date = (TextView)  v.findViewById(R.id.txt_date);
            time = (TextView) v.findViewById(R.id.txt_time);
            amount = (TextView) v.findViewById(R.id.txt_amount);

        }
    }

}

