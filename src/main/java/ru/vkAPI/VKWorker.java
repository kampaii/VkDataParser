package ru.vkAPI;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.likes.LikesGetListFilter;
import com.vk.api.sdk.queries.likes.LikesType;
import com.vk.api.sdk.queries.wall.WallGetFilter;

import java.util.List;

/**
 * Created by Kirill on 22.01.2017.
 */
public class VKWorker {
    private int APP_ID = 5807855;
    private String CLIENT_SECRET = "46Sa9Dgg9ElpCkrFtNq2";
    private String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    private VkApiClient vk;

    public VKWorker(){
        TransportClient tc = new HttpTransportClient();
        vk = new VkApiClient(tc);
    }

    public VKWorker(int appid,String secret,String redirect_url){
        this.APP_ID = appid;
        this.CLIENT_SECRET = secret;
        this.REDIRECT_URL = redirect_url;
        TransportClient tc = new HttpTransportClient();
        vk = new VkApiClient(tc);
    }

    public void authorize(String token){
        vk.oauth().userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URL, token);
    }

    public int getGroupId(String groupName) throws Exception {
        List<GroupFull> query = vk.groups().getById().groupId(groupName).execute();
        if (query.size() == 1) {
            GroupFull g = query.get(0);
            try {
                return Integer.parseInt(g.getId());
            } catch (Exception ex) {
                System.out.println(g.getId());
                return 0;
            }
        }
        else throw new Exception("unique group not found");
    }

    public List<WallpostFull> getGroupWall(int groupId) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(-groupId).filter(WallGetFilter.ALL).execute();
        return g.getItems();
    }

    public List<WallpostFull> getGroupWall(int groupId, int count) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(-groupId).filter(WallGetFilter.ALL).count(count).execute();
        return g.getItems();
    }

    public List<WallpostFull> getGroupWall(int groupId, int count, int offset) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(-groupId).filter(WallGetFilter.ALL).count(count).offset(offset).execute();
        return g.getItems();
    }

    public List<WallpostFull> getUserWall(int userID) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(userID).filter(WallGetFilter.ALL).execute();
        return g.getItems();
    }

    public List<WallpostFull> getUserWall(int userID, int count) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(userID).filter(WallGetFilter.ALL).count(count).execute();
        return g.getItems();
    }

    public List<WallpostFull> getUserWall(int userID, int count, int offset) throws ClientException, ApiException {
        GetResponse g = vk.wall().get().ownerId(userID).filter(WallGetFilter.ALL).count(count).offset(offset).execute();
        return g.getItems();
    }

    public GetListResponse getPostLikes(int ownerId,int itemId) throws ClientException, ApiException {

        return vk.likes().getList(LikesType.POST).ownerId(ownerId).filter(LikesGetListFilter.LIKES).itemId(itemId).execute();
    }

    public void printInfoWall(List<WallpostFull> items){
        for (WallpostFull f : items) {
            System.out.println("post id: " + f.getId());
            System.out.println(f.getText());
            System.out.println(f.getLikes().toString());
        }
    }

}
