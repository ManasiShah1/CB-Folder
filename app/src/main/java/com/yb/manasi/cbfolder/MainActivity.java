package com.yb.manasi.cbfolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity
    implements AgendaListAdapter.OnAgendaItemSelectedListener {

    //private static final String TAG = MainActivity.class.getSimpleName();

    //private TextView fileContent;

    private static final String PATH_TO_YBINFO = "https://docs.google.com/spreadsheets/d/1z-j7NLLMGYf5UtXzPKMcQZvjyJIYx5ecjbibH6CbMpA/export?usp=sharing&format=csv";
    private static final String PATH_TO_CALENDAR = "https://www.googleapis.com/calendar/v3/calendars/ois700e8oasjr3i81s3qgd1dkk%40group.calendar.google.com/events?orderBy=startTime&singleEvents=true&fields=description%2Citems(creator%2Cdescription%2Cend%2Cstart%2Clocation%2Cstatus%2Csummary)%2Ckind%2Csummary&key=AIzaSyBSE-tK9lHhmUSHLWHT3A6hZwmROfKKv9c";
    private static final String PATH_TO_CALENDAR_TODAY = "&timeMin=2018-08-06T00:00:00-07:00";
    // private static final String PATH_TO_CALENDAR_TODAY = String.format("&timeMin=%sT00:00:00-07:00", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    private URL urlYbInfo;
    private URL urlCalendar;

    private static final String FILE_NAME_YB_INFO = "ybInfo.csv";
    private static final String FILE_NAME_YB_EVENTS = "ybEvents.json";

//    private Map<String, String> mapYbInfo;
    private ArrayList<YbInfo> arrYbInfo;
    private ArrayList<YbEvent> arrYbEvents; // TODO: set from calendar object
    private Calendar schedule;

    private AgendaListAdapter m_alaAdapter;
    private ContactListAdapter m_claAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO: display camp schedule - list view, create tabs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayouts();
        initialize();
        download();

    }

    private Intent m_intentAgendaDetail;
    private Intent m_intentContactDetail;

    public ArrayList<YbInfo> getArrYbInfo() {
        return arrYbInfo;
    }

    public ArrayList<YbEvent> getArrYbEvents() {
        return arrYbEvents;
    }

    public void setArrYbInfo(ArrayList<YbInfo> arrYbInfo) {
        this.arrYbInfo = arrYbInfo;
    }

    public void setArrYbEvents(ArrayList<YbEvent> arrYbEvents) {
        this.arrYbEvents = arrYbEvents;
    }

    public void setAlaAdapter(AgendaListAdapter alaAdapter) {
        this.m_alaAdapter = alaAdapter;
    }

    public void setClaAdapter (ContactListAdapter claAdapter) {
        this.m_claAdapter = claAdapter;
    }

    private TabLayout m_tabLayout;
    private ViewPager m_vp;

    protected void initLayouts() {
        // set up Tab Layout
        m_tabLayout = findViewById(R.id.id_tabs);
        m_tabLayout.addTab(m_tabLayout.newTab().setText(R.string.agenda_view));
        m_tabLayout.addTab(m_tabLayout.newTab().setText("Contact"));
        m_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        m_vp = (ViewPager) findViewById(R.id.id_viewpager);
        final YBPagerAdapter pa = new YBPagerAdapter(this, getSupportFragmentManager(), m_tabLayout.getTabCount());
        m_vp.setAdapter(pa);
        m_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(m_tabLayout));
        m_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                m_vp.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    protected void initialize() {
        try {
            this.urlYbInfo = new URL(MainActivity.PATH_TO_YBINFO);
            String  urlCalendar = MainActivity.PATH_TO_CALENDAR + MainActivity.PATH_TO_CALENDAR_TODAY;
            this.urlCalendar = new URL(urlCalendar);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void download() {
        DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
        downloadFilesTask.m_activity = this;
        downloadFilesTask.execute(urlCalendar, urlYbInfo);
    }

    // Called by DownloadFilesTask when download is all completed.
    public void downloadsComplete() {
        try {
            readJsonStream(FILE_NAME_YB_EVENTS);
            csvToArrayList(FILE_NAME_YB_INFO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: Load the various views with the data from here
        if (m_alaAdapter != null) {
            m_alaAdapter.setArrYbEvents(this.arrYbEvents);
        }
        if (m_claAdapter != null) {
            m_claAdapter.setArrYbInfo(this.arrYbInfo);
        }

    }

    private void csvToArrayList(String fileName) {
        try {
            // TODO: Implement simple CSV Parser
            // Each line has FirstName, LastName, Email, Cell_Number, Group, Partner
            // While (readline) - use InputStreamReader to read line at a time
            // Make YBInfo class
            // split on ',' and move the items into appropriate fields of the YBInfo class
            // Add the YbInfo(s) to ArrayList, change Map<String, String> to ArrayList<YbInfo>

            String[] current;
            String first;
            String last;
            String email;
            String cell;
            String group;
            String partner;
            this.arrYbInfo = new ArrayList<YbInfo>();

            this.arrYbInfo.add(new YbInfo("dummy", "dummy", "dummy", "dummy", "dummy", "dummy"));

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)), 256);
            String line;
            line = buffReader.readLine();
            // TODO: Throw an exception if the Header Line is not what we expect
            while ((line = buffReader.readLine()) != null) {
                current = line.split(",");
                first = current[0];
                last = current[1];
                email = current[2];
                cell = current[3];
                group = current[4];
                partner = current[5];

                this.arrYbInfo.add(new YbInfo(first, last, email, cell, group, partner));
                line = "";
                // TODO figure out why Karun is read an extra time
            }
//            CSVReaderHeaderAware csvreader = new CSVReaderHeaderAware(new InputStreamReader(openFileInput(fileName))); // change CSVReader to BufferedReader - reader.readLine();
//            this.mapYbInfo = csvreader.readMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJsonStream(String fileName) throws IOException { //TODO reads from correct place?

        JsonReader reader = new JsonReader(new InputStreamReader(openFileInput(fileName))); // TODo file not found?
        try {
            readCalendar(reader);
        } finally {
            reader.close();
        }
    }

    private void readCalendar(JsonReader reader) {
        String summary = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("summary")) {
                    summary = reader.nextString();
                } else if (name.equals("items")) {
                    // events.add(readEvent(reader));
                    this.arrYbEvents = readEventsArray(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.schedule = new Calendar(summary, arrYbEvents);
    }

    private ArrayList<YbEvent> readEventsArray(JsonReader reader) {
        ArrayList<YbEvent> events = new ArrayList<>();
        events.add(new YbEvent("dummy", "dummy", LocalDateTime.now(), LocalDateTime.now(), "dummy", "dummy")); // empty event under tabs

        try {
            reader.beginArray();
            while (reader.hasNext()) {
                events.add(readEvent(reader));
            }
            reader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    private YbEvent readEvent(JsonReader reader) throws IOException {
        String status = null;
        String summary = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        String description = null;
        String location = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("status")) {
                status = reader.nextString();
            } else if (name.equals("summary")) {
                summary = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("start")) {
                start = readTime(reader);
            } else if (name.equals("end")) {
                end = readTime(reader);
            } else if (name.equals("location")) {
                location = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new YbEvent(status, summary, start, end, description, location);
    }

    private LocalDateTime readTime(JsonReader reader) {
        LocalDateTime dateTime = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("dateTime")) {
                    String strDt = reader.nextString();
                    dateTime = LocalDateTime.parse(strDt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime;
    }

    public static final String YBEVENT_KEY = "YbEvent";
    @Override
    public void onAgendaItemSelected(YbEvent ybEvent) {
        if ( m_intentAgendaDetail == null ) {
            m_intentAgendaDetail = new Intent(this, AgendaDetail.class);
        }

        m_intentAgendaDetail.putExtra(MainActivity.YBEVENT_KEY, ybEvent);
        startActivity(m_intentAgendaDetail);
    }

    // Implementation for AgendaView.OnContactItemSelectedListener()
    public void onContactItemSelected (Uri uri) {
        if ( m_intentContactDetail == null ) {
            m_intentContactDetail = new Intent(this, AgendaDetail.class);
        }
        startActivity(m_intentContactDetail);
    }


    private class DownloadFilesTask extends AsyncTask<URL, Void, Void> {

        private MainActivity m_activity;

        protected Void doInBackground(URL... urls) {
            downloadFile(urls[0], FILE_NAME_YB_EVENTS);
            downloadFile(urls[1], FILE_NAME_YB_INFO);
            return null;
        }
        protected void onPostExecute(Void foo) {
            // TODO: use cached files if download fails
            m_activity.downloadsComplete();
        }
        private void downloadFile(URL url, String fileName) { // save files to device
            InputStream in = null;
            FileOutputStream out = null;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(conn.getInputStream());
                out = openFileOutput(fileName, Context.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int cb;
                while((cb=in.read(buf)) != -1){
                    out.write(buf, 0, cb);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if(conn != null) conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
