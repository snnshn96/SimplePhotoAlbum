package cs.rutgers.edu.android96;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Photo;
import cs.rutgers.edu.android96.models.Tag;

public class SlideshowActivity extends MainActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    ImageView slideshow;
    ArrayList<Photo> photos;
    int index;
    int albumPos;
//    Photo starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        this.slideshow = findViewById(R.id.slideshowView);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
        deserialize();
        this.albumPos = getIntent().getIntExtra("album", 0);
        this.photos = albums.get(albumPos).getPhotos();
        this.index = getIntent().getIntExtra("picPos", 0);
//        starting = a.getPhoto(photopos);
        display(index);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (x2 > x1)
                    {
//                        right swipe
                        index++;
                        if(index > photos.size() - 1){
                            index = 0;
                        }
                        display(index);
                    }

                    else
                    {
//                        left swipe
                        index--;
                        if(index < 0){
                            index = photos.size() - 1;
                        }
                        display(index);
                    }

                }
                else
                {
//                     other cases, ie tap
                    return false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void display(int index){
        this.slideshow.setImageResource(0);
        Photo p = photos.get(index);
        this.slideshow.setImageBitmap(p.getBitmap());

    }

    public void addTag(final View view){
        PopupMenu tagMenu = new PopupMenu(context, view);

        tagMenu.getMenu().add("Person");
        tagMenu.getMenu().add("Location");

        tagMenu.show();
        tagMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Location")) promptTag(true);
                else promptTag(false);
                return false;
            }
        });
    }

    public void promptTag(final boolean type){
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.userInputEditText);
        userInput.setHint("Tag");

        final TextView promptTextView = promptsView.findViewById(R.id.promptTextView);
        promptTextView.setText("Tag: ");

        // set dialog message
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Add Tag", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Do nothing here
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        //overwrite ok button so stays open if album name is taken
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Tag tmp = new Tag((type ? "Location" : "Person"), userInput.getText().toString());

                albums.get(albumPos).getPhoto(index).addTag(tmp);
                serialize();
                photos = albums.get(albumPos).getPhotos();
                alertDialog.dismiss();
            }
        });
    }

    public void removeTag(final View view){
        PopupMenu tagMenu = new PopupMenu(context, view);
        final ArrayList<Tag> tags = albums.get(albumPos).getPhoto(index).getTags();

        if (tags.size() == 0){
            Toast.makeText(context, "No Tags To Remove.", Toast.LENGTH_LONG).show();
            return;
        }

        for(Tag tag : tags){
            String name = tag.getType() + ":" + tag.getValue();
            tagMenu.getMenu().add(name);
        }
        tagMenu.show();
        tagMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String[] part = item.getTitle().toString().split(":");
                int count = 0;
                for(int i = 0; i<tags.size();i++){
                    if(tags.get(i).getType().equals(part[0]) && tags.get(i).getValue().equals(part[1])){
                        count = i;
                        break;
                    }
                }
                albums.get(albumPos).getPhoto(index).deleteTag(count);
                serialize();
                photos = albums.get(albumPos).getPhotos();
                return false;
            }
        });
    }
}
