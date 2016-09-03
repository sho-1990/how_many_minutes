package sho_1990.jp.how_many_minutes.presenter.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.databinding.SectionSelectorBinding;
import sho_1990.jp.how_many_minutes.domain.SectionRegisterUseCase;
import sho_1990.jp.how_many_minutes.domain.TimeRegisterUseCase;
import sho_1990.jp.how_many_minutes.infra.Sections;
import sho_1990.jp.how_many_minutes.infra.TravelTimes;
import sho_1990.jp.how_many_minutes.infra.dao.SectionDao;

/**
 * 区間登録用Dialogクラス
 */
public class SectionSelectorFragment extends DialogFragment {

    // 区間移動時間
    private long time;

    private SectionSelectorBinding mBinding;

    // 時間
    private static final String ARG_TIME = "time";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SectionSelectorFragment() {
    }

    public static SectionSelectorFragment newInstance(long time) {
        SectionSelectorFragment fragment = new SectionSelectorFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle args = getArguments();
        time = args.getLong(ARG_TIME);

        // ---
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.section_selector,
                null,
                false
        );
        // 区間名ラジオボタングループ
        setSectionRadios(mBinding.sectionSelector);

        builder
            .setTitle(String.valueOf(time))
            .setNegativeButton("キャンセル", null)
            .setPositiveButton(getActivity().getString(R.string.register), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // registerTimeNotExistingSection
                if (mBinding.selectNewSection.isChecked()) {
                    SectionRegisterUseCase useCase = SectionRegisterUseCase.newInstance();

                    if (useCase.duplicateCheck(mBinding.textNewSection.getText().toString()) == Status.DUPLICATE) {
                        return;
                    }
                    Status status = useCase
                            .resisterSection(mBinding.textNewSection.getText().toString(), time);

                    if (status == Status.OK) {
                        successToast();
                    }

                    return;
                }

                int checkedId = mBinding.sectionSelector.getCheckedRadioButtonId();
                RadioButton r = (RadioButton) mBinding.sectionSelector.findViewById(checkedId);
                String sectionName = r.getText().toString();
                int sectionId = SectionRegisterUseCase.newInstance().findSectionId(sectionName);

                // タイム登録
                Status status = registerTimeExistingSection(sectionId);
                if (status == Status.OK) {
                    successToast();
                }
            }
        }).setView(mBinding.getRoot());

        return builder.create();

    }

    private void setSectionRadios(RadioGroup radios) {

        List<Sections> sections = SectionDao.newSectionDao().sectionListAll();
        if (sections.isEmpty()) {
            return;
        }

        for (Sections s : sections) {
            RadioButton r = new RadioButton(getActivity());
            r.setText(s.getName());
            radios.addView(r);
        }
    }

    private Status registerTimeExistingSection(int sectionId) {
        TravelTimes travelTimes = new TravelTimes();
        travelTimes.setTime(time);
        travelTimes.setSectionId(sectionId);
        travelTimes.setUpdateDate(new Date().getTime());
        return TimeRegisterUseCase.newInstance().registerTime(travelTimes);
    }

    private void successToast() {
        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
    }
}

