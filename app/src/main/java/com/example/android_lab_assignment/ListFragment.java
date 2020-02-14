package com.example.android_lab_assignment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    SupportMapFragment fragment;
    GoogleMap mMap;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocationDB locationDB = LocationDB.getInstance(getContext());
        List<FavLocation> favLocations = locationDB.daoObjct().getDefault();

        if(favLocations.size()!=0) {
            RecyclerView recyclerView = view.findViewById(R.id.locationList);
            LocationAdapter locationAdapter = new LocationAdapter();


            locationAdapter.setFavLocationList(favLocations);
            locationAdapter.context = getContext();
            locationAdapter.expanded = false;
            recyclerView.setAdapter(locationAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            ItemTouchHelper itemTouchHelper = new
                    ItemTouchHelper(new SwipeToDeleteCallbackForList(locationAdapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }else
        {
            LinearLayout linearLayout = view.findViewById(R.id.listempty);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }
}
