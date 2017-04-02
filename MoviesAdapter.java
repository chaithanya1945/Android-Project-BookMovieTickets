package movie.com.bookmovietickets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by nageshtelugunti on 30/08/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ContactViewHolder> {

    private List<MovieBean> moviesList;

    public MoviesAdapter(List<MovieBean> moviesList) {
        this.moviesList = moviesList;
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        MovieBean ci = moviesList.get(i);
        contactViewHolder.mName.setText("Movie Name   :" + ci.getMovieName());
        contactViewHolder.mLocation.setText("Location  :"+ci.getPlace());
       // contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
        byte[] theByteArray = ci.getImageArray();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(theByteArray);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        contactViewHolder.mPath.setImageBitmap(theImage);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.grades_row, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView mName;
        protected TextView mLocation;
        protected ImageView mPath;
        //protected TextView vTitle;

        public ContactViewHolder(View v) {
            super(v);
            mName =  (TextView) v.findViewById(R.id.txtName);
            mLocation = (TextView)  v.findViewById(R.id.txtSurname);
            mPath = (ImageView)  v.findViewById(R.id.txtEmail);
           // vTitle = (TextView) v.findViewById(R.id.title);
        }
    }

}
