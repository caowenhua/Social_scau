package org.social.response;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class ShareGroundResponse extends BaseResponse {

    /**
     * shares : [{"shareId":123456,"userId":"00001","userName":"Tom","avatar":"http://XXX/XXXX/XXX.jpg","shareTime":1234567890,"content":"哇哈哈哈哈啊哈哈哈哈哈","imgList":["url1","url2"],"likeCount":20,"commentCount":20,"isLike":true},{"shareId":123456,"userId":"00001","userName":"Tom","avatar":"http://XXX/XXXX/XXX.jpg","shareTime":1234567890,"content":"哇哈哈哈哈啊哈哈哈哈哈","imgList":["url1","url2"],"likeCount":20,"commentCount":20,"isLike":true}]
     */

    private List<SharesEntity> shares;

    public void setShares(List<SharesEntity> shares) {
        this.shares = shares;
    }

    public List<SharesEntity> getShares() {
        return shares;
    }


}
