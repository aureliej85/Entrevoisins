package com.openclassrooms.entrevoisins.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class Neighbour implements Parcelable {

    private Integer id;
    private String name;
    private String avatarUrl;
    private String adress;
    private String tel;
    private String url;
    private String description;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(avatarUrl);
        parcel.writeString(adress);
        parcel.writeString(tel);
        parcel.writeString(url);
        parcel.writeString(description);

    }

    public static final Creator<Neighbour> CREATOR = new Creator<Neighbour>() {
        @Override
        public Neighbour createFromParcel(Parcel in) {
            return new Neighbour(in);
        }

        @Override
        public Neighbour[] newArray(int size) {
            return new Neighbour[size];
        }
    };

    protected Neighbour(Parcel in) {
        id = in.readInt();
        name = in.readString();
        avatarUrl = in.readString();
        adress = in.readString();
        tel = in.readString();
        url = in.readString();
        description = in.readString();
    }

    /**
     * Constructor
     * @param id
     * @param name
     * @param avatarUrl
     * @param adress
     * @param tel
     * @param url
     * @param description
     */

    public Neighbour(Integer id, String name, String avatarUrl, String adress, String tel, String url, String description) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.adress = adress;
        this.tel = tel;
        this.url = url;
        this.description = description;
    }

    public Neighbour(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbour neighbour = (Neighbour) o;
        return Objects.equals(id, neighbour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
