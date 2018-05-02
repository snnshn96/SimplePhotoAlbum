package cs.rutgers.edu.android96;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import cs.rutgers.edu.android96.models.Album;
import cs.rutgers.edu.android96.models.Photo;


//private ListView thumbnails;

public class AlbumActivity extends MainActivity{
    protected ListView thumbnails;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_album);
        System.out.println("albumactivity");
        Intent intent = getIntent();
        Bundle albumbund = intent.getExtras();
        int position = albumbund.getInt("album position");
        deserialize();
        Album a = albums.get(position);
        this.thumbnails = findViewById(R.id.thumbnails);
    }




}
