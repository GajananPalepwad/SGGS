package com.sggs.sggs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.R;
import com.sggs.sggs.model.NotificationModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private  ArrayList<NotificationModel> timeList;
    private  Context context;

    private List<Integer> colorList;
    public NotificationAdapter(ArrayList<NotificationModel> timeList, Context context) {

        this.timeList = timeList;
        this.context = context;

        colorList = new ArrayList<>();
        colorList.add(R.color.color1);
        colorList.add(R.color.color2);
        colorList.add(R.color.color3);
        colorList.add(R.color.color4);
        colorList.add(R.color.color5);
        colorList.add(R.color.color6);
        colorList.add(R.color.color7);
        colorList.add(R.color.color8);
        colorList.add(R.color.color9);
        colorList.add(R.color.color10);

        Collections.shuffle(colorList);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, body, time;
        CardView itemCard;


        public ViewHolder(View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.itemCard);
            title = itemView.findViewById(R.id.notifTitle);
            time = itemView.findViewById(R.id.time);
            body = itemView.findViewById(R.id.notifBody);
        }

    }


    private void showFullDialog(String title, String body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(body);

        builder.setPositiveButton("Got it", (dialog, which) -> {
            // Call the logout function
            dialog.dismiss();
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationModel notification = timeList.get(position);

        String[] parts = notification.getTitleNbody().split("\\(");
        String titleString = parts[0].trim();
        String bodyString = parts[1].split("\\)")[0].trim();
        holder.time.setText(notification.getTime().replace(", ", "\n"));
        holder.title.setText(titleString);
        holder.body.setText(bodyString);

        holder.itemCard.setOnClickListener(v -> showFullDialog(titleString, bodyString));


        int colorResId = colorList.get(position % colorList.size());

        // Retrieve the color from resources
        int color = ContextCompat.getColor(context, colorResId);

        // Set the background color of the CardView
        holder.itemCard.setCardBackgroundColor(color);


    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }
}

