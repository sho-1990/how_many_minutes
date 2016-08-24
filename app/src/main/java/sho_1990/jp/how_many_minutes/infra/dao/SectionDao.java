package sho_1990.jp.how_many_minutes.infra.dao;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.Sections;

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

    public List<Sections> sectionListAll() {
        return Realm.getDefaultInstance().where(Sections.class).findAll();
    }

    public Status insert(@NonNull final Sections data) {

        final Status[] status = new Status[1];

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Sections section = realm.createObject(Sections.class);
                section.setName(data.getName());
                section.setUpdateDate(data.getUpdateDate());

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
