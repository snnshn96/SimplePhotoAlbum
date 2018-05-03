package cs.rutgers.edu.android96;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Photo;

public class SearchPhotoListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Photo> data;

    private static LayoutInflater inflater=null;

    public SearchPhotoListAdapter(Context context, ArrayList<Photo> photos) {
        this.data = photos;
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        ImageView photoThumbnail;
    }

    Holder itemView = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.photo_grid_item, null);

            itemView = new Holder();
            itemView.photoThumbnail =(ImageView) vi.findViewById(R.id.photoThumbnail);

            itemView.photoThumbnail.setImageBitmap(data.get(position).getBitmap());
            vi.setTag(itemView);
        }else{
            itemView = (Holder) vi.getTag();
        }

        itemView.photoThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Toast.makeText(context, "You Clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });

        return vi;
    }
}
