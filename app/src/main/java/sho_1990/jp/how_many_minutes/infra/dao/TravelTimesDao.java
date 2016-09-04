package sho_1990.jp.how_many_minutes.infra.dao;

import android.support.annotation.NonNull;

import io.realm.Realm;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.TravelTimes;

/**
 * Created on 2016/08/24.
 */

public class TravelTimesDao {

    private static TravelTimesDao travelTimesDao;

    private TravelTimesDao() {}

    public static TravelTimesDao newTravelTimesDao() {
        if (travelTimesDao == null) {
            return new TravelTimesDao();
        }
        return travelTimesDao;
    }

    public Status insert(@NonNull final TravelTimes data) {

        final Status[] status = new Status[1];

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TravelTimes t = new TravelTimes();
                t.setSectionId(data.getSectionId());
                t.setTime(data.getTime());
                t.setTravelTimesId(realm.where(TravelTimes.class).max("travelTimesId").intValue() + 1);
                t.setUpdateDate(data.getUpdateDate());
                realm.copyToRealm(t);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                status[0] = result(Status.OK);
            }
        });

        return status[0];
    }

    /**
     * 処理結果を返す
     */
    private Status result(Status status) {
        return status;
    }
}
