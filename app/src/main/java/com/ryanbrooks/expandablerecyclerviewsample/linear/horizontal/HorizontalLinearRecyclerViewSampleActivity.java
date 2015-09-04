package com.ryanbrooks.expandablerecyclerviewsample.linear.horizontal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ClickListeners.ExpandCollapseListener;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.ryanbrooks.expandablerecyclerviewsample.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

public class HorizontalLinearRecyclerViewSampleActivity extends AppCompatActivity implements ExpandCollapseListener {

    private static final String CHILD_TEXT = "Child ";
    private static final String SECOND_CHILD_TEXT = "_2";
    private static final String PARENT_TEXT = "Parent ";
    private static final long INITIAL_ROTATION_SPEED_MS = 100;

    @InjectView(R.id.activity_horizontal_linear_recycler_view_sample_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.activity_horizontal_linear_recycler_view_sample_recyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.vertical_sample_toolbar_checkbox)
    CheckBox mAnimationEnabledCheckBox;
    @InjectView(R.id.vertical_sample_toolbar_spinner)
    Spinner mToolbarSpinner;

    private HorizontalExpandableAdapter mExpandableAdapter;
    private ArrayList<Long> mDurationList;

    public static Intent newIntent(Context context) {
        return new Intent(context, HorizontalLinearRecyclerViewSampleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_linear_recycler_view_sample);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Generate spinner's list of rotation speeds (in ms)
        mDurationList = generateSpinnerSpeeds();

        // Create a new adapter with 20 test data items
        mExpandableAdapter = new HorizontalExpandableAdapter(this, setUpTestData(20));

        // Attach this activity to the Adapter as the ExpandCollapseListener
        mExpandableAdapter.addExpandCollapseListener(this);

        // Set the RecyclerView's adapter to the ExpandableAdapter we just created
        mRecyclerView.setAdapter(mExpandableAdapter);
        // Set the layout manager to a LinearLayout manager for vertical list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set spinner adapter
        HorizontalSpinnerAdapter customSpinnerAdapter = new HorizontalSpinnerAdapter(this, mDurationList);
        mToolbarSpinner.setAdapter(customSpinnerAdapter);
    }

    /**
     * Save the instance state of the adapter to keep expanded/collapsed states when rotating or
     * pausing the activity.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mExpandableAdapter.onSaveInstanceState(outState);
    }

    /**
     * Load the expanded/collapsed states of the adapter back into the view when done rotating or
     * resuming the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mExpandableAdapter.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * On item selected listener of the rotation speed spinner in the Toolbar.
     *
     * @param position
     */
    @OnItemSelected(R.id.vertical_sample_toolbar_spinner)
    void onItemSelected(int position) {
        if (mAnimationEnabledCheckBox.isChecked()) { // Only the custom triggering view triggers expansion
            if (mDurationList.get(position) == 0) {
                // Sets the rotation animation to off
                mExpandableAdapter.setParentClickableViewAnimationDuration(
                        ExpandableRecyclerAdapter.CUSTOM_ANIMATION_DURATION_NOT_SET);
            } else {
                // Sets the animation duration to the corresponding duration at the selected position
                mExpandableAdapter.setParentClickableViewAnimationDuration(mDurationList.get(position));
            }
            // Disables clicking of both the item and the custom clickable view declared by the user
            mExpandableAdapter.setParentAndIconExpandOnClick(false);
        } else { // Both the custom triggering view and the parent item trigger expansion when clicked
            if (mDurationList.get(position) == 0) {
                // Sets the rotation animation to off
                mExpandableAdapter.setParentClickableViewAnimationDuration(
                        ExpandableRecyclerAdapter.CUSTOM_ANIMATION_DURATION_NOT_SET);
                // Disable clicking of both parent and child to trigger expansion/collapsing
                mExpandableAdapter.setParentAndIconExpandOnClick(false);
            } else {
                // Sets the animation duration to the corresponding duration at the selected position
                mExpandableAdapter.setParentClickableViewAnimationDuration(mDurationList.get(position));
                // Sets the custom triggering view to the id of the view
                mExpandableAdapter.setCustomParentAnimationViewId(R.id.list_item_parent_horizontal_arrow_imageView);
                // Sets both the custom triggering view and the parent item to trigger expansion
                mExpandableAdapter.setParentAndIconExpandOnClick(true);
            }
        }
        mExpandableAdapter.notifyDataSetChanged();
    }

