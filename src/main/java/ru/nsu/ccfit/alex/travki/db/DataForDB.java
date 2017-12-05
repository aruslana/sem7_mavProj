package ru.nsu.ccfit.alex.travki.db;

import java.util.ArrayList;

/**
 * Created by alexandra on 11.11.17.
 */

public class DataForDB {
    private ElemForDB stem_struct, stem_type, stem_leaf;
    private ElemForDB leaf_type, leaf_set, leaf_form;
    private ElemForDB flow_form, flow_group;
    private static final DataForDB ourInstance = new DataForDB();

    public static DataForDB getInstance() {
        return ourInstance;
    }

    private DataForDB() {
        stem_struct = new ElemForDB("stem_long", "st_st");
        stem_type = new ElemForDB("stem_type", "st_t");
        stem_leaf = new ElemForDB("stem_leaf", "st_l");

        leaf_type = new ElemForDB("leaf_type", "l_t");
        leaf_set = new ElemForDB("leaf_set", "l_s");
        leaf_form = new ElemForDB("leaf_form", "l_f");

        flow_form = new ElemForDB("flow_form", "f_f");
        flow_group = new ElemForDB("flow_group", "f_g");
    }

    public ArrayList<ElemForDB> getArray(){
        ArrayList<ElemForDB> res = new ArrayList<>(8);

        res.add(stem_struct);
        res.add(stem_type);
        res.add(stem_leaf);

        res.add(leaf_type);
        res.add(leaf_set);
        res.add(leaf_form);

        res.add(flow_form);
        res.add(flow_group);

        return res;
    }

    /*public String getStemStruct(){
        return stem_struct.getFieldDate();
    }
    public String getStemType(){
        return stem_type.getFieldDate();
    }
    public String getStemLeaf(){
        return stem_leaf.getFieldDate();
    }*/

    public void setStemStruct(String val){
        stem_struct.setFieldDate(val);
    }
    public void setStemType(String val){
        stem_type.setFieldDate(val);
    }
    public void setStemLeaf(String val){
        stem_leaf.setFieldDate(val);
    }

   /* public String getLeafType(){
        return leaf_type.getFieldDate();
    }
    public String getLeafSet(){
        return leaf_set;
    }
    public String getLeafForm(){
        return leaf_form;
    }*/

    public void setLeafType(String val){
        leaf_type.setFieldDate(val);
    }
    public void setLeafSet(String val){
        leaf_set.setFieldDate(val);
    }
    public void setLeafForm(String val){
        leaf_form.setFieldDate(val);
    }

    /*public String getFlowForm(){
        return flow_form;
    }
    public String getFlowGroup(){
        return flow_group;
    }*/

    public void setFlowForm(String val){
        flow_form.setFieldDate(val);
    }
    public void setFlowGroup(String val){
        flow_group.setFieldDate(val);
    }
}
