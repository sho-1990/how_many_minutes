package sho_1990.jp.how_many_minutes.infra;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created on 2016/08/16.
 */

public class TravelTimes extends RealmObject {

    @PrimaryKey
    private int travelTimesId;
    private long time;
    private int sectionId;
    private long updateDate;

    public int getTravelTimesId() {
        return travelTimesId;
    }

    public void setTravelTimesId(int travelTimesId) {
        this.travelTimesId = travelTimesId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
