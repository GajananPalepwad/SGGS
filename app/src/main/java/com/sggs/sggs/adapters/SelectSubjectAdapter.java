package com.sggs.sggs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.CourseSelecter;
import com.sggs.sggs.R;

import java.util.List;

public class SelectSubjectAdapter extends RecyclerView.Adapter<SelectSubjectAdapter.SubjectViewHolder> {

    private List<String> subjects;

    public SelectSubjectAdapter(List<String> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item_for_select_course, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        String subject = subjects.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectTextView;
        ImageView btnRemove;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subject);
            btnRemove  = itemView.findViewById(R.id.btnRemove);

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the adapter position
                    int position = getAdapterPosition();

                    // Check if the position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        // Remove the item from the subjects list
                        CourseSelecter.subjects.remove(position);

                        // Notify the adapter about the item removal
                        notifyItemRemoved(position);
                    }
                }
            });

        }

        public void bind(String subject) {
            subjectTextView.setText(subject);
        }
    }
}

