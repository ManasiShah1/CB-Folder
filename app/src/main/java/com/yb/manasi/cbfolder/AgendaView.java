package com.yb.manasi.cbfolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgendaListAdapter.OnAgendaItemSelectedListener} interface
 * to handle interaction events.
 * Use the {@link AgendaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendaView extends Fragment {
    private RecyclerView m_rvEvents;
    private MainActivity m_mainActivity;
    private AgendaListAdapter m_alaAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgendaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgendaView.
     */
    // TODO: Rename and change types and number of parameters
    public static AgendaView newInstance(String param1, String param2) {
        AgendaView fragment = new AgendaView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_mainActivity = (MainActivity)context;
        m_alaAdapter = new AgendaListAdapter(m_mainActivity.getArrYbEvents(), m_mainActivity);
        m_mainActivity.setAlaAdapter(m_alaAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_agenda_view, container, false);
        RecyclerView rv = (RecyclerView) vw.findViewById(R.id.id_rvybEvents);
        // Bind the AgendaListAdapter to the RecyclerView
        rv.setAdapter(m_alaAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        m_alaAdapter.notifyDataSetChanged();
        rv.setClickable(true);

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), llm.getOrientation());
        rv.addItemDecoration(did);

        return vw;
    }

    public AgendaListAdapter getAdapter() {
        return m_alaAdapter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
