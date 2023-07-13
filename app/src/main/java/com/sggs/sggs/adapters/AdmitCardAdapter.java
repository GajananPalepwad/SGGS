package com.sggs.sggs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sggs.sggs.R;
import com.sggs.sggs.model.AdmitCardModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdmitCardAdapter extends RecyclerView.Adapter<AdmitCardAdapter.ViewHolder> {

    private List<AdmitCardModel> dataList;
    private List<Integer> colorList;
    private Context context;

    public AdmitCardAdapter(List<AdmitCardModel> dataList, Context context) {
        this.dataList = dataList;
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

            ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
            layoutParams1.topMargin = 0;
            layoutParams1.bottomMargin = 0;
            holder.card.setLayoutParams(layoutParams1);
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

                int colorResId = colorList.get(position % colorList.size());

                // Retrieve the color from resources
                int color = ContextCompat.getColor(context, colorResId);

                // Set the background color of the CardView
                holder.card.setCardBackgroundColor(color);

            }


        }else {
            ViewGroup.LayoutParams layoutParams = holder.card.getLayoutParams();
            layoutParams.height = 0;
            holder.card.setLayoutParams(layoutParams);

            ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
            layoutParams1.topMargin = 0;
            layoutParams1.bottomMargin = 0;
            holder.card.setLayoutParams(layoutParams1);

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
        CardView card;
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
