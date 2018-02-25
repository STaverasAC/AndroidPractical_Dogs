package nyc.c4q.androidpractical.controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.androidpractical.PhotoActivity;
import nyc.c4q.androidpractical.R;
import nyc.c4q.androidpractical.model.Image;

/**
 * Created by Shant on 2/25/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    List<Image> images = new ArrayList<>();

    public ImageAdapter(List<Image> images){
        this.images = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog,parent,false);
        return new ImageAdapter.ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image pic = images.get(position);
        holder.onBind(pic);

    }



    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.dog_image);
        }

        public void onBind(final Image image){
            Picasso.with(itemView.getContext()).load(image.getUrl()).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = image.getUrl();
                    Intent goToPhotoActivity = new Intent(itemView.getContext(), PhotoActivity.class);
                    goToPhotoActivity.putExtra("url",url);
                    itemView.getContext().startActivity(goToPhotoActivity);
                }
            });
        }
    }
}
