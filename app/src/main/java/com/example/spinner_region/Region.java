package com.example.spinner_region;

public class Region {
    public Region(){;}
    public Region(String code, String name, String rnum) {
        this.code = code;
        this.name = name;
        this.rnum = rnum;
    }

    private String code;
    private String name;
    private String rnum;

    public void setCode(String regionCode) {
        this.code=regionCode;
    }
    public void setName(String regionName){
        this.name=regionName;
    }
    public void setRnum(String idNumber){
        this.rnum=idNumber;
    }
    public String getCode(){return code;}
    public String getName(){return name;}
    public String getRnum(){return rnum;}

@Override
    public String toString(){
        return name;
}

}
