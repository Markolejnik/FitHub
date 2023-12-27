package sk.spse.olejnik.fithub;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class Exercises extends ViewModel {


    private String name, type, link;
    private List<String> muscleGroup;
    private int image1, image2, imageMini;
    public Exercises(String name, String type, String link, List<String> muscleGroup, int image1, int image2, int imageMini) {
        this.name = name;
        this.type = type;
        this.link = link;
        this.muscleGroup = muscleGroup;
        this.image1 = image1;
        this.image2 = image2;
        this.imageMini = imageMini;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(List<String> muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImageMini() {
        return imageMini;
    }

    public void setImageMini(int imageMini) {
        this.imageMini = imageMini;
    }


}
