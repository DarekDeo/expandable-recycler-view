package com.ryanbrooks.expandablerecyclerviewsample.linear.horizontal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.ryanbrooks.expandablerecyclerviewsample.R;

import java.util.List;

/**
 * An example custom implementation of the ExpandableRecyclerAdapter.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class HorizontalExpandableAdapter extends ExpandableRecyclerAdapter<HorizontalParentViewHolder, HorizontalChildViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private LayoutInflater mInflater;

    /**
     * Public primary constructor.
     *
     * @param context for inflating views
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public HorizontalExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Public secondary constructor. This constructor adds the ability to add a custom triggering
     * view when the adapter is created without having to set it later. This is here for demo
     * purposes.
     *
     * @param context for inflating views
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     * @param customClickableViewId the id of the view that triggers the expansion
     */
    public HorizontalExpandableAdapter(Context context, List<ParentObject> parentItemList,
                                       int customClickableViewId) {
        super(context, parentItemList, customClickableViewId);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Public secondary constructor. This constructor adds the ability to add a custom triggering
     * view and a custom animation duration when the adapter is created without having to set them
     * later. This is here for demo purposes.
     *
     * @param context for inflating views
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     * @param customClickableViewId the id of the view that triggers the expansion
     * @param animationDuration the duration (in ms) of the rotation animation
     */
    public HorizontalExpandableAdapter(Context context, List<ParentObject> parentItemList,
                                       int customClickableViewId, long animationDuration) {
        super(context, parentItemList, customClickableViewId, animationDuration);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public HorizontalParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_parent_horizontal, parent, false);
        return new HorizontalParentViewHolder(view);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public HorizontalChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_child_horizontal, parent, false);
        return new HorizontalChildViewHolder(view);
    }

    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(HorizontalParentViewHolder parentViewHolder, int position, Object parentObject) {
        HorizontalParentObject horizontalParentObject = (HorizontalParentObject) parentObject;
        parentViewHolder.numberText.setText(Integer.toString(horizontalParentObject.getParentNumber()));
        parentViewHolder.dataText.setText(horizontalParentObject.getParentText());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(HorizontalChildViewHolder childViewHolder, int position, Object childObject) {
        HorizontalChildObject horizontalChildObject = (HorizontalChildObject) childObject;
        childViewHolder.dataText.setText(horizontalChildObject.getChildText());
    }
}