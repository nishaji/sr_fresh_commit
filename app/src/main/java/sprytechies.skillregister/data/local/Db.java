package sprytechies.skillregister.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.Contact;
import sprytechies.skillregister.data.model.EduMeta;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.LiveSync;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.data.model.Meta;
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.model.SaveProfile;
import sprytechies.skillregister.data.model.SignUp;
import sprytechies.skillregister.data.model.Volunteer;


public class Db {

    public Db() { }

    public abstract static class RibotProfileTable {


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
        public static final String EDUCATION_DESCRIPTION="education_description";

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
        public static final String ACHIEVEMENTS= "achievements";
        public static final String PROJECTFROM = "projfrom";
        public static final String PROJECTUPTO = "projupto";
        public static final String PROJECT_DESCRIPTION="project_description";

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
        public static final String EXP_DESCRIPTION="exp_description";

        public static final String TBCONTACT = "tbcontact";
        public static final String CONTACT_COLUMN_ID = "_id";
        public static final String CONTACT_MONGOID = "mongoid";
        public static final String CONTACT = "contact";
        public static final String CATEGORY = "category";
        public static final String CONTACTSTATUS = "contactstatus";
        public static final String CONTACTTYPE = "contacttype";

        public static final String TBCERTIFICATE = "tbcertificate";
        public static final String CERTIFICATE_COLUMN_ID = "_id";
        public static final String CERTIFICATE_MONGOID = "mongoid";
        public static final String CERTIFICATENAME = "certificatename";
        public static final String CERTIFICATETYPE = "certificatetype";
        public static final String CERTIFICATEDATE = "certificatedate";
        public static final String AUTHORITY = "authority";
        public static final String RANK = "rank";

        public static final String VOLUNTEER = "volunteer";
        public static final String VOLUNTEER_COLUMN_ID = "_id";
        public static final String VOLUNTEER_MONGOID = "mongoid";
        public static final String VOLUNTEER_ROLE = "role";
        public static final String VOLUNTEER_ORG = "organisation";
        public static final String VOLUNTEER_FROM = "volun_from";
        public static final String VOLUNTEER_UPTO = "upto";
        public static final String VOLUNTEER_TYPE = "type";
        public static final String VOLUNTEER_DESCRIPTION = "description";

        public static final String AWARDS = "awards";
        public static final String AWARD_COLUMN_ID = "_id";
        public static final String AWARD_MONGOID = "mongoid";
        public static final String AWARD_TITLE = "title";
        public static final String AWARD_ORGANISATION = "organisation";
        public static final String AWARD_DATE = "date";
        public static final String AWARD_DESCRIPTION = "description";

        public static final String PUBLICATION = "publication";
        public static final String PUBLICATION_COLUMN_ID = "_id";
        public static final String PUBLICATION_MONGOID = "mongoid";
        public static final String PUBLICATION_TITLE = "title";
        public static final String PUBLISHER = "publisher";
        public static final String AUTHOR = "author";
        public static final String PUB_URL = "url";
        public static final String PUBLICATION_DATE = "date";
        public static final String PUBLICATION_DESCRIPTION = "description";

        public static final String SAVE_PROFILE_BIT="save_profile_bit";
        public static final String SAVE_PROFILE_BIT_COLUMN_ID="_id";
        public static final String TYPE="type";
        public static final String META="meta";
        public static final String SKILL="skills";
        public static  final String Language="language";

        public static final String LIVE_SYNC_STATUS="live_sync";
        public static final String LIVE_SYNC_STATUS_COLUMN_ID="_id";
        public static final String BIT_TYPE="bit_type";
        public static final String BIT_BEFORE_DATA="bit_before_data";
        public static final String BIT_AFTER_DATA="bit_after_data";
        public static final String SYNC_STATUS="sync_status";
        public static final String BIT_Id="id";
        public static final String BIT_MONGO_ID="mongo_id";




