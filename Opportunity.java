public class Opportunity {
    private String title;
    private String type;
    private String organizer;
    private String deadline;
    private String link;
    private String postedBy;
    private String description;

    public Opportunity(String title, String type, String organizer,
                       String deadline, String link, String postedBy,
                       String description) {
        this.title = title;
        this.type = type;
        this.organizer = organizer;
        this.deadline = deadline;
        this.link = link;
        this.postedBy = postedBy;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getOrganizer() { return organizer; }
    public String getDeadline() { return deadline; }
    public String getLink() { return link; }
    public String getPostedBy() { return postedBy; }
    public String getDescription() { return description; }
}
