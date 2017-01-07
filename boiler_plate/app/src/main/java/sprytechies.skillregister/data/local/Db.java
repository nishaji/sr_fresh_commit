package sprytechies.skillregister.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.Contact;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.data.model.Profile;
import sprytechies.skillregister.data.model.ProfileBit;
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SignUp;
import sprytechies.skillregister.data.model.Volunteer;


public class Db {

    public Db() { }

    public abstract static class RibotProfileTable {

        public static final String TABLE_NAME = "ribot_profile";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_HEX_COLOR = "hex_color";
        public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_BIO = "bio";

        public static final String EDUCATION = "education";
        public static final String EDUCATION_COLUMN_ID = "_id";
        public static final String EDUCATION_MONGOID = "mongoid";
        public static final String SCHOOLNAME = "schoolname";
        public static final String SCHOOLTYPE = "schooltype";
        public static final String COURSETYPE = "coursetype";
        public static final String EDUCATIONTITLE = "educationtitle";
        public static final String EDUCATIONTYPE = "educationtype";
        public static final String CGPI = "cgpi";
        public static final String CGPITYPE = "cgpitype";
        public static final String LOCATIONNAME = "locationname";
        public static final String LOCATIONTYPE = "locationtype";
        public static final String DEGREEFROM = "degreefrom";
        public static final String DEGREEUPTO = "upto";
        public static final String EDUCATION_LOCAL_CREATE_FLAG="education_local_create_flag";
        public static final String EDUCATION_LOCAL_UPDATE_FLAG="education_local_update_flag";
        public static final String EDUCATION_REMOTE_POST_FLAG="education_remote_post_flag";
        public static final String EDUCATION_REMOTE_PUT_FLAG="education_remote_put_flag";
        public static final String EDU_API_CALL_DATE_TIME="date_time";


        public static final String SIGNUP = "signup";
        public static final String SIGNUP_COLUMN_ID = "_id";
        public static final String F_NAME = "f_name";
        public static final String L_NAME = "l_name";
        public static final String PASSWORD = "password";
        public static final String EMAILL = "email";


        public static final String PROJECTS = "projects";
        public static final String PROJECTS_COLUMN_ID = "_id";
        public static final String PROJECT_MONGOID = "mongoid";
        public static final String PROJECTNAME = "projectname";
        public static final String ROLE = "role";
        public static final String RESPONSIBILITY = "responsibility";
        public static final String PROJECTFROM = "projfrom";
        public static final String PROJECTUPTO = "projupto";
        public static final String PROJECTS_LOCAL_CREATE_FLAG="projects_local_create_flag";
        public static final String PROJECTS_LOCAL_UPDATE_FLAG="projects_local_update_flag";
        public static final String PROJECTS_REMOTE_POST_FLAG="projects_remote_post_flag";
        public static final String PROJECTS_REMOTE_PUT_FLAG="projects_remote_put_flag";
        public static final String PRO_API_CALL_DATE_TIME="date_time";

        public static final String WORKEXP = "workexp";
        public static final String WORKEXK_COLUMN_ID = "_id";
        public static final String EXP_MONGOID = "mongoid";
        public static final String COMPANYNAME = "companyname";
        public static final String COMPANYTITLE = "comapnytitle";
        public static final String JOB = "job";
        public static final String JOBTYPE = "jobtype";
        public static final String EXPFROM = "expfrom";
        public static final String EXPUPTO = "expupto";
        public static final String EXPSTATUS = "expstatus";
        public static final String JOBLOCATION = "joblocation";
        public static final String JOBLOCATION_TYPE = "joblocation_type";
        public static final String WORKEXP_LOCAL_CREATE_FLAG="workexp_create_flag";
        public static final String WORKEXP_LOCAL_UPDATE_FLAG="workexp_update_flag";
        public static final String WORKEXP_REMOTE_POST_FLAG="workexp_remote_post_flag";
        public static final String WORKEXP_REMOTE_PUT_FLAG="workexp_remote_put_flag";
        public static final String EXP_API_CALL_DATE_TIME="date_time";

        public static final String TBCONTACT = "tbcontact";
        public static final String CONTACT_COLUMN_ID = "_id";
        public static final String CONTACT_MONGOID = "mongoid";
        public static final String CONTACT = "contact";
        public static final String CATEGORY = "category";
        public static final String CONTACTSTATUS = "contactstatus";
        public static final String CONTACTTYPE = "contacttype";
        public static final String CONTACT_LOCAL_CREATE_FLAG="contact_local_create_flag";
        public static final String CONTACT_LOCAL_UPDATE_FLAG="contact_local_update_flag";
        public static final String CONTACT_REMOTE_POST_FLAG="contact_remote_post_flag";
        public static final String CONTACT_REMOTE_PUT_FLAG="contact_remote_put_flag";
        public static final String CONTACT_API_CALL_DATE_TIME="date_time";

