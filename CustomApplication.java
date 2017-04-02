package movie.com.bookmovietickets;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by nageshtelugunti on 02/09/16.
 */
public class CustomApplication  extends Application{
    private ArrayList<History> historyArrayList;

    @Override
    public void onCreate() {
        super.onCreate();
        AppSingleTon.getInstance().setApplication(this);

    }

    public ArrayList<History> getHistoryArrayList() {
        return historyArrayList;
    }

    public void setHistoryArrayList(ArrayList<History> historyArrayList) {
        this.historyArrayList = historyArrayList;
    }
}
