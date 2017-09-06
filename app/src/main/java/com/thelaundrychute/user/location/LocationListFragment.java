package com.thelaundrychute.user.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.location.functions.getLocationsByTypes;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.Toolbar;
import com.thelaundrychute.business.location.Location;

import java.util.List;

public class LocationListFragment extends Fragment {
    MapView mapView;
    GoogleMap map;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private LocationAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.location_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);
        return view;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.fragment_wash_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Toolbar.onOptionsItemSelected(getActivity(), item);
    }

    private void updateUI() {
        LocationRepository locationRepository = LocationRepository.get(getActivity());
        List<Location> locations = locationRepository.getLocations();

        getLocationsByTypes request = new getLocationsByTypes();

        getLocationsByTypes.request params = request.newRequest();
        params.setRadius(25);

        request.execute(params,new INetFunctionDelegate<getLocationsByTypes.request, getLocationsByTypes.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<getLocationsByTypes.request, getLocationsByTypes.response> function, com.inMotion.core.Error error) {

                Log.d("testing", "Failed to get success function call");
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<getLocationsByTypes.request, getLocationsByTypes.response> function, FuncResponse<getLocationsByTypes.response> result) {
                getLocationsByTypes.response data = result.getData();
                //mAdapter.setLocations(data.getLocations());
                mAdapter.notifyDataSetChanged();
            }
        });

        if (mAdapter == null) {
            mAdapter = new LocationAdapter(locations);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setLocations(locations);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class LocationHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Location mLocation;

        public LocationHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_solved_check_box);
        }

        public void bindLocation(Location location) {
            mLocation = location;
            mTitleTextView.setText(mLocation.getName());
            //mDateTextView.setText(mLocation.getDate().toString());
            //mSolvedCheckBox.setChecked(mLocation.isSolved());
        }

        @Override
        public void onClick(View v) {
            //Intent intent = WashPagerActivity.newIntent(getActivity(), mLocation.getId());
            //startActivity(intent);
        }
    }

    private class LocationAdapter extends RecyclerView.Adapter<LocationHolder> {

        private List<Location> mLocations;

        public LocationAdapter(List<Location> locations) {
            mLocations = locations;
        }

        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_wash, parent, false);
            return new LocationHolder(view);
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {
            Location location = mLocations.get(position);
            holder.bindLocation(location);
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }

        public void setLocations(List<Location> locations) {
            mLocations = locations;
        }
    }
}
