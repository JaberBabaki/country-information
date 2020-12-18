package com.jaber.country;

import java.util.ArrayList;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AdapterMessage extends ArrayAdapter<StructMessage> {

    public AdapterMessage(ArrayList<StructMessage> array) {
        super(G.context, R.layout.adpter_message, array);

    }


    private static class ViewHolder {

        public TextView       txtTitle;
        public CustomTextView txtText;
        public CustomTextView txtDate;

        public LinearLayout   layReadOnRead;
        public LinearLayout   layText;


        public ViewHolder(View view) {
            txtTitle = (TextView) view.findViewById(R.id.txtTitleM);
            txtText = (CustomTextView) view.findViewById(R.id.txtText);
            txtDate = (CustomTextView) view.findViewById(R.id.txtDate);
            layReadOnRead = (LinearLayout) view.findViewById(R.id.layReadOnRead);
            layText = (LinearLayout) view.findViewById(R.id.layText);

        }


        public void fill(ArrayAdapter<StructMessage> adapter, final StructMessage item, int position) {
            txtTitle.setText("" + item.title);
            txtText.setText(item.text);
            txtDate.setText("" + item.date);

            if (item.readOnRead == 0) {
                layReadOnRead.setBackgroundColor(Color.parseColor("#D2D2D2"));
            } else {
                layReadOnRead.setBackgroundColor(Color.parseColor("#FF3B2F"));
            }

            layText.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(G.currentActivity, ActivityShowMessage.class);
                    intent.putExtra("date", item.date);
                    intent.putExtra("title", item.title);
                    intent.putExtra("text", item.text);
                    intent.putExtra("id", item.id);

                    G.currentActivity.startActivity(intent);

                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructMessage item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.adpter_message, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}
