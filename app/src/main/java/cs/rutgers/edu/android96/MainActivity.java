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

public class MainActivity extends AppCompatActivity {

    final Context context = this;

    public ArrayList<Album> albums;

    ListView albumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Load Existing albums to the listView
        this.albumListView = findViewById(R.id.albumListView);
        populateList();

//        attempting to add listener to each item on list to open album
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("album position", position);
                Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void serialize(){
        try {
            File f = new File(context.getFilesDir(), getString(R.string.file_name));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(albums);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void deserialize(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(getString(R.string.file_name));
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.albums = (ArrayList<Album>) ois.readObject();
            if (albums == null || albums.isEmpty()) {
                albums = new ArrayList<Album>();
            }
            ois.close();
            fis.close();
        } catch (FileNotFoundException e1) {
            System.out.print("Creating albums.dat");
            serialize();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        System.out.println("Deserializing... ");
    }

    public void promptToCreateAlbum(final View view) {

        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.prompt, null);

        Builder alertDialogBuilder = new Builder(context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.userInputEditText);

        final TextView promptTextView = promptsView.findViewById(R.id.promptTextView);
        promptTextView.setText("New Album Name: ");

        // set dialog message
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                Boolean answer = createNewAlbum(userInput.getText().toString());

                if (answer) {
                    alertDialog.dismiss();
                }else{
                    final TextView alertTextView = (TextView) promptsView.findViewById(R.id.alertTextView);
                    alertTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public boolean checkExistance(String name){
        for (Album a : albums) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean createNewAlbum(String name){
        if (name.equals("")) return false;

        if(checkExistance(name))return false;

        albums.add(new Album(name));
        System.out.println("Added New Album: " + name);
        serialize();
        populateList();
        Toast.makeText(context, " Created New Album: " + name, Toast.LENGTH_LONG).show();
        return true;
    }

    public void populateList(){
        deserialize();
        final List<String> albumNames = new ArrayList<String>();
        for(Album a : this.albums) {
            albumNames.add(a.getName());
        }

        ListAdapter adp = new AlbumListAdapter(context, albumNames);
        albumListView.setAdapter(adp);
    }

    public void removeAlbum(int position){
        String old = albums.get(position).getName();
        this.albums.remove(position);
        serialize();
        populateList();
        Toast.makeText(context, "Removed " + " : " + old, Toast.LENGTH_LONG).show();
    }

    public boolean renameAlbum(int position, String newName){
        String old = albums.get(position).getName();
        if (newName.equals("")) return false;

        if(checkExistance(newName))return false;

        this.albums.get(position).setName(newName);
        serialize();
        populateList();
        Toast.makeText(context, " Renamed " + old + " to " + newName, Toast.LENGTH_LONG).show();
        return true;

    }

    public boolean promptToRename(final int position){
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.prompt, null);

        Builder alertDialogBuilder = new Builder(context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.userInputEditText);
        userInput.setText(albums.get(position).getName());

        final TextView promptTextView = promptsView.findViewById(R.id.promptTextView);
        promptTextView.setText("New Album Name: ");

        // set dialog message
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
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
                Boolean answer = renameAlbum(position, userInput.getText().toString());

                if (answer) {
                    alertDialog.dismiss();
                }else{
                    final TextView alertTextView = (TextView) promptsView.findViewById(R.id.alertTextView);
                    alertTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        return  false;
    }

    public void openAlbum(int pos){

    }


}
