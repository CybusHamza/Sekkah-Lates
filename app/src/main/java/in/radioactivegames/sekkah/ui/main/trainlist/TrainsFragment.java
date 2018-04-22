package in.radioactivegames.sekkah.ui.main.trainlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.radioactivegames.sekkah.R;
import in.radioactivegames.sekkah.base.BaseFragment;
import in.radioactivegames.sekkah.data.model.StationPOJO;
import in.radioactivegames.sekkah.data.model.Train;
import in.radioactivegames.sekkah.data.model.TrainPOJO;
import in.radioactivegames.sekkah.di.component.FragmentComponent;
import in.radioactivegames.sekkah.ui.main.track.TrackFragment;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TrainsFragment extends BaseFragment implements TrainsContract.View
{
    private View mFragment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.rvTrns) RecyclerView mRecyclerView;

    @Inject TrainsPresenter mPresenter;

    private static final String TAG = TrainsFragment.class.getSimpleName();

    public static TrainsFragment newInstance()
    {
        return new TrainsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInject(FragmentComponent fragmentComponent)
    {
        fragmentComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mFragment = inflater.inflate(R.layout.fragment_trains, container, false);
        setUnbinder(ButterKnife.bind(this, mFragment));
        mPresenter.onAttach(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //mPresenter.getTrainData();
        Realm realm = Realm.getDefaultInstance();
        mPresenter.getTrainData(realm);
        return mFragment;
    }


    @Override
    public void setTrainPojoData(List<TrainPOJO> data) {
        mAdapter = new TrainAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startTracking() {
        getFragmentManager().beginTransaction()
                .add(R.id.frameMain, TrackFragment.newInstance(), "TrackFragment")
                .addToBackStack("TrackFragment")
                .commit();
    }

    public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder>
    {
        private List<TrainPOJO> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView mTvTrainNumber, mTvTrainClass, mTvDepartureStation, mTvDestinationStation;
            public TextView mTvDepartureTime, mTvDestinationTime;
            public ImageView mBtnTrack, mBtnReminder;
            public ViewHolder(View v)
            {
                super(v);
                mTvTrainNumber = v.findViewById(R.id.tvTrainNumber);
                mTvTrainClass = v.findViewById(R.id.tvTrainClass);
                mTvDepartureStation = v.findViewById(R.id.tvDepartureStation);
                mTvDestinationStation = v.findViewById(R.id.tvDestinationStation);
                mTvDepartureTime = v.findViewById(R.id.tvDepartureTime);
                mTvDestinationTime = v.findViewById(R.id.tvDestinationTime);
                mBtnTrack = v.findViewById(R.id.btnTrack);
                mBtnReminder = v.findViewById(R.id.btnReminder);
            }
        }

        public TrainAdapter(List<TrainPOJO> myDataset)
        {
            mDataset = myDataset;
        }

        @Override
        public TrainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_train, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            holder.mTvTrainNumber.setText(mDataset.get(position).getId() );
            holder.mTvTrainClass.setText(mDataset.get(position).getNameen());
            holder.mTvDepartureStation.setText(mDataset.get(position).getDepStation());
            holder.mTvDepartureTime.setText(mDataset.get(position).getGetDepStationtime());
            holder.mTvDestinationStation.setText(mDataset.get(position).getFinalStation());
            holder.mTvDestinationTime.setText(mDataset.get(position).getFinalStationDepStationtime());
            holder.mBtnTrack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startTracking();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return mDataset.size();
        }
    }
}
