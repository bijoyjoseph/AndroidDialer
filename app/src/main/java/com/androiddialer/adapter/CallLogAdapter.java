package com.androiddialer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androiddialer.R;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.models.CallDetailsModel;
import com.androiddialer.utils.AppConstants;

import java.util.ArrayList;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {

    private final String TAG = CallLogAdapter.class.getSimpleName();
    private ArrayList<CallDetailsModel> callDetailsModelArrayList;
    private Context context;

    public CallLogAdapter(Context context, ArrayList<CallDetailsModel> callDetailsModelArrayList) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "CallLogAdapter ++");
        try {
            this.context = context;
            this.callDetailsModelArrayList = callDetailsModelArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "CallLogAdapter --");
    }

    @NonNull
    @Override
    public CallLogAdapter.CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreateViewHolder ++");
        View view = LayoutInflater.from(context).inflate(R.layout.call_log_adapter_layout, parent, false);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreateViewHolder --");
        return new CallLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogAdapter.CallLogViewHolder holder, int position) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onBindViewHolder ++");

        CallDetailsModel callDetailsModel = callDetailsModelArrayList.get(position);

        holder.tv_is_internet_connected.setText(context.getResources().getString(R.string.is_connected_to_internet) +
                String.valueOf(callDetailsModel.isConnectedToInternet()));

        holder.tv_is_mobile_internet_connected.setText(context.getResources().
                getString(R.string.is_connected_to_mobile_internet) +
                String.valueOf(callDetailsModel.isConnectedToMobileInternet()));

        holder.tv_mob_net_type.setText(context.getResources().
                getString(R.string.mobile_network_type) + callDetailsModel.getMobileNetworkType());

        holder.tv_mob_net_sig_strength.setText(context.getResources().getString(R.string.mobile_network_signal_strength)
                + String.valueOf(callDetailsModel.getMobileNetworkSignalStrength()));

        holder.tv_radio_type.setText(context.getResources().getString(R.string.radio_type) +
                callDetailsModel.getRadioType());

        holder.tv_carrier.setText(context.getResources().getString(R.string.carrier) + callDetailsModel.getCarrier());

        holder.tv_cell_tower_location.setText(context.getResources().getString(R.string.cell_tower_location) +
                callDetailsModel.getCellTowerLocation());

        holder.tv_wifi_connected.setText(context.getResources().getString(R.string.is_connected_to_wifi) +
                String.valueOf(callDetailsModel.isConnectedToWifi()));

        holder.tv_wifi_sig_strength.setText(context.getResources().getString(R.string.wifi_signal_strength) +
                callDetailsModel.getWifiSignalStrength());

        holder.tv_ssid.setText(context.getResources().getString(R.string.ssid) + callDetailsModel.getSsid());
        holder.tv_gps_location.setText(context.getResources().getString(R.string.gps_location) + callDetailsModel.getGpsLocation());
        holder.tv_phone_number.setText(context.getResources().getString(R.string.phone_number) + callDetailsModel.getPhoneNumber());
        holder.tv_call_status.setText(context.getResources().getString(R.string.call_status) + callDetailsModel.getCallStatus());
        holder.tv_call_duration.setText(context.getResources().getString(R.string.call_duration) + String.valueOf(callDetailsModel.getCallDuration()));
        holder.tv_call_day_time.setText(context.getResources().getString(R.string.carrier) + callDetailsModel.getCallDayAndTime());

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onBindViewHolder --");
    }

    @Override
    public int getItemCount() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getItemCount ++");
        return callDetailsModelArrayList != null ? callDetailsModelArrayList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<CallDetailsModel> callDetailsModelArrayList) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "update ++");
        this.callDetailsModelArrayList = callDetailsModelArrayList;
        notifyDataSetChanged();
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "update --");
    }

    public class CallLogViewHolder extends RecyclerView.ViewHolder {

        TextView tv_is_internet_connected;
        TextView tv_is_mobile_internet_connected;
        TextView tv_mob_net_type;
        TextView tv_mob_net_sig_strength;
        TextView tv_radio_type;
        TextView tv_carrier;
        TextView tv_cell_tower_location;
        TextView tv_wifi_connected;
        TextView tv_wifi_sig_strength;
        TextView tv_ssid;
        TextView tv_gps_location;
        TextView tv_phone_number;
        TextView tv_call_status;
        TextView tv_call_duration;
        TextView tv_call_day_time;

        public CallLogViewHolder(@NonNull View view) {
            super(view);

            tv_is_internet_connected = view.findViewById(R.id.tv_is_internet_connected);
            tv_is_mobile_internet_connected = view.findViewById(R.id.tv_is_mobile_internet_connected);
            tv_mob_net_type = view.findViewById(R.id.tv_mob_net_type);
            tv_mob_net_sig_strength = view.findViewById(R.id.tv_mob_net_sig_strength);
            tv_radio_type = view.findViewById(R.id.tv_radio_type);
            tv_carrier = view.findViewById(R.id.tv_carrier);
            tv_cell_tower_location = view.findViewById(R.id.tv_cell_tower_location);
            tv_wifi_connected = view.findViewById(R.id.tv_wifi_connected);
            tv_wifi_sig_strength = view.findViewById(R.id.tv_wifi_sig_strength);
            tv_ssid = view.findViewById(R.id.tv_ssid);
            tv_gps_location = view.findViewById(R.id.tv_gps_location);
            tv_phone_number = view.findViewById(R.id.tv_phone_number);
            tv_call_status = view.findViewById(R.id.tv_call_status);
            tv_call_duration = view.findViewById(R.id.tv_call_duration);
            tv_call_day_time = view.findViewById(R.id.tv_call_day_time);

        }
    }
}
