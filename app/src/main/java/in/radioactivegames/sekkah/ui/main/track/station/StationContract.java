package in.radioactivegames.sekkah.ui.main.track.station;

import java.util.ArrayList;
import java.util.List;

import in.radioactivegames.sekkah.base.BaseMvpPresenter;
import in.radioactivegames.sekkah.base.BaseMvpView;
import in.radioactivegames.sekkah.data.model.Station;
import in.radioactivegames.sekkah.data.model.StationPOJO;
import in.radioactivegames.sekkah.data.model.Train;
import in.radioactivegames.sekkah.ui.main.track.map.MapContract;
import io.realm.Realm;

/**
 * Created by AntiSaby on 12/26/2017.
 * www.radioactivegames.in
 */

public class StationContract
{
    public interface View extends BaseMvpView
    {
        public void setStationData(ArrayList<StationPOJO> stationData);
    }

    public interface Presenter extends BaseMvpPresenter<StationContract.View>
    {
        public void getStationData(Realm realm , String trainId);
    }
}
