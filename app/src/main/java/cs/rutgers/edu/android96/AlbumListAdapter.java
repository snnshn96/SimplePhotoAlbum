package cs.rutgers.edu.android96;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_with_menu, null);

            itemView = new albumItemView();
            itemView.albumTitle = (TextView) vi.findViewById(R.id.albumTitle);
            itemView.menuDots = (ImageView) vi.findViewById(R.id.menuDots);

            vi.setTag(itemView);

        } else {
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
                                            ((MainActivity) context).promptToRename(pos);
                                            break;
                                        case R.id.delete:
                                            ((MainActivity) context).removeAlbum(pos);
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
