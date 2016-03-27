package com.example.kieran.trafficrssprojectxmlpullparser;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSItem {

    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String georssPoint;

    public RSSItem(String title, String description, String link, String pubDate, String georssPoint){
        this.title=title;
        this.description=description;
        this.link=link;
        this.pubDate=pubDate;
        this.georssPoint=georssPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeorssPoint() {
        return georssPoint;
    }

    public void setGeorssPoint(String georssPoint) {
        this.georssPoint = georssPoint;
    }

    @Override
    public String toString() {
        return "StackSite [name=" + title + ", description=" +description+ ", link="
                + link + ", georss:point=" + georssPoint + ", publication date="  + pubDate + "]";
    }
}

