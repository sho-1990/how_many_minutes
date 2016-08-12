package sho_1990.jp.how_many_minutes.presenter.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.Date;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.Status;
import sho_1990.jp.how_many_minutes.databinding.SectionSelectorBinding;
import sho_1990.jp.how_many_minutes.infra.Section;
import sho_1990.jp.how_many_minutes.infra.dao.SectionDao;

/**
 * 区間登録用Dialogクラス
 */
public class SectionSelectorFragment extends DialogFragment {

    // 区間移動時間
    private String time;

    private SectionSelectorBinding mBinding;

    // 時間
    private static final String ARG_TIME = "time";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SectionSelectorFragment() {
    }

    // 使う
    @SuppressWarnings("unused")
    public static SectionSelectorFragment newInstance(@NonNull String time) {
        SectionSelectorFragment fragment = new SectionSelectorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle args = getArguments();
        time = args.getString(ARG_TIME, getActivity().getString(R.string.init_time));

        // --- todo テーブルから区間名を引っ張ってくる
        setSectionRadios();

        // ---
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.section_selector,
                null,
                false
        );

        builder
            .setTitle(time)
            .setPositiveButton(getActivity().getString(R.string.register), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                // 新規登録の場合、区間名登録処理
                if (mBinding.selectNewSection.isChecked()) {
                    Section section = new Section();
                    section.setName(mBinding.textNewSection.getText().toString());
                    section.setDate((String) DateFormat.format("yyyy/MM/dd hh:mm:ss", new Date()));
                    Status status = SectionDao.newSectionDao().insert(section);

                    if (status == Status.SUCCESS) {
                        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                    }
                }







            }
        })
            .setView(mBinding.getRoot());

        return builder.create();

    }

    private void setSectionRadios() {


    }
}
