package sho_1990.jp.how_many_minutes.presenter.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.databinding.FragmentTimeBinding;
import sho_1990.jp.how_many_minutes.presenter.widget.MyTimer;

/**
 * 時間計測用画面Fragment
 */
public class TimeFragment extends Fragment {

    private FragmentTimeBinding mBinding;
    private MyTimer mMyTimer;

    private OnFragmentInteractionListener mListener;

    public TimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeFragment newInstance(String param1, String param2) {
        TimeFragment fragment = new TimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey("TAB_ITEM_NAME")) {
                String subItem = extras.getString("TAB_ITEM_NAME");
                // Do something with that string
            }
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
                    public void onTextView(String time) {
                        mBinding.timeView.setText(time);
                    }
                }
        ).set();

        if (mBinding.timeView.getText().length() == 0) {
            initTimer(mBinding.timeView);
        }

        mBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean stop = getString(R.string.init_time).equals(mBinding.timeView.getText());
                if (stop) {
                    mBinding.timeView.setText(mMyTimer.start());
                    return;
                }
                final String time = mMyTimer.stop();
                initTimer(mBinding.timeView);

                // 区間登録用ダイアログ表示
                SectionSelectorFragment registerFragment = SectionSelectorFragment.newInstance(time);
                registerFragment.show(getFragmentManager(), null);
            }
        });

    }

    private void initTimer(TextView timeView) {
        timeView.setText(getString(R.string.init_time));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(mMyTimer);
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
