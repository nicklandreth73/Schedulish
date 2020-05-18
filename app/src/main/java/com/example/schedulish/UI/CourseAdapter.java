package com.example.schedulish.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulish.AddCourse;
import com.example.schedulish.CourseDetails;
import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.R;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameField;
        private final FloatingActionButton courseEditButton;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseEditButton = itemView.findViewById(R.id.courseEditButton);
            courseEditButton.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final CourseEntity current = mCourses.get(position);
                Intent intent = new Intent(context, AddCourse.class);
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("courseName", current.getCourseName());
                intent.putExtra("courseStartDate", current.getCourseStartDate());
                intent.putExtra("courseEndDate", current.getCourseEndDate());
                intent.putExtra("instructorName", current.getInstructorName());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("courseNotes", current.getCourseNotes());
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("position", position);
                context.startActivity(intent);
            });
            courseNameField = itemView.findViewById(R.id.courseNameField);
            courseNameField.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                final CourseEntity current = mCourses.get(position);
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("courseID", current.getCourseID());
                intent.putExtra("courseName", current.getCourseName());
                intent.putExtra("courseStartDate", current.getCourseStartDate());
                intent.putExtra("courseEndDate", current.getCourseEndDate());
                intent.putExtra("instructorName", current.getInstructorName());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("courseNotes", current.getCourseNotes() );
                intent.putExtra("termID", current.getTermID());
                intent.putExtra("position", position);
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
            CourseEntity current = mCourses.get(position);
            holder.courseNameField.setText(current.getCourseName());
        }else{
            holder.courseNameField.setText("No Courses");
        }

    }
    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mCourses != null){
            return mCourses.size();
        }else return 0;
    }
}


