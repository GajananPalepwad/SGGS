package com.sggs.sggs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sggs.sggs.CourseSelecter;
import com.sggs.sggs.EditCourse;
import com.sggs.sggs.R;
import android.content.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditSubjectAdapter extends RecyclerView.Adapter<EditSubjectAdapter.SubjectViewHolder> {

    private List<String> subjects;
    private Context context;
    private String academicYear, div, reg;
    public EditSubjectAdapter(String academicYear, String div, String reg, List<String> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
        this.div =div;
        this.academicYear = academicYear;
        this.reg = reg;
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
                        showConfirmationDialog(subjects.get(position), position);

                    }
                }
            });

        }

        private void showConfirmationDialog(String subject, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning!!!");
            builder.setMessage("Removing the wrong subject will erase your whole attendance of this subject.\nAre you sure to remove this subject.");

            builder.setPositiveButton("Yes", (dialog, which) -> {
                removeDocument(subject, position);
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                // Dismiss the dialog
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        public void removeFieldFromDocument(String subject, int position) {
            // Initialize Firestore
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            // Create a reference to the document
            DocumentReference documentReference = firestore.collection("Students").document(reg);

            // Create a map with the field to be removed
            Map<String, Object> updates = new HashMap<>();
            updates.put(subject, FieldValue.delete());

            // Update the document
            documentReference.update(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                                EditCourse.subjectList.remove(position);
                                // Notify the adapter about the item removal
                                notifyItemRemoved(position);
                        } else {
                            // Error handling
                            Exception e = task.getException();
                            if (e != null) {

                            }
                        }
                    });
        }

        public void removeDocument(String subject, int position) {
            // Initialize Firestore
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            // Create a reference to the document
            DocumentReference documentReference = firestore.
                    collection("Teachers").
                    document(academicYear).
                    collection(subject).
                    document(div).
                    collection("students").
                    document(reg);

            // Delete the document
            documentReference.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Document removed successfully
                            removeFieldFromDocument(subject, position);
                        } else {
                            // Error handling
                            Exception e = task.getException();
                            if (e != null) {
                            }
                        }
                    });
        }

        public void bind(String subject) {
            subjectTextView.setText(subject);
        }
    }
}

