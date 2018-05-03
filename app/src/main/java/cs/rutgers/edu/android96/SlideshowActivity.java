package cs.rutgers.edu.android96;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Photo;

public class SlideshowActivity extends MainActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    ImageView slideshow;
    ArrayList<Photo> photos;
    int index;
//    Photo starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        this.slideshow = findViewById(R.id.slideshowView);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
        deserialize();
        int albumpos = getIntent().getIntExtra("album", 0);
        this.photos = albums.get(albumpos).getPhotos();
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
}
