package com.thelaundrychute.user.wash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.user.common.web.WebActivity;
//import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.Toolbar;

import java.util.List;

public class WashListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private WashAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: Temporary
        com.inMotion.session.Context.init(getActivity());

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wash_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.wash_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
        /*
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Toolbar.onOptionsItemSelected(getActivity(), item);
    }

    private void updateSubtitle() {
        WashRepository washRepository = WashRepository.get(getActivity());
        int washCount = washRepository.getWashes().size();
        String subtitle = getString(R.string.subtitle_format, washCount);

        if (!mSubtitleVisible) {
            //subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        WashRepository washRepository = WashRepository.get(getActivity());
        List<Wash> washes = washRepository.getWashes();

        if (mAdapter == null) {
            mAdapter = new WashAdapter(washes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWashes(washes);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class WashHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Wash mWash;

        public WashHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_solved_check_box);
        }

        public void bindWash(Wash wash) {
            mWash = wash;
            mTitleTextView.setText(mWash.getBag().getId());
           // mDateTextView.setText(mWash.getDate().toString());
           // mSolvedCheckBox.setChecked(mWash.isSolved());
        }

        @Override
        public void onClick(View v) {
            //Intent intent = WashPagerActivity.newIntent(getActivity(), mWash.getId());
            //Intent intent = WebActivity.newIntent(getActivity(), WebPages.HOME, "/bag-list/", String.valueOf(mWash.getId()));
            Intent intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
            startActivity(intent);
        }
    }

    private class WashAdapter extends RecyclerView.Adapter<WashHolder> {

        private List<Wash> mWashes;

        public WashAdapter(List<Wash> washes) {
            mWashes = washes;
        }

        @Override
        public WashHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_wash, parent, false);
            return new WashHolder(view);
        }

        @Override
        public void onBindViewHolder(WashHolder holder, int position) {
            Wash wash = mWashes.get(position);
            holder.bindWash(wash);
        }

        @Override
        public int getItemCount() {
            return mWashes.size();
        }

        public void setWashes(List<Wash> washes) {
            mWashes = washes;
        }
    }
}
