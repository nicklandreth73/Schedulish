package com.example.schedulish.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.schedulish.Database.SchoolScheduleRepository;
import com.example.schedulish.Entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseID;
    private SchoolScheduleRepository mRepository;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;

    public AssessmentViewModel(Application application, int courseID){
        super(application);
        mRepository = new SchoolScheduleRepository(application);
        mAssociatedAssessments = mRepository.getAssociatedAssessments(courseID);
    }
    public AssessmentViewModel(Application application){
        super(application);
        mRepository = new SchoolScheduleRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
        mAssociatedAssessments = mRepository.getAssociatedAssessments(courseID);
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments() { return mAllAssessments;}
    public void insert(AssessmentEntity assessmentEntity) {mRepository.insert(assessmentEntity);}
    public void delete(AssessmentEntity assessmentEntity) {mRepository.delete(assessmentEntity);}
    public int lastID() {return mAllAssessments.getValue().size();}
}