        public static final String TBCERTIFICATE = "tbcertificate";
        public static final String CERTIFICATE_COLUMN_ID = "_id";
        public static final String CERTIFICATE_MONGOID = "mongoid";
        public static final String CERTIFICATENAME = "certificatename";
        public static final String CERTIFICATETYPE = "certificatetype";
        public static final String CERTIFICATEDATE = "certificatedate";
        public static final String AUTHORITY = "authority";
        public static final String RANK = "rank";
        public static final String CERTIFICATE_LOCAL_CREATE_FLAG="certificate_local_create_flag";
        public static final String CERTIFICATE_LOCAL_UPDATE_FLAG="certificate_local_update_flag";
        public static final String CERTIFICATE_REMOTE_POST_FLAG="certificate_remote_post_flag";
        public static final String CERTIFICATE_REMOTE_PUT_FLAG="certificate_remote_put_flag";
        public static final String CERT_API_CALL_DATE_TIME="date_time";

        public static final String VOLUNTEER = "volunteer";
        public static final String VOLUNTEER_COLUMN_ID = "_id";
        public static final String VOLUNTEER_MONGOID = "mongoid";
        public static final String VOLUNTEER_ROLE = "role";
        public static final String VOLUNTEER_ORG = "organisation";
        public static final String VOLUNTEER_FROM = "volun_from";
        public static final String VOLUNTEER_UPTO = "upto";
        public static final String VOLUNTEER_TYPE = "type";
        public static final String VOLUNTEER_DESCRIPTION = "description";
        public static final String VOLUNTEER_LOCAL_CREATE_FLAG="volunteer_local_create_flag";
        public static final String VOLUNTEER_LOCAL_UPDATE_FLAG="volunteer_local_update_flag";
        public static final String VOLUNTEER_REMOTE_POST_FLAG="volunteer_remote_post_flag";
        public static final String VOLUNTEER_REMOTE_PUT_FLAG="volunteer_remote_put_flag";
        public static final String VOLUN_API_CALL_DATE_TIME="date_time";

        public static final String AWARDS = "awards";
        public static final String AWARD_COLUMN_ID = "_id";
        public static final String AWARD_MONGOID = "mongoid";
        public static final String AWARD_TITLE = "title";
        public static final String AWARD_ORGANISATION = "organisation";
        public static final String AWARD_DATE = "date";
        public static final String AWARD_DESCRIPTION = "description";
        public static final String AWARD_LOCAL_CREATE_FLAG="award_local_create_flag";
        public static final String AWARD_LOCAL_UPDATE_FLAG="award_local_update_flag";
        public static final String AWARD_REMOTE_POST_FLAG="award_remote_post_flag";
        public static final String AWARD_REMOTE_PUT_FLAG="award_remote_put_flag";
        public static final String AWARD_API_CALL_DATE_TIME="date_time";

        public static final String PUBLICATION = "publication";
        public static final String PUBLICATION_COLUMN_ID = "_id";
        public static final String PUBLICATION_MONGOID = "mongoid";
        public static final String PUBLICATION_TITLE = "title";
        public static final String PUBLISHER = "publisher";
        public static final String AUTHOR = "author";
        public static final String PUB_URL = "url";
        public static final String PUBLICATION_DATE = "date";
        public static final String PUBLICATION_DESCRIPTION = "description";
        public static final String PUBLICATION_LOCAL_CREATE_FLAG="publication_local_create_flag";
        public static final String PUBLICATION_LOCAL_UPDATE_FLAG="publication_local_update_flag";
        public static final String PUBLICATION_REMOTE_POST_FLAG="publication_remote_post_flag";
        public static final String PUBLICATION_REMOTE_PUT_FLAG="publication_remote_put_flag";
        public static final String PUB_API_CALL_DATE_TIME="date_time";

        public static final String SAVE_PROFILE_BIT="save_profile_bit";
        public static final String SAVE_PROFILE_BIT_COLUMN_ID="_id";
        public static final String TYPE="type";
        public static final String META="meta";
        public static final String SKILL="skills";
        public static  final String Language="language";

