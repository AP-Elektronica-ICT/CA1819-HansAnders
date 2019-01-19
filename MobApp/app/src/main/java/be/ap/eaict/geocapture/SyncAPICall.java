package be.ap.eaict.geocapture;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.HttpEntity;

public class SyncAPICall {
    //private static final String BASE_URL = "http://localhost:39858/api/Regio/";
    private static final String BASE_URL = "http://webapplication520181127093524.azurewebsites.net/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, HttpEntity params, AsyncHttpResponseHandler responseHandler) {
        //client.post(getAbsoluteUrl(url), params, responseHandler);
        client.post(null, getAbsoluteUrl(url), params,"application/json" ,responseHandler);
    }

    public static void put(String url, HttpEntity params, AsyncHttpResponseHandler responseHandler) {
        //client.put(getAbsoluteUrl(url), params, responseHandler);
        client.put(null, getAbsoluteUrl(url), params,"application/json" ,responseHandler);
    }

    public static void delete(String url, HttpEntity params, AsyncHttpResponseHandler responseHandler) {
        client.delete(null, getAbsoluteUrl(url), params,"application/json" ,responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}

//EXAMPLE:

//class TwitterRestClientUsage {
//    public void getPublicTimeline() throws JSONException {
//        TwitterRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
//                JSONObject firstEvent = timeline.get(0);
//                String tweetText = firstEvent.getString("text");
//
//                // Do something with the response
//                System.out.println(tweetText);
//            }
//        });
//    }
//}