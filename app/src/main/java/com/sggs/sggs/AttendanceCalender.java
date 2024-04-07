package com.sggs.sggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sggs.sggs.adapters.AttendanceCalendarAdapter;
import com.sggs.sggs.model.CalendarModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AttendanceCalender extends AppCompatActivity {


    String sub;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_calendar);
        calendarView = findViewById(R.id.calendarView);
        sub = getIntent().getStringExtra("sub");
        TextView subject = findViewById(R.id.subject);
        subject.setText(sub);
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
                    setDates(attendanceList);
                } else {

                }
            }
        }.execute();
    }

    private void setDates(List<CalendarModel> attendanceList){
        List<EventDay> events = new ArrayList<>();

        for (CalendarModel model : attendanceList) {
            try {
                Calendar calendar = convertStringToCalendar(model.getDate_time());
                int drawableRes = model.getAttendance_status().equals("P") ? R.drawable.correct_ic : R.drawable.wrong_ic;
                events.add(new EventDay(calendar, drawableRes));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        calendarView.setEvents(events);
    }

    private Calendar convertStringToCalendar(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}




