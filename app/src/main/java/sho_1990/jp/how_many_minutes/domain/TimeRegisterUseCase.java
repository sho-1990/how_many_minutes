package sho_1990.jp.how_many_minutes.domain;

import java.util.Date;

import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.TravelTimes;
import sho_1990.jp.how_many_minutes.infra.dao.TravelTimesDao;

/**
 * Created on 2016/08/27.
 */

public class TimeRegisterUseCase {

    private static TimeRegisterUseCase registerUseCase;

    private TimeRegisterUseCase() {}

    public static TimeRegisterUseCase newInstance() {
        if (registerUseCase == null) {
            registerUseCase = new TimeRegisterUseCase();
        }
        return registerUseCase;
    }

    public Status registerTime(TravelTimes travelTimes) {

        TravelTimes t = new TravelTimes();
        t.setTime(travelTimes.getTime());
        t.setUpdateDate((new Date()).getTime());
        t.setSectionId(travelTimes.getSectionId());
        t.setTravelTimesId(TravelTimesDao.newTravelTimesDao().findNextTravelTimesId());
        return TravelTimesDao.newTravelTimesDao().insert(t);

    }
}
