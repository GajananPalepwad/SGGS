package com.sggs.sggs.adapters;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.R;
import com.sggs.sggs.model.CalendarModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceCalendarAdapter extends RecyclerView.Adapter<AttendanceCalendarAdapter.AttendanceViewHolder> {

    private List<CalendarModel> attendanceList;
    private Context context;

    public AttendanceCalendarAdapter(List<CalendarModel> attendanceList, Context context) {
        this.attendanceList = attendanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_card, parent, false);
        return new AttendanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        CalendarModel attendance = attendanceList.get(position);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date;
        try {
            date = inputFormat.parse(attendance.getDate_time());
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Format the date to desired formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        // Format the date and time into strings
        String formattedDate = dateFormat.format(date);
        String formattedTime = timeFormat.format(date);

        holder.dateTextView.setText(formattedDate);
        holder.timeTextView.setText(formattedTime);

        if(attendance.getAttendance_status().equals("P")){

            int color = Color.parseColor("#0E9B00");
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(color));

        }else{

            int color = Color.parseColor("#AF0000");
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(color));

        }


    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView timeTextView;
        CardView cardView;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tvDate);
            timeTextView = itemView.findViewById(R.id.tvTime);
            cardView = itemView.findViewById(R.id.card);

        }
    }
}