        public static final String LIVE_SYNC_STATUS="live_sync";
        public static final String LIVE_SYNC_STATUS_COLUMN_ID="_id";
        public static final String BIT_TYPE="bit_type";
        public static final String POST_BIT_FLAG="post_bit_flag";
        public static final String PUT_BIT_FLAG="put_bit_flag";




        public static final String CREATE_LIVE_SYNC_STATUS =
                "CREATE TABLE " + LIVE_SYNC_STATUS + " (" +
                        LIVE_SYNC_STATUS_COLUMN_ID + " integer primary key autoincrement, " +
                        BIT_TYPE + " text , " +
                        POST_BIT_FLAG + " text , " +
                        PUT_BIT_FLAG + " text ) ";


        public static final String CREATE_EDUCATION = "create table " + EDUCATION + " ( "
                + EDUCATION_COLUMN_ID + " integer primary key autoincrement , "
                + EDUCATION_MONGOID + " text ,"
                + SCHOOLNAME + " text ,"
                + SCHOOLTYPE + " text  , "
                + COURSETYPE + "  text  , "
                + EDUCATIONTITLE + "  text  , "
                + EDUCATIONTYPE + " text ,"
                + CGPI + "  text ,"
                + CGPITYPE + " text ,"
                + LOCATIONNAME + "  text  , "
                + LOCATIONTYPE + "  text ,"
                + DEGREEFROM + " text ,"
                + DEGREEUPTO + " text ,"
                + EDUCATION_LOCAL_CREATE_FLAG + " integer , "
                + EDUCATION_LOCAL_UPDATE_FLAG + " integer , "
                + EDUCATION_REMOTE_POST_FLAG + " integer ,"
                +EDUCATION_REMOTE_PUT_FLAG + " integer ,"
                + EDU_API_CALL_DATE_TIME + " date )";


        public static final String CREATE_SIGUP = "create table " + SIGNUP + " ( "
                + SIGNUP_COLUMN_ID + " integer primary key autoincrement , "
                + F_NAME + " text  , "
                + L_NAME + "  text  , "
                + PASSWORD + " text ,"
                + EMAILL + "  text )";

        public static final String CREATE_EXP = "create table " + WORKEXP + " ( "
                + WORKEXK_COLUMN_ID + " integer primary key autoincrement , "
                + EXP_MONGOID + " text ,"
                + COMPANYNAME + " text ,"
                + COMPANYTITLE + " text  , "
                + JOB + " text ,"
                + JOBTYPE + "  text  , "
                + EXPSTATUS + " text ,"
                + JOBLOCATION + "  text ,"
                + JOBLOCATION_TYPE + "  text ,"
                + EXPFROM + " text ,"
                + EXPUPTO + " text ,"
                + WORKEXP_LOCAL_CREATE_FLAG + " integer ,"
                + WORKEXP_LOCAL_UPDATE_FLAG + " integer ,"
                + WORKEXP_REMOTE_POST_FLAG + " integer , "
                + WORKEXP_REMOTE_PUT_FLAG + " integer , "
                + EXP_API_CALL_DATE_TIME + " date )";

        public static final String CREATE_PROJECT = "create table " + PROJECTS + " ( "
                + PROJECTS_COLUMN_ID + " integer primary key autoincrement , "
                + PROJECT_MONGOID + " text ,"
                + PROJECTNAME + " text ,"
                + ROLE + " text  , "
                + RESPONSIBILITY + "  text  , "
                + PROJECTFROM + " text ,"
                + PROJECTUPTO + "  text ,"
                + PROJECTS_LOCAL_CREATE_FLAG +" integer ,"
                + PROJECTS_LOCAL_UPDATE_FLAG + " integer ,"
                + PROJECTS_REMOTE_POST_FLAG + " integer , "
                + PROJECTS_REMOTE_PUT_FLAG + " integer , "
                + PRO_API_CALL_DATE_TIME + " date )";

        public static final String CREATE_CONTACT = " create table " + TBCONTACT + " ( "
                + CONTACT_COLUMN_ID + " integer primary key autoincrement ,"
                + CONTACT_MONGOID + " text ,"
                + CONTACT + " text , "
                + CATEGORY + " text , "
                + CONTACTSTATUS + " text ,"
                + CONTACTTYPE + " text ,"
                + CONTACT_LOCAL_CREATE_FLAG + " integer ,"
                + CONTACT_LOCAL_UPDATE_FLAG + " integer ,"
                + CONTACT_REMOTE_POST_FLAG + " integer , "
                + CONTACT_REMOTE_PUT_FLAG + " integer , "
                + CONTACT_API_CALL_DATE_TIME + " date )";

