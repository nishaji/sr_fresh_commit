package sprytechies.skillregister.data.local;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.data.model.Contact;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.LiveSyncinsert;
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SaveProfileInsert;
import sprytechies.skillregister.data.model.SignUp;
import sprytechies.skillregister.data.model.Volunteer;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.data.remote.remote_model.Cert;
import sprytechies.skillregister.injection.ApplicationContext;


@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;Context context;
    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper, @ApplicationContext Context context) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);/*mDb.setLoggingEnabled(true);*/this.context=context;
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public void setSyncstatus(final LiveSync liveSync) {
        Observable.create(new Observable.OnSubscribe<LiveSync>() {
            @Override
            public void call(Subscriber<? super LiveSync> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.LIVE_SYNC_STATUS, Db.RibotProfileTable.insert_sync_status(liveSync), SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(liveSync);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<LiveSync>() {
            @Override
            public void onNext(final LiveSync item) {
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Live sync inserting Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("sync status inseted successfully.");
            }
        });
    }

    public Observable<List<LiveSyncinsert>> getLiveSync() {
        return mDb.createQuery(Db.RibotProfileTable.LIVE_SYNC_STATUS,
                "SELECT * FROM " + Db.RibotProfileTable.LIVE_SYNC_STATUS + " WHERE " + Db.RibotProfileTable.POST_BIT_FLAG + "=" + 0)
                .mapToList(new Func1<Cursor, LiveSyncinsert>() {
                    @Override
                    public LiveSyncinsert call(Cursor cursor) {
                        return LiveSyncinsert.create(Db.RibotProfileTable.parseLiveSync(cursor));
                    }
                });
    }

    //////////////**********************SIGNUP CURD****************************///////
    public void setUser(final SignUp newUser) {
        Observable.create(new Observable.OnSubscribe<SignUp>() {
            @Override
            public void call(Subscriber<? super SignUp> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.SIGNUP, Db.RibotProfileTable.insert_user(newUser), SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newUser);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SignUp>() {
            @Override
            public void onNext(final SignUp item) {
            }
            @Override
            public void onError(Throwable error) {
            }
            @Override
            public void onCompleted() {
                System.out.println("User created successfully.");
            }
        });}
    //////////////**********************SIGNUP CURD****************************///////
    //////////////**********************EDUCATION CURD****************************///////
    public void setEducation(final Education newEducation) {
        Observable.create(new Observable.OnSubscribe<Education>() {
            @Override
            public void call(Subscriber<? super Education> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.insert_education(newEducation), SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newEducation);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Education>() {
            @Override
            public void onNext(Education item) {
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education inserted successfully.");
            }
        });
    }
    public void update_Education(final Education newEducation,final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Education>() {
            @Override
            public void call(Subscriber<? super Education> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.insert_education(newEducation), "_id" + "=" + post_flag,  null);
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Education>() {
            @Override
            public void onNext(Education item) {
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education inserted successfully.");
            }
        });
    }
    public Observable<List<EducationInsert>> getEducation() {
        return mDb.createQuery(Db.RibotProfileTable.EDUCATION,
                "SELECT * FROM " + Db.RibotProfileTable.EDUCATION)
                .mapToList(new Func1<Cursor, EducationInsert>() {
                    @Override
                    public EducationInsert call(Cursor cursor) {
                        return EducationInsert.create(Db.RibotProfileTable.parseEducation(cursor));
                    }
                });
    }
    public Observable<List<EducationInsert>> getEducationForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.EDUCATION, "SELECT * FROM " + Db.RibotProfileTable.EDUCATION + " WHERE " + Db.RibotProfileTable.EDUCATION_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, EducationInsert>() {
                    @Override
                    public EducationInsert call(Cursor cursor) {
                        return EducationInsert.create(Db.RibotProfileTable.parseEducation(cursor));
                    }
                });

    }
    public Observable<List<EducationInsert>> getEducationForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.EDUCATION, "SELECT * FROM " + Db.RibotProfileTable.EDUCATION + " WHERE " + Db.RibotProfileTable.EDUCATION_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, EducationInsert>() {
                    @Override
                    public EducationInsert call(Cursor cursor) {
                        return EducationInsert.create(Db.RibotProfileTable.parseEducation(cursor));
                    }
                });
    }
    public Observable<List<EducationInsert>> getEducationForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.EDUCATION, "SELECT * FROM " + Db.RibotProfileTable.EDUCATION + " WHERE " + Db.RibotProfileTable.EDUCATION_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, EducationInsert>() {
                    @Override
                    public EducationInsert call(Cursor cursor) {
                        return EducationInsert.create(Db.RibotProfileTable.parseEducation(cursor));
                    }
                });
    }
    public void delete_education(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.EDUCATION_COLUMN_ID + "=" + id, null);
                    if (result >= 0) observer.onNext(id);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education delete Successfully .");
            }
        });
    }
    public void flush_education(final Education education , final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Education>() {
            @Override
            public void call(Subscriber<? super Education> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.EDUCATION_REMOTE_POST_FLAG + "=" + post_flag,  null);if (result >= 0) observer.onNext(education);
                    transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Education>() {
            @Override
            public void onNext(Education item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education flushed Successfully .");
            }
        });
    }
    public void edit_education(final Education education, final String id) {
        Observable.create(new Observable.OnSubscribe<Education>() {
            @Override
            public void call(Subscriber<? super Education> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.insert_education(education), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(education);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Education>() {
            @Override
            public void onNext(Education item) {
                System.out.println("Next: " + item);
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education Updated Successfully .");
            }
        });
    }
    public void update_education_flag(final Education education, final String id ){
        Observable.create(new Observable.OnSubscribe<Education>() {
            @Override
            public void call(Subscriber<? super Education> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.EDUCATION, Db.RibotProfileTable.set_education_status(education), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(education);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Education>() {
            @Override
            public void onNext(Education item) {
                System.out.println("Next: " + item);
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Education post status Updated Successfully .");
            }
        });
    }
    //////////////**********************EXPERIENCE CURD****************************///////
    public void setExperience(final Experience newExperience) {
        Observable.create(new Observable.OnSubscribe<Experience>() {
            @Override
            public void call(Subscriber<? super Experience> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.WORKEXP, Db.RibotProfileTable.insert_experience(newExperience), SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newExperience);transaction.markSuccessful();observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Experience>() {
            @Override
            public void onNext(Experience item) {
                System.out.println("Next: " + item);
            }
            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("Inserting Experience Successfully .");
            }
        });
    }
    public Observable<List<ExperienceInsert>> getExperience() {
        return mDb.createQuery(Db.RibotProfileTable.WORKEXP, "SELECT * FROM " + Db.RibotProfileTable.WORKEXP)
                .mapToList(new Func1<Cursor, ExperienceInsert>() {
                    @Override
                    public ExperienceInsert call(Cursor cursor) {
                        return ExperienceInsert.create(Db.RibotProfileTable.parseExperience(cursor));
                    }
                });
    }
    public Observable<List<ExperienceInsert>> getExperienceForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.WORKEXP, "SELECT * FROM " + Db.RibotProfileTable.WORKEXP + " WHERE " + Db.RibotProfileTable.WORKEXK_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, ExperienceInsert>() {
                    @Override
                    public ExperienceInsert call(Cursor cursor) {
                        return ExperienceInsert.create(Db.RibotProfileTable.parseExperience(cursor));
                    }
                });

    }
    public Observable<List<ExperienceInsert>> getExperienceForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.WORKEXP, "SELECT * FROM " + Db.RibotProfileTable.WORKEXP + " WHERE " + Db.RibotProfileTable.WORKEXP_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ExperienceInsert>() {
                    @Override
                    public ExperienceInsert call(Cursor cursor) {
                        return ExperienceInsert.create(Db.RibotProfileTable.parseExperience(cursor));
                    }
                });
    }
    public Observable<List<ExperienceInsert>> getExperienceForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.WORKEXP, "SELECT * FROM " + Db.RibotProfileTable.WORKEXP + " WHERE " + Db.RibotProfileTable.WORKEXP_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ExperienceInsert>() {
                    @Override
                    public ExperienceInsert call(Cursor cursor) {
                        return ExperienceInsert.create(Db.RibotProfileTable.parseExperience(cursor));
                    }
                });
    }
    public void edit_experience(final Experience experience, final String id) {
        Observable.create(new Observable.OnSubscribe<Experience>() {
            @Override
            public void call(Subscriber<? super Experience> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.WORKEXP, Db.RibotProfileTable.insert_experience(experience), "_id" + "=" + id, null);if (result >= 0) observer.onNext(experience);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Experience>() {
            @Override
            public void onNext(Experience item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Experience Updated Successfully .");
            }
        });
    }

    public void delete_xperience(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.WORKEXP, Db.RibotProfileTable.WORKEXK_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Experience delete Successfully .");
            }
        });
    }
    public void flush_experience(final Experience experience , final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Experience>() {
            @Override
            public void call(Subscriber<? super Experience> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.WORKEXP, Db.RibotProfileTable.WORKEXP_REMOTE_POST_FLAG + "=" + post_flag,  null);
                    if (result >= 0) observer.onNext(experience);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Experience>() {
            @Override
            public void onNext(Experience item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award flushed Successfully .");
            }
        });
    }
    public void update_experience_flag(final Experience experience,final String id ){
        Observable.create(new Observable.OnSubscribe<Experience>() {
            @Override
            public void call(Subscriber<? super Experience> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.WORKEXP, Db.RibotProfileTable.set_experience_status(experience), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(experience);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Experience>() {
            @Override
            public void onNext(Experience item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Experience post status Updated Successfully .");
            }
        });
    }
    ////*///////////**********************EXPERIENCE CURD****************************///////

    //////////////**********************AWARD CURD****************************///////
    public void setAwards(final Award newAwards) {
        Observable.create(new Observable.OnSubscribe<Award>() {
            @Override
            public void call(Subscriber<? super Award> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.AWARDS,
                            Db.RibotProfileTable.insert_awards(newAwards),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newAwards);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Award>() {
            @Override
            public void onNext(Award item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award inserted successfully .");
            }
        });
    }

    public Observable<List<AwardInsert>> getAwards() {
        return mDb.createQuery(Db.RibotProfileTable.AWARDS,
                "SELECT * FROM " + Db.RibotProfileTable.AWARDS)
                .mapToList(new Func1<Cursor, AwardInsert>() {
                    @Override
                    public AwardInsert call(Cursor cursor) {
                        return AwardInsert.create(Db.RibotProfileTable.parseAward(cursor));
                    }
                });
    }
    public Observable<List<AwardInsert>> getAwardForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.AWARDS,
                "SELECT * FROM " + Db.RibotProfileTable.AWARDS + " WHERE " + Db.RibotProfileTable.AWARD_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, AwardInsert>() {
                    @Override
                    public AwardInsert call(Cursor cursor) {
                        return AwardInsert.create(Db.RibotProfileTable.parseAward(cursor));
                    }
                });

    }
    public Observable<List<AwardInsert>> getAwardForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.AWARDS,
                "SELECT * FROM " + Db.RibotProfileTable.AWARDS + " WHERE " + Db.RibotProfileTable.AWARD_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, AwardInsert>() {
                    @Override
                    public AwardInsert call(Cursor cursor) {
                        return AwardInsert.create(Db.RibotProfileTable.parseAward(cursor));
                    }
                });
    }
    public Observable<List<AwardInsert>> getAwardForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.AWARDS,
                "SELECT * FROM " + Db.RibotProfileTable.AWARDS + " WHERE " + Db.RibotProfileTable.AWARD_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, AwardInsert>() {
                    @Override
                    public AwardInsert call(Cursor cursor) {
                        return AwardInsert.create(Db.RibotProfileTable.parseAward(cursor));
                    }
                });
    }
    public void delete_AWARDS(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.AWARDS, Db.RibotProfileTable.AWARD_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award deleted Successfully .");
            }
        });
    }
    public void flush_awards(final Award award, final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Award>() {
            @Override
            public void call(Subscriber<? super Award> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.AWARDS, Db.RibotProfileTable.AWARD_REMOTE_POST_FLAG + "=" + post_flag,  null);
                    if (result >= 0) observer.onNext(award);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Award>() {
            @Override
            public void onNext(Award item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award flushed Successfully .");
            }
        });
    }
    public void edit_awards(final Award award, final String id) {
        Observable.create(new Observable.OnSubscribe<Award>() {
            @Override
            public void call(Subscriber<? super Award> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.AWARDS, Db.RibotProfileTable.insert_awards(award), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(award);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Award>() {
            @Override
            public void onNext(Award item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Education Updated Successfully .");
            }
        });
    }
    public void update_award_flag(final Award award,final String id ){
        Observable.create(new Observable.OnSubscribe<Award>() {
            @Override
            public void call(Subscriber<? super Award> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.AWARDS, Db.RibotProfileTable.set_award_status(award), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(award);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Award>() {
            @Override
            public void onNext(Award item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award post status Updated Successfully .");
            }
        });
    }
    //////////////**********************AWARD CURD****************************///////
    //////////////**********************CERTIFICATE CURD****************************///////
    public void setCertificate(final Certificate newCertificate) {
        Observable.create(new Observable.OnSubscribe<Certificate>() {
            @Override
            public void call(Subscriber<? super Certificate> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.TBCERTIFICATE,
                            Db.RibotProfileTable.insert_certificate(newCertificate),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newCertificate);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Certificate>() {
            @Override
            public void onNext(Certificate item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Certificate inserted successfully .");
            }
        });
    }

    public Observable<List<CertificateInsert>> getCertificate() {
        return mDb.createQuery(Db.RibotProfileTable.TBCERTIFICATE,
                "SELECT * FROM " + Db.RibotProfileTable.TBCERTIFICATE)
                .mapToList(new Func1<Cursor, CertificateInsert>() {
                    @Override
                    public CertificateInsert call(Cursor cursor) {
                        return CertificateInsert.create(Db.RibotProfileTable.parseCertificate(cursor));
                    }
                });
    }
    public Observable<List<CertificateInsert>> getCertificateForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.TBCERTIFICATE,
                "SELECT * FROM " + Db.RibotProfileTable.TBCERTIFICATE + " WHERE " + Db.RibotProfileTable.CERTIFICATE_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, CertificateInsert>() {
                    @Override
                    public CertificateInsert call(Cursor cursor) {
                        return CertificateInsert.create(Db.RibotProfileTable.parseCertificate(cursor));
                    }
                });

    }
    public Observable<List<CertificateInsert>> getCertificateForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.TBCERTIFICATE,
                "SELECT * FROM " + Db.RibotProfileTable.TBCERTIFICATE + " WHERE " + Db.RibotProfileTable.CERTIFICATE_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, CertificateInsert>() {
                    @Override
                    public CertificateInsert call(Cursor cursor) {
                        return CertificateInsert.create(Db.RibotProfileTable.parseCertificate(cursor));
                    }
                });
    }
    public Observable<List<CertificateInsert>> getCertificateForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.TBCERTIFICATE,
                "SELECT * FROM " + Db.RibotProfileTable.TBCERTIFICATE + " WHERE " + Db.RibotProfileTable.CERTIFICATE_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, CertificateInsert>() {
                    @Override
                    public CertificateInsert call(Cursor cursor) {
                        return CertificateInsert.create(Db.RibotProfileTable.parseCertificate(cursor));
                    }
                });
    }

    public void delete_certificate(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.TBCERTIFICATE, Db.RibotProfileTable.CERTIFICATE_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Certificate deleted Successfully .");
            }
        });
    }
    public void flush_certificate(final Certificate certificate) {
        Observable.create(new Observable.OnSubscribe<Certificate>() {
            @Override
            public void call(Subscriber<? super Certificate> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.TBCERTIFICATE, null,  null);
                    if (result >= 0) observer.onNext(certificate);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Certificate>() {
            @Override
            public void onNext(Certificate item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Certificate flushed Successfully .");
            }
        });
    }
    public void edit_certificate(final Certificate certificate, final String id) {
        Observable.create(new Observable.OnSubscribe<Certificate>() {
            @Override
            public void call(Subscriber<? super Certificate> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.TBCERTIFICATE, Db.RibotProfileTable.insert_certificate(certificate), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(certificate);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Certificate>() {
            @Override
            public void onNext(Certificate item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Certificate Updated Successfully .");
            }
        });
    }
    public void update_certificate_flag(final Certificate certificate,final String id ){
        Observable.create(new Observable.OnSubscribe<Certificate>() {
            @Override
            public void call(Subscriber<? super Certificate> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.TBCERTIFICATE, Db.RibotProfileTable.set_certificate_status(certificate), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(certificate);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Certificate>() {
            @Override
            public void onNext(Certificate item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Certificate post status Updated Successfully .");
            }
        });
    }

    //////////////**********************CERTIFICATE CURD****************************///////
    //////////////**********************CONTACT CURD****************************///////
    public void setContact(final Contact newContact) {
        Observable.create(new Observable.OnSubscribe<Contact>() {
            @Override
            public void call(Subscriber<? super Contact> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.TBCONTACT,
                            Db.RibotProfileTable.insert_contact(newContact),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newContact);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Contact>() {
            @Override
            public void onNext(Contact item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact inserted successfully .");
            }
        });
    }

    public Observable<List<ContactInsert>> getContact() {
        return mDb.createQuery(Db.RibotProfileTable.TBCONTACT,
                "SELECT * FROM " + Db.RibotProfileTable.TBCONTACT)
                .mapToList(new Func1<Cursor, ContactInsert>() {
                    @Override
                    public ContactInsert call(Cursor cursor) {
                        return ContactInsert.create(Db.RibotProfileTable.parseContact(cursor));
                    }
                });
    }
    public Observable<List<ContactInsert>> getContactForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.TBCONTACT,
                "SELECT * FROM " + Db.RibotProfileTable.TBCONTACT + " WHERE " + Db.RibotProfileTable.CONTACT_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, ContactInsert>() {
                    @Override
                    public ContactInsert call(Cursor cursor) {
                        return ContactInsert.create(Db.RibotProfileTable.parseContact(cursor));
                    }
                });

    }
    public Observable<List<ContactInsert>> getContactForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.TBCONTACT,
                "SELECT * FROM " + Db.RibotProfileTable.TBCONTACT + " WHERE " + Db.RibotProfileTable.CONTACT_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ContactInsert>() {
                    @Override
                    public ContactInsert call(Cursor cursor) {
                        return ContactInsert.create(Db.RibotProfileTable.parseContact(cursor));
                    }
                });
    }
    public Observable<List<ContactInsert>> getContactForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.TBCONTACT,
                "SELECT * FROM " + Db.RibotProfileTable.TBCONTACT + " WHERE " + Db.RibotProfileTable.CONTACT_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ContactInsert>() {
                    @Override
                    public ContactInsert call(Cursor cursor) {
                        return ContactInsert.create(Db.RibotProfileTable.parseContact(cursor));
                    }
                });
    }
    public void delete_contact(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.TBCONTACT, Db.RibotProfileTable.CONTACT_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact deleted Successfully .");
            }
        });
    }
    public void flush_contact(final Contact contact,final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Contact>() {
            @Override
            public void call(Subscriber<? super Contact> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.TBCONTACT, Db.RibotProfileTable.CONTACT_REMOTE_POST_FLAG + "=" + post_flag,  null);
                    if (result >= 0) observer.onNext(contact);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Contact>() {
            @Override
            public void onNext(Contact item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Award flushed Successfully .");
            }
        });
    }
    public void edit_contact(final Contact contact, final String id) {
        Observable.create(new Observable.OnSubscribe<Contact>() {
            @Override
            public void call(Subscriber<? super Contact> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.TBCONTACT, Db.RibotProfileTable.insert_contact(contact), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(contact);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Contact>() {
            @Override
            public void onNext(Contact item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact Updated Successfully .");
            }
        });
    }
    public void update_contact_flag(final Contact contact,final String id ){
        Observable.create(new Observable.OnSubscribe<Contact>() {
            @Override
            public void call(Subscriber<? super Contact> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.TBCONTACT, Db.RibotProfileTable.set_contact_status(contact), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(contact);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Contact>() {
            @Override
            public void onNext(Contact item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact post status Updated Successfully .");
            }
        });
    }
    //////////////**********************CONTACT CURD****************************///////
    //////////////**********************PROJECT CURD****************************///////

    public void setProject(final Project newProject) {
        Observable.create(new Observable.OnSubscribe<Project>() {
            @Override
            public void call(Subscriber<? super Project> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.PROJECTS,
                            Db.RibotProfileTable.insert_project(newProject),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newProject);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Project>() {
            @Override
            public void onNext(Project item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Project inserted successfully .");
            }
        });
    }

    public Observable<List<ProjectInsert>> getProject() {
        return mDb.createQuery(Db.RibotProfileTable.PROJECTS,
                "SELECT * FROM " + Db.RibotProfileTable.PROJECTS)
                .mapToList(new Func1<Cursor, ProjectInsert>() {
                    @Override
                    public ProjectInsert call(Cursor cursor) {
                        return ProjectInsert.create(Db.RibotProfileTable.parseProject(cursor));
                    }
                });
    }
    public Observable<List<ProjectInsert>> getProjectForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.PROJECTS,
                "SELECT * FROM " + Db.RibotProfileTable.PROJECTS + " WHERE " + Db.RibotProfileTable.PROJECTS_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, ProjectInsert>() {
                    @Override
                    public ProjectInsert call(Cursor cursor) {
                        return ProjectInsert.create(Db.RibotProfileTable.parseProject(cursor));
                    }
                });

    }
    public Observable<List<ProjectInsert>> getProjectForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.PROJECTS,
                "SELECT * FROM " + Db.RibotProfileTable.PROJECTS + " WHERE " + Db.RibotProfileTable.PROJECTS_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ProjectInsert>() {
                    @Override
                    public ProjectInsert call(Cursor cursor) {
                        return ProjectInsert.create(Db.RibotProfileTable.parseProject(cursor));
                    }
                });
    }
    public Observable<List<ProjectInsert>> getProjectForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.PROJECTS,
                "SELECT * FROM " + Db.RibotProfileTable.PROJECTS + " WHERE " + Db.RibotProfileTable.PROJECTS_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, ProjectInsert>() {
                    @Override
                    public ProjectInsert call(Cursor cursor) {
                        return ProjectInsert.create(Db.RibotProfileTable.parseProject(cursor));
                    }
                });
    }
    public void delete_project(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.PROJECTS, Db.RibotProfileTable.PROJECTS_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact deleted Successfully .");
            }
        });
    }
    public void flush_project(final Project project ,final Integer post_flag) {
        Observable.create(new Observable.OnSubscribe<Project>() {
            @Override
            public void call(Subscriber<? super Project> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.PROJECTS, Db.RibotProfileTable.PROJECTS_REMOTE_POST_FLAG + "=" + post_flag,  null);
                    if (result >= 0) observer.onNext(project);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Project>() {
            @Override
            public void onNext(Project item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Project flushed Successfully .");
            }
        });
    }
    public void edit_project(final Project project, final String id) {
        Observable.create(new Observable.OnSubscribe<Project>() {
            @Override
            public void call(Subscriber<? super Project> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.PROJECTS, Db.RibotProfileTable.insert_project(project), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(project);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Project>() {
            @Override
            public void onNext(Project item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Project Updated Successfully .");
            }
        });
    }
    public void update_project_flag(final Project project,final String id ){
        Observable.create(new Observable.OnSubscribe<Project>() {
            @Override
            public void call(Subscriber<? super Project> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.PROJECTS, Db.RibotProfileTable.set_project_status(project), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(project);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Project>() {
            @Override
            public void onNext(Project item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Project post status Updated Successfully .");
            }
        });
    }
    //////////////**********************PROJECT CURD****************************///////
    //////////////**********************PUBLICATION CURD****************************///////
    public void setPublication(final Publication newPublication) {
        Observable.create(new Observable.OnSubscribe<Publication>() {
            @Override
            public void call(Subscriber<? super Publication> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.PUBLICATION,
                            Db.RibotProfileTable.insert_publication(newPublication),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newPublication);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Publication>() {
            @Override
            public void onNext(Publication item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publication inserted successfully .");
            }
        });
    }
    public Observable<List<PublicationInsert>> getPublicationForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.PUBLICATION,
                "SELECT * FROM " + Db.RibotProfileTable.PUBLICATION + " WHERE " + Db.RibotProfileTable.PUBLICATION_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, PublicationInsert>() {
                    @Override
                    public PublicationInsert call(Cursor cursor) {
                        return PublicationInsert.create(Db.RibotProfileTable.parsePublication(cursor));
                    }
                });

    }
    public Observable<List<PublicationInsert>> getPublication() {
        return mDb.createQuery(Db.RibotProfileTable.PUBLICATION,
                "SELECT * FROM " + Db.RibotProfileTable.PUBLICATION)
                .mapToList(new Func1<Cursor, PublicationInsert>() {
                    @Override
                    public PublicationInsert call(Cursor cursor) {
                        return PublicationInsert.create(Db.RibotProfileTable.parsePublication(cursor));
                    }
                });
    }
    public Observable<List<PublicationInsert>> getPublicationForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.PUBLICATION,
                "SELECT * FROM " + Db.RibotProfileTable.PUBLICATION + " WHERE " + Db.RibotProfileTable.PUBLICATION_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, PublicationInsert>() {
                    @Override
                    public PublicationInsert call(Cursor cursor) {
                        return PublicationInsert.create(Db.RibotProfileTable.parsePublication(cursor));
                    }
                });
    }
    public Observable<List<PublicationInsert>> getPublicationForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.PUBLICATION,
                "SELECT * FROM " + Db.RibotProfileTable.PUBLICATION + " WHERE " + Db.RibotProfileTable.PUBLICATION_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, PublicationInsert>() {
                    @Override
                    public PublicationInsert call(Cursor cursor) {
                        return PublicationInsert.create(Db.RibotProfileTable.parsePublication(cursor));
                    }
                });
    }
    public void delete_publication(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.PUBLICATION, Db.RibotProfileTable.PUBLICATION_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publication deleted Successfully .");
            }
        });
    }
    public void flush_publication(final Publication publication) {
        Observable.create(new Observable.OnSubscribe<Publication>() {
            @Override
            public void call(Subscriber<? super Publication> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.PUBLICATION, null,  null);
                    if (result >= 0) observer.onNext(publication);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Publication>() {
            @Override
            public void onNext(Publication item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publication flushed Successfully .");
            }
        });
    }
    public void edit_publication(final Publication publication, final String id) {
        Observable.create(new Observable.OnSubscribe<Publication>() {
            @Override
            public void call(Subscriber<? super Publication> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.AWARDS, Db.RibotProfileTable.insert_publication(publication), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(publication);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Publication>() {
            @Override
            public void onNext(Publication item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publication Updated Successfully .");
            }
        });
    }
    public void update_publication_flag(final Publication publication,final String id ){
        Observable.create(new Observable.OnSubscribe<Publication>() {
            @Override
            public void call(Subscriber<? super Publication> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.PUBLICATION, Db.RibotProfileTable.set_publication_status(publication), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(publication);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Publication>() {
            @Override
            public void onNext(Publication item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Publication post status Updated Successfully .");
            }
        });
    }
    //////////////**********************PUBLICATION CURD****************************///////
    //////////////**********************VOLUNTEER CURD****************************///////
    public void setVolunteer(final Volunteer newPublication) {
        Observable.create(new Observable.OnSubscribe<Volunteer>() {
            @Override
            public void call(Subscriber<? super Volunteer> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.VOLUNTEER,
                            Db.RibotProfileTable.insert_volunteer(newPublication),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) observer.onNext(newPublication);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Volunteer>() {
            @Override
            public void onNext(Volunteer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Volunteer inserted successfully .");
            }
        });
    }

    public Observable<List<volunteerInsert>> getVolunteer() {
        return mDb.createQuery(Db.RibotProfileTable.VOLUNTEER,
                "SELECT * FROM " + Db.RibotProfileTable.VOLUNTEER)
                .mapToList(new Func1<Cursor, volunteerInsert>() {
                    @Override
                    public volunteerInsert call(Cursor cursor) {
                        return volunteerInsert.create(Db.RibotProfileTable.parseVolunteer(cursor));
                    }
                });
    }
    public Observable<List<volunteerInsert>> getVolunteerForUpdate(String id) {
        return mDb.createQuery(Db.RibotProfileTable.VOLUNTEER,
                "SELECT * FROM " + Db.RibotProfileTable.VOLUNTEER + " WHERE " + Db.RibotProfileTable.VOLUNTEER_COLUMN_ID + "=" + id)
                .mapToList(new Func1<Cursor, volunteerInsert>() {
                    @Override
                    public volunteerInsert call(Cursor cursor) {
                        return volunteerInsert.create(Db.RibotProfileTable.parseVolunteer(cursor));
                    }
                });

    }
    public Observable<List<volunteerInsert>> getVolunteerForPost(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.VOLUNTEER,
                "SELECT * FROM " + Db.RibotProfileTable.VOLUNTEER + " WHERE " + Db.RibotProfileTable.VOLUNTEER_REMOTE_POST_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, volunteerInsert>() {
                    @Override
                    public volunteerInsert call(Cursor cursor) {
                        return volunteerInsert.create(Db.RibotProfileTable.parseVolunteer(cursor));
                    }
                });
    }
    public Observable<List<volunteerInsert>> getVolunteerForPut(Integer integer) {
        return mDb.createQuery(Db.RibotProfileTable.VOLUNTEER,
                "SELECT * FROM " + Db.RibotProfileTable.VOLUNTEER + " WHERE " + Db.RibotProfileTable.VOLUNTEER_REMOTE_PUT_FLAG + "=" + integer)
                .mapToList(new Func1<Cursor, volunteerInsert>() {
                    @Override
                    public volunteerInsert call(Cursor cursor) {
                        return volunteerInsert.create(Db.RibotProfileTable.parseVolunteer(cursor));
                    }
                });
    }
    public void delete_volunteer(final String id) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.VOLUNTEER, Db.RibotProfileTable.VOLUNTEER_COLUMN_ID + "=" + id,  null);
                    if (result >= 0) observer.onNext(id);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onNext(String item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Contact deleted Successfully .");
            }
        });
    }
    public void flush_volunteer(final Volunteer volunteer) {
        Observable.create(new Observable.OnSubscribe<Volunteer>() {
            @Override
            public void call(Subscriber<? super Volunteer> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.VOLUNTEER, null,  null);
                    if (result >= 0) observer.onNext(volunteer);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Volunteer>() {
            @Override
            public void onNext(Volunteer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Volunteer flushed Successfully .");
            }
        });
    }
    public void edit_volunteer(final Volunteer project, final String id) {
        Observable.create(new Observable.OnSubscribe<Volunteer>() {
            @Override
            public void call(Subscriber<? super Volunteer> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.VOLUNTEER, Db.RibotProfileTable.insert_volunteer(project), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(project);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<Volunteer>() {
            @Override
            public void onNext(Volunteer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Volunteer Updated Successfully .");
            }
        });
    }
    //////////////**********************VOLUNTEER CURD****************************///////

    //////////////**********************PROFILE CURD****************************///////
    public void setProfile(final SaveProfile probooks) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(Db.RibotProfileTable.SAVE_PROFILE_BIT,
                            Db.RibotProfileTable.insert_profile(probooks));
                    if (result >= 0) observer.onNext(probooks);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Books inserted successfully .");
            }
        });
    }
    public void saveProfile(final SaveProfile profile,final String id) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.SAVE_PROFILE_BIT, Db.RibotProfileTable.insert_profile_from_api(profile), "_id" + "=" + id,  null);

                    if (result >= 0) observer.onNext(profile);

                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Save Profile successfully .");
            }
        });
    }
    public void flush_profile(final SaveProfile profile) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.delete(Db.RibotProfileTable.SAVE_PROFILE_BIT, null,  null);
                    if (result >= 0) observer.onNext(profile);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Profile flushed Successfully .");
            }
        });
    }
    /*public Observable<List<SavePrfile>> getProile() {
        return mDb.createQuery(Db.RibotProfileTable.SAVE_PROFILE_BIT,
                "SELECT * FROM " + Db.RibotProfileTable.SAVE_PROFILE_BIT)
                .mapToList(new Func1<Cursor, ProfileInsert>() {
                    @Override
                    public ProfileInsert call(Cursor cursor) {
                        return ProfileInsert.create(Db.RibotProfileTable.parseProfile(cursor));
                    }
                });
    }*/
    public Observable<List<SaveProfileInsert>> getSavedProile() {
        return mDb.createQuery(Db.RibotProfileTable.SAVE_PROFILE_BIT,
                "SELECT * FROM " + Db.RibotProfileTable.SAVE_PROFILE_BIT)
                .mapToList(new Func1<Cursor, SaveProfileInsert>() {
                    @Override
                    public SaveProfileInsert call(Cursor cursor) {
                        return SaveProfileInsert.create(Db.RibotProfileTable.parseSaveProfile(cursor));
                    }
                });
    }
    public void update_Meta(final SaveProfile profile, final String id) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.SAVE_PROFILE_BIT, Db.RibotProfileTable.insert_Meta(profile), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(profile);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error updating profile: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Profile Updated Successfully .");
            }
        });
    }

    public void update_Skills(final SaveProfile profile, final String id) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.SAVE_PROFILE_BIT, Db.RibotProfileTable.insert_Skill(profile), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(profile);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error updating skills: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("skills Updated Successfully .");
            }
        });
    }
    public void update_Languages(final SaveProfile profile, final String id) {
        Observable.create(new Observable.OnSubscribe<SaveProfile>() {
            @Override
            public void call(Subscriber<? super SaveProfile> observer) {
                if (observer.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.update(Db.RibotProfileTable.SAVE_PROFILE_BIT, Db.RibotProfileTable.insert_Language(profile), "_id" + "=" + id,  null);
                    if (result >= 0) observer.onNext(profile);
                    transaction.markSuccessful();
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                } finally {
                    transaction.end();
                }
            }
        }).subscribe(new Subscriber<SaveProfile>() {
            @Override
            public void onNext(SaveProfile item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error updating languages: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("languages Updated Successfully .");
            }
        });
    }

    }


    //////////////**********************PROFILE CURD****************************///////




