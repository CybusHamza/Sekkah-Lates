package in.radioactivegames.sekkah.ui.main.contact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.radioactivegames.sekkah.R;
import in.radioactivegames.sekkah.base.BaseFragment;
import in.radioactivegames.sekkah.data.callbacks.JSONCallback;
import in.radioactivegames.sekkah.data.model.User;
import in.radioactivegames.sekkah.di.component.FragmentComponent;
import in.radioactivegames.sekkah.ui.main.report.ReportPresenter;

public class ContactFragment extends BaseFragment implements ContactContract.View
{
    private View mFragment;
    @Inject ContactPresenter mPresenter;

    @BindView (R.id.tvSubject)
    EditText tvSubject;
    @BindView (R.id.tvMessage)
    EditText tvMessage;
    @BindView (R.id.rgLocation)
    RadioGroup rgType;

    String strSubject,strMessage,strType;

    public static ContactFragment newInstance()
    {
        return new ContactFragment();
    }

    @Override
    public void onInject(FragmentComponent fragmentComponent)
    {
        fragmentComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mFragment = inflater.inflate(R.layout.fragment_contact, container, false);
        setUnbinder(ButterKnife.bind(this, mFragment));
        mPresenter.onAttach(this);
        return mFragment;
    }

    @OnClick (R.id.tvSubmit)
    public void contactUs(){

        showProgressDialog("Please wait...");

        strMessage = tvMessage.getText().toString();
        strSubject = tvSubject.getText().toString();

        int radioButtonID = rgType.getCheckedRadioButtonId();
        View radioButton = rgType.findViewById(radioButtonID);
        int idx = rgType.indexOfChild(radioButton);

        RadioButton r = (RadioButton)  rgType.getChildAt(idx);
         strType = r.getText().toString();

        mPresenter.contactUs(strMessage, strType, strSubject, new JSONCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {

                hideProgressBar();
                Toast.makeText(getActivity(), "Success ", Toast.LENGTH_SHORT).show();

                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1)
                    getActivity().getSupportFragmentManager().popBackStack();
                else {
                    getActivity().finish();
                }

            }

            @Override
            public void onFail(String errorMessage) {
                hideProgressBar();
                Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
