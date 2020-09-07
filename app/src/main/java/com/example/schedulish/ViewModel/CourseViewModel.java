package com.example.schedulish.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.schedulish.Database.SchoolScheduleRepository;
import com.example.schedulish.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termID;
    private SchoolScheduleRepository mRepository;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;


    public CourseViewModel(Application application, int termID){
        super(application);
        mRepository = new SchoolScheduleRepository(application);
        mAssociatedCourses = mRepository.getAssociatedCourses(termID);
    }
    public CourseViewModel(Application application){
        super(application);
        mRepository = new SchoolScheduleRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mAssociatedCourses = mRepository.getAssociatedCourses(termID);
    }


    public LiveData<List<CourseEntity>> getAllCourses() { return mAllCourses;}
    public void insert(CourseEntity courseEntity) {mRepository.insert(courseEntity);}
    public void delete(CourseEntity courseEntity) {mRepository.delete(courseEntity);}
    public int lastID() {return mAllCourses.getValue().size();}
}
