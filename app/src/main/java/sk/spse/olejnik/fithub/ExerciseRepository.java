package sk.spse.olejnik.fithub;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExerciseRepository {

    public List<Exercises> allExercises;
    List<String> muscleGroup;
    int image1, image2;

    // Exercise types
    String bodyweight = "Bodyweight";
    String bodybuilding = "Bodybuilding";
    String powerlifting = "Powerlifting";

    // Muscle groups
    String chest = "Chest";
    String triceps = "Triceps";
    String frontDelts = "Front Delts";
    String rearDelts = "Rear Delts";
    String shoulders = "Shoulders";
    String abdominals = "Abdominals";
    String biceps = "Biceps";
    String forearms = "Forearms";
    String traps = "Traps";
    String lats = "Lats";
    String quads = "Quads";
    String glutes = "Glutes";
    String calfs = "Calfs";
    String hamstrings = "Hamstrings";

    public ExerciseRepository() {
        // List of all exercises
        allExercises = new ArrayList<>();
        muscleGroup = new ArrayList<>();

        // Push Up
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(triceps);
        image1 = R.drawable.pushup1;
        image2 = R.drawable.pushup2;
        Exercises pushup = new Exercises("Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/push-up", muscleGroup, image1, image1);
        allExercises.add(pushup);
        muscleGroup.removeAll(muscleGroup);

        // Dips
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(frontDelts);
        image1 = R.drawable.dips1;
        image2 = R.drawable.dips2;
        Exercises dips = new Exercises("Dips", bodyweight, "https://musclewiki.com/bodyweight/male/chest/dips", muscleGroup, image1, image1);
        allExercises.add(dips);
        muscleGroup.removeAll(muscleGroup);

        // Incline Push Ups
        muscleGroup.add(chest);
        image1 = R.drawable.inclinepushup1;
        image2 = R.drawable.inclinepushup2;
        Exercises inclinePushUp = new Exercises("Incline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/incline-push-up", muscleGroup, image1, image1);
        allExercises.add(inclinePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Decline Push Ups
        muscleGroup.add(chest);
        image1 = R.drawable.declinepushup1;
        image2 = R.drawable.declinepushup2;
        Exercises declinePushUp = new Exercises("Decline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/decline-push-up", muscleGroup, image1, image1);
        allExercises.add(declinePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Burpees
        muscleGroup.add(chest);
        muscleGroup.add(abdominals);
        muscleGroup.add(quads);
        image1 = R.drawable.burpee1;
        image2 = R.drawable.burpee2;
        Exercises burpees = new Exercises("Burpees", bodyweight, "https://musclewiki.com/bodyweight/male/chest/burpee", muscleGroup, image1, image1);
        allExercises.add(burpees);
        muscleGroup.removeAll(muscleGroup);





        allExercises.sort(Comparator.comparing(Exercises::getName));
    }
}
