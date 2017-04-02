package movie.com.bookmovietickets;

import android.view.View;

/**
 * Created by nageshtelugunti on 17/09/16.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);

}
