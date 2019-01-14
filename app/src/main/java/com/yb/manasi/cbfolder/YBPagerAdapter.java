package com.yb.manasi.cbfolder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class YBPagerAdapter extends FragmentPagerAdapter {
    MainActivity m_mainActivity;
    int m_cTabs;
    public YBPagerAdapter(Context context, FragmentManager fm, int cTabs) {
        super(fm);
        m_mainActivity = (MainActivity) context;
        this.m_cTabs = cTabs;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                AgendaView av = new AgendaView();
                return av;
            case 1:
                ContactView cv = new ContactView();
                return cv;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return m_cTabs;
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        // Generate Title based on item position
        switch (pos) {
            case 0:
                return m_mainActivity.getString(R.string.agenda_view);
            case 1:
                return m_mainActivity.getString(R.string.contact_view);
            default:
                return null;
        }
    }
}
