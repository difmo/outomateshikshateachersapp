package com.saptrishi.outomateshikshateachersapp.expandableListViewAdapter;

import java.util.ArrayList;

public class ExpandListGroup {
    private String Name;
    private String heading;
    private String dueDate;
    private ArrayList<ExpandListChild> Items;

    public String getName() {
        return Name;
    }
    public String getHeading() {
        return heading;
    }
    public String getDueDate()
    {
        return dueDate;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public void setHeading(String heading)
    {
        this.heading=heading;
    }
    public void setDueDate(String dueDate)
    {
        this.dueDate=dueDate;
    }

    public ArrayList<ExpandListChild> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ExpandListChild> Items) {
        this.Items = Items;
    }

}