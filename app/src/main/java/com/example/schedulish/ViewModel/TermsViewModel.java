package com.example.schedulish.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.schedulish.Database.SchoolScheduleRepository;
import com.example.schedulish.Entities.TermEntity;

import java.util.List;

public class TermsViewModel extends AndroidViewModel {
    private SchoolScheduleRepository mRepository;
    private LiveData<List<TermEntity>> mAllTerms;

    public TermsViewModel(Application application){
        super(application);
        mRepository = new SchoolScheduleRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }
    public LiveData<List<TermEntity>> getAllTerms() {return mAllTerms;}
    public void insert(TermEntity termEntity) {mRepository.insert(termEntity);}
    public void delete(TermEntity termEntity) {mRepository.delete(termEntity);}
    public int lastID() {return mAllTerms.getValue().size();}
}
