package com.example.firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {
        private final ArrayList<String> userEmail;
        private final ArrayList<String> userComment;
        private final ArrayList<String> userImage;
        private final Activity context;

    public PostClass(ArrayList<String> useremail, ArrayList<String> usercomment, ArrayList<String> userImage, Activity context) {
        super(context,R.layout.custom_view,useremail);
        this.userEmail = useremail;
        this.userComment = usercomment;
        this.userImage = userImage;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =context.getLayoutInflater();
        View customview=layoutInflater.inflate(R.layout.custom_view,null,true);
        TextView useremail=customview.findViewById(R.id.customView_useremailText);
        TextView usercomment=customview.findViewById(R.id.Customview_usercomment);
        ImageView userimg=customview.findViewById(R.id.Customview_imageview);
        useremail.setText(userEmail.get(position));
        usercomment.setText(userComment.get(position));
        Picasso.get().load(userImage.get(position)).into(userimg);


        return customview;
    }
}
