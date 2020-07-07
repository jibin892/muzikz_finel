package com.qboxus.musictok.Corona;

public class Coronaadapter {

    private String Kerala,kerala_r,Tamilnadu,Tamilnadu_r,India,India_r,Karnataga,Karnataga_r,Delhi,Delhi_r,world,world_r,India_deth,world_death ;

    public Coronaadapter(String Kerala,String kerala_r, String Tamilnadu,String Tamilnadu_r,String India,String India_r,String Karnataga,String Karnataga_r,
                         String Delhi,String Delhi_r,String world,String world_r,String India_deth,String world_death) {
        this.Kerala = Kerala;
        this.kerala_r = kerala_r;
        this.Tamilnadu = Tamilnadu;
        this.Tamilnadu_r = Tamilnadu_r;
        this.India = India;
        this.India_r = India_r;
        this.Karnataga = Karnataga;
        this.Karnataga_r = Karnataga_r;
        this.Delhi = Delhi;
        this.Delhi_r = Delhi_r;
        this.world = world;
        this.world_r = world_r;
        this.India_deth = India_deth;
        this.world_death = world_death;



    }
    public Coronaadapter(){

    }

    public String getKerala() {
        return Kerala;
    }

    public void setKerala(String Kerala) {
        this.Kerala = Kerala;
    }


    public String getKerala_r() { return kerala_r; }

    public void setKerala_r(String kerala_r) {
        this.kerala_r = kerala_r;
    }



    public String getTamilnadu_r() {
        return Tamilnadu_r;
    }

    public void setTamilnadu_r(String Tamilnadu_r) {
        this.Tamilnadu_r = Tamilnadu_r;
    }

    public String getTamilnadu() {
        return Tamilnadu;
    }

    public void setTamilnadu(String Tamilnadu) {
        this.Tamilnadu = Tamilnadu;
    }

    public String getIndia() {
        return India;
    }

    public void setIndia(String India) {
        this.India = India;
    }

    public String getIndia_r() {
        return India_r;
    }

    public void setIndia_r(String India_r) {
        this.India_r = India_r;
    }

    public String getKarnataga() {
        return Karnataga;
    }

    public void setKarnataga(String Karnataga) {
        this.Karnataga = Karnataga;
    }



    public String getKarnataga_r() {
        return Karnataga_r;
    }

    public void setKarnataga_r(String Karnataga_r) {
        this.Karnataga_r = Karnataga_r;
    }


    public String getDelhi() {
        return Delhi;
    }

    public void setDelhi_(String Delhi) {
        this.Delhi = Delhi;
    }


    public String getDelhi_r() { return Delhi_r; }


    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }



    public String getWorld_r() {
        return world_r;
    }

    public void setWorld_r(String world_r) {
        this.world_r = world_r;
    }

    public String getIndia_deth() {
        return India_deth;
    }
    public String getworld_death() {
        return world_death;
    }

    public void setDelhi_r(String Delhi_r) {
        this.Delhi_r = Delhi_r;
    }

    @Override
    public String toString() {
        return "Corona_details{" +
                "Kerala='" + Kerala + '\'' +
                ", kerala_r='" +kerala_r + '\'' +
                ", Tamilnadu='" +Tamilnadu + '\'' +
                ", Tamilnadu_r='" +Tamilnadu_r + '\'' +
                ", India='" +India + '\'' +
                ", India_r='" +India_r + '\'' +
                ", Karnataga='" +Karnataga + '\'' +
                ", Karnataga_r='" +Karnataga_r + '\'' +
                ", Delhi='" +Delhi + '\'' +
                ", Delhi_r='" +Delhi_r + '\'' +
                ", world='" + world + '\'' +
                ", world_r='" +world_r + '\'' +
                ", India_deth='" +India_deth + '\'' +
                ", world_death='" +world_death + '\'' +

                '}';
    }
}
