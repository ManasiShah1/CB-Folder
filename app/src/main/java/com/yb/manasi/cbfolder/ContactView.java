package com.yb.manasi.cbfolder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ContactView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactView extends Fragment {
    private RecyclerView m_rvInfo;
    private MainActivity m_mainActivity;
    private ContactListAdapter m_claAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactView.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactView newInstance(String param1, String param2) {
        ContactView fragment = new ContactView();
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
        View vw = inflater.inflate(R.layout.fragment_contact_view, container, false);
        RecyclerView rv = (RecyclerView) vw.findViewById(R.id.id_rvybInfo);
        // Create and Bind the ContactListAdapter to the RecyclerView
        m_claAdapter = new ContactListAdapter(m_mainActivity.getArrYbInfo(), m_mainActivity);
        m_mainActivity.setClaAdapter(m_claAdapter);
        rv.setAdapter(m_claAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
       // m_claAdapter.notifyDataSetChanged(); TODO: verify

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), llm.getOrientation());
        rv.addItemDecoration(did);

        return vw;
    }

    public ContactListAdapter getAdapter() {
        return m_claAdapter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
