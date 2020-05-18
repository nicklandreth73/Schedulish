package com.example.schedulish.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulish.AddTerm;
import com.example.schedulish.TermDetails;
import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermViewHolder> {

    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termNameField;
        private final FloatingActionButton editTermButton;

        private TermViewHolder(View itemView){
            super(itemView);
            editTermButton = itemView.findViewById(R.id.termEditButton);
            editTermButton.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final TermEntity current = mTerms.get(position);
                Intent intent = new Intent(context, AddTerm.class);
                intent.putExtra("termName", current.getTermName());
                intent.putExtra("termStartDate", current.getTermStartDate());
                intent.putExtra("termEndDate", current.getTermEndDate());
                intent.putExtra("position", position);
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("termNotes", current.getTermNotes());
                context.startActivity(intent);
            });
            termNameField = itemView.findViewById(R.id.termNameField);
            termNameField.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final TermEntity current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetails.class);
                intent.putExtra("termName", current.getTermName());
                intent.putExtra("termStartDate", current.getTermStartDate());
                intent.putExtra("termEndDate", current.getTermEndDate());
                intent.putExtra("position", position);
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("termNotes", current.getTermNotes());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms;

    public TermsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);

        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position){
        if(mTerms != null) {
            final TermEntity current = mTerms.get(position);
            holder.termNameField.setText(current.getTermName());
        }else{
            holder.termNameField.setText("No Term");
        }

    }
    public void setTerms(List<TermEntity> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null){
            return mTerms.size();
        }else{
            return 0;
        }
    }

}