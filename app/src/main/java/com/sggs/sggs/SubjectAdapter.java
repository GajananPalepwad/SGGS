package com.sggs.sggs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private ArrayList<Subject> subjectList;

    public SubjectAdapter(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView subject;
        TextView marks;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            subject = itemView.findViewById(R.id.subject);
            marks = itemView.findViewById(R.id.marks);
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
        Subject subject = subjectList.get(position);
        holder.subject.setText(subject.getSubjectName());
        holder.marks.setText(String.valueOf(subject.getMarks()+"%"));
        holder.progressBar.setProgress(subject.getMarks());
        holder.progressBar.setMax(100);
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
