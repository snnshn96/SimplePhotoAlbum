package cs.rutgers.edu.android96;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.content.Intent;
import cs.rutgers.edu.android96.models.Photo;

import java.util.ArrayList;
import java.util.List;

import cs.rutgers.edu.android96.models.Album;

public class AlbumActivity extends MainActivity {

    GridView imagesGridView;
    List<Photo> photos;
    Album a;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        this.imagesGridView = findViewById(R.id.imagesGridView);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        deserialize();
//        this.a = albums.get(bundle.getInt("album position"));

    }

}
