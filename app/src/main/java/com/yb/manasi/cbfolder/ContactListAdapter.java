package com.yb.manasi.cbfolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.CLViewHolder> {

    private ArrayList<YbInfo> m_arrYbInfo;
    Context m_context;

    public ContactListAdapter(ArrayList<YbInfo> arrYbInfo, Context context) {
        this.m_arrYbInfo = arrYbInfo;
        this.m_context = context;
    }

    public void setArrYbInfo(ArrayList<YbInfo> arrYbInfo) {
        this.m_arrYbInfo = arrYbInfo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view_row, parent, false);
        return new CLViewHolder(vw);
    }

    @Override
    public void onBindViewHolder(@NonNull CLViewHolder clViewHolder, int pos) {
        YbInfo ybInfo = m_arrYbInfo.get(pos);
        clViewHolder.tvName.setText(ybInfo.m_strFirstName + " " + ybInfo.m_strLastName);
        clViewHolder.tvGroup.setText(ybInfo.m_strGroupNum);
        clViewHolder.tvCell.setText(ybInfo.m_strCellNum);
        clViewHolder.tvPartner.setText(ybInfo.m_strYbPartner);
    }

    @Override
    public int getItemCount() {
        return (m_arrYbInfo == null ? 0 : m_arrYbInfo.size());
    }

    static class CLViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvGroup;
        public TextView tvCell;
        public TextView tvPartner;

        public CLViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.id_tvYbName);
            tvGroup = (TextView) itemView.findViewById(R.id.id_tvGroup);
            tvCell = (TextView) itemView.findViewById(R.id.id_tvYbCell);
            tvPartner = (TextView) itemView.findViewById(R.id.id_tvPartner);
        }
    }
}
