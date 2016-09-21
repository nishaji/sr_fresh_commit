package com.example.sprydev5.skillsregister.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pradeep on 1/11/15.
 */
public class Car {
    public String id;
    public String make;
    public String model;
    public String year;
    public String variant;
   // public String price;
    public String mileage;

    public String name;
    public String phone;
    public String city;
    public String state;
    public String street;
    public String pincode;
    public String date;
    public String time;

    public String email;
    public static String hidden="1";

    public Car(){

    }

    public Car(String id, String email, String make, String model, String year, String variant, String mileage){
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.variant = variant;
        this.mileage = mileage;

        this.email = email;
    }

    public JSONObject getCarJsonObj(String id, String make, String model, String year, String variant, String mileage) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.variant = variant;
      //  this.price = price;
        this.mileage = mileage;

        JSONObject carJObj = new JSONObject();
        try {
            carJObj.put("id",id);
            carJObj.put("make_name",make);
            carJObj.put("model_name",model);
            carJObj.put("variant_name",variant);
            carJObj.put("makeyear",year);
            carJObj.put("milage",mileage);
            //carJObj.put("price",price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carJObj;
    }

    public JSONObject getAppointmentJsonObj(String id, String name, String phone, String street, String city, String state, String pincode, String date, String time){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.street=street;
        this.state=state;
        this.city=city;
        this.pincode=pincode;
        this.date=date;
        this.time=time;


        JSONObject appoJObj = new JSONObject();
        try {
            appoJObj.put("id",id);
            appoJObj.put("name",name);
            appoJObj.put("phone",phone);
            appoJObj.put("street",street);
            appoJObj.put("city",city);
            appoJObj.put("state",state);
            appoJObj.put("pcode",pincode);
            appoJObj.put("date",date);
            appoJObj.put("time",time);
            appoJObj.put("hidden",hidden);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appoJObj;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(make).append(", ")
                .append(model).append(", ")
                .append(year).append(", ")
                .append(variant).append(", ")
               //.append(price).append(", ")
                .append(mileage);
        return builder.toString();
    }

    public String address() {
        StringBuilder builder = new StringBuilder();
        builder.append(street).append(", ")
                .append(city).append(", ")
                .append(state).append(" ")
                .append(pincode);
        return builder.toString();

    }

    public void addAppointment(String name, String phone, String street, String city, String state, String pincode, String date, String time) {
        this.name=name;
        this.phone=phone;
        this.street=street;
        this.state=state;
        this.city=city;
        this.pincode=pincode;
        this.date=date;
        this.time=time;
    }
}