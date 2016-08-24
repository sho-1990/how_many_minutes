package sho_1990.jp.how_many_minutes.domain;

import java.util.Date;

import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.infra.Sections;

import static sho_1990.jp.how_many_minutes.infra.dao.SectionDao.newSectionDao;

/**
 * Created on 2016/08/24.
 */

public class SectionRegisterUseCase {

    private static SectionRegisterUseCase registerUseCase;

    private SectionRegisterUseCase() {}

    public static SectionRegisterUseCase newInstance() {
        if (registerUseCase == null) {
            return new SectionRegisterUseCase();
        }
        return registerUseCase;
    }

    public Status resisterSection(String name) {
        Sections section = new Sections();
        section.setName(name);
        section.setUpdateDate((new Date()).getTime());
        return newSectionDao().insert(section);
    }
}