        public static final String CREATE_LIVE_SYNC_STATUS =
                "CREATE TABLE " + LIVE_SYNC_STATUS + " (" +
                        LIVE_SYNC_STATUS_COLUMN_ID + " integer primary key autoincrement, " +
                        BIT_TYPE + " text , " +
                        BIT_BEFORE_DATA + " integer , " +
                        BIT_AFTER_DATA + " text , " +
                        BIT_Id + " integer , " +
                        BIT_MONGO_ID + " text , " +
                        SYNC_STATUS + " text ) ";


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
                + EDUCATION_DESCRIPTION + "  text ,"
                + DEGREEFROM + " text ,"
                + DEGREEUPTO + " text )";


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
                + EXP_DESCRIPTION + "  text ,"
                + EXPFROM + " text ,"
                + EXPUPTO + " text )";

        public static final String CREATE_PROJECT = "create table " + PROJECTS + " ( "
                + PROJECTS_COLUMN_ID + " integer primary key autoincrement , "
                + PROJECT_MONGOID + " text ,"
                + PROJECTNAME + " text ,"
                + ROLE + " text  , "
                + RESPONSIBILITY + "  text  , "
                + ACHIEVEMENTS + "  text  , "
                + PROJECTFROM + " text ,"
                + PROJECTUPTO + "  text ,"
                + PROJECT_DESCRIPTION + "  text )";

        public static final String CREATE_CONTACT = " create table " + TBCONTACT + " ( "
                + CONTACT_COLUMN_ID + " integer primary key autoincrement ,"
                + CONTACT_MONGOID + " text ,"
                + CONTACT + " text , "
                + CATEGORY + " text , "
                + CONTACTSTATUS + " text ,"
                + CONTACTTYPE + " text )";

        public static final String CREATE_CERTIFICATE = "create table " + TBCERTIFICATE + " ( "
                + CERTIFICATE_COLUMN_ID + " integer primary key autoincrement , "
                + CERTIFICATE_MONGOID + " text ,"
                + CERTIFICATENAME + " text ,"
                + CERTIFICATETYPE + "  text  , "
                + CERTIFICATEDATE + " text ,"
                + AUTHORITY + "  text ,"
                + RANK + " text )";


        public static final String CREATE_VOLUNTEER = "create table " + VOLUNTEER + "("
                + VOLUNTEER_COLUMN_ID + " integer primary key autoincrement ,"
                + VOLUNTEER_MONGOID + " text ,"
                + VOLUNTEER_ROLE + " text ,"
                + VOLUNTEER_TYPE + " text ,"
                + VOLUNTEER_ORG + " text ,"
                + VOLUNTEER_FROM + " text ,"
                + VOLUNTEER_UPTO + " text ,"
                + VOLUNTEER_DESCRIPTION + " text )";

        public static final String CREATE_AWARDS = "create table " + AWARDS + "("
                + AWARD_COLUMN_ID + " integer primary key autoincrement ,"
                + AWARD_MONGOID + " text ,"
                + AWARD_TITLE + " text ,"
                + AWARD_ORGANISATION + " text ,"
                + AWARD_DATE + " text ,"
                + AWARD_DESCRIPTION + " text )";

        public static final String CREATE_PUBLICATION = "create table " + PUBLICATION + "("
                + PUBLICATION_COLUMN_ID + " integer primary key autoincrement ,"
                + PUBLICATION_MONGOID + " text ,"
                + PUBLICATION_TITLE + " text ,"
                + PUBLISHER + " text ,"
                + AUTHOR + " text ,"
                + PUB_URL + " text ,"
                + PUBLICATION_DATE + " text ,"
                + PUBLICATION_DESCRIPTION + " text )";


        public static final String CREATE_SAVE_PROFILE = " create table " + SAVE_PROFILE_BIT + " ( "
                + SAVE_PROFILE_BIT_COLUMN_ID + " integer primary key autoincrement , "
                + TYPE + " text  , "
                + META + "  text  , "
                + SKILL + " text ,"
                + Language + "  text )";

