package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private Button buttonAddNewCrime;
    CrimeExpandableAdapter crimeExpandableAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crimeExpandableAdapter = new CrimeExpandableAdapter(getActivity(), generateCrimes());
        crimeExpandableAdapter.onRestoreInstanceState(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        crimeExpandableAdapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        crimeExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        crimeExpandableAdapter.setParentAndIconExpandOnClick(true);

        mCrimeRecyclerView.setAdapter(crimeExpandableAdapter);

        buttonAddNewCrime = (Button) view.findViewById(R.id.button_add_crime);

        return view;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((CrimeExpandableAdapter) mCrimeRecyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    private ArrayList<ParentObject> generateCrimes() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (Crime crime : crimes) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new CrimeChild(crime.getDate(), crime.isSolved()));
            crime.setChildObjectList(childList);
            parentObjects.add(crime);
        }
        return parentObjects;
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonAddNewCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crimeExpandableAdapter.addItems(addCrime());
            }
        });
    }

    @Override
    public void onStop() {
        buttonAddNewCrime.setOnClickListener(null);
        super.onStop();
    }

    private ArrayList<ParentObject> addCrime() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        Crime crime = crimeLab.generateNewCrime();
        ArrayList<ParentObject> parentObjects = new ArrayList<>();

        ArrayList<Object> childList = new ArrayList<>();
        childList.add(new CrimeChild(crime.getDate(), crime.isSolved()));
        crime.setChildObjectList(childList);
        parentObjects.add(crime);

        return parentObjects;
    }
}
