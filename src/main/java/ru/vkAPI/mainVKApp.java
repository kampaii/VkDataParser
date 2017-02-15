package ru.vkAPI;

import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;

import java.util.List;

/**
 * Created by Kirill on 05.01.2017.
 */
public class mainVKApp {

    /**
         * https://oauth.vk.com/authorize?client_id=5807855&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=wall,groups,offline&response_type=code&v=5.60
     * */

    public static void main(String[] args) throws Exception {

        String code="e19feabe890e9742a4";

        String searchedGroup = "pn6";

        VKWorker vk = new VKWorker();
        System.out.println("helloworld");
        System.out.println("do some shit");
        List<WallpostFull> posts = vk.getGroupWall(vk.getGroupId(searchedGroup),100);
        System.out.println(posts.size());
        WallpostFull post = posts.get(0);
        //GetListResponse likes = vk.getPostLikes(34598686,);
        System.out.println(post.getOwnerId());
        System.out.println(post.getText());
        System.out.println(post.toString());

        //vk.printInfoWall(posts);

    }

}
