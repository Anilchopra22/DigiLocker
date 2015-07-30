package com.example.anichopr.digilocker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anichopr on 7/28/2015.
 */
public class DocImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<DigiDoc> mDocumentsInfo;

    public DocImageAdapter(Context c, List<DigiDoc> documentsInfo) {
        mContext = c;
        mDocumentsInfo = documentsInfo;
    }

    public int getCount() {
        if (mDocumentsInfo == null)
            return 0;
        return mDocumentsInfo.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mDocumentsInfo == null)
            return null;

        View myView;
        Activity activity = ((Activity) mContext);

        LayoutInflater li = activity.getLayoutInflater();
        myView = li.inflate(R.layout.doc_image_layout, null);
        ImageView img = (ImageView) myView.findViewById(R.id.img2);
        TextView txt = (TextView) myView.findViewById(R.id.txt2);

        Bitmap bitmap = mDocumentsInfo.get(position).bitmap;
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.docwhite);
        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 450, 450, true);

        img.setImageBitmap(bitmap2);
        txt.setText(mDocumentsInfo.get(position).documentName);

        return myView;
    }
}
