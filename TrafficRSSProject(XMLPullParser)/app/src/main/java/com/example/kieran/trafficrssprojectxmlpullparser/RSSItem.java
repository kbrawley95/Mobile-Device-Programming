package com.example.kieran.trafficrssprojectxmlpullparser;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */
public class RSSItem {

    private String title;
    private String description;
    private String link;
    private String pubDate;

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

    @Override
    public String toString() {
        return "StackSite [name=" + title + ", description=" +description+ ", link="
                + link + ", publication date=" + pubDate + "]";
    }
}

