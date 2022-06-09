package com.ocean.asynctaskappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.ocean.asynctaskappdemo.databinding.ActivityMainBinding;

import java.nio.channels.AsynchronousChannelGroup;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String response;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: here view binding is implemented
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRun.setOnClickListener(view -> {

            //TODO: creating a class that extends async task
            AsyncTaskRunner runner = new AsyncTaskRunner();
            //TODO: creating param to assign to async task runner class object
            String sleepTime = binding.inTime.getText().toString();

            runner.execute(sleepTime);//assigning to async task runner class object the param we create

        });


    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(MainActivity.this, "ProgressDialog", "Wait for .. ."
                                                    + binding.inTime.getText().toString() + " seconds..");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            //TODO: publishProgress method
            publishProgress("Sleeping ... .. .");

            //TODO: calls onProgressUpdate method
            try {
                int time = Integer.parseInt(strings[0]) * 1000;
                Thread.sleep(time);
                response = "Slept for ..." + strings[0] + " seconds .." ;

            }catch (InterruptedException e){
                e.printStackTrace();
                response = e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                response = e.getMessage();
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            binding.tvResult.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            binding.tvResult.setText(result);

            super.onPostExecute(result);
        }
    }
}