package ru.nsu.ccfit.alex.travki.db;

/**
 * Created by alexandra on 28.11.17.
 */

public class ElemForDB {
    private String field_date;
    private String tab_name, field_name;

    ElemForDB(String _tn, String _fn){
        tab_name = _tn;
        field_name = _fn;
    }

    public String getFieldDate(){
        return field_date;
    }
    public String getTabName(){
        return tab_name;
    }
    public String getFieldName(){
        return field_name;
    }

    public void setFieldDate(String val){
        field_date = val;
    }
    /*public void setTabName(String val){
        tab_name = val;
    }
    public void setFieldName(String val){
        field_name = val;
    }*/
}
