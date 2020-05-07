package com.example.schedulish.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulish.AssessmentDetails;
import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentNameField;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentNameField = itemView.findViewById(R.id.assessmentNameField);
            assessmentNameField.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final AssessmentEntity current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentName", current.getAssessmentName());
                intent.putExtra("assessmentDate", current.getAssessmentDate());
                intent.putExtra("position", position);
                intent.putExtra("assessmentID", current.getAssessmentID());
                intent.putExtra("courseID", current.getCourseID());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;
    public AssessmentsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position){
        if(mAssessments != null) {
            AssessmentEntity current = mAssessments.get(position);
            holder.assessmentNameField.setText(current.getAssessmentName());
        }else{
            holder.assessmentNameField.setText("Loading");
        }

    }
    public void setAssessments(List<AssessmentEntity> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null){
            return mAssessments.size();
        }else return 0;
    }
}
