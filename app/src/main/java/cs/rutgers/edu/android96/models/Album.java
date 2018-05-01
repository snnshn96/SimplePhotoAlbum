package cs.rutgers.edu.android96.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Album implements Comparable<Album>, Comparator<Album>, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String idName;

    private ArrayList<Photo> photos;

    public Album(String name){
        this.setName(name);
        photos = new ArrayList<Photo>();
    }

    public void addPhoto(Photo photo){
//		create new array list of photos if it doesnt already exist
        if(photos == null)
            photos = new ArrayList<Photo>();
        for (Photo p : photos){
            if (p.equals(photo)){
//				if photo exists dont add
                return;
            }
        }
        photos.add(photo);
    }


    public void deletePhoto(Photo photo){
        if(photos == null)
            return;
//		check if the selected photo to be deleted exists in album
        if(!photos.contains(photo))
            return;
        photos.remove(photo);
    }


    public boolean moveTo(Photo target, Album destination){
        if (target == null || destination == null) return false;
        for (Photo p : destination.getPhotos()){
            if (p.equals(target)){
                return false;
            }
        }
        this.deletePhoto(target);
        destination.addPhoto(target);
        return true;

    }

    public Photo getPhoto(int index){
        if(photos == null)
            return null;
        return photos.get(index);
    }


    public int getNumPhotos(){
        if (photos == null) return 0;
        return photos.size();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setIdName(name.toLowerCase());
    }


    public String toString(){
        return this.getName();
    }


    public int compare(Album o1, Album o2) {
        return o1.compareTo(o2);
    }


    public int compareTo(Album o) {
        return this.getIdName().compareTo(o.getIdName());
    }


    public int indexOf(Photo photo){
        int ret = -1;
        for (Photo p : photos){
            if (p.equals(photo)) ret = photos.indexOf(p);
        }
        return ret;
    }


    public String getIdName() {
        return idName;
    }


    public void setIdName(String idName) {
        this.idName = idName;
    }


    public ArrayList<Photo> getPhotos(){
        return photos;
    }
}
