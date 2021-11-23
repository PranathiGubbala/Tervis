package com.example.pranathi.tervis;

public class Model {
    private int pid;
    private String medname;
    private String status;

    public Model(int pid, String medname, String status) {
        this.pid = pid;
        this.medname = medname;
        this.status = status;
    }
    public int getPid() {return pid;}
    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