    /**
     * Check changed listener for the custom triggering view checkbox.
     *
     * @param isChecked
     */
    @OnCheckedChanged(R.id.vertical_sample_toolbar_checkbox)
    void onCheckChanged(boolean isChecked) {
        if (isChecked) { // Only the custom triggering view can trigger expansion
            mExpandableAdapter.setParentAndIconExpandOnClick(false);
            mExpandableAdapter.setCustomParentAnimationViewId(R.id.list_item_parent_horizontal_arrow_imageView);
            mExpandableAdapter.setParentClickableViewAnimationDuration((Long) mToolbarSpinner.getSelectedItem());
            mExpandableAdapter.notifyDataSetChanged();
        } else { // Both the custom triggering view and the parent item can trigger expansion
            mExpandableAdapter.setParentAndIconExpandOnClick(true);
            mExpandableAdapter.setCustomParentAnimationViewId(R.id.list_item_parent_horizontal_arrow_imageView);
            mExpandableAdapter.setParentClickableViewAnimationDuration((Long) mToolbarSpinner.getSelectedItem());
            mExpandableAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Method to set up test data used in the RecyclerView.
     * <p/>
     * Each child object contains a string.
     * Each parent object contains a number corresponding to the number of the parent and a string
     * that contains a message.
     * Each parent also contains a list of children which is generated in this. Every odd numbered
     * parent gets one child and every even numbered parent gets two children.
     *
     * @param numItems
     * @return an ArrayList of Objects that contains all parent items. Expansion of children are handled in the adapter
     */
    private ArrayList<ParentObject> setUpTestData(int numItems) {
        ArrayList<ParentObject> parentObjectList = new ArrayList<>();
        for (int i = 0; i < numItems; i++) {
            ArrayList<Object> childObjectList = new ArrayList<>();

            // Evens get 2 children, odds get 1
            if (i % 2 == 0) {
                HorizontalChildObject horizontalChildObject = new HorizontalChildObject();
                HorizontalChildObject horizontalChildObject2 = new HorizontalChildObject();
                horizontalChildObject.setChildText(CHILD_TEXT + i);
                horizontalChildObject2.setChildText(CHILD_TEXT + i + SECOND_CHILD_TEXT);
                childObjectList.add(horizontalChildObject);
                childObjectList.add(horizontalChildObject2);
            } else {
                HorizontalChildObject horizontalChildObject = new HorizontalChildObject();
                horizontalChildObject.setChildText(CHILD_TEXT + i);
                childObjectList.add(horizontalChildObject);
            }

            HorizontalParentObject horizontalParentObject = new HorizontalParentObject();
            horizontalParentObject.setChildObjectList(childObjectList);
            horizontalParentObject.setParentNumber(i);
            horizontalParentObject.setParentText(PARENT_TEXT + i);
            if (i == 0) {
                horizontalParentObject.setInitiallyExpanded(true);
            }
            parentObjectList.add(horizontalParentObject);
        }
        return parentObjectList;
    }

    /**
     * Method to set up the list of animation durations for the Toolbar's Spinner.
     * <p/>
     * The list contains long values that correspond to the length of time (in ms) of the animation.
     *
     * @return the list of times (in ms) to be populated into the Toolbar's spinner.
     */
    private ArrayList<Long> generateSpinnerSpeeds() {
        ArrayList<Long> speedList = new ArrayList<>();
        speedList.add(ExpandableRecyclerAdapter.CUSTOM_ANIMATION_DURATION_NOT_SET);
        for (int i = 1; i <= 10; i++) {
            speedList.add(INITIAL_ROTATION_SPEED_MS * i);
        }
        return speedList;
    }

    @Override
    public void onRecyclerViewItemExpanded(int position) {
        Toast.makeText(this, "Item Expanded " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerViewItemCollapsed(int position) {
        Toast.makeText(this, "Item Collapsed " + position, Toast.LENGTH_SHORT).show();
    }
}
