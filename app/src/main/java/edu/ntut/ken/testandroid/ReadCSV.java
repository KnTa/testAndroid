package edu.ntut.ken.testandroid;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ReadCSV {
    CSVReader reader;
    List<String[]> myEntries;

    public ReadCSV(String fileName){
        try{
            reader = new CSVReader(new FileReader(fileName));
            myEntries = reader.readAll();
        }catch(IOException e){

        }
    }

    public ReadCSV(Context ctx, int rawID){
        try{
            reader = new CSVReader(new BufferedReader(new InputStreamReader(
                    ctx.getResources().openRawResource(rawID)
            )));

            myEntries = reader.readAll();
        }catch(IOException e){

        }
    }

    public ReadCSV(File file){
        try{
            reader = new CSVReader(new FileReader(file));
            myEntries = reader.readAll();
        }catch(IOException e){

        }
    }

    public List<String[]> getCSVData(){
        return  myEntries;
    }
}


