package cs.rutgers.edu.android96;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Photo;

public class PhotoListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Photo> data;


    private static LayoutInflater inflater=null;

    public PhotoListAdapter(Context context, ArrayList<Photo> photos) {
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
                //Toast.makeText(context, "You Clicked "+position, Toast.LENGTH_SHORT).show();
                final PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.album_popup_menu2, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.display:
                                ((AlbumActivity) context).Display(position);
                                break;
                            case R.id.move:
                                PopupMenu albMenu = new PopupMenu(context, v);
                                ArrayList<String> als = ((AlbumActivity) context).getAlbums();
                                als.remove(position);
                                for(String a : als){
                                    albMenu.getMenu().add(a);
                                }
                                albMenu.show();
                                albMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        Toast.makeText(context, " Moved "  + item.getTitle(), Toast.LENGTH_LONG).show();
                                        ((AlbumActivity) context).Move(position, item.getTitle());
                                        return false;
                                    }
                                });
                                break;
                            case R.id.delete:
                                ((AlbumActivity) context).removePhoto(position);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });

        return vi;
    }
}
