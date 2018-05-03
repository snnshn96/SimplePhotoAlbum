package cs.rutgers.edu.android96.models;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import cs.rutgers.edu.android96.MyBitmap;

public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String caption;
    private String path;
    private File file;
    private String pathName;
    private ArrayList<Tag> tags;
    private MyBitmap bitmap;

    public Photo(File file, Bitmap bitmap, String caption){
        this.path = file.getPath();
        this.caption = caption;
        this.file = file;
        tags = new ArrayList<Tag>();
        this.bitmap = new MyBitmap(bitmap);

        this.pathName = file.toURI().toString();
    }


    public int getNumTags(){
        if (tags == null) return 0;
        return tags.size();
    }


    public ArrayList<Tag> getTags(){
        return this.tags;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addTag(Tag tag){
//		create new arraylist of tags if tags is null
        if(tags == null)
            tags = new ArrayList<Tag>();
        for (Tag t : tags){
            if (t.equals(tag)){
//				prevent duplicate tags
                System.out.println("Tag already exists in this photo");
                return;
            }
        }
        tags.add(tag);
        Collections.sort(tags);
    }

    public void deleteTag(int index){
//		cant delete if tags is empty
        if(tags == null)
            return;
        tags.remove(index);
    }

    public File getFile(){
        return file;
    }

    public Bitmap getBitmap() {
        return this.bitmap.getBitmap();
    }

    public String getPath() {
        return path;
    }

    public String toString(){
        return this.getCaption();
    }

    public boolean equals(Object o){
        if (o == null || !(o instanceof Photo))
            return false;
        Photo op = (Photo) o;
        //return this.getPath().equals(op.getPath());
        return this.getPath().equals(op.getPath());
    }

    public String getPathName(){
        return this.pathName;
    }


}
