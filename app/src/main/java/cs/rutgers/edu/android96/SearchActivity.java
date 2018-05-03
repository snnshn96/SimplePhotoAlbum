package cs.rutgers.edu.android96;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

import cs.rutgers.edu.android96.models.Album;
import cs.rutgers.edu.android96.models.Photo;
import cs.rutgers.edu.android96.models.Tag;

public class SearchActivity extends MainActivity {

    EditText searchEditText;
    GridView searchResultGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.searchEditText = findViewById(R.id.searchEditText);
        this.searchResultGridView = findViewById(R.id.searchResultGridView);
    }

    public void search(final View view){
        this.searchEditText.setEnabled(false);
        this.searchEditText.setEnabled(true);
        String searchQuery = searchEditText.getText().toString();
        ArrayList<Photo> searchResult = new ArrayList<Photo>();

        for(Album albm : albums){
            for(Photo pht : albm.getPhotos()){
                if(searchQuery.contains("test")){
                    searchResult.add(pht);
                }
                for(Tag tg : pht.getTags()){
                    if(searchQuery.contains(tg.getValue())){
                        searchResult.add(pht);
                        break;
                    }
                }
            }
        }

        final SearchPhotoListAdapter adapter = new SearchPhotoListAdapter(this, searchResult);
        this.searchResultGridView.setAdapter(adapter);
        this.searchResultGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });

        populateGrid(searchResult);
    }

    public void populateGrid(ArrayList<Photo> searchResult) {
        SearchPhotoListAdapter adp = new SearchPhotoListAdapter(context, searchResult);
        this.searchResultGridView.setAdapter(adp);
    }
}
