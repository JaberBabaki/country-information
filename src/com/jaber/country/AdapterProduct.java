package com.jaber.country;

import java.util.ArrayList;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;


public class AdapterProduct extends ArrayAdapter<StructProduct> {

    public AdapterProduct(ArrayList<StructProduct> array) {
        super(G.context, R.layout.adapter_product, array);

    }


    private static class ViewHolder {

        public CustomTextView txtTitleProduct;
        public CustomTextView txtTextProduct;
        public Button         btnGetProduct;
        public ImageView      imgIconProduct;
        public Bitmap         bitmap;


        //public LinearLayout   layReadOnRead;
        //public LinearLayout   layText;

        public ViewHolder(View view) {
            txtTitleProduct = (CustomTextView) view.findViewById(R.id.txtTitleProduct);
            txtTextProduct = (CustomTextView) view.findViewById(R.id.txtTextProduct);
            btnGetProduct = (Button) view.findViewById(R.id.btnGetProduct);
            imgIconProduct = (ImageView) view.findViewById(R.id.imgIconProduct);
            // layReadOnRead = (LinearLayout) view.findViewById(R.id.layReadOnRead);
            //layText = (LinearLayout) view.findViewById(R.id.layText);

        }


        public void fill(ArrayAdapter<StructProduct> adapter, final StructProduct item, int position) {
            txtTitleProduct.setText("" + item.title);
            txtTextProduct.setText(item.text);
            bitmap = BitmapFactory.decodeFile(G.DIR_PRODUCT + "/" + item.icon);
            imgIconProduct.setImageBitmap(bitmap);
            btnGetProduct.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + item.pack));
                    G.currentActivity.startActivity(intent);

                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructProduct item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.adapter_product, parent, false);
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
