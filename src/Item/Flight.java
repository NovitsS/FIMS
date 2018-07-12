package Item;

public class Flight {
    private String fname;
    private String ftime;
    private String fmax;
    private String fcur;

    public Flight(String fname, String ftime, String fmax, String fcur) {
        this.fname = fname;
        this.ftime = ftime;
        this.fmax = fmax;
        this.fcur = fcur;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getFmax() {
        return fmax;
    }

    public void setFmax(String fmax) {
        this.fmax = fmax;
    }

    public String getFcur() {
        return fcur;
    }

    public void setFcur(String fcur) {
        this.fcur = fcur;
    }
}
