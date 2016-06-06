package tjabc.tj.com.lollipopdesign.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.activity.NavigationActivity;

/**
 * Created by Lee on 2016/4/11.
 */
public class PageThreeFragment extends Fragment implements View.OnClickListener{

    @Bind(R.id.navigationBtn) Button navigationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_viewpage_b, null);
        ButterKnife.bind(this, view);

        navigationBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBtn:
                Intent intent=new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
