package com.example.app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.database.DatabaseHandler;
import com.example.app.database.LockPackageDatabase;
import com.example.app.model.AppDetailsModel;
import com.example.app.other.Constants;
import com.example.app.service.AppLaunchDectionService;

import java.util.ArrayList;

public class AddLockerAdpter extends RecyclerView.Adapter<AddLockerAdpter.AddViewHolder> {
    OnItemClickListener mItemClickListener;
    Context context;
    ArrayList<AppDetailsModel> list_details;
    DatabaseHandler databaseHandler;
    LockPackageDatabase packageDatabase;

    public AddLockerAdpter(Context context, ArrayList<AppDetailsModel> list) {
        this.context = context;
        this.list_details = list;
        databaseHandler = new DatabaseHandler(context);
        packageDatabase = new LockPackageDatabase(context);
    }

    @Override
    public AddViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_applist_item, parent, false);
        AddViewHolder holder = new AddViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AddViewHolder holder, final int position) {
        final AppDetailsModel model = list_details.get(position);

        holder.imageView.setImageDrawable(model.getAppIcon());
        holder.name.setText(model.getAppName());

        boolean result = packageDatabase.getpack_name(model.getPackge_name());
        if (result) {
            holder.lock.setImageResource(R.drawable.ic_lock);
        } else {
            holder.lock.setImageResource(R.drawable.ic_unlock);
        }

        holder.lock.setOnClickListener(v -> {
            boolean result1 = packageDatabase.getpack_name(model.getPackge_name());
            if (result1) {
                holder.lock.setImageResource(R.drawable.ic_unlock);
                packageDatabase.delete(model.getPackge_name());
            } else {
                packageDatabase.addPackage(model.getPackge_name());
                holder.lock.setImageResource(R.drawable.ic_lock);

                Intent intent = new Intent(context, AppLaunchDectionService.class);
                intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_details.size();
    }

    public class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView name;
        ImageButton lock, delete;

        public AddViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.app_icon);
            name = itemView.findViewById(R.id.app_name);
            lock = itemView.findViewById(R.id.btn_lock);
            delete = itemView.findViewById(R.id.btn_delete);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            //add alert dialog
            AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
            a_builder.setMessage("Are you sure to remove this app from Smart App Locker ?").setCancelable(false)
                    .setPositiveButton("Remove", (dialog, which) -> {
                        if (mItemClickListener != null) {
                            // String pack_name=list.get(getPosition());
                            mItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = a_builder.create();
            alertDialog.show();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int Position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
