package com.example.pengingataktivitas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengingataktivitas.R;
import com.example.pengingataktivitas.model.Aktivitas;

import java.util.ArrayList;
import java.util.List;

public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.AktivitasHolder> {
    private List<Aktivitas> aktivitasList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    @NonNull
    @Override
    public AktivitasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aktivitas, parent, false);
        return new AktivitasHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AktivitasHolder holder, int position) {
        Aktivitas current = aktivitasList.get(position);
        holder.textViewJudul.setText(current.getJudul());
        holder.textViewTanggal.setText(current.getTanggal() + " " + current.getWaktu());
    }

    @Override
    public int getItemCount() {
        return aktivitasList.size();
    }

    public void setAktivitas(List<Aktivitas> aktivitas) {
        this.aktivitasList = aktivitas;
        notifyDataSetChanged();
    }

    class AktivitasHolder extends RecyclerView.ViewHolder {
        private final TextView textViewJudul, textViewTanggal;

        public AktivitasHolder(View itemView) {
            super(itemView);
            textViewJudul = itemView.findViewById(R.id.text_view_judul);
            textViewTanggal = itemView.findViewById(R.id.text_view_tanggal);

            // Klik biasa untuk edit
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(aktivitasList.get(pos));
                }
            });

            // Klik lama untuk pilih edit/hapus
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (longClickListener != null && pos != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClick(aktivitasList.get(pos));
                    return true;
                }
                return false;
            });
        }
    }

    // Interface klik biasa
    public interface OnItemClickListener {
        void onItemClick(Aktivitas aktivitas);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Interface klik lama
    public interface OnItemLongClickListener {
        void onItemLongClick(Aktivitas aktivitas);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
