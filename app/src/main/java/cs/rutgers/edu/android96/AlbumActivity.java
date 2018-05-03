package cs.rutgers.edu.android96;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Photo;

public class AlbumActivity extends MainActivity {

    GridView imagesGridView;
    Button addPhotoButton;
    ArrayList<Photo> photos;

    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);


        this.imagesGridView = findViewById(R.id.imagesGridView);

        this.addPhotoButton = findViewById(R.id.addPhotoButton);

        this.photos = new ArrayList<Photo>();
        this.position = getIntent().getIntExtra("albumPos", 0);
        this.photos = albums.get(this.position).getPhotos();

        final PhotoListAdapter adapter = new PhotoListAdapter(this, photos);
        imagesGridView.setAdapter(adapter);
        imagesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);

                //
            }
        });

        populateGrid();
    }

    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {

            Uri selectedImageURI = data.getData();

            Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


            File pFile = new File(selectedImageURI.getPath());
            Photo newPhoto = new Photo(pFile, bitmap, "No Caption");
            albums.get(this.position).addPhoto(newPhoto);
            serialize();
            populateGrid();
        }
    }


    public void populateGrid() {
        deserialize();
        this.photos = albums.get(this.position).getPhotos();
        PhotoListAdapter adp = new PhotoListAdapter(context, photos);
        imagesGridView.setAdapter(adp);
    }

    public void Display(int position) {
        //TODO DISPLAY STUF
    }

    public void removePhoto(int photoPosition) {
        albums.get(this.position).deletePhoto(albums.get(this.position).getPhoto(photoPosition));
        serialize();
        populateGrid();
    }
}