        public static final String CREATE_CERTIFICATE = "create table " + TBCERTIFICATE + " ( "
                + CERTIFICATE_COLUMN_ID + " integer primary key autoincrement , "
                + CERTIFICATE_MONGOID + " text ,"
                + CERTIFICATENAME + " text ,"
                + CERTIFICATETYPE + "  text  , "
                + CERTIFICATEDATE + " text ,"
                + AUTHORITY + "  text ,"
                + RANK + " text ,"
                + CERTIFICATE_LOCAL_CREATE_FLAG + " integer ,"
                + CERTIFICATE_LOCAL_UPDATE_FLAG + " integer ,"
                + CERTIFICATE_REMOTE_POST_FLAG + " integer , "
                + CERTIFICATE_REMOTE_PUT_FLAG + " integer , "
                + CERT_API_CALL_DATE_TIME + " date )";


        public static final String CREATE_VOLUNTEER = "create table " + VOLUNTEER + "("
                + VOLUNTEER_COLUMN_ID + " integer primary key autoincrement ,"
                + VOLUNTEER_MONGOID + " text ,"
                + VOLUNTEER_ROLE + " text ,"
                + VOLUNTEER_TYPE + " text ,"
                + VOLUNTEER_ORG + " text ,"
                + VOLUNTEER_FROM + " text ,"
                + VOLUNTEER_UPTO + " text ,"
                + VOLUNTEER_DESCRIPTION + " text ,"
                + VOLUNTEER_LOCAL_CREATE_FLAG + " integer ,"
                + VOLUNTEER_LOCAL_UPDATE_FLAG + " integer ,"
                + VOLUNTEER_REMOTE_POST_FLAG + " integer , "
                + VOLUNTEER_REMOTE_PUT_FLAG + " integer , "
                + VOLUN_API_CALL_DATE_TIME + " date )";

        public static final String CREATE_AWARDS = "create table " + AWARDS + "("
                + AWARD_COLUMN_ID + " integer primary key autoincrement ,"
                + AWARD_MONGOID + " text ,"
                + AWARD_TITLE + " text ,"
                + AWARD_ORGANISATION + " text ,"
                + AWARD_DATE + " text ,"
                + AWARD_DESCRIPTION + " text ,"
                + AWARD_LOCAL_CREATE_FLAG + " integer ,"
                + AWARD_LOCAL_UPDATE_FLAG + " integer ,"
                + AWARD_REMOTE_POST_FLAG + " integer , "
                + AWARD_REMOTE_PUT_FLAG + " integer , "
                + AWARD_API_CALL_DATE_TIME + " date )";

        public static final String CREATE_PUBLICATION = "create table " + PUBLICATION + "("
                + PUBLICATION_COLUMN_ID + " integer primary key autoincrement ,"
                + PUBLICATION_MONGOID + " text ,"
                + PUBLICATION_TITLE + " text ,"
                + PUBLISHER + " text ,"
                + AUTHOR + " text ,"
                + PUB_URL + " text ,"
                + PUBLICATION_DATE + " text ,"
                + PUBLICATION_DESCRIPTION + " text ,"
                + PUBLICATION_LOCAL_CREATE_FLAG + " integer ,"
                + PUBLICATION_LOCAL_UPDATE_FLAG + " integer ,"
                + PUBLICATION_REMOTE_POST_FLAG + " integer , "
                + PUBLICATION_REMOTE_PUT_FLAG + " integer , "
                + PUB_API_CALL_DATE_TIME + " date )";


        public static final String CREATE_SAVE_PROFILE = " create table " + SAVE_PROFILE_BIT + " ( "
                + SAVE_PROFILE_BIT_COLUMN_ID + " integer primary key autoincrement , "
                + TYPE + " text  , "
                + META + "  text  , "
                + SKILL + " text ,"
                + Language + "  text )";

