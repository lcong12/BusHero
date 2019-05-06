package com.example.bushero;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
RecyclerView.Adapter
RecyclerView.ViewHolder
 */
public class BusRouteAdapter extends RecyclerView.Adapter<BusRouteAdapter.BusRouteViewHolder> {
    private Context mCtx;
    private List<Bus> busList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BusRouteAdapter(Context mCtx, List<Bus> busList) {
        this.mCtx = mCtx;
        this.busList = busList;
    }

    @Override
    public BusRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        BusRouteViewHolder holder = new BusRouteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BusRouteViewHolder holder, int position) {
        Bus route = busList.get(position);
        holder.textViewID.setText(route.getRouteId());
        holder.textViewName.setText(route.getName());
        holder.textViewTime.setText(route.getTimeLeft());
        holder.textViewCapacity.setText(route.getCapacity());
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    class BusRouteViewHolder extends  RecyclerView.ViewHolder{

        TextView textViewID, textViewName, textViewTime, textViewCapacity;

        public BusRouteViewHolder(View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.routeID);
            textViewName = itemView.findViewById(R.id.routeName);
            textViewTime = itemView.findViewById(R.id.timeToArrive);
            textViewCapacity = itemView.findViewById(R.id.capacity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
