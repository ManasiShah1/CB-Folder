package com.yb.manasi.cbfolder;

import java.util.ArrayList;

public class Calendar {

    public String m_strSummary;
    public ArrayList<YbEvent> m_arrEvents;

    public Calendar(String m_strSummary, ArrayList<YbEvent> m_arrEvents) {
        this.m_strSummary = m_strSummary;
        this.m_arrEvents = m_arrEvents;
    }
}
