package cs.rutgers.edu.android96;

import android.os.Bundle;
import android.widget.GridView;

public class AlbumActivity extends MainActivity {

    GridView imagesGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        this.imagesGridView = findViewById(R.id.imagesGridView);
    }
}
