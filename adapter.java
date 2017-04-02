package movie.com.bookmovietickets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayInputStream;
import java.util.List;

public class adapter extends ArrayAdapter<MovieBean> {

    private Context context;
    private List<MovieBean> movies;

    public adapter(Context context, int resource, List<MovieBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_file, parent, false);
        MovieBean movie = movies.get(position);
        TextView tv = (TextView) view.findViewById(R.id.name);
        TextView place = (TextView) view.findViewById(R.id.place);

        tv.setText(movie.getMovieName());
        place.setText(movie.getPlace());
        ImageView img = (ImageView) view.findViewById(R.id.img);
        byte[] theByteArray = movie.getImageArray();
        Log.d("nagesh",theByteArray+"");
        ByteArrayInputStream imageStream = new ByteArrayInputStream(theByteArray);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        img.setImageBitmap(theImage);

        //img.setImageBitmap(BitmapFactory.decodeFile(movie.getImagePath()));
       // Picasso.with(getContext()).load(url + flower.getImagePath()).resize(100, 100).into(img);
        return view;
    }
}