package com.sggs.sggs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private static ArrayList<Subject> subjectList;
    private static Context context;

    public SubjectAdapter(ArrayList<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ProgressBar progressBar;
        TextView subject;
        TextView marks;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            subject = itemView.findViewById(R.id.subject);
            marks = itemView.findViewById(R.id.marks);
            card = itemView.findViewById(R.id.cardColor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Handle the click action here
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Perform the desired action for the clicked item
                Subject subject = subjectList.get(position);
                showConfirmationDialog(subject.getSubjectName());
            }
        }

        private void showConfirmationDialog(String subject) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(subject);

            builder.setPositiveButton("Ok", (dialog, which) -> {
                // Call the logout function
                dialog.dismiss();
            });


            AlertDialog dialog = builder.create();
            dialog.show();
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

        String subName = subject.getSubjectName();
        String[] words = subName.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(word.charAt(0));
            }
        }

        String abbreviation = result.toString();

        holder.subject.setText(abbreviation);

        holder.marks.setText(String.valueOf(subject.getMarks() + "%"));
        holder.progressBar.setProgress(subject.getMarks());
        holder.progressBar.setMax(100);

        switch (randomNumber) {
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

