package sho_1990.jp.how_many_minutes.infra;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created on 2016/08/06.
 */

public class Sections extends RealmObject {

    @PrimaryKey
    private int sectionId;
    private String name;
    private long updateDate;

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long date) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
