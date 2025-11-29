package com.example.tiktokexperience.ui.experience;

import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiktokexperience.R;
import com.example.tiktokexperience.data.model.ExperienceItem;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.VH> {

    private List<ExperienceItem> data;
    private Context context;

    public ExperienceAdapter(List<ExperienceItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class VH extends RecyclerView.ViewHolder {

        ImageView imgAvatar;       // ⭐ 封面图
        ImageView imgUserAvatar;   // ⭐ 用户头像
        ImageView imgLike;

        TextView txtUsername;
        TextView txtTitle;
        TextView txtLikes;

        public VH(@NonNull View v) {
            super(v);
            imgAvatar = v.findViewById(R.id.imgAvatar);
            imgUserAvatar = v.findViewById(R.id.imgUserAvatar); // ⭐ 你之前缺少
            imgLike = v.findViewById(R.id.imgLike);

            txtUsername = v.findViewById(R.id.txtUsername);
            txtTitle = v.findViewById(R.id.txtTitle);
            txtLikes = v.findViewById(R.id.txtLikes);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.item_experience, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int p) {
        ExperienceItem item = data.get(p);

        Log.d("AVATAR_URL", "头像URL = " + item.userAvatar);

        // ⭐ 加载卡片封面图
        Glide.with(context)
                .load(item.coverUrl)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // ⭐ 启用磁盘缓存
                .skipMemoryCache(false)                     // ⭐ 启用内存缓存
                .into(h.imgAvatar);

        // ⭐ 加载用户头像
        Glide.with(context)
                .load(item.userAvatar)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // ⭐ 启用磁盘缓存
                .skipMemoryCache(false)
                .into(h.imgUserAvatar);

        // 设置文字
        h.txtUsername.setText(item.username);
        h.txtTitle.setText(item.title);
        h.txtLikes.setText(String.valueOf(item.likes));

        // 点赞图标
        h.imgLike.setImageResource(item.liked
                ? R.drawable.ic_heart_filled
                : R.drawable.ic_heart);

        // 点赞点击事件
        h.imgLike.setOnClickListener(v -> {
            item.liked = !item.liked;
            item.likes += (item.liked ? 1 : -1);
            notifyItemChanged(p);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
