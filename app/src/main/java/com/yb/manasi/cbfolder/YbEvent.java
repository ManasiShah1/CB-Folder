package com.yb.manasi.cbfolder;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class YbEvent implements Parcelable {

    public String m_strStatus;
    public String m_strSummary;
    public LocalDateTime m_StartDateTime;
    public LocalDateTime m_EndDateTime;
    public String m_strDescription;
    public String m_strLocation;

//    public ArrayList<String> m_arrYBs; // TODO only if time, otherwise leave description as notes
//    public ArrayList<String> m_arrHelpers;
//    public String m_strDay;
//    public String m_strStartTime;
//    public String m_strEndTime;

    public YbEvent(String m_strStatus, String m_strSummary, LocalDateTime m_StartDateTime, LocalDateTime m_EndDateTime, String m_strDescription, String m_strLocation) {
        this.m_strStatus = m_strStatus;
        this.m_strSummary = m_strSummary;
        this.m_StartDateTime = m_StartDateTime;
        this.m_EndDateTime = m_EndDateTime;
        this.m_strDescription = m_strDescription;
        this.m_strLocation = m_strLocation;
    }

    public String getStartShort() {
        return m_StartDateTime.format(DateTimeFormatter.ofPattern("EEE, HH:mm"));
    }

    public String getEndShort() {
        return m_EndDateTime.format(DateTimeFormatter.ofPattern("EEE, HH:mm"));
    }

    public String getStart(String format) {
        return m_StartDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public String getEnd(String format) {
        return m_EndDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public String getStartLong() {
        return m_StartDateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy-MM-dd HH:mm"));
    }

    public String getEndLong() {
        return m_EndDateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy-MM-dd HH:mm"));
    }


        @Override
    public String toString() {
        return String.format("Summary: %s; st=%s; end=%s; loc=%s;",
                m_strSummary, m_StartDateTime, m_EndDateTime, m_strLocation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m_strStatus);
        dest.writeString(this.m_strSummary);
        dest.writeString(this.m_strLocation);
        dest.writeString(this.m_strDescription);
        dest.writeSerializable(this.m_StartDateTime);
        dest.writeSerializable(this.m_EndDateTime);
    }

    protected YbEvent(Parcel in) {
        this.m_strStatus = in.readString();
        this.m_strSummary = in.readString();
        this.m_strLocation = in.readString();
        this.m_strDescription = in.readString();
        this.m_StartDateTime = (LocalDateTime) in.readSerializable();
        this.m_EndDateTime = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<YbEvent> CREATOR = new Creator<YbEvent>() {
        @Override
        public YbEvent createFromParcel(Parcel source) {
            return new YbEvent(source);
        }

        @Override
        public YbEvent[] newArray(int size) {
            return new YbEvent[size];
        }
    };
}
