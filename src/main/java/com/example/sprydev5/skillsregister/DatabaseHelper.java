package com.example.sprydev5.skillsregister;

/**
 * Created by sprydev5 on 22/7/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sprydev5.skillsregister.model.Award;
import com.example.sprydev5.skillsregister.model.Certificate;
import com.example.sprydev5.skillsregister.model.Contact;
import com.example.sprydev5.skillsregister.model.Edu;
import com.example.sprydev5.skillsregister.model.Project;
import com.example.sprydev5.skillsregister.model.Publication;
import com.example.sprydev5.skillsregister.model.Skill;
import com.example.sprydev5.skillsregister.model.Workexperience;

import org.json.JSONObject;

/**
 * Created by sprydev5 on 14/6/16.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {


    /** Database name */
    private static String DBNAME = "skillsregister";

    /** Version number of the database */
    private static int VERSION = 1;

    private SQLiteDatabase mDB;
    private DatabaseHelper databaseHelper;

    public static final String PERSONAL_INFO="personal_info";
    public static final String PERSONAL_INFO_COLUMN_ID="_id";
    public static final String LOCATION="location";
    public static final String FULL_NAME="full_name";
    public static final String PHONE="phone";
    public static final String POSITION="position";
    public static final String EMAIL="email";
    public static final String DOB="dob";
    public static final String PERSONAL_CREATED="created";
    public static final String PERSONAL_UPDATED="updated";
    public static final String PERSONAL_SYNC="synchronisation";
    public static final String IMAGE="image";

    public static final String EDUCATION="education";
    public static final String EDUCATION_COLUMN_ID="_id";
    public static final String EDUCATION_MONGOID="mongoid";
    public static final String SCHOOLNAME="schoolname";
    public static final String SCHOOLTYPE="schooltype";
    public static final String COURSETYPE="coursetype";
    public static final String EDUCATIONTITLE="educationtitle";
    public static final String EDUCATIONTYPE="educationtype";
    public static final String CGPI="cgpi";
    public static final String CGPITYPE="cgpitype";
    public static final String LOCATIONNAME="locationname";
    public static final String LOCATIONTYPE="locationtype";
    public static final String DEGREEFROM="degreefrom";
    public static final String DEGREEUPTO="upto";
    public static final String EDUCATION_CREATED="created";
    public static final String EDUCATION_UPDATED="updated";
    public static final String EDUCATION_SYNC="synchronisation";


    public static final String SIGNUP="signup";
    public static final String SIGNUP_COLUMN_ID="_id";
    public static final String F_NAME="f_name";
    public static final String L_NAME="l_name";
    public static final String PASSWORD="password";
    public static final String EMAILL="email";
    public static final String SIGNUP_CREATED="created";
    public static final String SIGN_UPDATED="updated";
    public static final String SIGN_SYNC="synchronisation";

    public static final String PROJECTS="projects";
    public static final String PROJECTS_COLUMN_ID="_id";
    public static final String PROJECTNAME="projectname";
    public static final String ROLE="role";
    public static final String RESPONSIBILITY="responsibility";
    public static final String PROJECTFROM="projfrom";
    public static final String PROJECTUPTO="projupto";
    public static final String PROJECTS_CREATED="created";
    public static final String PROJECTS_UPDATED="updated";
    public static final String PROJECTS_SYNC="synchronisation";

    public static final String WORKEXP="workexp";
    public static final String WORKEXK_COLUMN_ID="_id";
    public  static final String COMPANYNAME="companyname";
    public  static final String COMPANYTITLE="comapnytitle";
    public static final String JOB="job";
    public static final String JOBTYPE="jobtype";
    public static final String EXPFROM="expfrom";
    public static final String EXPUPTO="expupto";
    public static final String EXPSTATUS="expstatus";
    public static final String JOBLOCATION="joblocation";
    public static final String  WORKEXP_CREATED="created";
    public static final String WORKEXP_UPDATED="updated";
    public static final String WORKEXP_SYNC="synchronisation";

    public static final String SKILLS="skills";
    public static final String SKILLS_COLUMN_ID="_id";
    public static final String SKILLTITLE="skititle";
    public static final String SKILLLEVEL="skilllevel";
    public static final String  SKILLS_CREATED="created";
    public static final String SKILLS_UPDATED="updated";
    public static final String SKILLS_SYNC="synchronisation";

    public static final String OBJECTIVE="objective";
    public static final String OBJECTIVE_COLUMN_ID="_id";
    public static final String OBJECTIVELIST="objectivelist";
    public static final String OBJECTIVE_CREATED="created";
    public static final String OBJECTIVE_UPDATED="updated";
    public static final String OBJECTIVE_SYNC="sync";

    public static final String TBCONTACT="tbcontact";
    public static final String CONTACT_COLUMN_ID="_id";
    public static final String CONTACT="contact";
    public static final String CATEGORY="category";
    public static final String CONTACTSTATUS="contactstatus";
    public static final String CONTACTTYPE="contacttype";

    public static final String TBCERTIFICATE="tbcertificate";
    public static final String CERTIFICATE_COLUMN_ID="_id";
    public static final String CERTIFICATENAME="certificatename";
    public static final String CERTIFICATETYPE="certificatetype";
    public static final String CERTIFICATESTATUS="status";
    public static final String CERTIFICATEDATE="certificatedate";
    public static final String AUTHORITY="authority";
    public static final String RANK="rank";

    public static final String VOLUNTEER="volunteer";
    public static final String VOLUNTEER_COLUMN_ID="_id";
    public static final String VOLUNTEER_ROLE="role";
    public static final String VOLUNTEER_ORG="organisation";
    public static final String VOLUNTEER_FROM="volun_from";
    public static final String VOLUNTEER_UPTO="upto";
    public static final String VOLUNTEER_TYPE="type";
    public static final String VOLUNTEER_DESCRIPTION="description";

    public static final String AWARDS="awards";
    public static final String AWARD_COLUMN_ID="_id";
    public static final String AWARD_TITLE="title";
    public static final String AWARD_ORGANISATION="organisation";
    public static final String AWARD_DATE="date";
    public static final String AWARD_DESCRIPTION="description";

    public static final String PUBLICATION="publication";
    public static final String PUBLICATION_COLUMN_ID="_id";
    public static final String PUBLICATION_TITLE="title";
    public static final String PUBLICATION_ORGANISATION="organisation";
    public static final String PUBLICATION_DATE="date";
    public static final String PUBLICATION_DESCRIPTION="description";

    public static final String PERSONBIT="personbit";
    public static final String PERSONBITCOLUMN_ID="_id";
    public static final String PERSONBIT_FOREIGN_KEY="reference_id";
    public static final String PERSONBIT_MONGOID="mongoid";
    public static final String BITTYPE="bittype";
    public static final String DATA="data";
    public static final String SYNCDATE="syncdate";
    public static final String SYNCUPDATE="updatedate";
    public static final String STATUS="status";

    public static final String LIVE_SYNC="live_sync";
    public static final String LIVE_SYNC_COLUMN_ID="_id";
    public static final String PERSONBIT_TYPE="personbittype";
    public static final String URL="url";
    public static final String METHOD="method";
    public static final String LAST_SYNC="lastsync";
    public static final String LIVE_STATUS="livestatus";



    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    public DatabaseHelper open() throws SQLException {
        mDB = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return mDB;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 = 	"create table "+ PERSONAL_INFO + " ( "
                + PERSONAL_INFO_COLUMN_ID + " integer primary key autoincrement , "
                + LOCATION + " text ,"
                + FULL_NAME + "  text , "
                + PHONE + "  text ,"
                + POSITION +" text ,"
                + EMAIL + " text ,"
                + DOB +" text ,"
                + IMAGE + " BLOB NOT NULL ,"
                + PERSONAL_CREATED + " text ,"
                + PERSONAL_UPDATED + " text ,"
                + PERSONAL_SYNC + " text) " ;

        String sql2 = 	"create table "+ EDUCATION + " ( "
                + EDUCATION_COLUMN_ID+ " integer primary key autoincrement , "
                + EDUCATION_MONGOID + " text ,"
                + SCHOOLNAME + " text ,"
                + SCHOOLTYPE + " text  , "
                + COURSETYPE + "  text  , "
                + EDUCATIONTITLE + "  text  , "
                + EDUCATIONTYPE + " text ,"
                + CGPI + "  text ,"
                + CGPITYPE +" text ,"
                + LOCATIONNAME + "  text  , "
                + LOCATIONTYPE + "  text ,"
                + DEGREEFROM + " text ,"
                + DEGREEUPTO + " text ,"
                + EDUCATION_CREATED + " text ,"
                + EDUCATION_UPDATED + " text ,"
                + EDUCATION_SYNC + " text) " ;

        String sql3 = 	"create table "+ SIGNUP + " ( "
                + SIGNUP_COLUMN_ID + " integer primary key autoincrement , "
                + F_NAME + " text  , "
                + L_NAME + "  text  , "
                + PASSWORD +" text ,"
                + EMAILL + "  text ,"
                + SIGNUP_CREATED + " text , "
                + SIGN_UPDATED + " text ,"
                + SIGN_SYNC + " text)  " ;
        String sql4 = 	"create table "+ WORKEXP + " ( "
                + WORKEXK_COLUMN_ID + " integer primary key autoincrement , "
                + COMPANYNAME + " text ,"
                + COMPANYTITLE + " text  , "
                + JOB + " text ,"
                + JOBTYPE + "  text  , "
                + EXPSTATUS + " text ,"
                + JOBLOCATION + "  text ,"
                + EXPFROM + " text ,"
                + EXPUPTO + " text ,"
                + WORKEXP_CREATED + " text ,"
                + WORKEXP_UPDATED + " text ,"
                + WORKEXP_SYNC + " text ) " ;

        String sql5 = 	"create table "+ PROJECTS + " ( "
                + PROJECTS_COLUMN_ID + " integer primary key autoincrement , "
                + PROJECTNAME + " text ,"
                + ROLE + " text  , "
                + RESPONSIBILITY + "  text  , "
                + PROJECTFROM +" text ,"
                + PROJECTUPTO + "  text ,"
                + PROJECTS_CREATED + " text , "
                + PROJECTS_UPDATED + " text ,"
                + PROJECTS_SYNC + " text)  " ;
        String sql6 = 	"create table "+ SKILLS + " ( "
                + SKILLS_COLUMN_ID + " integer primary key autoincrement , "
                + SKILLTITLE + " text ,"
                + SKILLLEVEL + " text  , "
                + SKILLS_CREATED + " text , "
                + SKILLS_UPDATED + " text ,"
                + SKILLS_SYNC + " text )  " ;
        String sql7 =  " create table " + OBJECTIVE + " ( "
                + OBJECTIVE_COLUMN_ID + " integer primary key autoincrement ,"
                + OBJECTIVELIST + " text , "
                + OBJECTIVE_CREATED + " text , "
                + OBJECTIVE_UPDATED + " text ,"
                + OBJECTIVE_SYNC + "text )";
        String sql8 =  " create table " + TBCONTACT + " ( "
                + CONTACT_COLUMN_ID + " integer primary key autoincrement ,"
                + CONTACT + " text , "
                + CATEGORY + " text , "
                + CONTACTSTATUS + " text ,"
                + CONTACTTYPE + " text )";

        String sql9 = 	"create table "+ TBCERTIFICATE + " ( "
                + CERTIFICATE_COLUMN_ID + " integer primary key autoincrement , "
                + CERTIFICATENAME + " text ,"
                + CERTIFICATESTATUS + " text  , "
                + CERTIFICATETYPE + "  text  , "
                + CERTIFICATEDATE +" text ,"
                + AUTHORITY + "  text ,"
                + RANK + " text )";

        String sql10= "create table "+ PERSONBIT + "("
                + PERSONBITCOLUMN_ID + " integer primary key autoincrement ,"
                + PERSONBIT_FOREIGN_KEY + " text ,"
                + PERSONBIT_MONGOID + " text ,"
                + BITTYPE + " text ,"
                + DATA + " text ,"
                + SYNCDATE + " text ,"
                + SYNCUPDATE + " text ,"
                + STATUS + " text ,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+SIGNUP+"("+SIGNUP_COLUMN_ID+"));,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+SKILLS+"("+SKILLS_COLUMN_ID+"));,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+TBCONTACT+"("+CONTACT_COLUMN_ID+"));,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+TBCERTIFICATE+"("+CERTIFICATE_COLUMN_ID+"));,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+PROJECTS+"("+PROJECTS_COLUMN_ID+"));,"
                + " FOREIGN KEY ("+PERSONBIT_FOREIGN_KEY+") REFERENCES "+EDUCATION+"("+EDUCATION_COLUMN_ID+"));,"
                + " FOREIGN KEY("+PERSONBIT_FOREIGN_KEY+")REFERENCES " + WORKEXP + "("+WORKEXK_COLUMN_ID + "));";

        String sql11= "create table "+ LIVE_SYNC + "("
                + LIVE_SYNC_COLUMN_ID + " integer primary key autoincrement ,"
                + PERSONBIT_TYPE + " text ,"
                + URL + " text ,"
                + METHOD + " text ,"
                + LAST_SYNC + " text ,"
                + LIVE_STATUS + " text )";

        String sql12= "create table "+ VOLUNTEER + "("
                + VOLUNTEER_COLUMN_ID + " integer primary key autoincrement ,"
                + VOLUNTEER_ROLE + " text ,"
                + VOLUNTEER_TYPE + " text ,"
                + VOLUNTEER_ORG + " text ,"
                + VOLUNTEER_FROM + " text ,"
                + VOLUNTEER_UPTO + " text ,"
                + VOLUNTEER_DESCRIPTION + " text )";

        String sql13= "create table "+ AWARDS + "("
                + AWARD_COLUMN_ID + " integer primary key autoincrement ,"
                + AWARD_TITLE + " text ,"
                + AWARD_ORGANISATION + " text ,"
                + AWARD_DATE + " text ,"
                + AWARD_DESCRIPTION + " text )";

        String sql14= "create table "+ PUBLICATION + "("
                + PUBLICATION_COLUMN_ID + " integer primary key autoincrement ,"
                + PUBLICATION_TITLE + " text ,"
                + PUBLICATION_ORGANISATION + " text ,"
                + PUBLICATION_DATE + " text ,"
                + PUBLICATION_DESCRIPTION + " text )";


        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
        db.execSQL(sql13);
        db.execSQL(sql14);
        Log.d("personal_info",sql2);
        Log.d("carrier",sql1);
        Log.d("education",sql3);
        Log.d("workexp",sql4);
        Log.d("projects",sql5);
        Log.d("Skills",sql5);
        Log.d("Objective",sql7);
        Log.d("Personbit",sql10);

    }


    public boolean insert_personal_info(String location, String full_name , String position , String email , String phone , String dob,byte[] imageBytes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION,location);
        contentValues.put(FULL_NAME,full_name);
        contentValues.put(PHONE,phone);
        contentValues.put(POSITION,position);
        contentValues.put(EMAIL,email);
        contentValues.put(DOB,dob);
        contentValues.put(IMAGE,imageBytes);
        db.insert(PERSONAL_INFO, null, contentValues);
        return true;
    }
    public boolean insert_personbit(long forieng,String mongoid,String bitytpe,JSONObject data,String syncdate,String syncupdate,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PERSONBIT_FOREIGN_KEY,forieng);
        contentValues.put(PERSONBIT_MONGOID,mongoid);
        contentValues.put(BITTYPE,bitytpe);
        contentValues.put(DATA,data.toString());
        contentValues.put(SYNCDATE,syncdate);
        contentValues.put(SYNCUPDATE,syncupdate);
        contentValues.put(STATUS,status);
        db.insert(PERSONBIT,null,contentValues);
        return true;
    }
    public void update_personbit(String forieng,JSONObject data,String status,String bit){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update personbit set status = '"+ status +"' , data = '"+ data +"' where bittype ='" + bit + "' AND reference_id="+"'"+ forieng +"'";
        Log.d("update person_bit query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
       /* SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DATA,data.toString());
        contentValues.put(STATUS,status);
        return mDB.update(PERSONBIT, contentValues, "_id" + "=" +forieng, null) > 0;*/
    }
    public void updatePostSyncStatus(String id, String status,String syncdate){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update personbit set status = '"+ status +"' , syncdate = '"+ syncdate +"' where _id="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
    public void updateUpdateSyncStatus(String id, String status,String updatedate){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update personbit set status = '"+ status +"' , updatedate = '"+ updatedate +"'where mongoid="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
    public void updateid(String id,String mongoid){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update personbit set mongoid = '"+ mongoid +"' where _id="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();

    }
    public void updatestatus(String id,String status,JSONObject data){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update personbit set status = '"+ status +"' AND data = '"+ data +"' where _id="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();

    }
    public boolean insert_live_sync(String personbittype,String url,String method,String lastsync,String livestatus){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PERSONBIT_TYPE,personbittype);
        contentValues.put(URL,url);
        contentValues.put(METHOD,method);
        contentValues.put(LAST_SYNC,lastsync);
        contentValues.put(LIVE_STATUS,livestatus);
        db.insert(LIVE_SYNC,null,contentValues);
        return true;
    }
    public boolean insert_volunteers(String role,String type,String org,String from,String upto,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(VOLUNTEER_ROLE,role);
        contentValues.put(VOLUNTEER_TYPE,type);
        contentValues.put(VOLUNTEER_ORG,org);
        contentValues.put(VOLUNTEER_FROM,from);
        contentValues.put(VOLUNTEER_UPTO,upto);
        contentValues.put(VOLUNTEER_DESCRIPTION,description);
        db.insert(VOLUNTEER,null,contentValues);
        return true;
    }
    public boolean insert_awards(String title,String org,String date,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(AWARD_TITLE,title);
        contentValues.put(AWARD_ORGANISATION,org);
        contentValues.put(AWARD_DATE,date);
        contentValues.put(AWARD_DESCRIPTION,description);
        db.insert(AWARDS,null,contentValues);
        return true;
    }
    public boolean insert_publication(String title,String org,String date,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PUBLICATION_TITLE,title);
        contentValues.put(PUBLICATION_ORGANISATION,org);
        contentValues.put(PUBLICATION_DATE,date);
        contentValues.put(PUBLICATION_DESCRIPTION,description);
        db.insert(PUBLICATION,null,contentValues);
        return true;
    }
    public boolean update_person_bit(String id,String mongoid,String syncdate,String syncupdate,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PERSONBITCOLUMN_ID,mongoid);
        contentValues.put(SYNCDATE,syncdate);
        contentValues.put(SYNCUPDATE,syncupdate);
        contentValues.put(STATUS,status);
        return mDB.update(PERSONBIT, contentValues, "_id" + "=" +id, null) > 0;
    }
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Api are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    public boolean updatepersonaldetail(String id,String location, String full_name , String position , String email , String phone , String dob,byte[] imageBytes){
        ContentValues initialValues = new ContentValues();
        initialValues.put(LOCATION,location);
        initialValues.put(FULL_NAME,full_name);
        initialValues.put(PHONE,phone);
        initialValues.put(POSITION,position);
        initialValues.put(EMAIL,email);
        initialValues.put(DOB,dob);
        initialValues.put(IMAGE,imageBytes);
        return mDB.update(PERSONAL_INFO, initialValues, "_ID" + "=" +id, null) > 0;

    }
    public boolean insert_work_exp(String expfrom,String expupto ,String expstatus,String companytitle,String comanyname , String job , String jobtype , String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPFROM,expfrom);
        contentValues.put(EXPUPTO,expupto);
        contentValues.put(EXPSTATUS,expstatus);
        contentValues.put(COMPANYNAME,comanyname);
        contentValues.put(COMPANYTITLE,companytitle);
        contentValues.put(JOB,job);
        contentValues.put(JOBTYPE,jobtype);
        contentValues.put(JOBLOCATION,location);
        db.insert(WORKEXP, null, contentValues);
        return true;
    }


    public boolean create_account(String f_name, String l_name,String pass,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(F_NAME,f_name);
        contentValues.put(L_NAME,l_name);
        contentValues.put(PASSWORD,pass);
        contentValues.put(EMAILL,email);
        db.insert(SIGNUP,null,contentValues);
        return true;
    }
    public boolean insert_certificate(String certificatename, String status, String certificatetype, String certificatedate,String authority,String rank){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CERTIFICATENAME,certificatename);
        contentValues.put(CERTIFICATESTATUS,status);
        contentValues.put(CERTIFICATETYPE,certificatetype);
        contentValues.put(CERTIFICATEDATE,certificatedate);
        contentValues.put(AUTHORITY,authority);
        contentValues.put(RANK,rank);
        db.insert(TBCERTIFICATE,null,contentValues);
        return  true;
    }
    public boolean updatecert(String id,String certificatename, String status, String certificatetype, String certificatedate,String authority,String rank){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CERTIFICATENAME,certificatename);
        contentValues.put(CERTIFICATESTATUS,status);
        contentValues.put(CERTIFICATETYPE,certificatetype);
        contentValues.put(CERTIFICATEDATE,certificatedate);
        contentValues.put(AUTHORITY,authority);
        contentValues.put(RANK,rank);
        return mDB.update(TBCERTIFICATE, contentValues, "_ID" + "=" +id, null) > 0;
    }
    public boolean insert_project(String projectname, String role, String responsibility,String projfrom,String projupto)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROJECTNAME,projectname);
        contentValues.put(ROLE,role);
        contentValues.put(RESPONSIBILITY,responsibility);
        contentValues.put(PROJECTFROM,projfrom);
        contentValues.put(PROJECTUPTO,projupto);
        db.insert(PROJECTS,null,contentValues);
        return true;
    }
    public boolean insert_education(String schoolname,String schooltype,String coursetype,String educationtitle,String edutype,String cgpi,String cgpitype,String locationname, String locationtype,String degreefrom,String degreupto,String mongoid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOLNAME,schoolname);
        contentValues.put(SCHOOLTYPE,schooltype);
        contentValues.put(COURSETYPE,coursetype);
        contentValues.put(EDUCATIONTITLE,educationtitle);
        contentValues.put(EDUCATIONTYPE,edutype);
        contentValues.put(CGPI,cgpi);
        contentValues.put(CGPITYPE,cgpitype);
        contentValues.put(LOCATIONNAME,locationname);
        contentValues.put(LOCATIONTYPE,locationtype);
        contentValues.put(DEGREEFROM,degreefrom);
        contentValues.put(DEGREEUPTO,degreupto);
        contentValues.put(EDUCATION_MONGOID,mongoid);
        db.insert(EDUCATION,null,contentValues);
        return true;
    }
    public long getlastid(){
        long lastId=1;
        String query = "SELECT last_insert_rowid()";
        Cursor c = mDB.rawQuery (query,null);
        if (c != null && c.moveToFirst())
        {
            lastId = c.getLong(0);
//The 0 is the column index, we only have 1 column, so the index is 0
        }
        c.close();
        return lastId;
    }
    public boolean insert_skills(String title, String level){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SKILLTITLE,title);
        contentValues.put(SKILLLEVEL,level);
        db.insert(SKILLS,null,contentValues);
        return true;

    }
    public boolean update_skill(String id,String title, String level){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SKILLTITLE,title);
        contentValues.put(SKILLLEVEL,level);
        return mDB.update(SKILLS, contentValues, "_ID" + "=" +id, null) > 0;
    }
    public boolean insert_objective(String objective){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(OBJECTIVELIST,objective);
        db.insert(OBJECTIVE,null,contentValues);
        return true;
    }
    public boolean insert_contact(String contact, String category, String status,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CONTACT,contact);
        contentValues.put(CATEGORY,category);
        contentValues.put(CONTACTSTATUS,status);
        contentValues.put(CONTACTTYPE,type);
        db.insert(TBCONTACT,null,contentValues);
        return true;
    }
    public boolean update_contact(String id,String contact, String category, String status,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(CONTACT,contact);
        contentValues.put(CATEGORY,category);
        contentValues.put(CONTACTSTATUS,status);
        contentValues.put(CONTACTTYPE,type);
        return mDB.update(TBCONTACT, contentValues, "_ID" + "=" +id, null) > 0;
    }
    public String getSinlgeEntry(String userName) {
        Cursor cursor = mDB.query("signup", null, " email=?",
                new String[] { userName }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }
    public ArrayList<HashMap<String, String>> getNotSyncData(){
        ArrayList<HashMap<String, String>> edulist = new ArrayList<HashMap<String, String>>();
        edulist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("bittype", cursor.getString(cursor.getColumnIndex("bittype")));
                track.put("mongoid",cursor.getString(cursor.getColumnIndex("mongoid")));
                edulist.add(track);
            } while (cursor.moveToNext());
        }
        //  jSon = new Gson().toJson(trackList);
        cursor.close();
        return edulist;
    }
    public ArrayList<HashMap<String, String>> getNotSyncEduData(String edu_bit){
        ArrayList<HashMap<String, String>> edulist = new ArrayList<HashMap<String, String>>();
        edulist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"' AND bittype = '"+ edu_bit +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("data", cursor.getString(cursor.getColumnIndex("data")));
                track.put("mongoid", cursor.getString(cursor.getColumnIndex("mongoid")));
                edulist.add(track);
            } while (cursor.moveToNext());
        }
      //  jSon = new Gson().toJson(trackList);
        cursor.close();
        return edulist;
    }
    public ArrayList<HashMap<String, String>> getNotSyncProjData(String proj_bit){
        ArrayList<HashMap<String, String>> edulist = new ArrayList<HashMap<String, String>>();
        edulist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"' AND bittype = '"+ proj_bit +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("data", cursor.getString(cursor.getColumnIndex("data")));
                track.put("mongoid", cursor.getString(cursor.getColumnIndex("mongoid")));
                edulist.add(track);
            } while (cursor.moveToNext());
        }
        //  jSon = new Gson().toJson(trackList);
        cursor.close();
        return edulist;
    }
    public ArrayList<HashMap<String, String>> getNotSyncCertData(String cert_bit){
        ArrayList<HashMap<String, String>> edulist = new ArrayList<HashMap<String, String>>();
        edulist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"' AND bittype = '"+ cert_bit +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("data", cursor.getString(cursor.getColumnIndex("data")));
                track.put("mongoid", cursor.getString(cursor.getColumnIndex("mongoid")));
                edulist.add(track);
            } while (cursor.moveToNext());
        }
        //  jSon = new Gson().toJson(trackList);
        cursor.close();
        return edulist;
    }
    public ArrayList<HashMap<String, String>> getNotSyncContactData(String contact_bit){
        ArrayList<HashMap<String, String>> edulist = new ArrayList<HashMap<String, String>>();
        edulist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"' AND bittype = '"+ contact_bit +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("data", cursor.getString(cursor.getColumnIndex("data")));
                track.put("mongoid", cursor.getString(cursor.getColumnIndex("mongoid")));
                edulist.add(track);
            } while (cursor.moveToNext());
        }
        //  jSon = new Gson().toJson(trackList);
        cursor.close();
        return edulist;
    }
    public ArrayList<HashMap<String, String>>getallid(){
        ArrayList<HashMap<String, String>>id=new ArrayList<>();
        String selectQuery = "SELECT  * FROM personbit ";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("bittype", cursor.getString(cursor.getColumnIndex("bittype")));
                id.add(track);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;
    }
    public ArrayList<HashMap<String, String>> getNotSyncExpData(String exp_bit){
        ArrayList<HashMap<String, String>> explist = new ArrayList<HashMap<String, String>>();
        explist.clear();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"' AND bittype = '"+ exp_bit +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> track = new HashMap<String, String>();
                track.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                track.put("data", cursor.getString(cursor.getColumnIndex("data")));
                track.put("mongoid", cursor.getString(cursor.getColumnIndex("mongoid")));
                explist.add(track);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return explist;

    }
    public ArrayList<String> getnotsynceid(){
        ArrayList<String> jsonObject=new ArrayList<>();
        // synclist = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM personbit where status = '"+"pending"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    jsonObject.add(cursor.getString(0));

                }catch (Exception e){
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        database.close();

        return jsonObject;
    }
    public ArrayList<Workexperience> getAllexp(){
        ArrayList<Workexperience> labels = new ArrayList<Workexperience>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + WORKEXP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Workexperience user = new Workexperience(cursor.getString(1),cursor.getString(2),cursor.getString(6),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;
    }
    public ArrayList<Certificate> getAllcert(){
        ArrayList<Certificate> labels = new ArrayList<Certificate>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TBCERTIFICATE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Certificate user = new Certificate(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;
    }
    public ArrayList<Edu> getallpersonaldata(){
        ArrayList<Edu> labels = new ArrayList<Edu>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + EDUCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Edu user = new Edu(cursor.getString(2), cursor.getString(3),cursor.getString(9),cursor.getString(0),cursor.getString(1));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
       // db.close();

        // returning lables
        return labels;
    }
public ArrayList<Volunteer>getallvolunteer(){
    ArrayList<Volunteer> labels = new ArrayList<Volunteer>();

    // Select All Query
    String selectQuery = "SELECT  * FROM " + VOLUNTEER;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Volunteer user = new Volunteer(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(0));
            labels.add(user);
        } while (cursor.moveToNext());
    }

    // closing connection
    cursor.close();
    // db.close();

    // returning lables
    return labels;

}
    public ArrayList<Award>getallaward(){
        ArrayList<Award> labels = new ArrayList<Award>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + AWARDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Award user = new Award(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;

    }
    public ArrayList<Publication>getallpublication(){
        ArrayList<Publication> labels = new ArrayList<Publication>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PUBLICATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Publication user = new Publication(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;

    }

    public ArrayList<String> editcert(String id){
        ArrayList<String> labels = new ArrayList<String>();

        Cursor cursor;
        cursor = mDB.rawQuery("SELECT * FROM " + TBCERTIFICATE + " WHERE _id = '" + id + "'", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
                labels.add(cursor.getString(1));
                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
                labels.add(cursor.getString(5));
                labels.add(cursor.getString(6));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;
    }
    public ArrayList<Project> getAllProjects(){
        ArrayList<Project> labels = new ArrayList<Project>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROJECTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Project user = new Project(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
       // db.close();

        // returning lables
        return labels;
    }
    public ArrayList<String> getAllpeople(){
        ArrayList<String> people = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + PERSONAL_INFO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                people.add(cursor.getString(0));
                people.add(cursor.getString(1));
                people.add(cursor.getString(2));
                people.add(cursor.getString(3));
                people.add(cursor.getString(4));
                people.add(cursor.getString(5));
                people.add(cursor.getString(6));
                // people.add(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        //db.close();

        // returning lables
        return people;
    }
    public ArrayList<String> getallexp(String id){
        ArrayList<String> people = new ArrayList<String>();
        mDB = this.getReadableDatabase();
        Cursor cursor;
        cursor = mDB.rawQuery("SELECT * FROM " + WORKEXP + " WHERE _id = '" + id + "'", null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                people.add(cursor.getString(0));
                people.add(cursor.getString(1));
                people.add(cursor.getString(2));
                people.add(cursor.getString(3));
                people.add(cursor.getString(4));
                people.add(cursor.getString(5));
                people.add(cursor.getString(6));
                people.add(cursor.getString(7));
                people.add(cursor.getString(8));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return people;
    }
    public ArrayList<String> getallskill(String id){
        ArrayList<String> people = new ArrayList<String>();
        mDB = this.getReadableDatabase();
        Cursor cursor;
        cursor = mDB.rawQuery("SELECT * FROM " + SKILLS + " WHERE _id = '" + id + "'", null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                people.add(cursor.getString(0));
                people.add(cursor.getString(1));
                people.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return people;
    }
    public ArrayList<String> getuserdata(){
        ArrayList<String> people = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SIGNUP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                people.add(cursor.getString(1));
                people.add(cursor.getString(2));
                people.add(cursor.getString(4));

//
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
       // db.close();

        // returning lables
        return people;
    }
    public ArrayList<Skill> getallSkills(){
        ArrayList<Skill> labels = new ArrayList<Skill>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SKILLS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Skill user = new Skill(cursor.getString(1), cursor.getString(2),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
       // db.close();

        // returning lables
        return labels;
    }

    public ArrayList<Contact> getAllcontact(){
        ArrayList<Contact> labels = new ArrayList<Contact>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TBCONTACT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact user = new Contact(cursor.getString(1), cursor.getString(2),cursor.getString(4),cursor.getString(0));
                labels.add(user);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;
    }
    public ArrayList<String> editcontact(String id){
        ArrayList<String> labels = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = mDB.rawQuery("SELECT * FROM " + TBCONTACT + " WHERE _id = '" + id + "'", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
                labels.add(cursor.getString(1));
                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // db.close();

        // returning lables
        return labels;
    }
    public boolean updateParameters(int position,String sync,String updated){
        ContentValues initialValues = new ContentValues();
        initialValues.put(PERSONAL_SYNC,sync);
        initialValues.put(PERSONAL_UPDATED,updated);
        return mDB.update(PERSONAL_INFO, initialValues, "_ID" + "=" +position, null) > 0;
    }

    public boolean updateexp(String id,String expfrom,String expupto ,String expstatus,String companytitle,String comanyname , String job , String jobtype , String location){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPFROM,expfrom);
        contentValues.put(EXPUPTO,expupto);
        contentValues.put(EXPSTATUS,expstatus);
        contentValues.put(COMPANYNAME,comanyname);
        contentValues.put(COMPANYTITLE,companytitle);
        contentValues.put(JOB,job);
        contentValues.put(JOBTYPE,jobtype);
        contentValues.put(JOBLOCATION,location);
        return mDB.update(WORKEXP, contentValues, "_ID" + "=" +id, null) > 0;

    }
    public boolean updateeducation(String id,String schoolname,String schooltype,String coursetype,String educationtitle,String cgpi,String cgpitype,String locationname, String locationtype,String degreefrom,String degreeupto ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOLNAME,schoolname);
        contentValues.put(SCHOOLTYPE,schooltype);
        contentValues.put(COURSETYPE,coursetype);
        contentValues.put(EDUCATIONTITLE,educationtitle);
        contentValues.put(CGPI,cgpi);
        contentValues.put(CGPITYPE,cgpitype);
        contentValues.put(LOCATIONNAME,locationname);
        contentValues.put(LOCATIONTYPE,locationtype);
        contentValues.put(DEGREEFROM,degreefrom);
        contentValues.put(DEGREEUPTO,degreeupto);
        return mDB.update(EDUCATION, contentValues, "_id" + "=" +id, null) > 0;

    }
    public boolean updateeproject(String id,String projectname, String role, String responsibility,String projfrom,String projupto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROJECTNAME,projectname);
        contentValues.put(ROLE,role);
        contentValues.put(RESPONSIBILITY,responsibility);
        contentValues.put(PROJECTFROM,projfrom);
        contentValues.put(PROJECTUPTO,projupto);
        return mDB.update(PROJECTS, contentValues, "_id" + "=" +id, null) > 0;

    }
    public byte[] retreiveImageFromDB() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + PERSONAL_INFO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        byte[] blob = new byte[0];
        /*Cursor cur = mDB.query(true, PERSONAL_INFO, new String[]{IMAGE,},
                null, null, null, null,
                PERSONAL_INFO_COLUMN_ID + " DESC", "1");*/

        if (cursor.moveToFirst()) {
            do {
                blob = cursor.getBlob(cursor.getColumnIndex(IMAGE));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
      //  db.close();

        // returning lables
        return blob;
       /* if (cur.moveToFirst()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(IMAGE));
           // cur.close();
            return blob;
        }
        cur.close();
        return null;*/
    }
    public boolean isExist(String email_id) {
        mDB = this.getReadableDatabase();
        Cursor cur;
        cur = mDB.rawQuery("SELECT * FROM " + PERSONAL_INFO + " WHERE _id = '" + email_id + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        // mDB.close();
        return exist;

    }
    public boolean ismongoidExist(String mongoid) {
        mDB = this.getReadableDatabase();
        Cursor cur;
        cur = mDB.rawQuery("SELECT * FROM " + PERSONBIT + " WHERE _id = '" + mongoid + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        // mDB.close();
        return exist;

    }
    public ArrayList<String> getalledu(String id){
        ArrayList<String> edu = new ArrayList<String>();
        mDB = this.getReadableDatabase();
        Cursor cur;
        cur = mDB.rawQuery("SELECT * FROM " + EDUCATION + " WHERE _id = '" + id + "'", null);
        if (cur.moveToFirst()) {
            do {
                edu.add(cur.getString(0));
                edu.add(cur.getString(1));
                edu.add(cur.getString(2));
                edu.add(cur.getString(3));
                edu.add(cur.getString(4));
                edu.add(cur.getString(5));
                edu.add(cur.getString(6));
                edu.add(cur.getString(7));
                edu.add(cur.getString(8));
                edu.add(cur.getString(9));
                edu.add(cur.getString(10));
                edu.add(cur.getString(11));
                edu.add(cur.getString(12));
            } while (cur.moveToNext());
        }
        cur.close();
        // mDB.close();
        return edu;

    }
    public ArrayList<String> getallproject(String id){
        ArrayList<String> pro = new ArrayList<String>();
        mDB = this.getReadableDatabase();
        Cursor cur;
        cur = mDB.rawQuery("SELECT * FROM " + PROJECTS + " WHERE _id = '" + id + "'", null);
        if (cur.moveToFirst()) {
            do {
                pro.add(cur.getString(0));
                pro.add(cur.getString(1));
                pro.add(cur.getString(2));
                pro.add(cur.getString(3));
                pro.add(cur.getString(4));
                pro.add(cur.getString(5));
            } while (cur.moveToNext());
        }
        cur.close();
        // mDB.close();
        return pro;
    }
    public boolean deleterow(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(EDUCATION, EDUCATION_COLUMN_ID + "=" + id, null) > 0;

    }
    public boolean deleteexp(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(WORKEXP, WORKEXK_COLUMN_ID + "=" + id, null) > 0;

    }
    public boolean deletproj(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(PROJECTS, PROJECTS_COLUMN_ID + "=" + id, null) > 0;

    }
    public boolean deletedu(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(TBCONTACT, CONTACT_COLUMN_ID + "=" + id, null) > 0;

    }
    public boolean deletcert(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(TBCERTIFICATE, CERTIFICATE_COLUMN_ID + "=" + id, null) > 0;

    }
    public boolean deletskill(String id)
    {
        mDB = this.getWritableDatabase();
        return mDB.delete(SKILLS, SKILLS_COLUMN_ID + "=" + id, null) > 0;

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSONAL_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + EDUCATION);
        db.execSQL("DROP TABLE IF EXISTS " + WORKEXP);
        db.execSQL("DROP TABLE IF EXISTS " + PROJECTS);
        onCreate(db);

    }
}

