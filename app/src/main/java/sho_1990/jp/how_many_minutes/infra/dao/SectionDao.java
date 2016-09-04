package sho_1990.jp.how_many_minutes.infra.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.realm.Realm;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.Sections;

import static io.realm.Realm.getDefaultInstance;

/**
 * Created on 2016/08/06.
 */

public class SectionDao {

    public interface SectionDaoListener {
        Status onSuccess(int sectionId);
    }


    private static SectionDao mSectionDao;

    private SectionDao() {}

    public static SectionDao newSectionDao() {
        if (mSectionDao != null) {
            return mSectionDao;
        }

        return new SectionDao();
    }

    public int findSectionId(@NonNull String sectionName) {
        Sections sections =
                getDefaultInstance()
                .where(Sections.class)
                .equalTo("name", sectionName)
                .findFirst();

        if (sections == null) {
            return -1;
        }
        return sections.getSectionId();
    }

    public List<Sections> sectionListAll() {
        return Realm.getDefaultInstance().where(Sections.class).findAll();
    }

    public Status insert(@NonNull final Sections data, @Nullable final SectionDaoListener listener) {

        final Status[] status = new Status[1];

        final Realm realm = getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                Sections section = new Sections();
                section.setSectionId(realm.where(Sections.class).max("sectionId").intValue() + 1);
                section.setName(data.getName());
                section.setUpdateDate(data.getUpdateDate());
                realm.copyToRealm(section);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (listener != null) {
                    status[0] = listener.onSuccess(findSectionId(data.getName()));
                } else {
                    status[0] = Status.OK;
                }
            }
        });

        return status[0];
    }

    public boolean duplicate(@NonNull final String name) {

        Realm realm = getDefaultInstance();
        return ! realm
                .where(Sections.class)
                .equalTo("name", name)
                .findAll()
                .isEmpty();
    }
}
