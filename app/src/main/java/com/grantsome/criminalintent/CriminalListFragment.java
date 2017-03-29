package com.grantsome.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import java.util.List;


/**
 * Created by tom on 2017/3/5.
 */

public class CriminalListFragment extends Fragment{

    private RecyclerView mRecyclerViewCrime;

    private MyAdapter myAdapter;

    private boolean mSubtitleVisiable;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mRecyclerViewCrime = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewCrime.setLayoutManager(linearLayoutManager);
        if(savedInstanceState!=null){
            mSubtitleVisiable = savedInstanceState.getBoolean("subtitle");
        }
        updateUI();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void updateSubtitle(){
       CrimeTab crimeTab = CrimeTab.get(getActivity());
       int crimeCount = crimeTab.getCrimes().size();
       String subtitle = getString(R.string.subtitle_format, crimeCount+"");
        if(!mSubtitleVisiable){
            subtitle = null;
        }
       AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
       appCompatActivity.getSupportActionBar().setSubtitle(subtitle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeTab.get(getActivity()).addCrime(crime);
                Intent intent = CriminalPagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisiable =! mSubtitleVisiable;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_cirme_list,menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(!mSubtitleVisiable){
            menuItem.setTitle(R.string.show_subtitle);
        }else {
            menuItem.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("subtitle",mSubtitleVisiable);
    }

    private void updateUI(){
        CrimeTab crimeTab = CrimeTab.get(getActivity());
        List<Crime> crimes = crimeTab.getCrimes();
        for(int i = 0;i < crimes.size();i++){
            Log.d("Fragment mCrimes.size()"," mCrime.getTitle()" + crimes.get(i).getTitle());
            Log.d("Fragment mCrimes.size()"," mCrime.Date" + crimes.get(i).getDate());
        }
        if(myAdapter == null){
            myAdapter = new MyAdapter(crimes);
            mRecyclerViewCrime.setAdapter(myAdapter);
        }else {

            myAdapter.setCrimes(crimes);
            mRecyclerViewCrime.setAdapter(myAdapter);
            //myAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    class MyAdapter extends RecyclerView.Adapter {

        private List<Crime> mCrimes;

        public  MyAdapter(List<Crime> crimes){
            this.mCrimes = crimes;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView = layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(itemView);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.bindCrime(crime);
        }


        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes){
            this.mCrimes = crimes;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTitleTextView;

            private CheckBox mCheckBox;

            private TextView mDateTextView;

            private Crime mCrime;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
                mDateTextView = (TextView) itemView.findViewById(R.id.list_view_item_crime_date_text_view);
                itemView.setOnClickListener(this);
            }

            public  void bindCrime(Crime crime){
                this.mCrime = crime;
                if(mCrime.getTitle() != null) {
                    mTitleTextView.setText(mCrime.getTitle().toString());
                    mDateTextView.setText(mCrime.getDate().toString());
                    mCheckBox.setChecked(mCrime.isSolved());
                }else {
                    updateUI();
                    //mTitleTextView.setText(mCrime.getTitle().toString());
                    mDateTextView.setText(mCrime.getDate().toString());
                    mCheckBox.setChecked(mCrime.isSolved());
                }
            }

            @Override
            public void onClick(View view) {
                Intent intent = CriminalPagerActivity.newIntent(getActivity(),mCrime.getId());
                //Toast.makeText(getActivity(), "mCrime.getId()"+mCrime.getId(), Toast.LENGTH_SHORT).show();
               // startActivityForResult(intent,1);
                startActivity(intent);
            }
        }

    }


  /* @Override
    public void onActivityResult(int requestCode,int resultCode,Intent date){
        if(requestCode==1){

        }
    }
    */

}
