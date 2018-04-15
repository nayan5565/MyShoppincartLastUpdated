package com.swapnopuri.shopping.cart.my.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.swapnopuri.shopping.cart.my.R;
import com.swapnopuri.shopping.cart.my.activity.ActivityVideoPlayer;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by JEWEL on 9/1/2016.
 */
public class DownloadAsync extends AsyncTask<String, Double, Boolean> {


    private Context context;
    private String path, title;
    private Dialog dialog;
    private DownloadAsync instance;
    private TextView tvDialogMsg;

    public DownloadAsync(Context context, String path, String title) {
        this.context = context;
        this.path = path;
        this.title = title;
        instance = this;

    }

    @Override
    protected void onPreExecute() {
        openDialog();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        int count;

        try {
            URL url = new URL(params[0]);

            URLConnection conn = url.openConnection();
            conn.connect();
            int lenghtOfFile = conn.getContentLength();

            // downlod the file
            InputStream input = new BufferedInputStream(url.openStream());


            OutputStream output = new FileOutputStream(path);

            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                publishProgress((total * 100 / (double) lenghtOfFile));
                output.write(data, 0, count);

            }

            output.flush();
            output.close();
            input.close();
            return true;
        } catch (Exception e) {
//            Utils.showToast(Global.MSG_SORRY);
            Utils.deleteFile(path);
        }

        return false;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        super.onProgressUpdate(values);

        tvDialogMsg.setText(Global.VIDEO_ACTION_MASSAGE + Utils.convertNum(String.format(" %1$.2f", values[0]) + "%"));
        MyLog.e("TEst", "v:" + values[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (dialog != null)
            dialog.dismiss();

        if (result) {
            ActivityVideoPlayer.start(context, title, path,0);
        }

    }

    private void openDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lay_dialog);

        dialog.setCancelable(false);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setVisibility(View.GONE);

        tvDialogMsg = (TextView) dialog.findViewById(R.id.tvDetails);
        tvDialogMsg.setText(Global.VIDEO_ACTION_MASSAGE + Utils.convertNum("0%"));

        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setText(Global.MSG_CANCEL);
        btnOk.setText(Global.MSG_HIDE);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.cancel(true);
                Utils.deleteFile(path);
                dialog.dismiss();
                instance = null;
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}