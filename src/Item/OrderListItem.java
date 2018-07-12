package Item;

public class OrderListItem {
    private String num;
    private String key;
    private String id;
    private String start,end,startAir,endAir;
    private String ftmon,fjmon;
    private String ftcur,fjcur;
    private String hour;
    private String time;
    private String ftid;
    private String kind;
    private int isprint;
    private UserItem item=null;

    public UserItem getItem() {
        return item;
    }

    public void setItem(UserItem item) {
        this.item = item;
    }

    public int getIsprint() {
        return isprint;
    }

    public void setIsprint(int isprint) {
        this.isprint = isprint;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public OrderListItem(String id, String start, String end, String time
                    , String startAir, String endAir){
        this.id = id;
        this.start = start;
        this.end = end;
        this.time = time;
        this.startAir = startAir;
        this.endAir = endAir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartAir() {
        return startAir;
    }

    public void setStartAir(String startAir) {
        this.startAir = startAir;
    }

    public String getEndAir() {
        return endAir;
    }

    public void setEndAir(String endAir) {
        this.endAir = endAir;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFtmon() {
        return ftmon;
    }

    public void setFtmon(String ftmon) {
        this.ftmon = ftmon;
    }

    public String getFjmon() {
        return fjmon;
    }

    public void setFjmon(String fjmon) {
        this.fjmon = fjmon;
    }

    public String getFtcur() {
        return ftcur;
    }

    public void setFtcur(String ftcur) {
        this.ftcur = ftcur;
    }

    public String getFjcur() {
        return fjcur;
    }

    public void setFjcur(String fjcur) {
        this.fjcur = fjcur;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getFtid() {
        return ftid;
    }

    public void setFtid(String ftid) {
        this.ftid = ftid;
    }
}
