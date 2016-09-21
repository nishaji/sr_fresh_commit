package sprytechies.skillregister.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 27/7/16.
 */
public class ResumeList extends Fragment {
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.resumelist, container, false);
        list=(ListView)v.findViewById(R.id.list);
        try {

            ArrayList<String> FilesInFolder = GetFiles("/sdcard/Trinity/PDF Files");
            if (FilesInFolder.size() != 0)
                list.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_list_item_1, FilesInFolder));

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    File pdfFile = new File("/sdcard/Trinity/PDF Files/resume.pdf");
                    if(pdfFile.exists())
                    {
                        Uri path = Uri.fromFile(pdfFile);
                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                        pdfIntent.setDataAndType(path, "application/pdf");
                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        try{
                            startActivity(pdfIntent);
                        }catch(ActivityNotFoundException e){
                            System.out.println("No Application available to view PDF");
                        }
                    }
                    else
                    {
                        System.out.println("File not found");
                    }


                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return v;
    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }
}
