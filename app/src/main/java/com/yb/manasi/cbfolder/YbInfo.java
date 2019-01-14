package com.yb.manasi.cbfolder;

import android.os.Parcel;
import android.os.Parcelable;

public class YbInfo implements Parcelable {

    public String m_strFirstName;
    public String m_strLastName;
    public String m_strEmail;
    public String m_strCellNum;
    public String m_strGroupNum;
    public String m_strYbPartner;

    public YbInfo(String m_strFirstName, String m_strLastName, String m_strEmail, String m_strCellNum, String m_strGroupNum, String m_strYbPartner) {
        this.m_strFirstName = m_strFirstName;
        this.m_strLastName = m_strLastName;
        this.m_strEmail = m_strEmail;
        this.m_strCellNum = m_strCellNum;
        this.m_strGroupNum = m_strGroupNum;
        this.m_strYbPartner = m_strYbPartner;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s;", m_strFirstName, m_strLastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m_strFirstName);
        dest.writeString(this.m_strLastName);
        dest.writeString(this.m_strEmail);
        dest.writeString(this.m_strCellNum);
        dest.writeString(this.m_strGroupNum);
        dest.writeString(this.m_strYbPartner);
    }

    protected YbInfo(Parcel in) {
        this.m_strFirstName = in.readString();
        this.m_strLastName = in.readString();
        this.m_strEmail = in.readString();
        this.m_strCellNum = in.readString();
        this.m_strGroupNum = in.readString();
        this.m_strYbPartner = in.readString();
    }

    public static final Creator<YbInfo> CREATOR = new Creator<YbInfo>() {
        @Override
        public YbInfo createFromParcel(Parcel source) {
            return new YbInfo(source);
        }

        @Override
        public YbInfo[] newArray(int size) {
            return new YbInfo[size];
        }
    };
}
