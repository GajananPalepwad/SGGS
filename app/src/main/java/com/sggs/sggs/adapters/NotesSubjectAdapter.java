package com.sggs.sggs.adapters;



import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.R;
import com.sggs.sggs.model.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NotesSubjectAdapter extends RecyclerView.Adapter<NotesSubjectAdapter.SubjectViewHolder> {

    private ArrayList<Subject> subjectList;
    private Context context;
    private String branch, year, sem;
    private List<Integer> colorList;

    public NotesSubjectAdapter(ArrayList<Subject> subjectList,
                               Context context,
                               String branch,
                               String year,
                               String sem) {
        this.subjectList = subjectList;
        this.context = context;
        this.branch = branch;
        this.year = year;
        this.sem = sem;

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

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_subject_card, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
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

        holder.textSubjectName.setText(abbreviation);

        CollectionReference collectionRef = FirebaseFirestore.getInstance()
                .collection("Notes")
                .document(branch)
                .collection(year)
                .document(sem)
                .collection(subName);

        collectionRef.get()
                .addOnSuccessListener(querySnapshot -> {
                    holder.count.setText(String.valueOf(querySnapshot.size()));
                });


        int colorResId = colorList.get(position % colorList.size());

        int color = ContextCompat.getColor(context, colorResId);

        holder.card.setBackgroundTintList(ColorStateList.valueOf(color));

    }


    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView textSubjectName;
        ImageView card;
        TextView count;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubjectName = itemView.findViewById(R.id.subject);
            card = itemView.findViewById(R.id.icon2);
            count = itemView.findViewById(R.id.number_of_notes);
        }
    }
}
