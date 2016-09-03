package sho_1990.jp.how_many_minutes.domain;

import android.support.annotation.NonNull;

import java.util.Date;

import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.Sections;
import sho_1990.jp.how_many_minutes.infra.TravelTimes;
import sho_1990.jp.how_many_minutes.infra.dao.SectionDao;
import sho_1990.jp.how_many_minutes.infra.dao.TravelTimesDao;

import static sho_1990.jp.how_many_minutes.infra.dao.SectionDao.newSectionDao;

/**
 * Created on 2016/08/24.
 */

public class SectionRegisterUseCase {

    private static SectionRegisterUseCase registerUseCase;

    private SectionRegisterUseCase() {}

    public static SectionRegisterUseCase newInstance() {
        if (registerUseCase == null) {
            registerUseCase = new SectionRegisterUseCase();
        }
        return registerUseCase;
    }

    /**
     * 区間名の登録を行う。第２引数が0でない場合、TrvelTimesの登録処理も行う。
     *
     * */
    public Status resisterSection(@NonNull String name, final long time) {
        Sections section = new Sections();
        section.setName(name);
        section.setUpdateDate((new Date()).getTime());
        if (time == 0L) {
            return newSectionDao().insert(section, null);
        }
        return newSectionDao().insert(
                section,
                // 時刻の登録
                new SectionDao.SectionDaoListener() {
                    @Override
                    public Status onSuccess(final int sectionId) {
                        TravelTimes travelTimes = new TravelTimes();
                        travelTimes.setSectionId(sectionId);
                        travelTimes.setTime(time);
                        travelTimes.setUpdateDate(new Date().getTime());
                        return TravelTimesDao.newTravelTimesDao().insert(travelTimes);
                    }
                });
    }

    public Status duplicateCheck(@NonNull String name) {

        if (newSectionDao().duplicate(name)) {
            return Status.DUPLICATE;
        }
        return Status.OK;
    }

    public int findSectionId(String name) {
        return newSectionDao().findSectionId(name);

    }
}
