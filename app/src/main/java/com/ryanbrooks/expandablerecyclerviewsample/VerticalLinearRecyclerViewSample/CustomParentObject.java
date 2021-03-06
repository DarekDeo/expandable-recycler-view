package com.ryanbrooks.expandablerecyclerviewsample.VerticalLinearRecyclerViewSample;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Custom parent object that holds a string and an int for displaying data in the parent item. You
 * can use any Object here as long as it implements ParentObject and sets the list to a private
 * variable.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class CustomParentObject implements ParentObject {
    // A List<Object> or subclass of List must be added for the object to work correctly
    private List<Object> mChildObjectList;

    private String mParentText;
    private int mParentNumber;
    private boolean mInitiallyExpanded;


    public CustomParentObject() {
    }

    public String getParentText() {
        return mParentText;
    }

    public void setParentText(String parentText) {
        mParentText = parentText;
    }

    public int getParentNumber() {
        return mParentNumber;
    }

    public void setParentNumber(int parentNumber) {
        mParentNumber = parentNumber;
    }

    /**
     * Getter method for the list of children associated with this parent object
     *
     * @return list of all children associated with this specific parent object
     */
    @Override
    public List<Object> getChildObjectList() {
        return mChildObjectList;
    }

    /**
     * Setter method for the list of children associated with this parent object
     *
     * @param childObjectList the list of all children associated with this parent object
     */
    @Override
    public void setChildObjectList(List<Object> childObjectList) {
        mChildObjectList = childObjectList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return mInitiallyExpanded;
    }

    public void setInitiallyExpanded(boolean initiallyExpanded) {
        mInitiallyExpanded = initiallyExpanded;
    }
}
