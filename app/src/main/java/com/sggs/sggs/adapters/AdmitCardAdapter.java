package com.sggs.sggs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.R;
import com.sggs.sggs.model.AdmitCardModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdmitCardAdapter extends RecyclerView.Adapter<AdmitCardAdapter.ViewHolder> {

    private List<AdmitCardModel> dataList;

    public AdmitCardAdapter(List<AdmitCardModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admit_card_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdmitCardModel model = dataList.get(position);
        String subString = model.getKey();

        if(model.getValue().equals("") || model.getValue()==null) {
            ViewGroup.LayoutParams layoutParams = holder.card.getLayoutParams();
            layoutParams.height = 0;
            holder.card.setLayoutParams(layoutParams);
        }


        if(subString.charAt(0) == 's' && subString.charAt(1) == 'u' && subString.charAt(2) == 'b'){

            String subjectString = model.getValue();
            String pattern = "\\b([A-Z]{3}\\d{3})\\b";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(subjectString);
            if (matcher.find()) {
                String extracted = matcher.group(1);
                holder.subCode.setText(extracted);

                int colonIndex = subjectString.indexOf(':');
                int openParenIndex = subjectString.indexOf('(');
                String subject = subjectString.substring(colonIndex + 2, openParenIndex - 1);
                holder.subject.setText(subject);

                openParenIndex = subjectString.indexOf('(');
                String th = subjectString.substring(openParenIndex + 1, openParenIndex + 3);
                holder.th.setText(th);

                openParenIndex = subjectString.indexOf('/');
                String pr = subjectString.substring(openParenIndex + 1, openParenIndex + 3);
                holder.pr.setText(pr);

            }


        }else {
            ViewGroup.LayoutParams layoutParams = holder.card.getLayoutParams();
            layoutParams.height = 0;
            holder.card.setLayoutParams(layoutParams);
        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subCode;
        TextView subject;
        TextView th;
        TextView pr;
        ConstraintLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCode = itemView.findViewById(R.id.subjectCode);
            subject = itemView.findViewById(R.id.subject);
            th = itemView.findViewById(R.id.th);
            pr = itemView.findViewById(R.id.pr);
            card = itemView.findViewById(R.id.card);
        }
    }
}
