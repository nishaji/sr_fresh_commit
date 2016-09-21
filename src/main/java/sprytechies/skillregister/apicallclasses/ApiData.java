package sprytechies.skillregister.apicallclasses;

import android.content.Context;
import android.content.SharedPreferences;




import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sprytechies.skillregister.database.DatabaseHelper;

/**
 * Created by pradeep on 1/11/15.
 */


public class ApiData implements AsyncResponse {


    public static String edumongo, expmongo, projmongo, certmongo, contactmongo;
    private Context context;
    private String TAG = "ApiData";
    String acesstoken, accesid;
    DatabaseHelper databaseHelper;

    public ApiData(Context context) {
        this.context = context;
    }


    public void postuser(String f_anme, String l_name, String email, String password) {
        //getCars for User
        JSONObject params = new JSONObject();
        try {
            params.put("fname", f_anme);
            params.put("lname", l_name);
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        NetworkTask networkTask = new NetworkTask("/people/signup", params);
        networkTask.delegate = this;
        networkTask.execute();
    }

    public void getsignin(String email, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask("/people/signin", params);
        networkTask.delegate = this;
        networkTask.execute();
    }

    public void postedudata(String s) {
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefs.getString("acesstocken", "acesstoken");
            accesid = prefs.getString("id", "accesid");
            System.out.println(TAG + " " + accesid + " " + acesstoken + "accesstokenandid");
            NetworkTask networkTask = new NetworkTask("/" + "people/" + accesid + "/education?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postexpdata(String s) {
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefs.getString("acesstocken", "acesstoken");
            accesid = prefs.getString("id", "accesid");
            System.out.println(TAG + " " + accesid + " " + acesstoken + "accesstokenandid");
            NetworkTask networkTask = new NetworkTask("/" + "people/" + accesid + "/experience?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postcertificatedata(String s) {
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefs.getString("acesstocken", "acesstoken");
            accesid = prefs.getString("id", "accesid");
            System.out.println(TAG + " " + accesid + " " + acesstoken + "accesstokenandid");
            NetworkTask networkTask = new NetworkTask("/" + "people/" + accesid + "/certificate?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postcontactdata(String s) {
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefs.getString("acesstocken", "acesstoken");
            accesid = prefs.getString("id", "accesid");
            System.out.println(TAG + " " + accesid + " " + acesstoken + "accesstokenandid");
            NetworkTask networkTask = new NetworkTask("/" + "people/" + accesid + "/setContacts?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postprojdata(String s) {
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefs.getString("acesstocken", "acesstoken");
            accesid = prefs.getString("id", "accesid");
            System.out.println(TAG + " " + accesid + " " + acesstoken + "accesstokenandid");
            NetworkTask networkTask = new NetworkTask("/" + "people/" + accesid + "/project?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postlinkdata(String provider, String authscheme, String externalid, String jsondata) {
        JSONObject params = new JSONObject();
        try {
            params.put("provider", provider);
            params.put("authScheme", authscheme);
            params.put("externalId", externalid);
            params.put("jsondata", jsondata);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
        acesstoken = prefs.getString("acesstocken", "acesstoken");
        NetworkTask networkTask = new NetworkTask("/people/linkFromApp?access_token=" + acesstoken, params);
        networkTask.delegate = this;
        networkTask.execute();
    }


    public void updateedudata(String edumongo, String s) {
        getedumongoid(edumongo);

        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefss = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefss.getString("acesstocken", "acesstoken");
            accesid = prefss.getString("id", "accesid");
            System.out.println(TAG + " " + edumongo + " " + "monogoidinapidata");
            PutNetworkTask networkTask = new PutNetworkTask("/" + "people/" + accesid + "/education/" + edumongo + "?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void updateexpdata(String expmongo, String s) {
        getexpmongoid(expmongo);
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefss = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefss.getString("acesstocken", "acesstoken");
            accesid = prefss.getString("id", "accesid");
            System.out.println(TAG + " " + expmongo + " " + "monogoidinapidata");
            PutNetworkTask networkTask = new PutNetworkTask("/" + "people/" + accesid + "/experience/" + expmongo + "?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateprojdata(String projmongo, String s) {
        getprojmongoid(projmongo);
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefss = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefss.getString("acesstocken", "acesstoken");
            accesid = prefss.getString("id", "accesid");
            System.out.println(TAG + " " + edumongo + " " + "monogoidinapidata");
            PutNetworkTask networkTask = new PutNetworkTask("/" + "people/" + accesid + "/project/" + projmongo + "?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updatecertdata(String certmongo, String s) {
        getcertmongoid(certmongo);
        try {
            JSONObject params = new JSONObject(s);
            SharedPreferences prefss = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
            acesstoken = prefss.getString("acesstocken", "acesstoken");
            accesid = prefss.getString("id", "accesid");
            System.out.println(TAG + " " + edumongo + " " + "monogoidinapidata");
            PutNetworkTask networkTask = new PutNetworkTask("/" + "people/" + accesid + "/certificate/" + certmongo + "?access_token=" + acesstoken, params);
            networkTask.delegate = this;
            networkTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getedumongoid(String edu) {
        edumongo = edu;
        System.out.println(" sdsgfsgdffffffffffffff   " + edumongo);
    }

    public void getexpmongoid(String exp) {
        expmongo = exp;
    }

    public void getprojmongoid(String proj) {
        projmongo = proj;
    }

    public void getcertmongoid(String cert) {
        certmongo = cert;
    }

    public void getcontactmongoid(String con) {
        contactmongo = con;
    }

    @Override
    public void ProcessResponse(String path, String result) {
        if (path.equals("/people/signup")) {
            try {
                JSONObject signupjson = new JSONObject(result);
                System.out.println(signupjson + "signup");
                System.out.println(path + "signuppath");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/people/signin")) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                acesstoken = jsonObject.getString("id");
                JSONObject js = jsonObject.getJSONObject("profile");
                accesid = js.getString("id");
                System.out.println(jsonObject + "signin" + acesstoken + "gggggggg" + accesid);
                SharedPreferences sharedPreferences = context.getSharedPreferences("accesstocken", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("acesstocken", acesstoken);
                editor.putString("id", accesid);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/education?access_token=" + acesstoken)) {
            System.out.println("hhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiii");
            String edubit = "edu_bit";

            try {
                JSONObject jsonObject = new JSONObject(result);
                String edumongoid = jsonObject.getString("id");
                ArrayList<HashMap<String, String>> edumongo = databaseHelper.getallid();
                System.out.println("edumongonisha" + " " + edumongo);
                for (int i = 0; i < edumongo.size(); i++) {
                    System.out.println(edumongo.size() + "iddddddd");
                    System.out.println(edumongo.get(i).get("bittype").equals(edubit) + "checking condition nisha");
                    if (edumongo.get(i).get("bittype").equals(edubit)) {
                        if (jsonObject.has("id")) {
                            databaseHelper.updateid(edumongo.get(i).get("id"), edumongoid);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/experience?access_token=" + acesstoken)) {
            System.out.println("hhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiii");
            String expbit = "exp_bit";
            try {
                JSONObject jsonObject = new JSONObject(result);
                String edumongoid = jsonObject.getString("id");
                ArrayList<HashMap<String, String>> edumongo = databaseHelper.getallid();
                System.out.println("edumongonisha" + " " + edumongo);
                for (int i = 0; i < edumongo.size(); i++) {
                    System.out.println(edumongo.size() + "iddddddd");
                    System.out.println(edumongo.get(i).get("bittype").equals(expbit) + "checking condition nisha");
                    if (edumongo.get(i).get("bittype").equals(expbit)) {
                        if (jsonObject.has("id")) {
                            databaseHelper.updateid(edumongo.get(i).get("id"), edumongoid);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/certificate?access_token=" + acesstoken)) {
            System.out.println("hhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiii");
            String certbit = "cert_bit";

            try {
                JSONObject jsonObject = new JSONObject(result);
                String edumongoid = jsonObject.getString("id");
                ArrayList<HashMap<String, String>> edumongo = databaseHelper.getallid();
                System.out.println("edumongonisha" + " " + edumongo);
                for (int i = 0; i < edumongo.size(); i++) {
                    System.out.println(edumongo.size() + "iddddddd");
                    System.out.println(edumongo.get(i).get("bittype").equals(certbit) + "checking condition nisha");
                    if (edumongo.get(i).get("bittype").equals(certbit)) {
                        if (jsonObject.has("id")) {
                            databaseHelper.updateid(edumongo.get(i).get("id"), edumongoid);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/project?access_token=" + acesstoken)) {
            System.out.println("hhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiii");
            String projbit = "proj_bit";

            try {
                JSONObject jsonObject = new JSONObject(result);
                String edumongoid = jsonObject.getString("id");
                ArrayList<HashMap<String, String>> edumongo = databaseHelper.getallid();
                System.out.println("edumongonisha" + " " + edumongo);
                for (int i = 0; i < edumongo.size(); i++) {
                    System.out.println(edumongo.size() + "iddddddd");
                    System.out.println(edumongo.get(i).get("bittype").equals(projbit) + "checking condition nisha");
                    if (edumongo.get(i).get("bittype").equals(projbit)) {
                        if (jsonObject.has("id")) {
                            databaseHelper.updateid(edumongo.get(i).get("id"), edumongoid);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/setContacts?access_token=" + acesstoken)) {
            System.out.println("hhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiii");
            String conbit = "contact_bit";

            try {
                JSONObject jsonObject = new JSONObject(result);
                String edumongoid = jsonObject.getString("id");
                ArrayList<HashMap<String, String>> edumongo = databaseHelper.getallid();
                System.out.println("edumongonisha" + " " + edumongo);
                for (int i = 0; i < edumongo.size(); i++) {
                    System.out.println(edumongo.size() + "iddddddd");
                    System.out.println(edumongo.get(i).get("bittype").equals(conbit) + "checking condition nisha");
                    if (edumongo.get(i).get("bittype").equals(conbit)) {
                        if (jsonObject.has("id")) {
                            databaseHelper.updateid(edumongo.get(i).get("id"), edumongoid);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (path.equals("/" + "people/" + accesid + "/education/" + edumongo + "?access_token=" + acesstoken)) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                // System.out.println(jsonObject+"edudaaaaaaaaatttttttttupdate");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
