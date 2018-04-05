package com.kumar.vikas.assignmet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;
    TextView textView;
    JSONObject jsonObject ;
    String[] ar;
    int i=0;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        textView=findViewById(R.id.textView);
        jsonObject = new JSONObject();
        ar=new String[13];
        reference= FirebaseDatabase.getInstance().getReference();//Firebase database reference
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readExcelFile(MainActivity.this,"excel.xls");

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "json " + jsonObject, Toast.LENGTH_SHORT).show();
               // reference.setValue(jsonObject);
                textView.setText(String.valueOf(jsonObject));
                reference.child("list").setValue(String.valueOf(jsonObject));

            }
        });



    }

    private void readExcelFile(Context context, String filename) {


        try{
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /* We now need something to iterate through the cells.*/
            Iterator rowIter = mySheet.rowIterator();

            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.d("TAG", "Cell Value: " +  myCell.toString());
                    //Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();

                    ar[i++]=myCell.toString();
                }
                JSONArray array=new JSONArray();
                for (int k=1;k<13;k++){
                    array.put(ar[k]);
                }

                jsonObject.put(ar[0],array);
                i=0;
            }

        }catch (Exception e){e.printStackTrace(); }

    }



}
