package com.sggs.sggs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.R;
import com.sggs.sggs.model.TimeTableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {

    private  ArrayList<TimeTableModel> timeList;
    private  Context context;

    private List<Integer> colorList;
    public TimeTableAdapter(ArrayList<TimeTableModel> timeList, Context context) {

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
        TextView subject, teacher, time;
        CardView itemCard;


        public ViewHolder(View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.itemCard);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            teacher = itemView.findViewById(R.id.teacher);
        }

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TimeTableModel timeTable = timeList.get(position);

        String[] parts = timeTable.getSubjectNteacher().split("\\(");
        String subjectString = parts[0].trim();
        String teacherName = parts[1].split("\\)")[0].trim();
        holder.time.setText(timeTable.getTime());
        holder.subject.setText(subjectString);
        holder.teacher.setText(teacherName);



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

