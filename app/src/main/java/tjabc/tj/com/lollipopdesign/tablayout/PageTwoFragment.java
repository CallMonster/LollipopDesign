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
import tjabc.tj.com.lollipopdesign.activity.AppBarActivity;

/**
 * Created by Lee on 2016/4/11.
 */
public class PageTwoFragment extends Fragment {

    @Bind(R.id.appBarLayout) Button appBarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_viewpage_a, null);
        ButterKnife.bind(this, view);

        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AppBarActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
