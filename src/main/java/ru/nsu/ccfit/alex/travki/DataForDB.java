package ru.nsu.ccfit.alex.travki;

/**
 * Created by alexandra on 11.11.17.
 */

public class DataForDB {
    private String stem_struct, stem_type, stem_leaf;
    private String leaf_type, leaf_set, leaf_form;
    private static final DataForDB ourInstance = new DataForDB();

    public static DataForDB getInstance() {
        return ourInstance;
    }

    private DataForDB() {
    }

    public String getStemStruct(){
        return stem_struct;
    }
    public String getStemType(){
        return stem_type;
    }
    public String getStemLeaf(){
        return stem_leaf;
    }

    public void setStemStruct(String val){
        stem_struct = val;
    }
    public void setStemType(String val){
        stem_type = val;
    }
    public void setStemLeaf(String val){
        stem_leaf = val;
    }

    public String getLeafType(){
        return leaf_type;
    }
    public String getLeafSet(){
        return leaf_set;
    }
    public String getLeafForm(){
        return leaf_form;
    }

    public void setLeafType(String val){
        leaf_type = val;
    }
    public void setLeafSet(String val){
        leaf_set = val;
    }
    public void setLeafForm(String val){
        leaf_form = val;
    }
}
