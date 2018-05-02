package cs.rutgers.edu.android96;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public abstract class PhotoListAdapter extends BaseAdapter {

    Context context;
    List<String> data;
    private static LayoutInflater inflater = null;

    private class photoItemView {
        ImageView image;
    }


}
