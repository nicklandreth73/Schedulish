package com.example.schedulish.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulish.CourseDetails;
import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameField;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseNameField = itemView.findViewById(R.id.courses_view);
            courseNameField.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final CourseEntity current = mCourses.get(position);
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("courseName", current.getCourseName());
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("instructorName", current.getInstructorName());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("courseStartDate", current.getCourseStartDate());
                intent.putExtra("courseEndDate", current.getCourseEndDate());
                intent.putExtra("position", position);
                intent.putExtra("termID", current.getTermID());
                context.startActivity(intent);
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);

        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position){
        if(mCourses != null) {
            final CourseEntity current = mCourses.get(position);
            holder.courseNameField.setText(current.getCourseName());
        }else{
            holder.courseNameField.setText("No Course");
        }

    }
    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}