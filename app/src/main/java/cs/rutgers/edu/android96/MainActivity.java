package cs.rutgers.edu.android96;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Album;

public class MainActivity extends AppCompatActivity {

    final Context context = this;

    public ArrayList<Album> albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Load Existing albums to the listView
        deserialize();

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

    public boolean createNewAlbum(String name){
        
        return false;
    }
}
