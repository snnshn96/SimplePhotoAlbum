package cs.rutgers.edu.android96;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import cs.rutgers.edu.android96.models.*;

public class SlideshowActivity extends MainActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    ImageView slideshow;
    Album a;
    int index;
//    Photo starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        this.slideshow = findViewById(R.id.slideshowView);


        deserialize();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int albumpos = bundle.getInt("albumpos");
        a = albums.get(albumpos);
        int photopos = bundle.getInt("photopos");
        this.index = photopos;
//        starting = a.getPhoto(photopos);
        display(photopos);
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
                        if(index > a.getNumPhotos() - 1){
                            index = 0;
                        }
                        display(index);
                    }

                    else
                    {
//                        left swipe
                        index--;
                        if(index < 0){
                            index = a.getNumPhotos() - 1;
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
        Photo p = a.getPhoto(index);
        this.slideshow.setImageBitmap(BitmapFactory.decodeFile(p.getPath()));

    }
}
