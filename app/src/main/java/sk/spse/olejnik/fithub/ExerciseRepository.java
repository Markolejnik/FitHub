package sk.spse.olejnik.fithub;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExerciseRepository {

    public List<Exercises> allExercises;
    List<String> muscleGroup, muscleGroups;
    int image1, image2, imageMini;

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
    String rhomboids = "Rhomboids";
    String lowerBack = "Lower Back";
    String quads = "Quads";
    String glutes = "Glutes";
    String calves = "Calves";
    String hamstrings = "Hamstrings";

    public ExerciseRepository() {
        // List of all exercises
        allExercises = new ArrayList<>();
        muscleGroup = new ArrayList<>();

        // Push Up
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.pushup1;
        image2 = R.drawable.pushup2;
        imageMini = R.drawable.pushup1mini;
        Exercises pushup = new Exercises("Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/push-up", muscleGroup, image1, image2, imageMini);
        allExercises.add(pushup);

        // Diamond Push Up
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.diapushup1;
        image2 = R.drawable.diapushup2;
        imageMini = R.drawable.diapushup1mini;
        Exercises diamondPushup = new Exercises("Diamond Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/triceps/diamond-push-ups", muscleGroup, image1, image2, imageMini);
        allExercises.add(diamondPushup);

        // Dips
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(frontDelts);
        image1 = R.drawable.dips1;
        image2 = R.drawable.dips2;
        imageMini = R.drawable.dips1mini;
        Exercises dips = new Exercises("Dips", bodyweight, "https://musclewiki.com/bodyweight/male/chest/dips", muscleGroup, image1, image2, imageMini);
        allExercises.add(dips);

        // Bench Dips
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.benchdips1;
        image2 = R.drawable.benchdips2;
        imageMini = R.drawable.benchdips1mini;
        Exercises benchDips = new Exercises("Bench Dips", bodyweight, "https://musclewiki.com/bodyweight/male/triceps/bench-dips", muscleGroup, image1, image2, imageMini);
        allExercises.add(benchDips);

        // Incline Push Ups
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        image1 = R.drawable.inclinepushup1;
        image2 = R.drawable.inclinepushup2;
        imageMini = R.drawable.inclinepushup1mini;
        Exercises inclinePushUp = new Exercises("Incline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/incline-push-up", muscleGroup, image1, image2, imageMini);
        allExercises.add(inclinePushUp);

        // Decline Push Ups
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        image1 = R.drawable.declinepushup1;
        image2 = R.drawable.declinepushup2;
        imageMini = R.drawable.declinepushup1mini;
        Exercises declinePushUp = new Exercises("Decline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/decline-push-up", muscleGroup, image1, image2, imageMini);
        allExercises.add(declinePushUp);

        // Burpees
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(abdominals);
        muscleGroup.add(quads);
        image1 = R.drawable.burpee1;
        image2 = R.drawable.burpee2;
        imageMini = R.drawable.burpee1mini;
        Exercises burpees = new Exercises("Burpees", bodyweight, "https://musclewiki.com/bodyweight/male/chest/burpee", muscleGroup, image1, image2, imageMini);
        allExercises.add(burpees);

        // Bench Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.benchpress1;
        image2 = R.drawable.benchpress2;
        imageMini = R.drawable.benchpress1mini;
        Exercises benchpress = new Exercises("Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/barbell-bench-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(benchpress);

        // Close Grip Bench Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        muscleGroup.add(chest);
        image1 = R.drawable.cgbench1;
        image2 = R.drawable.cgbench2;
        imageMini = R.drawable.cgbench1mini;
        Exercises cgbenchpress = new Exercises("Close Grip Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-close-grip-bench-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(cgbenchpress);

        // Incline Bench Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.inclinebenchpress1;
        image2 = R.drawable.inclinebenchpress2;
        imageMini = R.drawable.inclinebenchpress1mini;
        Exercises inclineBenchpress = new Exercises("Incline Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/barbell-incline-bench-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(inclineBenchpress);

        // Dumbbell Incline Bench Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.dumbbellinclinepress1;
        image2 = R.drawable.dumbbellinclinepress2;
        imageMini = R.drawable.dumbbellinclinepress1mini;
        Exercises dumbbellInclineBenchpress = new Exercises("Dumbbell Incline Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/dumbbell-incline-bench-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellInclineBenchpress);

        // Dumbbell Bench Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.dumbbellpress1;
        image2 = R.drawable.dumbbellpress2;
        imageMini = R.drawable.dumbbellpress1mini;
        Exercises dumbbellBenchpress = new Exercises("Dumbbell Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/dumbbell-bench-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellBenchpress);

        // Dumbbell Incline Chest Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        image1 = R.drawable.dumbbellfly1;
        image2 = R.drawable.dumbbellfly2;
        imageMini = R.drawable.dumbbellfly1mini;
        Exercises dumbbellInclineFly = new Exercises("Dumbbell Incline Chest Fly", bodybuilding, "https://musclewiki.com/dumbbells/male/chest/dumbbell-incline-chest-flys", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellInclineFly);

        // Dumbbell Alternating Chest Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.alternatingdumbbellpress1;
        image2 = R.drawable.alternatingdumbbellpress2;
        imageMini = R.drawable.alternatingdumbbellpress1mini;
        Exercises dumbbellAlternatingPress = new Exercises("Dumbbell Alternating Single Arm Press", bodybuilding, "https://musclewiki.com/dumbbells/male/chest/dumbbell-alternating-single-arm-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellAlternatingPress);

        // Cable Chest Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.cablechestpress1;
        image2 = R.drawable.cablechestpress2;
        imageMini = R.drawable.cablechestpress1mini;
        Exercises cableChestPress = new Exercises("Cable Chest Press", bodybuilding, "https://musclewiki.com/cables/male/chest/cable-chest-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableChestPress);

        // Cable Pec Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add(chest);
        image1 = R.drawable.cablepecfly1;
        image2 = R.drawable.cablepecfly2;
        imageMini = R.drawable.cablepecfly1mini;
        Exercises cablePecFly = new Exercises("Cable Pec Fly", bodybuilding, "https://musclewiki.com/cables/male/chest/cable-pec-fly", muscleGroup, image1, image2, imageMini);
        allExercises.add(cablePecFly);

        // High-to-low Cable Pec Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add("Lower " + chest);
        image1 = R.drawable.hlcablepecfly1;
        image2 = R.drawable.hlcablepecfly2;
        imageMini = R.drawable.hlcablepecfly1mini;
        Exercises HLcablePecFly = new Exercises("High-To-Low Cable Pec Fly", bodybuilding, "https://www.youtube.com/watch?v=KwvJEXts2lg&ab_channel=PhysiqueDevelopment", muscleGroup, image1, image2, imageMini);
        allExercises.add(HLcablePecFly);

        // Cable Pec Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add("Upper " + chest);
        image1 = R.drawable.lhcablepecfly1;
        image2 = 0;
        imageMini = R.drawable.lhcablepecfly1mini;
        Exercises LHcablePecFly = new Exercises("Low-To-High Cable Pec Fly", bodybuilding, "https://the-optimal-you.com/low-cable-fly/", muscleGroup, image1, image2, imageMini);
        allExercises.add(LHcablePecFly);


        // Eleveated Pike Push Up
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.elevatedpikepushup1;
        image2 = R.drawable.elevatedpikepushup2;
        imageMini = R.drawable.elevatedpikepushup1mini;
        Exercises elevatedPikePushUp = new Exercises("Elevated Pike Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/shoulders/elevated-pike-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(elevatedPikePushUp);

        // Pike Push Up
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.pikepushup;
        image2 = 0;
        imageMini = R.drawable.pikepushupmini;
        Exercises PikePushUp = new Exercises("Pike Push Up", bodyweight, "https://strengthgang.com/bodyweight-shoulder-exercises/", muscleGroup, image1, image2, imageMini);
        allExercises.add(PikePushUp);

        // Handstand Push Up
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.hspushup1;
        image2 = R.drawable.hspushup2;
        imageMini = R.drawable.hspushup1mini;
        Exercises HandStandPushUp = new Exercises("Handstand Push Up", bodyweight, "https://www.youtube.com/watch?v=FaRge9WFzWg&ab_channel=CHRISHERIA", muscleGroup, image1, image2, imageMini);
        allExercises.add(HandStandPushUp);

        // Alternating Shoulder Taps
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        muscleGroup.add(abdominals);
        image1 = R.drawable.shouldertaps1;
        image2 = R.drawable.shouldertaps2;
        imageMini = R.drawable.shouldertaps1mini;
        Exercises shoulderTaps = new Exercises("Alternating Shoulder Taps", bodyweight, "https://www.youtube.com/watch?v=5tT6lsGVD_A&ab_channel=OnnitAcademy", muscleGroup, image1, image2, imageMini);
        allExercises.add(shoulderTaps);

        // Overhead Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.shoulderpress1;
        image2 = R.drawable.shoulderpress2;
        imageMini = R.drawable.shoulderpress1mini;
        Exercises overheadPress = new Exercises("Overhead Press", bodybuilding, "https://musclewiki.com/barbell/male/shoulders/barbell-overhead-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(overheadPress);

        // Barbell Front Raise
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.barbellfrontraise1;
        image2 = R.drawable.barbellfrontraise2;
        imageMini = R.drawable.barbellfrontraise1mini;
        Exercises barbellFrontRaise = new Exercises("Barbell Front Raise", bodybuilding, "https://musclewiki.com/barbell/male/shoulders/barbell-front-raise", muscleGroup, image1, image2, imageMini);
        allExercises.add(barbellFrontRaise);

        // Dumbbell Overhead Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.dumbbelloverheadpress1;
        image2 = R.drawable.dumbbelloverheadpress2;
        imageMini = R.drawable.dumbbelloverheadpress1mini;
        Exercises dumbbellOverheadPress = new Exercises("Dumbbell Shoulder Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-overhead-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellOverheadPress);

        // Dumbbell Neutral Overhead Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.neutralpress1;
        image2 = R.drawable.neutralpress2;
        imageMini = R.drawable.neutralpress1mini;
        Exercises dumbbellNeutralOverheadPress = new Exercises("Dumbbell Neutral Grip Shoulder Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-neutral-seated-overhead-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellNeutralOverheadPress);

        // Arnold Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.arnoldpress1;
        image2 = R.drawable.arnoldpress2;
        imageMini = R.drawable.arnoldpress1mini;
        Exercises arnoldPress = new Exercises("Arnold Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-arnold-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(arnoldPress);


        // Lateral Raise
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.latraise1;
        image2 = R.drawable.latraise2;
        imageMini = R.drawable.latraise1mini;
        Exercises lateralRaise = new Exercises("Dumbbell Lateral Raise", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-lateral-raise", muscleGroup, image1, image2, imageMini);
        allExercises.add(lateralRaise);

        // Front Raise
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.frontraise1;
        image2 = R.drawable.frontraise2;
        imageMini = R.drawable.frontraise1mini;
        Exercises frontRaise = new Exercises("Dumbbell Front Raise", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-front-raise", muscleGroup, image1, image2, imageMini);
        allExercises.add(frontRaise);

        // Rear Delt Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rearDelts);
        image1 = R.drawable.rearfly1;
        image2 = R.drawable.rearfly2;
        imageMini = R.drawable.rearfly1mini;
        Exercises rearDeltFly = new Exercises("Dumbbell Seated Rear Delt Fly", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-rear-delt-fly", muscleGroup, image1, image2, imageMini);
        allExercises.add(rearDeltFly);

        // Cable Overhead Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.cableohpress1;
        image2 = R.drawable.cableohpress2;
        imageMini = R.drawable.cableohpress1mini;
        Exercises cableOverheadPress = new Exercises("Cable Shoulder Press", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-overhead-press", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableOverheadPress);

        // Cable Lat Raise
        muscleGroup = new ArrayList<>();
        muscleGroup.add(shoulders);
        image1 = R.drawable.cablelatraise1;
        image2 = R.drawable.cablelatraise2;
        imageMini = R.drawable.cablelatraise1mini;
        Exercises cableLatRaise = new Exercises("Cable Lateral Raise", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-lateral-raise", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableLatRaise);

        // Cable Rear Delt Fly
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rearDelts);
        image1 = R.drawable.cablereardelt1;
        image2 = R.drawable.cablereardelt2;
        imageMini = R.drawable.cablereardelt1mini;
        Exercises cableRearDeltFly = new Exercises("Cable Rear Delt Fly", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-rear-delt-fly", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableRearDeltFly);

        // Barbell Skullcrushers
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.skullcrusher1;
        image2 = R.drawable.skullcrusher2;
        imageMini = R.drawable.skullcrusher1mini;
        Exercises skullcrushers = new Exercises("Barbell Skullcrushers", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-skullcrusher", muscleGroup, image1, image2, imageMini);
        allExercises.add(skullcrushers);

        // Dumbbell Skullcrushers
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.dbskullcrusher1;
        image2 = R.drawable.dbskullcrusher2;
        imageMini = R.drawable.dbskullcrusher1mini;
        Exercises dbskullcrushers = new Exercises("Dumbbell Skullcrushers", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-skullcrusher", muscleGroup, image1, image2, imageMini);
        allExercises.add(dbskullcrushers);

        // Barbell Overhead Tricep Extension
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.ohextension1;
        image2 = R.drawable.ohextension2;
        imageMini = R.drawable.ohextension1mini;
        Exercises ohtricepsextension = new Exercises("Barbell Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-overhead-tricep-extension", muscleGroup, image1, image2, imageMini);
        allExercises.add(ohtricepsextension);

        // Dumbbell Overhead Tricep Extension
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.dbohextension1;
        image2 = R.drawable.dbohextension2;
        imageMini = R.drawable.dbohextension1mini;
        Exercises dbohtricepsextension = new Exercises("Dumbbell Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-seated-overhead-tricep-extension", muscleGroup, image1, image2, imageMini);
        allExercises.add(dbohtricepsextension);

        // Dumbbell Single Arm Overhead Tricep Extension
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.odbtricepextension1;
        image2 = R.drawable.odbtricepextension2;
        imageMini = R.drawable.odbtricepextension1mini;
        Exercises odbohtricepsextension = new Exercises("Dumbbell Single Arm Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-single-arm-overhead-tricep-extension", muscleGroup, image1, image2, imageMini);
        allExercises.add(odbohtricepsextension);

        // Dumbbell Tricep Kickbacks
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.kickback1;
        image2 = R.drawable.kickback2;
        imageMini = R.drawable.kickback1mini;
        Exercises kickbacks = new Exercises("Dumbbell Tricep Kickbacks", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2, imageMini);
        allExercises.add(kickbacks);

        // Tricep Cable Pushdowns
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.pushdown1;
        image2 = R.drawable.pushdown2;
        imageMini = R.drawable.pushdown1mini;
        Exercises pushdowns = new Exercises("Tricep Cable Pushdowns", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2, imageMini);
        allExercises.add(pushdowns);

        // Cable Cross Push Downs
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.crosspushdown1;
        image2 = R.drawable.crosspushdown2;
        imageMini = R.drawable.crosspushdown1mini;
        Exercises crosspushdowns = new Exercises("Cross Cable Pushdowns", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2, imageMini);
        allExercises.add(crosspushdowns);

        // Cable Skullcrushers
        muscleGroup = new ArrayList<>();
        muscleGroup.add(triceps);
        image1 = R.drawable.cableskull1;
        image2 = R.drawable.cableskull2;
        imageMini = R.drawable.cableskull1mini;
        Exercises cableSkullcrushers = new Exercises("Cable Skullcrushers", bodybuilding, "https://musclewiki.com/cables/male/triceps/cable-skullcrusher", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableSkullcrushers);

        // Barbell Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.barbellcurl1;
        image2 = R.drawable.barbellcurl2;
        imageMini = R.drawable.barbellcurl1mini;
        Exercises bcurl = new Exercises("Barbell Curl", bodybuilding, "https://musclewiki.com/barbell/male/biceps/barbell-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(bcurl);

        // Dumbbell Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.dumbbellcurl1;
        image2 = R.drawable.dumbbellcurl2;
        imageMini = R.drawable.dumbbellcurl1mini;
        Exercises dcurl = new Exercises("Dumbbell Curl", bodybuilding, "https://musclewiki.com/dumbbells/male/biceps/dumbbell-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(dcurl);

        // Hammer Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.hammercurl1;
        image2 = R.drawable.hammercurl2;
        imageMini = R.drawable.hammercurl1mini;
        Exercises dhcurl = new Exercises("Dumbbell Hammer Curl", bodybuilding, "https://musclewiki.com/dumbbells/male/biceps/dumbbell-hammer-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(dhcurl);

        // Cross-body Hammer Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.crosscurl1;
        image2 = R.drawable.crosscurl2;
        imageMini = R.drawable.crosscurl1mini;
        Exercises crosscurl = new Exercises("Cross-body Hammer Curl", bodybuilding, "https://www.bodybuilding.com/exercises/cross-body-hammer-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(crosscurl);

        // Concentration Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.ccurl1;
        image2 = R.drawable.ccurl2;
        imageMini = R.drawable.ccurl1mini;
        Exercises ccurl = new Exercises("Concentration Curl", bodybuilding, "https://musclewiki.com/kettlebells/male/biceps/kettlebell-concentration-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(ccurl);

        // Ez-Bar Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.ezcurl1;
        image2 = R.drawable.ezcurl2;
        imageMini = R.drawable.ezcurl1mini;
        Exercises ezcurl = new Exercises("EZ-Bar Curl", bodybuilding, "https://www.bodybuilding.com/exercises/ez-bar-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(ezcurl);

        // Incline Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.inclinecurl1;
        image2 = R.drawable.inclinecurl2;
        imageMini = R.drawable.inclinecurl1mini;
        Exercises inclinecurl = new Exercises("Incline Curl", bodybuilding, "https://www.bodybuilding.com/exercises/alternate-incline-dumbbell-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(inclinecurl);

        // Incline Hammer Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.inclinehammercurl1;
        image2 = R.drawable.inclinehammercurl2;
        imageMini = R.drawable.inclinehammercurl1mini;
        Exercises inclinehammercurl = new Exercises("Incline Hammer Curl", bodybuilding, "https://www.bodybuilding.com/exercises/incline-hammer-curls", muscleGroup, image1, image2, imageMini);
        allExercises.add(inclinehammercurl);

        // Chin Ups
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.chinups1;
        image2 = R.drawable.chinups2;
        imageMini = R.drawable.chinups1mini;
        Exercises chinups = new Exercises("Chin Ups", bodyweight, "https://musclewiki.com/bodyweight/male/biceps/chin-ups", muscleGroup, image1, image2, imageMini);
        allExercises.add(chinups);

        // Pull Ups
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(traps);
        muscleGroup.add(biceps);
        image1 = R.drawable.pullups1;
        image2 = R.drawable.pullups2;
        imageMini = R.drawable.pullups1mini;
        Exercises pullups = new Exercises("Pull Ups", bodyweight, "https://musclewiki.com/bodyweight/male/traps_middle/pull-ups", muscleGroup, image1, image2, imageMini);
        allExercises.add(pullups);

        // OverHead Cable Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.ohcablecurl1;
        image2 = R.drawable.ohcablecurl2;
        imageMini = R.drawable.ohcablecurl1mini;
        Exercises ohcablecurl = new Exercises("Overhead Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/overhead-cable-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(ohcablecurl);

        // Cable Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.cablecurl1;
        image2 = R.drawable.cablecurl2;
        imageMini = R.drawable.cablecurl1mini;
        Exercises cablecurl = new Exercises("Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/overhead-cable-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(cablecurl);

        // Reverse Cable Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.revcablecurl1;
        image2 = R.drawable.revcablecurl2;
        imageMini = R.drawable.revcablecurl1mini;
        Exercises revcablecurl = new Exercises("Reverse Grip Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/reverse-cable-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(revcablecurl);

        // EZ-Bar Preacher Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(biceps);
        image1 = R.drawable.preachercurl1;
        image2 = R.drawable.preachercurl2;
        imageMini = R.drawable.preachercurl1mini;
        Exercises preachercurl = new Exercises("EZ-Bar Preacher Curl", bodybuilding, "https://www.bodybuilding.com/exercises/30-arms-ez-bar-preacher-curl", muscleGroup, image1, image2, imageMini);
        allExercises.add(preachercurl);

        // Barbell Shrug
        muscleGroup = new ArrayList<>();
        muscleGroup.add(traps);
        image1 = R.drawable.bbshrug1;
        image2 = R.drawable.bbshrug2;
        imageMini = R.drawable.bbshrug1mini;
        Exercises bbshrug = new Exercises("Barbell Shrugs", bodybuilding, "https://musclewiki.com/barbell/male/traps/barbell-shrug", muscleGroup, image1, image2, imageMini);
        allExercises.add(bbshrug);

        // Dumbbell Shrug
        muscleGroup = new ArrayList<>();
        muscleGroup.add(traps);
        image1 = R.drawable.dbshrug1;
        image2 = R.drawable.dbshrug2;
        imageMini = R.drawable.dbshrug1mini;
        Exercises dbshrug = new Exercises("Dumbbell Shrugs", bodybuilding, "https://musclewiki.com/barbell/male/traps/dumbbell-shrug", muscleGroup, image1, image2, imageMini);
        allExercises.add(dbshrug);

        // Cable Upright Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(traps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.cableupright1;
        image2 = R.drawable.cableupright2;
        imageMini = R.drawable.cableupright1mini;
        Exercises cableUprightRow = new Exercises("Cable Upright Row", bodybuilding, "https://musclewiki.com/cables/male/traps/cable-upright-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableUprightRow);

        // Inverted Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rhomboids);
        muscleGroup.add(lats);
        muscleGroup.add(traps);
        muscleGroup.add(biceps);
        image1 = R.drawable.invertedrow1;
        image2 = R.drawable.invertedrow2;
        imageMini = R.drawable.invertedrow1mini;
        Exercises invertedRow = new Exercises("Inverted Row", bodyweight, "https://musclewiki.com/bodyweight/male/traps-middle/inverted-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(invertedRow);

        // Deadlift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lowerBack);
        muscleGroup.add(traps);
        muscleGroup.add(glutes);
        image1 = R.drawable.deadlift1;
        image2 = R.drawable.deadlift2;
        imageMini = R.drawable.deadlift1mini;
        Exercises deadlift = new Exercises("Barbell Deadlift", powerlifting, "https://musclewiki.com/barbell/male/traps-middle/barbell-deadlift", muscleGroup, image1, image2, imageMini);
        allExercises.add(deadlift);

        // Dumbbell Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(traps);
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.dumbbellrow1;
        image2 = R.drawable.dumbbellrow2;
        imageMini = R.drawable.dumbbellrow1mini;
        Exercises dumbbellRow = new Exercises("Dumbbell Row", bodybuilding, "https://musclewiki.com/dumbbells/male/traps-middle/dumbbell-row-unilateral", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellRow);

        // Dumbbell Rear Delt Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rearDelts);
        muscleGroup.add(traps);
        image1 = R.drawable.dumbbellrowrd1;
        image2 = R.drawable.dumbbellrowrd2;
        imageMini = R.drawable.dumbbellrowrd1mini;
        Exercises dumbbellRDRow = new Exercises("Dumbbell Rear Delt Row", bodybuilding, "https://musclewiki.com/dumbbells/male/traps-middle/dumbbell-row-unilateral", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellRDRow);

        // Dumbbell Pullover
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        image1 = R.drawable.pullover1;
        image2 = R.drawable.pullover2;
        imageMini = R.drawable.pullover1mini;
        Exercises dumbbellPullover = new Exercises("Dumbbell Pullovers", bodybuilding, "https://musclewiki.com/dumbbells/male/lats/dumbbell-pullover", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellPullover);

        // Dumbbell Laying Incline Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.layingrow1;
        image2 = R.drawable.layingrow2;
        imageMini = R.drawable.layingrow1mini;
        Exercises dumbbellInclineRow = new Exercises("Dumbbell Laying Incline Row", bodybuilding, "https://musclewiki.com/dumbbells/male/lats/dumbbell-laying-incline-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(dumbbellInclineRow);

        // Bent Over Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.bentoverrow1;
        image2 = R.drawable.bentoverrow2;
        imageMini = R.drawable.bentoverrow1mini;
        Exercises bentOverRow = new Exercises("Barbell Bent Over Row", bodybuilding, "https://musclewiki.com/barbell/male/lats/barbell-bent-over-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(bentOverRow);

        // Bent Over Row (Pronated)
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.bentoverrow21;
        image2 = R.drawable.bentoverrow22;
        imageMini = R.drawable.bentoverrow21mini;
        Exercises bentOverRowP = new Exercises("Barbell Bent Over Row (Pronated Grip)", bodybuilding, "https://musclewiki.com/barbell/male/lats/barbell-pronated-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(bentOverRowP);

        // Lat Pull-down
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.latpulldown1;
        image2 = R.drawable.latpulldown2;
        imageMini = R.drawable.latpulldown1mini;
        Exercises latPulldown = new Exercises("Lat Pull-down", bodybuilding, "https://www.bodybuilding.com/exercises/wide-grip-lat-pulldown", muscleGroup, image1, image2, imageMini);
        allExercises.add(latPulldown);

        // Reverse Grip Lat Pull-down
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.revlatpulldown1;
        image2 = R.drawable.revlatpulldown2;
        imageMini = R.drawable.revlatpulldown1mini;
        Exercises RGlatPulldown = new Exercises("Reverse Grip Lat Pull-down", bodybuilding, "https://www.bodybuilding.com/exercises/wide-grip-lat-pulldown", muscleGroup, image1, image2, imageMini);
        allExercises.add(RGlatPulldown);

        // Reverse Grip Lat Pull-down
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.seatedrow1;
        image2 = R.drawable.seatedrow2;
        imageMini = R.drawable.seatedrow1mini;
        Exercises seatedRow = new Exercises("Seated Machine Row", bodybuilding, "https://www.bodybuilding.com/exercises/leverage-iso-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(seatedRow);

        // T-Bar Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rhomboids);
        image1 = R.drawable.tbar1;
        image2 = R.drawable.tbar2;
        imageMini = R.drawable.tbar1mini;
        Exercises tbarrow = new Exercises("T-Bar Row", bodybuilding, "https://www.bodybuilding.com/exercises/t-bar-row", muscleGroup, image1, image2, imageMini);
        allExercises.add(tbarrow);

        // Seated Cable Row
        muscleGroup = new ArrayList<>();
        muscleGroup.add(rhomboids);
        image1 = R.drawable.seatedcablerow1;
        image2 = R.drawable.seatedcablerow2;
        imageMini = R.drawable.seatedcablerow1mini;
        Exercises seatedCableRow = new Exercises("Seated Cable Row", bodybuilding, "https://www.bodybuilding.com/exercises/seated-cable-rows", muscleGroup, image1, image2, imageMini);
        allExercises.add(seatedCableRow);





        // Romanian Dead Lift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        muscleGroup.add(lowerBack);
        muscleGroup.add(glutes);
        image1 = R.drawable.dbromaniandl1;
        image2 = R.drawable.dbromaniandl2;
        imageMini = R.drawable.dbromaniandl1mini;
        Exercises romanianDeadLift = new Exercises("Dumbbell Romanian Deadlift", bodybuilding, "https://musclewiki.com/dumbbells/male/lowerback/dumbbell-romanian-deadlift/", muscleGroup, image1, image2, imageMini);
        allExercises.add(romanianDeadLift);

        // Barbell Romanian Dead Lift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        muscleGroup.add(lowerBack);
        muscleGroup.add(glutes);
        image1 = R.drawable.bbromaiandl1;
        image2 = R.drawable.bbromaiandl2;
        imageMini = R.drawable.bbromaiandl1mini;
        Exercises bbRomanianDeadLift = new Exercises("Barbell Romanian Deadlift", bodybuilding, "https://musclewiki.com/barbell/male/lowerback/barbell-romanian-deadlift/", muscleGroup, image1, image2, imageMini);
        allExercises.add(bbRomanianDeadLift);

        // Barbell Sumo Deadlift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(glutes);
        muscleGroup.add(quads);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.sumodl1;
        image2 = R.drawable.sumodl2;
        imageMini = R.drawable.sumodl1mini;
        Exercises bbSumoDeadLift = new Exercises("Barbell Sumo Deadlift", bodybuilding, "https://musclewiki.com/barbell/male/lowerback/barbell-sumo-deadlift/", muscleGroup, image1, image2, imageMini);
        allExercises.add(bbSumoDeadLift);

        // Barbell Reverse Deadlift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.reversedl1;
        image2 = R.drawable.reversedl2;
        imageMini = R.drawable.reversedl1mini;
        Exercises bbRevDeadLift = new Exercises("Barbell Reverse Deadlift", bodybuilding, "https://musclewiki.com/barbell/male/lowerback/barbell-reverse-deadlift/", muscleGroup, image1, image2, imageMini);
        allExercises.add(bbRevDeadLift);

        // Barbell Stiff Leg Deadlift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.stifflegdl1;
        image2 = R.drawable.stifflegdl2;
        imageMini = R.drawable.stifflegdl1mini;
        Exercises bbStiffDeadLift = new Exercises("Barbell Stiff Leg Deadlift", bodybuilding, "https://musclewiki.com/barbell/male/glutes/barbell-stiff-leg-deadlifts/", muscleGroup, image1, image2, imageMini);
        allExercises.add(bbStiffDeadLift);

        // Single Leg Dead Lift
        muscleGroup = new ArrayList<>();
        muscleGroup.add(glutes);
        muscleGroup.add(hamstrings);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.singlelegdl1;
        image2 = R.drawable.singlelegdl2;
        imageMini = R.drawable.singlelegdl1mini;
        Exercises singleLegDeadLift = new Exercises("Single Leg Deadlift", bodybuilding, "https://musclewiki.com/kettlebells/male/lowerback/kettlebell-single-leg-deadlift/", muscleGroup, image1, image2, imageMini);
        allExercises.add(singleLegDeadLift);

        // Kettlebell Swing
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        muscleGroup.add(lowerBack);
        muscleGroup.add(glutes);
        image1 = R.drawable.kbswing1;
        image2 = R.drawable.kbswing2;
        imageMini = R.drawable.kbswing1mini;
        Exercises kettlebellSwing = new Exercises("Kettlebell Swing", bodybuilding, "https://musclewiki.com/kettlebells/male/lowerback/kettlebell-swing/", muscleGroup, image1, image2, imageMini);
        allExercises.add(kettlebellSwing);

        // Supermans
        muscleGroup = new ArrayList<>();
        muscleGroup.add(lowerBack);
        image1 = R.drawable.supermans1;
        image2 = R.drawable.supermans2;
        imageMini = R.drawable.supermans1mini;
        Exercises supermans = new Exercises("Supermans", bodyweight, "https://musclewiki.com/bodyweight/male/lowerback/supermans/", muscleGroup, image1, image2, imageMini);
        allExercises.add(supermans);


        // Cable Pull Through
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.cablepullthrough1;
        image2 = R.drawable.cablepullthrough2;
        imageMini = R.drawable.cablepullthrough1mini;
        Exercises cablePullThrough = new Exercises("Cable Pull Through", bodybuilding, "https://musclewiki.com/cables/male/lowerback/cable-pull-through/", muscleGroup, image1, image2, imageMini);
        allExercises.add(cablePullThrough);

        // Cable Glute Kickback
        muscleGroup = new ArrayList<>();
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.glutekickback1;
        image2 = R.drawable.glutekickback2;
        imageMini = R.drawable.glutekickback1mini;
        Exercises cableGluteKickback = new Exercises("Cable Glute Kickback", bodybuilding, "https://musclewiki.com/cables/male/lowerback/cable-standing-straight-leg-glute-glute-kickback/", muscleGroup, image1, image2, imageMini);
        allExercises.add(cableGluteKickback);

        // Glute Ham Raise
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        image1 = R.drawable.glutehamraise1;
        image2 = R.drawable.glutehamraise2;
        imageMini = R.drawable.glutehamraise1mini;
        Exercises gluteHamRaise = new Exercises("Glute Ham Raise", bodyweight, "https://musclewiki.com/machine/male/hamstrings/glute-ham-raise/", muscleGroup, image1, image2, imageMini);
        allExercises.add(gluteHamRaise);

        // Hamstring Curl
        muscleGroup = new ArrayList<>();
        muscleGroup.add(hamstrings);
        image1 = R.drawable.hamstringcurl1;
        image2 = R.drawable.hamstringcurl2;
        imageMini = R.drawable.hamstringcurl1mini;
        Exercises hamstringCurl = new Exercises("Hamstring Curl", bodybuilding, "https://musclewiki.com/machine/male/hamstrings/machine-hamstring-curl/", muscleGroup, image1, image2, imageMini);
        allExercises.add(hamstringCurl);

        // Barbell Pause Box Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.pauseboxsquat1;
        image2 = R.drawable.pauseboxsquat2;
        imageMini = R.drawable.pauseboxsquat1mini;
        Exercises barbellPauseBoxSquat = new Exercises("Barbell Pause Box Squat", bodybuilding, "https://musclewiki.com/barbell/male/lowerback/barbell-pause-box-squat/", muscleGroup, image1, image2, imageMini);
        allExercises.add(barbellPauseBoxSquat);

        // Barbell Zercher Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        muscleGroup.add(lowerBack);
        image1 = R.drawable.zercher1;
        image2 = R.drawable.zercher2;
        imageMini = R.drawable.zercher1mini;
        Exercises barbellZercherSquat = new Exercises("Barbell Zercher Squat", bodybuilding, "https://musclewiki.com/barbell/male/lowerback/barbell-zercher-squat/", muscleGroup, image1, image2, imageMini);
        allExercises.add(barbellZercherSquat);

        // Barbell Front Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.frontsquat1;
        image2 = R.drawable.frontsquat2;
        imageMini = R.drawable.frontsquat1mini;
        Exercises barbellFrontSquat = new Exercises("Barbell Front Squat", bodybuilding, "https://musclewiki.com/barbell/male/glutes/barbell-front-squat-bodybuilding/", muscleGroup, image1, image2, imageMini);
        allExercises.add(barbellFrontSquat);

        // Barbell Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.squat1;
        image2 = R.drawable.squat2;
        imageMini = R.drawable.squat1mini;
        Exercises barbellSquat = new Exercises("Barbell Squat", bodybuilding, "https://musclewiki.com/barbell/male/glutes/barbell-squat/", muscleGroup, image1, image2, imageMini);
        allExercises.add(barbellSquat);

        // Jump Squats
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.jumpsquat1;
        image2 = R.drawable.jumpsquat2;
        imageMini = R.drawable.jumpsquat1mini;
        Exercises jumpSquat = new Exercises("Jump Squats", bodyweight, "https://musclewiki.com/bodyweight/male/glutes/jump-squats/", muscleGroup, image1, image2, imageMini);
        allExercises.add(jumpSquat);

        // Dumbbell Goblet Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.gobletsquat1;
        image2 = R.drawable.gobletsquat2;
        imageMini = R.drawable.gobletsquat1mini;
        Exercises gobletSquat = new Exercises("Dumbbell Goblet Squat", bodybuilding, "https://musclewiki.com/dumbbells/male/glutes/dumbbell-goblet-squat/", muscleGroup, image1, image2, imageMini);
        allExercises.add(gobletSquat);

        // Dumbbell Bulgarian Split Squat
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.splitsquat1;
        image2 = R.drawable.splitsquat2;
        imageMini = R.drawable.splitsquat1mini;
        Exercises splitSquat = new Exercises("Dumbbell Bulgarian Split Squat", bodybuilding, "https://musclewiki.com/dumbbells/male/glutes/dumbbell-bulgarian-split-squat/", muscleGroup, image1, image2, imageMini);
        allExercises.add(splitSquat);

        // Forward Lunges
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.lunges1;
        image2 = R.drawable.lunges2;
        imageMini = R.drawable.lunges1mini;
        Exercises lunges = new Exercises("Forward Lunges", bodyweight, "https://musclewiki.com/bodyweight/male/glutes/forward-lunges/", muscleGroup, image1, image2, imageMini);
        allExercises.add(lunges);

        // Barbell Curtsy Lunges
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.curtsylunge1;
        image2 = R.drawable.curtsylunge2;
        imageMini = R.drawable.curtsylunge1mini;
        Exercises curtsyLunges = new Exercises("Barbell Curtsy Lunges", bodybuilding, "https://musclewiki.com/barbell/male/glutes/barbell-curtsy-lunge/", muscleGroup, image1, image2, imageMini);
        allExercises.add(curtsyLunges);

        // Leg Press
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        muscleGroup.add(glutes);
        image1 = R.drawable.legpress1;
        image2 = R.drawable.legpress2;
        imageMini = R.drawable.legpress1mini;
        Exercises legPress = new Exercises("Leg Press", bodybuilding, "https://musclewiki.com/machine/male/glutes/machine-leg-press/", muscleGroup, image1, image2, imageMini);
        allExercises.add(legPress);

        // Leg Extensions
        muscleGroup = new ArrayList<>();
        muscleGroup.add(quads);
        image1 = R.drawable.legextension1;
        image2 = R.drawable.legextension2;
        imageMini = R.drawable.legextension1mini;
        Exercises legExtensions = new Exercises("Leg Extensions", bodybuilding, "https://musclewiki.com/machine/male/quads/machine-leg-extension/", muscleGroup, image1, image2, imageMini);
        allExercises.add(legExtensions);

        // Dumbbell Glute Bridge
        muscleGroup = new ArrayList<>();
        muscleGroup.add(glutes);
        muscleGroup.add(hamstrings);
        muscleGroup.add(quads);
        image1 = R.drawable.glutebridge1;
        image2 = R.drawable.glutebridge2;
        imageMini = R.drawable.glutebridge1mini;
        Exercises gluteBridge = new Exercises("Dumbbell Glute Bridge", bodybuilding, "https://musclewiki.com/dumbbells/male/glutes/dumbbell-glute-bridge/", muscleGroup, image1, image2, imageMini);
        allExercises.add(gluteBridge);

        // Machine Seated Calf Raises
        muscleGroup = new ArrayList<>();
        muscleGroup.add(calves);
        image1 = R.drawable.calfraises1;
        image2 = R.drawable.calfraises2;
        imageMini = R.drawable.calfraises1mini;
        Exercises seatedCalfRaises = new Exercises("Machine Seated Calf Raises", bodybuilding, "https://musclewiki.com/machine/male/calves/machine-seated-calf-raises/", muscleGroup, image1, image2, imageMini);
        allExercises.add(seatedCalfRaises);

        // Machine Standing Calf Raises
        muscleGroup = new ArrayList<>();
        muscleGroup.add(calves);
        image1 = R.drawable.standingcalfraise1;
        image2 = R.drawable.standingcalfraise2;
        imageMini = R.drawable.standingcalfraise1mini;
        Exercises standingCalfRaises = new Exercises("Machine Standing Calf Raises", bodybuilding, "https://musclewiki.com/machine/male/calves/machine-standing-calf-raises/", muscleGroup, image1, image2, imageMini);
        allExercises.add(standingCalfRaises);

        // Calf Raises
        muscleGroup = new ArrayList<>();
        muscleGroup.add(calves);
        image1 = R.drawable.bwcalfraises1;
        image2 = R.drawable.bwcalfraises2;
        imageMini = R.drawable.bwcalfraises1mini;
        Exercises calfRaises = new Exercises("Calf Raises", bodybuilding, "https://musclewiki.com/bodyweight/male/calves/calf-raises/", muscleGroup, image1, image2, imageMini);
        allExercises.add(calfRaises);
















        allExercises.sort(Comparator.comparing(Exercises::getName));
    }
}
