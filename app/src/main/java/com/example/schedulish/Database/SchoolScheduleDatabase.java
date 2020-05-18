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

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 5, exportSchema = false)


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


            return null;
        }


    }
}
