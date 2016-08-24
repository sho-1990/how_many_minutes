package sho_1990.jp.how_many_minutes.infra;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created on 2016/08/16.
 */

public class TravelTimes extends RealmObject {

    @PrimaryKey
    private int travelTimesId;
    @Required
    private long time;
    @Required
    private String sectionId;
    @Required
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

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
