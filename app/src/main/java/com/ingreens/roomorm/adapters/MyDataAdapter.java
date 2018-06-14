package com.ingreens.roomorm.adapters;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingreens.roomorm.DataBase.AppDatabase;
import com.ingreens.roomorm.R;
import com.ingreens.roomorm.constants.AppConstants;
import com.ingreens.roomorm.models.MyDataModel;
import com.ingreens.roomorm.utils.BitmapManager;

import java.util.List;

/**
 * Created by jeeban on 4/5/18.
 */

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.ViewHolder> {

    AppDatabase db;
    List<MyDataModel> myDataModels;
    Context context;
    LayoutInflater layoutInflater;

    public MyDataAdapter(List<MyDataModel> myDataModels, Context context) {
        this.myDataModels = myDataModels;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public MyDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.item_image,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyDataAdapter.ViewHolder holder, int position) {
        MyDataModel myDataModel=myDataModels.get(position);
        holder.tvName.setText(myDataModel.getName());
        holder.tvTeam.setText(myDataModel.getTeam());
        holder.tvRank.setText(String.valueOf(myDataModel.getRank()));
        //holder.tvRank.setText(myDataModel.getRank());
        //holder.tvTitle.setText(current.getTitle());
//        Bitmap photo=BitmapManager.base64ToBitmap(current.getPhoto());
//        Bitmap image=BitmapManager.byteToBitmap(current.getImage());
//        holder.ivPhoto.setImageBitmap(image);
        Bitmap photo=BitmapManager.base64ToBitmap(myDataModel.getPhoto());
        Bitmap image=BitmapManager.byteToBitmap(myDataModel.getImage());
        holder.imvplayer.setImageBitmap(image);

    }

    @Override
    public int getItemCount() {
        return myDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        ImageView ivPhoto;
//        TextView tvTitle;
//        Button btnDelete;
        ImageView imvplayer;
        TextView tvName;
        TextView tvTeam;
        TextView tvRank;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
//            ivPhoto=itemView.findViewById(R.id.ivPhoto);
//            tvTitle=itemView.findViewById(R.id.tvTitle);
            imvplayer=itemView.findViewById(R.id.imgPlayer);
            tvName=itemView.findViewById(R.id.tvName);
            tvTeam=itemView.findViewById(R.id.tvTeam);
            tvRank=itemView.findViewById(R.id.tvRank);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(this);
            imvplayer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnDelete: {
                    db= Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                    MyDataModel data=myDataModels.get(getPosition());
                    db.myDataDao().delete(data);
                    myDataModels.remove(getPosition());
                    notifyItemRemoved(getPosition());
                } break;
                case R.id.ivPhoto: {
                    //showDetails(myDataModels.get(getPosition()),context);
                } break;
            }

        }
    }

//    private void showDetails(MyDataModel myDataModel, Context context) {
//        BottomSheetDialog dialog=new BottomSheetDialog(context);
//        dialog.setTitle(myDataModel.getName());
//        dialog.setContentView(R.layout.item_details);
//        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
//        ImageView ivPhoto=dialog.findViewById(R.id.ivPhoto);
//        tvTitle.setText(myDataModel.getName());
//        ivPhoto.setImageBitmap(BitmapManager.byteToBitmap(myImage.getImage()));
//        dialog.show();
//    }


}
