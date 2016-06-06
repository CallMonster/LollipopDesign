package tjabc.tj.com.lollipopdesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tjabc.tj.com.lollipopdesign.R;

/**
 * Created by Lee on 2016/5/10.
 */
public class AppBarAdapter extends RecyclerView.Adapter<AppBarAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> infoArr;
    private Integer[] iconArr = new Integer[]{
            R.drawable.ic_usb_black_36dp, R.drawable.ic_wifi_lock_black_36dp,
            R.drawable.ic_wifi_lock_grey600_36dp, R.drawable.ic_wifi_tethering_black_36dp
    };

    public AppBarAdapter(Context context,ArrayList<String> infoArr){
        this.context=context;
        this.infoArr=infoArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_application, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(infoArr.get(position));
        holder.icon.setImageResource(iconArr[position%4]);
    }

    @Override
    public int getItemCount() {
        return infoArr == null ? 0 : infoArr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            icon = (ImageView) itemView.findViewById(R.id.countryImage);
        }
    }

}
