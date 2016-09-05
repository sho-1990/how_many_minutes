package sho_1990.jp.how_many_minutes.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.databinding.FragmentTimeBinding;
import sho_1990.jp.how_many_minutes.presenter.widget.MyTimer;

/**
 * 時間計測画面Fragment
 */
public class TimeFragment extends Fragment {

    private FragmentTimeBinding mBinding;
    private MyTimer mMyTimer;

    public TimeFragment() {
        // Required empty public constructor
    }

    public static TimeFragment newInstance(String param1, String param2) {
        TimeFragment fragment = new TimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMyTimer != null) {
            mMyTimer.set();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding = FragmentTimeBinding.bind(getView());
        mMyTimer = MyTimer.newMyTimer(
                getActivity(),
                new MyTimer.MyTimerListener() {
                    @Override
                    public void onTextView(final String time) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mBinding.timeView.setText(time);
                            }
                        });
                    }
                }
        );
        mMyTimer.set();
        mBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean stop = getString(R.string.init_time).equals(mBinding.timeView.getText());
                if (stop) {
                    mMyTimer.start(0L, 100L);
                    return;
                }
                long time = mMyTimer.stop();
                mMyTimer.set();
                // 区間登録用ダイアログ表示
                SectionSelectorFragment registerFragment = SectionSelectorFragment.newInstance(time);
                registerFragment.show(getFragmentManager(), null);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMyTimer != null) {
            mMyTimer.pause();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMyTimer = null;
    }
}
