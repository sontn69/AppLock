package com.example.app.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.database.DatabaseHandler;
import com.example.app.model.AllAppListModel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.RemaingView> {
    OnItemClickListener mItemClickListener;

    private List<ApplicationInfo> appsList;
    private final Context context;
    private final PackageManager packageManager;
    ArrayList<AllAppListModel> list;
    DatabaseHandler databaseHandler;

    public ApplicationAdapter(Context context, ArrayList<AllAppListModel> appsList) {
        this.context = context;
        this.list = appsList;
        packageManager = context.getPackageManager();
        databaseHandler = new DatabaseHandler(context);
    }

    @Override
    public RemaingView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        RemaingView remaingView = new RemaingView(view);
        return remaingView;
    }

    @Override
    public void onBindViewHolder(RemaingView holder, int position) {
        AllAppListModel model = list.get(position);

        holder.imageView.setImageDrawable(model.getAppIcon());
        holder.textView.setText(model.getAppName());

        boolean result = databaseHandler.getpack_name(model.getPackge_name());

        Log.e("result", String.valueOf(result));
        holder.textView.setChecked(result);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RemaingView extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckedTextView textView;
        ImageView imageView;
        RelativeLayout layout;

        public RemaingView(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.app_name);
            imageView = itemView.findViewById(R.id.app_icon);
            layout = itemView.findViewById(R.id.rl);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                AllAppListModel pack_name = list.get(getAdapterPosition());
                mItemClickListener.onItemClick(v, pack_name.getPackge_name());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String name);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
