package cs.rutgers.edu.android96.models;

import java.io.Serializable;
import java.util.Comparator;

public class Tag implements Comparable<Tag>, Comparator<Tag>, Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String value;


    public Tag(String type, String value){
        this.setType(type);
        this.setValue(value);
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String toString(){
        return this.getType() + ": " + this.getValue();
    }

    public boolean equals(Object o){
        if (o == null || !(o instanceof Tag)) return false;
        Tag ot = (Tag) o;
        if (ot.getType().equals("")){
            return this.getValue().toLowerCase().equals(ot.getValue().toLowerCase());
        }
        if (ot.getValue().equals("")){
            return this.getType().toLowerCase().equals(ot.getType().toLowerCase());
        }
        return (this.getType().toLowerCase().equals(ot.getType().toLowerCase())
                && this.getValue().toLowerCase().equals(ot.getValue().toLowerCase()));
    }


    public int compare(Tag t1, Tag t2) {
        return t1.compareTo(t2);
    }


    public int compareTo(Tag other) {
        if (this.getType().toLowerCase().equals(other.getType().toLowerCase())){
            return this.getValue().toLowerCase().compareTo(other.getValue().toLowerCase());
        }else{
            return this.getType().toLowerCase().compareTo(other.getType().toLowerCase());
        }
    }
}
