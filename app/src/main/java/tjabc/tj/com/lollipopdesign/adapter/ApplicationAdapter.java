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
import tjabc.tj.com.lollipopdesign.activity.MainActivity;
import tjabc.tj.com.lollipopdesign.entity.AppInfo;

/**
 * Created by Lee on 2016/4/21.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AppInfo> applicationList;
    public ApplicationAdapter(Context context,ArrayList<AppInfo> applicationList){
        this.context=context;
        this.applicationList=applicationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_application, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AppInfo appInfo = applicationList.get(position);
        holder.name.setText(appInfo.getName());
        holder.image.setImageDrawable(appInfo.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.animateActivity(appInfo, holder.image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationList == null ? 0 : applicationList.size();
    }

    /**
     * 清空Arraylist
     */
    public void clearApplications() {
        int size = this.applicationList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                applicationList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 添加ArrayList
     * @param applications
     */
    public void addApplications(ArrayList<AppInfo> applications) {
        this.applicationList.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            image = (ImageView) itemView.findViewById(R.id.countryImage);
        }

    }
}
