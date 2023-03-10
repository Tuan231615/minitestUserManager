package model;

public class Province {
    private int proId;
    private String proName;

    public Province() {
    }

    public Province(int proId, String proName) {
        this.proId = proId;
        this.proName = proName;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