        public static ContentValues insert_sync_status(LiveSync liveSync) {
            ContentValues values = new ContentValues();
            if (liveSync.bit() != null) values.put(BIT_TYPE, liveSync.bit());
            if (liveSync.post() != null) values.put(POST_BIT_FLAG, liveSync.post());
            if (liveSync.put() != null) values.put(PUT_BIT_FLAG, liveSync.put());
            return values;
        }
        public static ContentValues insert_awards(Award award) {

            ContentValues values = new ContentValues();
            values.put(AWARD_TITLE,award.title() );
            values.put(AWARD_ORGANISATION,award.organisation() );
            values.put(AWARD_DESCRIPTION,award.description() );
            values.put(AWARD_DATE,award.duration());
            values.put(AWARD_API_CALL_DATE_TIME,award.date());
            values.put(AWARD_REMOTE_POST_FLAG,award.postflag());
            values.put(AWARD_LOCAL_CREATE_FLAG,award.createflag());
            values.put(AWARD_LOCAL_UPDATE_FLAG,award.updateflag());
            values.put(AWARD_REMOTE_PUT_FLAG,award.putflag());
            values.put(AWARD_MONGOID,award.mongoid());
            return values;
        }
        public static ContentValues set_award_status(Award award){
            ContentValues values=new ContentValues();
            values.put(AWARD_REMOTE_POST_FLAG,award.postflag());
            values.put(AWARD_REMOTE_PUT_FLAG,award.putflag());
            values.put(AWARD_MONGOID,award.mongoid());
            return values;
        }

        public static ContentValues insert_certificate(Certificate certificate){
            ContentValues values = new ContentValues();
            values.put(CERTIFICATENAME, certificate.name());
            values.put(CERTIFICATETYPE, certificate.type());
            values.put(CERTIFICATEDATE, certificate.certdate());
            values.put(AUTHORITY, certificate.authority());
            values.put(RANK, certificate.rank());
            values.put(CERT_API_CALL_DATE_TIME,certificate.date());
            values.put(CERTIFICATE_REMOTE_POST_FLAG,certificate.postflag());
            values.put(CERTIFICATE_REMOTE_PUT_FLAG,certificate.putflag());
            values.put(CERTIFICATE_LOCAL_CREATE_FLAG,certificate.createflag());
            values.put(CERTIFICATE_LOCAL_UPDATE_FLAG,certificate.updateflag());
            values.put(CERTIFICATE_MONGOID,certificate.mongoid());

            return values;
        }
        public static ContentValues set_certificate_status(Certificate certificate){
            ContentValues values=new ContentValues();
            values.put(CERTIFICATE_REMOTE_POST_FLAG,certificate.postflag());
            values.put(CERTIFICATE_REMOTE_PUT_FLAG,certificate.putflag());
            values.put(CERTIFICATE_MONGOID,certificate.mongoid());
            return values;
        }
        public static ContentValues insert_education(Education education){
            ContentValues values = new ContentValues();
            values.put(SCHOOLNAME, education.school());
            values.put(SCHOOLTYPE, education.schooltype());
            values.put(COURSETYPE, education.course());
            values.put(EDUCATIONTITLE, education.title());
            values.put(EDUCATIONTYPE, education.edutype());
            values.put(CGPI, education.cgpi());
            values.put(CGPITYPE, education.cgpitype());
            values.put(LOCATIONNAME, education.location().getName());
            values.put(LOCATIONTYPE, education.location().getType());
            values.put(DEGREEFROM, education.from());
            values.put(DEGREEUPTO, education.upto());
            values.put(EDUCATION_LOCAL_CREATE_FLAG,education.createflag());
            values.put(EDU_API_CALL_DATE_TIME,education.date());
            values.put(EDUCATION_LOCAL_UPDATE_FLAG,education.updateflag());
            values.put(EDUCATION_MONGOID,education.mongoid());
            values.put(EDUCATION_REMOTE_POST_FLAG,education.postflag());
            values.put(EDUCATION_REMOTE_PUT_FLAG,education.putflag());
            return values;
        }
        public static ContentValues set_education_status(Education education){
            ContentValues values=new ContentValues();
            values.put(EDUCATION_REMOTE_POST_FLAG,education.postflag());
            values.put(EDUCATION_REMOTE_PUT_FLAG,education.postflag());
            values.put(EDUCATION_MONGOID,education.mongoid());
            values.put(EDU_API_CALL_DATE_TIME,education.date());
            return values;
        }
        public static ContentValues insert_user(SignUp signUp){
            ContentValues value=new ContentValues();
            value.put(F_NAME,signUp.first_name());
            value.put(L_NAME,signUp.last_name());
            value.put(EMAILL,signUp.email());
            value.put(PASSWORD,signUp.password());
            return value;

        }

