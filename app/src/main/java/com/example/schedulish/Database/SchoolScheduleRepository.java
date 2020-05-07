package com.example.schedulish.Database;

import androidx.lifecycle.LiveData;

import android.app.Application;
import android.os.AsyncTask;

import com.example.schedulish.DAO.AssessmentDAO;
import com.example.schedulish.DAO.CourseDAO;
import com.example.schedulish.DAO.TermDAO;
import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.Entities.TermEntity;

import java.util.List;

public class SchoolScheduleRepository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    private LiveData<List<TermEntity>> mAllTerms;
    private int termID;
    private int courseID;

    public SchoolScheduleRepository(Application application){
        SchoolScheduleDatabase db = SchoolScheduleDatabase.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
        mAllAssessments = mAssessmentDAO.getAllAssessments();
        mAssociatedAssessments = mAssessmentDAO.getAllAssociatedAssessments(courseID);
        mAllCourses = mCourseDAO.getAllCourses();
        mAssociatedCourses = mCourseDAO.getAllAssociatedCourses(termID);
        mAllTerms = mTermDAO.getAllTerms();
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments(){return mAllAssessments;}
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseID) {return mAssociatedAssessments;}
    public LiveData<List<CourseEntity>> getAllCourses(){return mAllCourses;}
    public LiveData<List<CourseEntity>> getAssociatedCourses(int termID) {return mAssociatedCourses;}
    public LiveData<List<TermEntity>> getAllTerms() {return mAllTerms;}
    public void insert (TermEntity termEntity) {
        new insertAsyncTask1(mTermDAO).execute(termEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<TermEntity, Void, Void> {

        private TermDAO mAsyncTaskDao;

        insertAsyncTask1(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insert (CourseEntity courseEntity) {
        new insertAsyncTask2(mCourseDAO).execute(courseEntity);
    }

    private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        insertAsyncTask2(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insert (AssessmentEntity assessmentEntity) {
        new insertAsyncTask3(mAssessmentDAO).execute(assessmentEntity);
    }

    private static class insertAsyncTask3 extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        insertAsyncTask3(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void delete (TermEntity termEntity) {
        new deleteAsyncTask1(mTermDAO).execute(termEntity);
    }

    private static class deleteAsyncTask1 extends AsyncTask<TermEntity, Void, Void> {

        private TermDAO mAsyncTaskDao;

        deleteAsyncTask1(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    public void delete (CourseEntity courseEntity) {
        new deleteAsyncTask2(mCourseDAO).execute(courseEntity);
    }

    private static class deleteAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        deleteAsyncTask2(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    public void delete (AssessmentEntity assessmentEntity) {
        new deleteAsyncTask3(mAssessmentDAO).execute(assessmentEntity);
    }

    private static class deleteAsyncTask3 extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        deleteAsyncTask3(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
