package com.wuyou.merchant.data.api;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by DELL on 2018/10/10.
 */

public class EosCreateVoteBean {
    @Expose
    private String creator;
    @Expose
    private String title;
    @Expose
    private String logo;
    @Expose
    private String description;
    @Expose
    private String organization;
    @Expose
    private List<VoteQuestion> contents;
    @Expose
    private String end_time;


    public EosCreateVoteBean(String creator, String title, String logo, String description, String organization, List<VoteQuestion> contents, String endTime) {
        this.creator = creator;
        this.title = title;
        this.logo = logo;
        this.description = description;
        this.organization = organization;
        this.contents = contents;
        this.end_time = endTime;
    }

    public String getActionName() {
        return "create";
    }
}