        public static ContentValues insert_experience(Experience experience){
            ContentValues values=new ContentValues();
            values.put(EXPFROM, experience.from());
            values.put(EXPUPTO, experience.upto());
            values.put(EXPSTATUS, experience.status());
            values.put(COMPANYNAME, experience.company());
            values.put(COMPANYTITLE, experience.title());
            values.put(JOB, experience.job());
            values.put(JOBTYPE, experience.type());
            values.put(JOBLOCATION, experience.location().name);
            values.put(JOBLOCATION_TYPE, experience.location().type);
            values.put(EXP_API_CALL_DATE_TIME,experience.date());
            values.put(EXP_MONGOID,experience.mongoid());
            values.put(WORKEXP_LOCAL_CREATE_FLAG,experience.createflag());
            values.put(WORKEXP_LOCAL_UPDATE_FLAG,experience.updateflag());
            values.put(WORKEXP_REMOTE_POST_FLAG,experience.postflag());
            values.put(WORKEXP_REMOTE_PUT_FLAG,experience.putflag());
            return values;
        }
        public static ContentValues set_experience_status(Experience experience){
            ContentValues values=new ContentValues();
            values.put(WORKEXP_REMOTE_POST_FLAG,experience.postflag());
            values.put(WORKEXP_REMOTE_PUT_FLAG,experience.putflag());
            values.put(EXP_MONGOID,experience.mongoid());

            return values;
        }
        public static ContentValues insert_project(Project project){
            ContentValues values=new ContentValues();
            values.put(PROJECTNAME, project.project());
            values.put(ROLE, project.role());
            values.put(RESPONSIBILITY, project.meta());
            values.put(PROJECTFROM, project.from());
            values.put(PROJECTUPTO, project.upto());
            values.put(PRO_API_CALL_DATE_TIME,project.date());
            values.put(PROJECTS_LOCAL_CREATE_FLAG,project.createflag());
            values.put(PROJECTS_LOCAL_UPDATE_FLAG,project.updateflag());
            values.put(PROJECTS_REMOTE_POST_FLAG,project.postflag());
            values.put(PROJECTS_REMOTE_PUT_FLAG,project.putflag());
            values.put(PROJECT_MONGOID,project.mongoid());
            return values;
        }
        public static ContentValues set_project_status(Project project){
            ContentValues values=new ContentValues();
            values.put(PROJECTS_REMOTE_POST_FLAG,project.postflag());
            values.put(PROJECTS_REMOTE_PUT_FLAG,project.putflag());
            values.put(PROJECT_MONGOID,project.mongoid());
            return values;
        }
        public static ContentValues insert_contact(Contact contact){
            ContentValues values=new ContentValues();
            values.put(CONTACT,contact.contact());
            values.put(CATEGORY,contact.category());
            values.put(CONTACTSTATUS,contact.status());
            values.put(CONTACTTYPE,contact.type());
            values.put(CONTACT_API_CALL_DATE_TIME,contact.date());
            values.put(CONTACT_LOCAL_CREATE_FLAG,contact.createflag());
            values.put(CONTACT_LOCAL_UPDATE_FLAG,contact.updateflag());
            values.put(CONTACT_REMOTE_POST_FLAG,contact.postflag());
            values.put(CONTACT_REMOTE_PUT_FLAG,contact.putflag());
            return values;
        }
        public static ContentValues set_contact_status(Contact contact){
            ContentValues values=new ContentValues();
            values.put(CONTACT_REMOTE_POST_FLAG,contact.postflag());
            values.put(CONTACT_REMOTE_PUT_FLAG,contact.putflag());
            values.put(CONTACT_MONGOID,contact.mongoid());
            return values;
        }
        public static ContentValues insert_publication(Publication publication){
            ContentValues values=new ContentValues();
            values.put(PUBLICATION_TITLE,publication.title());
            values.put(PUBLISHER,publication.publishers());
            values.put(AUTHOR,publication.authors());
            values.put(PUB_URL,publication.url());
            values.put(PUBLICATION_DESCRIPTION,publication.description());
            values.put(PUBLICATION_DATE,publication.date());
            values.put(PUB_API_CALL_DATE_TIME,publication.datetime());
            values.put(PUBLICATION_LOCAL_CREATE_FLAG,publication.createflag());
            values.put(PUBLICATION_LOCAL_UPDATE_FLAG,publication.updateflag());
            values.put(PUBLICATION_REMOTE_POST_FLAG,publication.postflag());
            values.put(PUBLICATION_REMOTE_PUT_FLAG,publication.putflag());
            return values;
        }
        public static ContentValues set_publication_status(Publication publication){
            ContentValues values=new ContentValues();
            values.put(PUBLICATION_REMOTE_POST_FLAG,publication.postflag());
            values.put(PUBLICATION_REMOTE_PUT_FLAG,publication.putflag());
            values.put(PUBLICATION_MONGOID,publication.mongoid());
            return values;
        }
        public static ContentValues insert_volunteer(Volunteer volunteer){
            ContentValues values=new ContentValues();
            values.put(VOLUNTEER_ROLE,volunteer.role());
            values.put(VOLUNTEER_TYPE,volunteer.type());
            values.put(VOLUNTEER_DESCRIPTION,volunteer.description());
            values.put(VOLUNTEER_ORG,volunteer.organisation());
            values.put(VOLUNTEER_FROM,volunteer.from());
            values.put(VOLUNTEER_UPTO,volunteer.upto());
            values.put(VOLUN_API_CALL_DATE_TIME,volunteer.date());
            values.put(VOLUNTEER_LOCAL_CREATE_FLAG,volunteer.createflag());
            values.put(VOLUNTEER_LOCAL_UPDATE_FLAG,volunteer.updateflag());
            values.put(VOLUNTEER_REMOTE_POST_FLAG,volunteer.postflag());
            values.put(VOLUNTEER_REMOTE_PUT_FLAG,volunteer.putflag());
            return values;
        }
        public static ContentValues set_volunteer_status(Volunteer volunteer){
            ContentValues values=new ContentValues();
            values.put(VOLUNTEER_MONGOID,volunteer.mongoid());
            values.put(VOLUNTEER_REMOTE_POST_FLAG,volunteer.postflag());
            values.put(VOLUNTEER_REMOTE_PUT_FLAG,volunteer.putflag());
            return values;
        }

