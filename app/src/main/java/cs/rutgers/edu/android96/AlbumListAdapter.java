package cs.rutgers.edu.android96;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AlbumListAdapter extends BaseAdapter {

    Context context;
    List<String> data;
    private static LayoutInflater inflater = null;

    private class albumItemView {
        TextView albumTitle;
        ImageView menuDots;
    }

    albumItemView itemView = null;

    public AlbumListAdapter(Context context, List<String> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_with_menu, null);

            itemView = new albumItemView();
            itemView.albumTitle = (TextView) vi.findViewById(R.id.albumTitle);
            itemView.menuDots = (ImageView) vi.findViewById(R.id.menuDots);

            vi.setTag(itemView);

        }else{
            itemView = (albumItemView) vi.getTag();
        }

        itemView.albumTitle.setText(data.get(position));

        final int pos = position;
        try {
            itemView.menuDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.menuDots:
                            PopupMenu popup = new PopupMenu(context, v);
                            popup.getMenuInflater().inflate(R.menu.album_popup_menu, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.rename:
                                            Toast.makeText(context, " Renamed Clicked at position " + " : " + pos, Toast.LENGTH_LONG).show();
                                            break;
                                        case R.id.delete:
                                            Toast.makeText(context, "Delete Clicked at position " + " : " + pos, Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vi;
    }
}
