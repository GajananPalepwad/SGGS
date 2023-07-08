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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.R;
import com.sggs.sggs.model.Subject;

import java.util.ArrayList;
import java.util.Random;

public class NotesSubjectAdapter extends RecyclerView.Adapter<NotesSubjectAdapter.SubjectViewHolder> {

    private ArrayList<Subject> subjectList;
    private Context context;
    private String branch, year, sem;

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







        int randomColor = generateRandomColor();
        holder.card.setBackgroundTintList(ColorStateList.valueOf(randomColor));

    }

    private int generateRandomColor() {
        Random random = new Random();

        // Generate random RGB values between 0 and 255
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Create a color from the RGB values
        return Color.rgb(red, green, blue);
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
