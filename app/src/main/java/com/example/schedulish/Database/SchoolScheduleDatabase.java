package com.example.schedulish.Database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.schedulish.DAO.AssessmentDAO;
import com.example.schedulish.DAO.CourseDAO;
import com.example.schedulish.DAO.TermDAO;
import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.Entities.TermEntity;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 1, exportSchema = false)


public abstract class SchoolScheduleDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile SchoolScheduleDatabase INSTANCE;

    static SchoolScheduleDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SchoolScheduleDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchoolScheduleDatabase.class, "school_schedule_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDataBaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDataBaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            //turn this on to have a clear on open option available
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final TermDAO mTermDao;
        private final CourseDAO mCourseDao;
        private final AssessmentDAO mAssessmentDao;

        PopulateDbAsync(SchoolScheduleDatabase db){
            mTermDao = db.termDAO();
            mAssessmentDao = db.assessmentDAO();
            mCourseDao = db.courseDAO();
        }

        @Override
        protected Void doInBackground(final Void... params){
            TermEntity term = new TermEntity(1, "Spring 2019", "3/10/2019", "6/10/2019");
            mTermDao.insert(term);
            term = new TermEntity(2, "Summer 2019", "6/10/2019", "9/10/2019");
            mTermDao.insert(term);
            term = new TermEntity(3, "Fall 2019", "3/10/2019", "6/10/2019");
            mTermDao.insert(term);

            CourseEntity course = new CourseEntity(1, "Math101", "Mr.Anderson" , "In progress" ,  "3/15/2019","6/5/2019", 1);
            mCourseDao.insert(course);
            course = new CourseEntity(2, "Math102", "Mr.Anderson" , "Future" , "6/15/2019","9/5/2019", 2);
            mCourseDao.insert(course);
            course = new CourseEntity(3, "Math103", "Mr.Anderson" , "Future" , "9/15/2019","12/5/2019", 3);
            mCourseDao.insert(course);

            AssessmentEntity assessment = new AssessmentEntity(1, "Math 101 Midterm", "4/29/2019",1);
            mAssessmentDao.insert(assessment);
            assessment = new AssessmentEntity(2, "Math 101 Final", "5/29/2019",1);
            mAssessmentDao.insert(assessment);
            assessment = new AssessmentEntity(3, "Math 102 Midterm", "7/29/2019",2);
            mAssessmentDao.insert(assessment);
            assessment = new AssessmentEntity(4, "Math 102 Final", "8/29/2019",2);
            mAssessmentDao.insert(assessment);
            assessment = new AssessmentEntity(5, "Math 103 Midterm", "10/29/2019",3);
            mAssessmentDao.insert(assessment);
            assessment = new AssessmentEntity(6, "Math 103 Final", "11/29/2019",3);
            mAssessmentDao.insert(assessment);

            return null;
        }


    }
}
