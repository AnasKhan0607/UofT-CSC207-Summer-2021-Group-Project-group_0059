public class Template {
    private String templatename;
    private String description;
    private int numchoice;
    public Template (String templatename) {
        this.templatename = templatename;
    }
    public String getTemplatename() {
        return this.templatename;
    }
    public void setTemplatename (String templatename) {
        this.templatename = templatename;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    public void setNumchoice(int numchoice) {
        this.numchoice = numchoice;
    }
    public int getNumchoice() {
        return this.numchoice;
    }
}