package org.social.api;

import android.content.Context;
import android.util.Log;

import org.apache.http.message.BasicNameValuePair;
import org.social.http.HttpClient;
import org.social.http.HttpException;
import org.social.http.Response;
import org.social.response.AdminListResponse;
import org.social.response.AllShareResponse;
import org.social.response.BaseResponse;
import org.social.response.FanListResponse;
import org.social.response.FollowListResponse;
import org.social.response.GagResponse;
import org.social.response.ShareDetailResponse;
import org.social.response.ShareGroundResponse;
import org.social.response.SignInResponse;
import org.social.response.UserInfoResponse;
import org.social.response.UserListResponse;
import org.social.util.JsonUtils;
import org.social.util.SpUtil;

import java.util.ArrayList;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class Api {
    public static String IP = "http://192.168.123.1:8080";
    private Context context;
    private String host = IP + "/Share/";
    private HttpClient client;

    public Api(Context context) {
        client = new HttpClient();
        this.context = context;
        IP = SpUtil.getIp(context);
    }

    public static enum Method {
        post, get
    }

    /**
     * @param method
     *            post or get
     * @param url
     *            url
     * @param params
     *
     * @param clz
     *
     * @return clz
     */
    private BaseResponse request(Method method, String url,
                                 ArrayList<BasicNameValuePair> params, Class<?> clz) {
        BaseResponse response = null;
        try {
            response = (BaseResponse) clz.newInstance();
            Response s = null;
            Log.e("chat to url", url);
            if (Method.post == method) {
//                Map<String, String> map = new HashMap<String, String>();
//                for (int i = 0; i < params.size(); i++) {
//                    map.put(params.get(i).getName(), params.get(i).getValue());
//                }
                s = client.post(url, params); //
            } else { // RequeseMethod.get == method
                s = client.get(url, null); //
            }
            Log.e("chat for result", s.asString());
            if (JsonUtils.isGoodJson(s.asString())) {
                // JSONObject json = new JSONObject(s.asString());//ת��Ϊjson��ʽ
                response = (BaseResponse) JsonUtils.toBean(s.asString(), clz);
            } else {
                response.setStatus("badjson");
                response.setMessage("服务器格式错误");
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
            response.setStatus("exception");
            response.setMessage("服务器异常");
        } catch (HttpException e) {
            e.printStackTrace();
            response.setStatus("http");
            response.setMessage("请检查网络");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("exception");
            response.setMessage("服务器异常");
        }
        return response;
    }

    public SignInResponse login(String userName, String password){
        String url = host + "user/login";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userName", userName));
        params.add(new BasicNameValuePair("password", password));
        BaseResponse response = request(Method.post, url, params,
                SignInResponse.class);
        return (SignInResponse) response;
    }

    public BaseResponse changePassword(String oldPassword, String newPassword,int userId){
        String url = host + "user/changePassword";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("oldPassword", oldPassword));
        params.add(new BasicNameValuePair("newPassword", newPassword));
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public SignInResponse signIn(String userName, String password){
        String url = host + "user/signIn";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userName", userName));
        params.add(new BasicNameValuePair("password", password));
        BaseResponse response = request(Method.post, url, params,
                SignInResponse.class);
        return (SignInResponse) response;
    }

    public AllShareResponse getSharesByUserId(int userId){
        String url = host + "share/getSharesByUserId";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                AllShareResponse.class);
        return (AllShareResponse) response;
    }

    public AllShareResponse main(int userId){
        String url = host + "share/main";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                AllShareResponse.class);
        return (AllShareResponse) response;
    }

    public ShareDetailResponse main(int userId, int shareId){
        String url = host + "share/main";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("shareId", shareId + ""));
        BaseResponse response = request(Method.post, url, params,
                ShareDetailResponse.class);
        return (ShareDetailResponse) response;
    }

    // type 榜单类型：1:最新发布，2:点赞最多，3:评论最多
    public ShareGroundResponse getList(int userId, int type){
        String url = host + "share/list";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("type", type + ""));
        BaseResponse response = request(Method.post, url, params,
                ShareGroundResponse.class);
        return (ShareGroundResponse) response;
    }

    public BaseResponse comment(int userId, int shareId, String comment){
        String url = host + "share/comment";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("shareId", shareId + ""));
        params.add(new BasicNameValuePair("comment", comment));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public BaseResponse like(int userId, int shareId, boolean isLike){
        String url = host + "share/like";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("shareId", shareId + ""));
        params.add(new BasicNameValuePair("isLike", isLike + ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public AllShareResponse findShareByKeyword(int userId, String keyword){
        String url = host + "share/findShareByKeyword";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("keyword", keyword));
        BaseResponse response = request(Method.post, url, params,
                AllShareResponse.class);
        return (AllShareResponse) response;
    }

    public BaseResponse shieldUser(int userId, int adminId, boolean isShare){
        String url = host + "admin/shieldUser";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("adminId", adminId + ""));
        params.add(new BasicNameValuePair("isShare", isShare + ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public BaseResponse collect(int userId, int shareId, boolean isCollect){
        String url = host + "share/collect";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("shareId", shareId + ""));
        params.add(new BasicNameValuePair("isCollect", isCollect + ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    //admin
    public UserListResponse showUserList(){
        String url = host + "user/showUserList";
        BaseResponse response = request(Method.get, url, null,
                UserListResponse.class);
        return (UserListResponse) response;
    }

    public FanListResponse showFans(int userId){
        String url = host + "user/showFans";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                FanListResponse.class);
        return (FanListResponse) response;
    }

    public FollowListResponse showAttention(int userId){
        String url = host + "user/showAttention";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                FollowListResponse.class);
        return (FollowListResponse) response;
    }

    public UserInfoResponse information(int userId, int useUserId){
        String url = host + "user/information";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("useUserId", useUserId + ""));
        BaseResponse response = request(Method.post, url, params,
                UserInfoResponse.class);
        return (UserInfoResponse) response;
    }

    public ShareDetailResponse getShareById(int userId, int shareId){
        String url = host + "share/getSharebyId";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("shareId", shareId + ""));
        BaseResponse response = request(Method.post, url, params,
                ShareDetailResponse.class);
        return (ShareDetailResponse) response;
    }

    public BaseResponse follow(int userId,int useUserId, boolean isFollow){
        String url = host + "user/attention";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("useUserId", useUserId + ""));
        params.add(new BasicNameValuePair("isFollow", isFollow + ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public BaseResponse changeInformation(int userId, int gender, String name,
           String sign, String phone, String mail){
        String url = host + "user/changeInformation1";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        params.add(new BasicNameValuePair("sex", gender + ""));
        params.add(new BasicNameValuePair("nickname", name));
        params.add(new BasicNameValuePair("signature", sign));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("email", mail));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public AllShareResponse getCollectByUserId(int userId){
        String url = host + "share/getcollects";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId + ""));
        BaseResponse response = request(Method.post, url, params,
                AllShareResponse.class);
        return (AllShareResponse) response;
    }

    public BaseResponse addKeyWord(String keyWord){
        String url = host + "admin/addKeyWord";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("keyWord", keyWord));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public GagResponse queryKeyWord(){
        String url = host + "admin/queryKeyWord";
        BaseResponse response = request(Method.get, url, null,
                GagResponse.class);
        return (GagResponse) response;
    }

    public BaseResponse deleteKeyWord(int id){
        String url = host + "admin/deleteKeyWord";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", id+""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public BaseResponse shutUp(int userId, boolean canShare){
        String url = host + "admin/shutUp";
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("userId", userId+ ""));
        params.add(new BasicNameValuePair("canShare", canShare+ ""));
        BaseResponse response = request(Method.post, url, params,
                BaseResponse.class);
        return response;
    }

    public AdminListResponse queryShutUp(){
        String url = host + "admin/queryShutUp";
        BaseResponse response = request(Method.get, url, null,
                AdminListResponse.class);
        return (AdminListResponse) response;
    }
}
