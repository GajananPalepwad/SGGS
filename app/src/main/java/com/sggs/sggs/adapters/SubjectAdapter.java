package com.sggs.sggs.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.AttendanceCalender;
import com.sggs.sggs.R;
import com.sggs.sggs.model.SubjectModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private static ArrayList<SubjectModel> subjectList;
    private static Context context;
    private List<Integer> colorList;
    public SubjectAdapter(ArrayList<SubjectModel> subjectList, Context context) {
        this.subjectList = subjectList;
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
                SubjectModel subject = subjectList.get(position);
//                showConfirmationDialog(subject.getSubjectName());
                Intent intent = new Intent(context, AttendanceCalender.class);
                intent.putExtra("sub", subject.getSubjectName());
                context.startActivity(intent);
            }
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
        SubjectModel subject = subjectList.get(position);

        String subName = subject.getSubjectName();
        String[] words = subName.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty() && !isIgnoredWord(word)) {
                result.append(word.charAt(0));
            }
        }

        String abbreviation = result.toString();

        holder.subject.setText(subName);

        holder.marks.setText(String.valueOf(subject.getMarks() + "%"));
        holder.progressBar.setProgress(subject.getMarks());
        holder.progressBar.setMax(100);

        int colorResId = colorList.get(position % colorList.size());

        int color = ContextCompat.getColor(context, colorResId);

        holder.card.setBackgroundTintList(ColorStateList.valueOf(color));

        holder.subject.setSelected(true);

    }

    private boolean isIgnoredWord(String word) {
        // Add more words to ignore if needed
        String[] ignoredWords = {"of", "and", "the", "for", "&"};
        for (String ignoredWord : ignoredWords) {
            if (ignoredWord.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}

