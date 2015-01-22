package com.dl.mytimeline;

public class Status {

    private Integer mId;
    private String mContent;
    private Long mTimestamp;

    public Status() {
    }

    public Status(Integer id) {
        super();
        mId = id;
    }
 
    public Status(Integer id, String content, Long timestamp) {
        super();
        mId = id;
        mContent = content;
        mTimestamp = timestamp;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setTimestamp(Long timastamp) {
        mTimestamp = timastamp;
    }

    public Integer getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public Long getTimestamp() {
        return mTimestamp;
    }

}
