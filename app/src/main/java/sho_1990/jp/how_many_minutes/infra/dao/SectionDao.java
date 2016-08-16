package sho_1990.jp.how_many_minutes.infra.dao;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.Section;

/**
 * Created on 2016/08/06.
 */

public class SectionDao {

    private static SectionDao mSectionDao;

    private SectionDao() {}

    public static SectionDao newSectionDao() {
        if (mSectionDao != null) {
            return mSectionDao;
        }

        return new SectionDao();
    }

    public List<Section> sectionListAll() {
        return Realm.getDefaultInstance().where(Section.class).findAll();
    }

    public Status insert(@NonNull final Section data) {

        final Status[] status = new Status[1];

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Section section = realm.createObject(Section.class);
                section.setName(data.getName());
                section.setDate(data.getDate());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                status[0] = result(Status.SUCCESS);
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
