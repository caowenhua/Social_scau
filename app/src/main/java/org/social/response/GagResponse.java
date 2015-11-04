package org.social.response;

import java.util.List;

/**
 * Created by caowenhua on 2015/11/3.
 */
public class GagResponse extends BaseResponse {
    private List<GagEntity> shutUpList;

    public List<GagEntity> getShutUpList() {
        return shutUpList;
    }

    public void setShutUpList(List<GagEntity> shutUpList) {
        this.shutUpList = shutUpList;
    }
}