          public static ContentValues insert_profile(SaveProfile profile){
              ContentValues values=new ContentValues();
              if (profile.type() != null) values.put(TYPE,profile.type());
              if (profile.meta() != null) values.put(META,profile.meta());
              if (profile.skill() != null) values.put(SKILL,profile.skill());
              if (profile.lan() != null) values.put(Language,profile.lan());

              return values;
          }
        public static ContentValues insert_profile_from_api(SaveProfile getProfile){
            ContentValues values=new ContentValues();
            values.put(TYPE,getProfile.type());
            values.put(SKILL,getProfile.skill());
            values.put(META,getProfile.meta());
            values.put(Language,getProfile.lan());
            return values;
        }

        public static ContentValues insert_Meta(SaveProfile profile){
            ContentValues values=new ContentValues();
            if (profile.meta() != null) values.put(META, profile.meta());
            return values;
        }
        public static ContentValues insert_Skill(SaveProfile profile){
            ContentValues values=new ContentValues();
            if (profile.skill() != null) values.put(SKILL, profile.skill());
            return values;
        }
        public static ContentValues insert_Language(SaveProfile profile){
            ContentValues values=new ContentValues();
            if (profile.lan() != null) values.put(Language, profile.lan());
            return values;
        }

        public static Award parseAward(Cursor cursor) {
            return Award.builder()
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_TITLE)))
                    .setOrganisation(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_ORGANISATION)))
                    .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_DESCRIPTION)))
                    .setDuration(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_DATE)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_API_CALL_DATE_TIME)))
                    .build();
        }
        public static LiveSync parseLiveSync(Cursor cursor){
            return LiveSync.builder()
                    .setPost(cursor.getString(cursor.getColumnIndexOrThrow(POST_BIT_FLAG))).build();
        }
        public static Education parseEducation(Cursor cursor) {

            return Education.builder()
                    .setSchool(cursor.getString(cursor.getColumnIndexOrThrow(SCHOOLNAME)))
                    .setCourse(cursor.getString(cursor.getColumnIndexOrThrow(COURSETYPE)))
                    .setLocation(new Location(cursor.getString(cursor.getColumnIndexOrThrow(LOCATIONNAME)),cursor.getString(cursor.getColumnIndexOrThrow(LOCATIONTYPE))))
                    .setFrom(cursor.getString(cursor.getColumnIndexOrThrow(DEGREEFROM)))
                    .setUpto(cursor.getString(cursor.getColumnIndexOrThrow(DEGREEUPTO)))
                    .setSchooltype(cursor.getString(cursor.getColumnIndexOrThrow(SCHOOLTYPE)))
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(EDUCATIONTITLE)))
                    .setEdutype(cursor.getString(cursor.getColumnIndexOrThrow(EDUCATIONTYPE)))
                    .setCgpi(cursor.getString(cursor.getColumnIndexOrThrow(CGPI)))
                    .setCgpitype(cursor.getString(cursor.getColumnIndexOrThrow(CGPITYPE)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(EDUCATION_COLUMN_ID)))
                    .setMongoid(cursor.getString(cursor.getColumnIndexOrThrow(EDUCATION_MONGOID)))
                    .build();
        }
        public static Experience parseExperience(Cursor cursor) {
            return Experience.builder()
                    .setFrom(cursor.getString(cursor.getColumnIndexOrThrow(EXPFROM)))
                    .setUpto(cursor.getString(cursor.getColumnIndexOrThrow(EXPUPTO)))
                    .setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COMPANYNAME)))
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COMPANYTITLE)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(JOBTYPE)))
                    .setJob(cursor.getString(cursor.getColumnIndexOrThrow(JOB)))
                    .setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COMPANYNAME)))
                    .setLocation(new Location(cursor.getString(cursor.getColumnIndexOrThrow(JOBLOCATION)),cursor.getString(cursor.getColumnIndexOrThrow(JOBLOCATION_TYPE))))
                    .setStatus(cursor.getString(cursor.getColumnIndexOrThrow(EXPSTATUS)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(WORKEXK_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(EXP_API_CALL_DATE_TIME)))
                    .build();
        }
        public static Project parseProject(Cursor cursor) {
            return Project.builder()
                    .setProject(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTNAME)))
                    .setRole(cursor.getString(cursor.getColumnIndexOrThrow(ROLE)))
                    .setMeta(cursor.getString(cursor.getColumnIndexOrThrow(RESPONSIBILITY)))
                    .setFrom(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTFROM)))
                    .setUpto(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTUPTO)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTS_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(PRO_API_CALL_DATE_TIME)))
                    .build();
        }
        public static Certificate parseCertificate(Cursor cursor) {
            return Certificate.builder()
                    .setName(cursor.getString(cursor.getColumnIndexOrThrow(CERTIFICATENAME)))
                    .setAuthority(cursor.getString(cursor.getColumnIndexOrThrow(AUTHORITY)))
                    .setCertdate(cursor.getString(cursor.getColumnIndexOrThrow(CERTIFICATEDATE)))
                    .setRank(cursor.getString(cursor.getColumnIndexOrThrow(RANK)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(CERTIFICATETYPE)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(CERTIFICATE_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(CERT_API_CALL_DATE_TIME)))
                    .build();
        }
        public static Contact parseContact(Cursor cursor) {
            return Contact.builder()
                    .setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY)))
                    .setContact(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(CONTACTTYPE)))
                    .setStatus(cursor.getString(cursor.getColumnIndexOrThrow(CONTACTSTATUS)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_API_CALL_DATE_TIME)))
                    .build();
        }
        public static Publication parsePublication(Cursor cursor) {
            return Publication.builder()
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(PUBLICATION_TITLE)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(PUBLICATION_COLUMN_ID)))
                    .setUrl(cursor.getString(cursor.getColumnIndexOrThrow(PUB_URL)))
                    .setPublishers(cursor.getString(cursor.getColumnIndexOrThrow(PUBLISHER)))
                    .setAuthors(cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(PUBLICATION_DATE)))
                    .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(PUBLICATION_DESCRIPTION)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(PUB_API_CALL_DATE_TIME)))
                    .build();
        }
        public static Volunteer parseVolunteer(Cursor cursor) {
            return Volunteer.builder()
                    .setOrganisation(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_ORG)))
                    .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_DESCRIPTION)))
                    .setRole(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_ROLE)))
                    .setFrom(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_FROM)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_TYPE)))
                    .setUpto(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_UPTO)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(VOLUNTEER_COLUMN_ID)))
                    .setDate(cursor.getString(cursor.getColumnIndexOrThrow(VOLUN_API_CALL_DATE_TIME)))
                    .build();
        }
        public static SaveProfile parseSaveProfile(Cursor cursor){
            return SaveProfile.builder()
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)))
                    .setskill(cursor.getString(cursor.getColumnIndexOrThrow(SKILL)))
                    .setLan(cursor.getString(cursor.getColumnIndexOrThrow(Language)))
                    .setMeta(cursor.getString(cursor.getColumnIndexOrThrow(META)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(SAVE_PROFILE_BIT_COLUMN_ID)))
                    .build();
        }

    }
}
