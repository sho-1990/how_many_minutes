package sho_1990.jp.how_many_minutes.infra;

import io.realm.RealmObject;

/**
 * Created on 2016/08/06.
 */

public class Section extends RealmObject {

    private String name;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
