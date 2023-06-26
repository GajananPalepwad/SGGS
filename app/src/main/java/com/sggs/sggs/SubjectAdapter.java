package com.sggs.sggs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Random;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private ArrayList<Subject> subjectList;
    private Context context;

    public SubjectAdapter(ArrayList<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView subject;
        TextView marks;
        CardView card ;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            subject = itemView.findViewById(R.id.subject);
            marks = itemView.findViewById(R.id.marks);
            card = itemView.findViewById(R.id.cardColor);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendence_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;

        Subject subject = subjectList.get(position);
        holder.subject.setText(subject.getSubjectName());
        holder.marks.setText(String.valueOf(subject.getMarks()+"%"));
        holder.progressBar.setProgress(subject.getMarks());
        holder.progressBar.setMax(100);

        switch (randomNumber){
            case 1:
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color1));

                break;
            case 2:
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color2));
                break;
            case 3:
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color3));
                break;
            case 4:
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color4));
                break;
            case 5:
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color5));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
