package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sggs.sggs.adapters.AttendanceCalendarAdapter;
import com.sggs.sggs.model.CalendarModel;
import com.sggs.sggs.model.SubjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AttendanceCalender extends AppCompatActivity {


    RecyclerView recyclerView;
    String sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_calendar);

        recyclerView = findViewById(R.id.recyclerView);
        sub = getIntent().getStringExtra("sub");
        loadSubjectAttendance();

    }

    @SuppressLint("StaticFieldLeak")
    private void loadSubjectAttendance() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("text/plain");
                    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("reg_no", Home.id)
                            .addFormDataPart("subject", sub)
                            .build();
                    Request request = new Request.Builder()
                            .url(getString(R.string.api_link)+"single_subject_datewise_attendance.php")
                            .method("POST", body)
                            .build();
                    Response response = client.newCall(request).execute();
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CalendarModel>>() {}.getType();
                    List<CalendarModel> attendanceList = gson.fromJson(result, listType);

                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    AttendanceCalendarAdapter adapter = new AttendanceCalendarAdapter(attendanceList, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } else {

                }
            }
        }.execute();



    }

}




