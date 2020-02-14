package com.example.android_lab_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    List<FavLocation> favLocationList;
    Boolean expanded;
    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_custom_cell,parent,false);

        return new ViewHolder(view);

    }

    public List<FavLocation> getFavLocationList() {
        return favLocationList;
    }

    public void setFavLocationList(List<FavLocation> favLocationList) {
        this.favLocationList = favLocationList;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
       final FavLocation favLocation = favLocationList.get(position);

        holder.lat.setText("Lat: "+ favLocation.getLatitude());
        holder.lng.setText( "Lng: " + favLocation.getLongitude() );
        holder.address.setText("Address: "+ favLocation.getAddress());
        holder.date.setText("Date: "+favLocation.getDate());



        holder.mycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(expanded) {

                    holder.lat.setVisibility(View.GONE);
                    holder.lng.setVisibility(View.GONE);
                    notifyItemChanged(position);
                    expanded = false;
                }
                else
                {
                    holder.lat.setVisibility(View.VISIBLE);
                    holder.lng.setVisibility(View.VISIBLE);
                    notifyItemChanged(position);
                    expanded = true;

                }
                //notifyDataSetChanged();
                notifyItemChanged(position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return favLocationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lat, address, date, lng,temp;
        CardView mycardview,mycardview1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mycardview = itemView.findViewById(R.id.newcard);
            //mycardview1 = itemView.findViewById(R.id.newcard1);
            lat = itemView.findViewById( R.id.lat);
            lng = itemView.findViewById( R.id.longi);
            address = itemView.findViewById(R.id.textView2);
            date = itemView.findViewById(R.id.textView3);
            //expanded= false;

            //temp = itemView.findViewById(R.id.latii);





        }
    }

    public void deleteItem(int position) {

        FavLocation places = favLocationList.get(position);
        LocationDB userDatabase = LocationDB.getInstance(context);
        userDatabase.daoObjct().delete(places);
        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
        favLocationList.remove(position);
        notifyDataSetChanged();
    }
}
