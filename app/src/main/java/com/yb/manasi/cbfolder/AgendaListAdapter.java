package com.yb.manasi.cbfolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AgendaListAdapter extends RecyclerView.Adapter<AgendaListAdapter.ALViewHolder> {
    private ArrayList<YbEvent> m_arrYbEvents;
    Context m_context;
    OnAgendaItemSelectedListener m_Listener;

    public interface OnAgendaItemSelectedListener {
        void onAgendaItemSelected(YbEvent ybEvent);
    }

    public AgendaListAdapter(ArrayList<YbEvent> ybEvents, Context context) {
        //super (context, R.layout.agenda_view_row, ybEvents);
        this.m_arrYbEvents = ybEvents;
        this.m_context = context;
        if (context instanceof OnAgendaItemSelectedListener) {
            this.m_Listener = (OnAgendaItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAgendaItemSelectedListener");
        }
    }

    public void setArrYbEvents(ArrayList<YbEvent> arrYbEvents) {
        this.m_arrYbEvents = arrYbEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ALViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View vw = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_view_row, parent, false);
        return new ALViewHolder(vw);
    }

    @Override
    public void onBindViewHolder(@NonNull ALViewHolder alViewHolder, int pos) {
        YbEvent ybevent = m_arrYbEvents.get(pos);
        alViewHolder.tvStartTime.setText(ybevent.getStartShort());
        alViewHolder.tvEndTime.setText(ybevent.getEnd("HH:mm"));
        alViewHolder.tvSummary.setText(ybevent.m_strSummary);
        alViewHolder.tvLocation.setText(ybevent.m_strLocation);
        alViewHolder.bind(ybevent, m_Listener);
    }

    @Override
    public int getItemCount() {
        return (m_arrYbEvents == null ? 0 : m_arrYbEvents.size());
    }

    static class ALViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStartTime;
        public TextView tvEndTime;
        public TextView tvSummary;
        public TextView tvLocation;

        public ALViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStartTime = (TextView) itemView.findViewById(R.id.id_tvStartTime);
            tvEndTime   = (TextView) itemView.findViewById(R.id.id_tvEndTime);
            tvSummary   = (TextView) itemView.findViewById(R.id.id_tvSummary);
            tvLocation  = (TextView) itemView.findViewById(R.id.id_tvLocation);
        }

        public void bind(final YbEvent ybevent, final OnAgendaItemSelectedListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onAgendaItemSelected(ybevent);
                }
            });
        }
    }
}
