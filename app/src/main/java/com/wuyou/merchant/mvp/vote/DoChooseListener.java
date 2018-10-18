package com.wuyou.merchant.mvp.vote;


import com.wuyou.merchant.data.api.VoteOptionContent;

import java.util.List;

/**
 * Created by Solang on 2018/10/16.
 */

public interface DoChooseListener {
    void doChoose(VoteOptionContent data, boolean isSingle, List<VoteOptionContent> voteOptionContents);
}