        public static ContentValues insert_sync_status(LiveSync liveSync) {
            ContentValues values = new ContentValues();
            if (liveSync.bit() != null) values.put(BIT_TYPE, liveSync.bit());
            if (liveSync.bitbefore() != null) values.put(BIT_BEFORE_DATA, liveSync.bitbefore());
            if (liveSync.bitafter() != null) values.put(BIT_AFTER_DATA, liveSync.bitafter());
            if (liveSync.syncstatus() != null) values.put(SYNC_STATUS, liveSync.syncstatus());
            if (liveSync.bitid() != null) values.put(BIT_Id, liveSync.bitid());
            if (liveSync.bitmongoid() != null) values.put(BIT_MONGO_ID, liveSync.bitmongoid());
            return values;
        }
        public static ContentValues insert_awards(Award award) {
            ContentValues values = new ContentValues();
            values.put(AWARD_TITLE,award.title() );
            values.put(AWARD_ORGANISATION,award.organisation() );
            values.put(AWARD_DESCRIPTION,award.description() );
            values.put(AWARD_DATE,award.duration());
            values.put(AWARD_MONGOID,award.mongoid());
            return values;
        }
        public static ContentValues set_award_status(Award award){
            ContentValues values=new ContentValues();
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
            values.put(EDUCATION_DESCRIPTION,education.meta().getDesc());
            values.put(EDUCATION_MONGOID,education.mongoid());
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
            values.put(JOBLOCATION, experience.location().getName());
            values.put(JOBLOCATION_TYPE, experience.location().getType());
            values.put(EXP_MONGOID,experience.mongoid());
            return values;
        }

        public static ContentValues insert_project(Project project){
            ContentValues values=new ContentValues();
            values.put(PROJECTNAME, project.project());
            values.put(ROLE, project.role());
            values.put(RESPONSIBILITY, project.meta().getResponsibility());
            values.put(PROJECT_DESCRIPTION, project.meta().getDesc());
            values.put(ACHIEVEMENTS, project.meta().getResponsibility());
            values.put(PROJECTFROM, project.from());
            values.put(PROJECTUPTO, project.upto());
            values.put(PROJECT_MONGOID,project.mongoid());
            return values;
        }

        public static ContentValues insert_contact(Contact contact){
            ContentValues values=new ContentValues();
            values.put(CONTACT,contact.contact());
            values.put(CATEGORY,contact.category());
            values.put(CONTACTSTATUS,contact.status());
            values.put(CONTACTTYPE,contact.type());
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
                    .setMongoid(cursor.getString(cursor.getColumnIndexOrThrow(AWARD_MONGOID)))
                    .build();
        }
        public static LiveSync parseLiveSync(Cursor cursor){
            return LiveSync.builder()
                    .setBitbefore(cursor.getString(cursor.getColumnIndexOrThrow(BIT_BEFORE_DATA)))
                    .setBitbefore(cursor.getString(cursor.getColumnIndexOrThrow(BIT_BEFORE_DATA)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(LIVE_SYNC_STATUS_COLUMN_ID)))
                    .setBitmongoid(cursor.getString(cursor.getColumnIndexOrThrow(BIT_MONGO_ID)))
                    .setBit(cursor.getString(cursor.getColumnIndexOrThrow(BIT_TYPE)))
                    .setBitid(cursor.getString(cursor.getColumnIndexOrThrow(BIT_Id)))
                    .build();
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
                    .setMeta(new EduMeta(cursor.getString(cursor.getColumnIndexOrThrow(EDUCATION_DESCRIPTION))))
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
                    .build();
        }
        public static Project parseProject(Cursor cursor) {
            return Project.builder()
                    .setProject(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTNAME)))
                    .setRole(cursor.getString(cursor.getColumnIndexOrThrow(ROLE)))
                    .setMeta(new Meta(cursor.getString(cursor.getColumnIndexOrThrow(RESPONSIBILITY)),cursor.getString(cursor.getColumnIndexOrThrow(ACHIEVEMENTS)),cursor.getString(cursor.getColumnIndexOrThrow(PROJECT_DESCRIPTION))))
                    .setFrom(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTFROM)))
                    .setUpto(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTUPTO)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(PROJECTS_COLUMN_ID)))
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
                    .build();
        }
        public static Contact parseContact(Cursor cursor) {
            return Contact.builder()
                    .setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY)))
                    .setContact(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(CONTACTTYPE)))
                    .setStatus(cursor.getString(cursor.getColumnIndexOrThrow(CONTACTSTATUS)))
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_COLUMN_ID)))
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
