package com.darkbears.volleylib.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by jsnaruka on 05-May-16.
 */

public class CustomRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private ProgressDialog progressDialog;
    private Context context;

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CustomRequest(String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, errorListener);
        this.listener = reponseListener;
        this.params = params;

        //LogWrapper.v("Making request to: " + url);
        //LogWrapper.v("with params: " + params.toString());

    }

    /*
        takes context and progress bar message
     */
    public CustomRequest(String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener, Context context, String message) {
        super(Method.POST, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        setContext(context);


        /*if (Utils.isNetworkAvailable(getContext())) {

            if (!ShpUtil.getFromPrefs(getContext(), Var.SHP.SHOWING_LOCK_DIALOG).equals("1")) {
                setProgressDialog(Utils.showProgressDialog(getContext(), message));
            }

           // LogWrapper.v("Making request to: " + url);
           // LogWrapper.v("with params: " + params.toString());
        } else {
            //LogWrapper.v("No Internet connection");

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("No Internet").setMessage("Please check if you are connected to Internet.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();

        }*/
    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener, Context context, String message) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        setContext(context);

        /*if (Utils.isNetworkAvailable(getContext())) {

            if (!ShpUtil.getFromPrefs(getContext(), Var.SHP.SHOWING_LOCK_DIALOG).equals("1")) {
                setProgressDialog(Utils.showProgressDialog(getContext(), message));
            }
           // LogWrapper.v("Making request to: " + url);
           // LogWrapper.v("with params: " + params.toString());

        } else {
         //   LogWrapper.v("No Internet connection");

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("No Internet").setMessage("Please check if you are connected to Internet.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();

        }*/
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        try {
            getProgressDialog().dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//            LogWrapper.v(response.data.toString());
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

           /* try {
                LogWrapper.v(new JSONObject(jsonString).toString(4));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        try {
            getProgressDialog().dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        String kachra = Html.fromHtml(response.toString()).toString();
        try {
            response = new JSONObject(kachra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onResponse(response);
    }
}
