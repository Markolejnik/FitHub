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
    String rhomboids = "Rhomboids";
    String lowerBack = "Lower Back";
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
        image1 = R.drawable.pushup1;
        image2 = R.drawable.pushup2;
        Exercises pushup = new Exercises("Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/push-up", muscleGroup, image1, image2);
        allExercises.add(pushup);
        muscleGroup.removeAll(muscleGroup);

        // Diamond Push Up
        muscleGroup.add(triceps);
        image1 = R.drawable.diapushup1;
        image2 = R.drawable.diapushup2;
        Exercises diamondPushup = new Exercises("Diamond Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/triceps/diamond-push-ups", muscleGroup, image1, image2);
        allExercises.add(diamondPushup);
        muscleGroup.removeAll(muscleGroup);

        // Dips
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(frontDelts);
        image1 = R.drawable.dips1;
        image2 = R.drawable.dips2;
        Exercises dips = new Exercises("Dips", bodyweight, "https://musclewiki.com/bodyweight/male/chest/dips", muscleGroup, image1, image2);
        allExercises.add(dips);
        muscleGroup.removeAll(muscleGroup);

        // Bench Dips
        muscleGroup.add(triceps);
        image1 = R.drawable.benchdips1;
        image2 = R.drawable.benchdips2;
        Exercises benchDips = new Exercises("Bench Dips", bodyweight, "https://musclewiki.com/bodyweight/male/triceps/bench-dips", muscleGroup, image1, image2);
        allExercises.add(benchDips);
        muscleGroup.removeAll(muscleGroup);

        // Incline Push Ups
        muscleGroup.add(chest);
        image1 = R.drawable.inclinepushup1;
        image2 = R.drawable.inclinepushup2;
        Exercises inclinePushUp = new Exercises("Incline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/incline-push-up", muscleGroup, image1, image2);
        allExercises.add(inclinePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Decline Push Ups
        muscleGroup.add(chest);
        image1 = R.drawable.declinepushup1;
        image2 = R.drawable.declinepushup2;
        Exercises declinePushUp = new Exercises("Decline Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/chest/decline-push-up", muscleGroup, image1, image2);
        allExercises.add(declinePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Burpees
        muscleGroup.add(chest);
        muscleGroup.add(abdominals);
        muscleGroup.add(quads);
        image1 = R.drawable.burpee1;
        image2 = R.drawable.burpee2;
        Exercises burpees = new Exercises("Burpees", bodyweight, "https://musclewiki.com/bodyweight/male/chest/burpee", muscleGroup, image1, image2);
        allExercises.add(burpees);
        muscleGroup.removeAll(muscleGroup);

        // Bench Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.benchpress1;
        image2 = R.drawable.benchpress2;
        Exercises benchpress = new Exercises("Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/barbell-bench-press", muscleGroup, image1, image2);
        allExercises.add(benchpress);
        muscleGroup.removeAll(muscleGroup);

        // Close Grip Bench Press
        muscleGroup.add(triceps);
        muscleGroup.add(chest);
        image1 = R.drawable.cgbench1;
        image2 = R.drawable.cgbench2;
        Exercises cgbenchpress = new Exercises("Close Grip Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-close-grip-bench-press", muscleGroup, image1, image2);
        allExercises.add(cgbenchpress);
        muscleGroup.removeAll(muscleGroup);

        // Incline Bench Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.inclinebenchpress1;
        image2 = R.drawable.inclinebenchpress2;
        Exercises inclineBenchpress = new Exercises("Incline Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/barbell-incline-bench-press", muscleGroup, image1, image2);
        allExercises.add(inclineBenchpress);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Incline Bench Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.dumbbellinclinepress1;
        image2 = R.drawable.dumbbellinclinepress2;
        Exercises dumbbellInclineBenchpress = new Exercises("Dumbbell Incline Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/dumbbell-incline-bench-press", muscleGroup, image1, image2);
        allExercises.add(dumbbellInclineBenchpress);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Bench Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.dumbbellinclinepress1;
        image2 = R.drawable.dumbbellinclinepress2;
        Exercises dumbbellBenchpress = new Exercises("Dumbbell Incline Bench Press", bodybuilding, "https://musclewiki.com/barbell/male/chest/dumbbell-bench-press", muscleGroup, image1, image2);
        allExercises.add(dumbbellBenchpress);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Incline Chest Fly
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.dumbbellfly1;
        image2 = R.drawable.dumbbellfly2;
        Exercises dumbbellInclineFly = new Exercises("Dumbbell Incline Bench Press", bodybuilding, "https://musclewiki.com/dumbbells/male/chest/dumbbell-incline-chest-flys", muscleGroup, image1, image2);
        allExercises.add(dumbbellInclineFly);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Alternating Chest Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.alternatingdumbbellpress1;
        image2 = R.drawable.alternatingdumbbellpress2;
        Exercises dumbbellAlternatingPress = new Exercises("Dumbbell Alternating Single Arm Press", bodybuilding, "https://musclewiki.com/dumbbells/male/chest/dumbbell-alternating-single-arm-press", muscleGroup, image1, image2);
        allExercises.add(dumbbellAlternatingPress);
        muscleGroup.removeAll(muscleGroup);

        // Cable Chest Press
        muscleGroup.add(chest);
        muscleGroup.add(triceps);
        image1 = R.drawable.cablechestpress1;
        image2 = R.drawable.cablechestpress2;
        Exercises cableChestPress = new Exercises("Cable Chest Press", bodybuilding, "https://musclewiki.com/cables/male/chest/cable-chest-press", muscleGroup, image1, image2);
        allExercises.add(cableChestPress);
        muscleGroup.removeAll(muscleGroup);

        // Cable Pec Fly
        muscleGroup.add(chest);
        image1 = R.drawable.cablepecfly1;
        image2 = R.drawable.cablepecfly2;
        Exercises cablePecFly = new Exercises("Cable Pec Fly", bodybuilding, "https://musclewiki.com/cables/male/chest/cable-pec-fly", muscleGroup, image1, image2);
        allExercises.add(cablePecFly);
        muscleGroup.removeAll(muscleGroup);

        // High-to-low Cable Pec Fly
        muscleGroup.add("Lower " + chest);
        image1 = R.drawable.hlcablepecfly1;
        image2 = R.drawable.hlcablepecfly2;
        Exercises HLcablePecFly = new Exercises("High-To-Low Cable Pec Fly", bodybuilding, "https://www.youtube.com/watch?v=KwvJEXts2lg&ab_channel=PhysiqueDevelopment", muscleGroup, image1, image2);
        allExercises.add(HLcablePecFly);
        muscleGroup.removeAll(muscleGroup);

        // Cable Pec Fly
        muscleGroup.add("Upper " + chest);
        image1 = R.drawable.lhcablepecfly1;
        image2 = 0;
        Exercises LHcablePecFly = new Exercises("Low-To-High Cable Pec Fly", bodybuilding, "https://the-optimal-you.com/low-cable-fly/", muscleGroup, image1, image2);
        allExercises.add(LHcablePecFly);
        muscleGroup.removeAll(muscleGroup);


        // Eleveated Pike Push Up
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.elevatedpikepushup1;
        image2 = R.drawable.elevatedpikepushup2;
        Exercises elevatedPikePushUp = new Exercises("Elevated Pike Push Up", bodyweight, "https://musclewiki.com/bodyweight/male/shoulders/elevated-pike-press", muscleGroup, image1, image2);
        allExercises.add(elevatedPikePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Pike Push Up
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.pikepushup;
        image2 = 0;
        Exercises PikePushUp = new Exercises("Pike Push Up", bodyweight, "https://strengthgang.com/bodyweight-shoulder-exercises/", muscleGroup, image1, image2);
        allExercises.add(PikePushUp);
        muscleGroup.removeAll(muscleGroup);

        // Handstand Push Up
        muscleGroup.add(shoulders);
        muscleGroup.add(triceps);
        image1 = R.drawable.hspushup1;
        image2 = R.drawable.hspushup2;
        Exercises HandStandPushUp = new Exercises("Handstand Push Up", bodyweight, "https://www.youtube.com/watch?v=FaRge9WFzWg&ab_channel=CHRISHERIA", muscleGroup, image1, image2);
        allExercises.add(HandStandPushUp);
        muscleGroup.removeAll(muscleGroup);

        // Alternating Shoulder Taps
        muscleGroup.add(shoulders);
        muscleGroup.add(abdominals);
        image1 = R.drawable.shouldertaps1;
        image2 = R.drawable.shouldertaps2;
        Exercises shoulderTaps = new Exercises("Alternating Shoulder Taps", bodyweight, "https://www.youtube.com/watch?v=5tT6lsGVD_A&ab_channel=OnnitAcademy", muscleGroup, image1, image2);
        allExercises.add(shoulderTaps);
        muscleGroup.removeAll(muscleGroup);

        // Overhead Press
        muscleGroup.add(shoulders);
        image1 = R.drawable.shoulderpress1;
        image2 = R.drawable.shoulderpress2;
        Exercises overheadPress = new Exercises("Overhead Press", bodybuilding, "https://musclewiki.com/barbell/male/shoulders/barbell-overhead-press", muscleGroup, image1, image2);
        allExercises.add(overheadPress);
        muscleGroup.removeAll(muscleGroup);

        // Barbell Front Raise
        muscleGroup.add(shoulders);
        image1 = R.drawable.barbellfrontraise1;
        image2 = R.drawable.barbellfrontraise2;
        Exercises barbellFrontRaise = new Exercises("Barbell Front Raise", bodybuilding, "https://musclewiki.com/barbell/male/shoulders/barbell-front-raise", muscleGroup, image1, image2);
        allExercises.add(barbellFrontRaise);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Overhead Press
        muscleGroup.add(shoulders);
        image1 = R.drawable.dumbbelloverheadpress1;
        image2 = R.drawable.dumbbelloverheadpress2;
        Exercises dumbbellOverheadPress = new Exercises("Dumbbell Overhead Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-overhead-press", muscleGroup, image1, image2);
        allExercises.add(dumbbellOverheadPress);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Neutral Overhead Press
        muscleGroup.add(shoulders);
        image1 = R.drawable.neutralpress1;
        image2 = R.drawable.neutralpress2;
        Exercises dumbbellNeutralOverheadPress = new Exercises("Dumbbell Neutral Overhead Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-neutral-seated-overhead-press", muscleGroup, image1, image2);
        allExercises.add(dumbbellNeutralOverheadPress);
        muscleGroup.removeAll(muscleGroup);

        // Arnold Press
        muscleGroup.add(shoulders);
        image1 = R.drawable.arnoldpress1;
        image2 = R.drawable.arnoldpress2;
        Exercises arnoldPress = new Exercises("Arnold Press", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-arnold-press", muscleGroup, image1, image2);
        allExercises.add(arnoldPress);
        muscleGroup.removeAll(muscleGroup);

        // Lateral Raise
        muscleGroup.add(shoulders);
        image1 = R.drawable.latraise1;
        image2 = R.drawable.latraise2;
        Exercises lateralRaise = new Exercises("Dumbbell Lateral Raise", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-lateral-raise", muscleGroup, image1, image2);
        allExercises.add(lateralRaise);
        muscleGroup.removeAll(muscleGroup);

        // Front Raise
        muscleGroup.add(shoulders);
        image1 = R.drawable.frontraise1;
        image2 = R.drawable.frontraise2;
        Exercises frontRaise = new Exercises("Dumbbell Front Raise", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-front-raise", muscleGroup, image1, image2);
        allExercises.add(frontRaise);
        muscleGroup.removeAll(muscleGroup);

        // Rear Delt Fly
        muscleGroup.add(rearDelts);
        image1 = R.drawable.frontraise1;
        image2 = R.drawable.frontraise2;
        Exercises rearDeltFly = new Exercises("Dumbbell Seated Rear Delt Fly", bodybuilding, "https://musclewiki.com/dumbbells/male/shoulders/dumbbell-seated-rear-delt-fly", muscleGroup, image1, image2);
        allExercises.add(rearDeltFly);
        muscleGroup.removeAll(muscleGroup);

        // Cable Overhead Press
        muscleGroup.add(shoulders);
        image1 = R.drawable.cableohpress1;
        image2 = R.drawable.cableohpress2;
        Exercises cableOverheadPress = new Exercises("Cable Overhead Press", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-overhead-press", muscleGroup, image1, image2);
        allExercises.add(cableOverheadPress);
        muscleGroup.removeAll(muscleGroup);

        // Cable Lat Raise
        muscleGroup.add(shoulders);
        image1 = R.drawable.cablelatraise1;
        image2 = R.drawable.cablelatraise2;
        Exercises cableLatRaise = new Exercises("Cable Lateral Raise", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-lateral-raise", muscleGroup, image1, image2);
        allExercises.add(cableLatRaise);
        muscleGroup.removeAll(muscleGroup);

        // Cable Rear Delt Fly
        muscleGroup.add(rearDelts);
        image1 = R.drawable.cablereardelt1;
        image2 = R.drawable.cablereardelt2;
        Exercises cableRearDeltFly = new Exercises("Cable Rear Delt Fly", bodybuilding, "https://musclewiki.com/cables/male/shoulders/cable-rear-delt-fly", muscleGroup, image1, image2);
        allExercises.add(cableRearDeltFly);
        muscleGroup.removeAll(muscleGroup);

        // Barbell Skullcrushers
        muscleGroup.add(triceps);
        image1 = R.drawable.skullcrusher1;
        image2 = R.drawable.skullcrusher2;
        Exercises skullcrushers = new Exercises("Barbell Skullcrushers", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-skullcrusher", muscleGroup, image1, image2);
        allExercises.add(skullcrushers);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Skullcrushers
        muscleGroup.add(triceps);
        image1 = R.drawable.dbskullcrusher1;
        image2 = R.drawable.dbskullcrusher2;
        Exercises dbskullcrushers = new Exercises("Dumbbell Skullcrushers", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-skullcrusher", muscleGroup, image1, image2);
        allExercises.add(dbskullcrushers);
        muscleGroup.removeAll(muscleGroup);

        // Barbell Overhead Tricep Extension
        muscleGroup.add(triceps);
        image1 = R.drawable.ohextension1;
        image2 = R.drawable.ohextension2;
        Exercises ohtricepsextension = new Exercises("Barbell Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/barbell/male/triceps/barbell-overhead-tricep-extension", muscleGroup, image1, image2);
        allExercises.add(ohtricepsextension);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Overhead Tricep Extension
        muscleGroup.add(triceps);
        image1 = R.drawable.dbohextension1;
        image2 = R.drawable.dbohextension2;
        Exercises dbohtricepsextension = new Exercises("Dumbbell Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-seated-overhead-tricep-extension", muscleGroup, image1, image2);
        allExercises.add(dbohtricepsextension);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Single Arm Overhead Tricep Extension
        muscleGroup.add(triceps);
        image1 = R.drawable.odbtricepextension1;
        image2 = R.drawable.odbtricepextension2;
        Exercises odbohtricepsextension = new Exercises("Dumbbell Single Arm Overhead Tricep Extension", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-single-arm-overhead-tricep-extension", muscleGroup, image1, image2);
        allExercises.add(odbohtricepsextension);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Tricep Kickbacks
        muscleGroup.add(triceps);
        image1 = R.drawable.kickback1;
        image2 = R.drawable.kickback2;
        Exercises kickbacks = new Exercises("Dumbbell Tricep Kickbacks", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2);
        allExercises.add(kickbacks);
        muscleGroup.removeAll(muscleGroup);

        // Tricep Cable Pushdowns
        muscleGroup.add(triceps);
        image1 = R.drawable.pushdown1;
        image2 = R.drawable.pushdown2;
        Exercises pushdowns = new Exercises("Tricep Cable Pushdowns", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2);
        allExercises.add(pushdowns);
        muscleGroup.removeAll(muscleGroup);

        // Cable Cross Push Downs
        muscleGroup.add(triceps);
        image1 = R.drawable.crosspushdown1;
        image2 = R.drawable.crosspushdown2;
        Exercises crosspushdowns = new Exercises("Cross Cable Pushdowns", bodybuilding, "https://musclewiki.com/dumbbells/male/triceps/dumbbell-tricep-kickback", muscleGroup, image1, image2);
        allExercises.add(crosspushdowns);
        muscleGroup.removeAll(muscleGroup);

        // Cable Skullcrushers
        muscleGroup.add(triceps);
        image1 = R.drawable.cableskull1;
        image2 = R.drawable.cableskull2;
        Exercises cableSkullcrushers = new Exercises("Cable Skullcrushers", bodybuilding, "https://musclewiki.com/cables/male/triceps/cable-skullcrusher", muscleGroup, image1, image2);
        allExercises.add(cableSkullcrushers);
        muscleGroup.removeAll(muscleGroup);

        // Barbell Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.barbellcurl1;
        image2 = R.drawable.barbellcurl2;
        Exercises bcurl = new Exercises("Barbell Curl", bodybuilding, "https://musclewiki.com/barbell/male/biceps/barbell-curl", muscleGroup, image1, image2);
        allExercises.add(bcurl);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.dumbbellcurl1;
        image2 = R.drawable.dumbbellcurl2;
        Exercises dcurl = new Exercises("Dumbbell Curl", bodybuilding, "https://musclewiki.com/dumbbells/male/biceps/dumbbell-curl", muscleGroup, image1, image2);
        allExercises.add(dcurl);
        muscleGroup.removeAll(muscleGroup);

        // Hammer Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.hammercurl1;
        image2 = R.drawable.hammercurl2;
        Exercises dhcurl = new Exercises("Dumbbell Hammer Curl", bodybuilding, "https://musclewiki.com/dumbbells/male/biceps/dumbbell-hammer-curl", muscleGroup, image1, image2);
        allExercises.add(dhcurl);
        muscleGroup.removeAll(muscleGroup);

        // Cross-body Hammer Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.crosscurl1;
        image2 = R.drawable.crosscurl2;
        Exercises crosscurl = new Exercises("Cross-body Hammer Curl", bodybuilding, "https://www.bodybuilding.com/exercises/cross-body-hammer-curl", muscleGroup, image1, image2);
        allExercises.add(crosscurl);
        muscleGroup.removeAll(muscleGroup);

        // Concentration Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.ccurl1;
        image2 = R.drawable.ccurl2;
        Exercises ccurl = new Exercises("Concentration Curl", bodybuilding, "https://musclewiki.com/kettlebells/male/biceps/kettlebell-concentration-curl", muscleGroup, image1, image2);
        allExercises.add(ccurl);
        muscleGroup.removeAll(muscleGroup);

        // Ez-Bar Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.ezcurl1;
        image2 = R.drawable.ezcurl2;
        Exercises ezcurl = new Exercises("EZ-Bar Curl", bodybuilding, "https://www.bodybuilding.com/exercises/ez-bar-curl", muscleGroup, image1, image2);
        allExercises.add(ezcurl);
        muscleGroup.removeAll(muscleGroup);

        // Incline Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.inclinecurl1;
        image2 = R.drawable.inclinecurl2;
        Exercises inclinecurl = new Exercises("Incline Curl", bodybuilding, "https://www.bodybuilding.com/exercises/alternate-incline-dumbbell-curl", muscleGroup, image1, image2);
        allExercises.add(inclinecurl);
        muscleGroup.removeAll(muscleGroup);

        // Incline Hammer Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.inclinehammercurl1;
        image2 = R.drawable.inclinehammercurl2;
        Exercises inclinehammercurl = new Exercises("Incline Hammer Curl", bodybuilding, "https://www.bodybuilding.com/exercises/incline-hammer-curls", muscleGroup, image1, image2);
        allExercises.add(inclinehammercurl);
        muscleGroup.removeAll(muscleGroup);

        // Chin Ups
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.chinups1;
        image2 = R.drawable.chinups2;
        Exercises chinups = new Exercises("Chin Ups", bodyweight, "https://musclewiki.com/bodyweight/male/biceps/chin-ups", muscleGroup, image1, image2);
        allExercises.add(chinups);
        muscleGroup.removeAll(muscleGroup);

        // Pull Ups
        muscleGroup.add(lats);
        muscleGroup.add(traps);
        muscleGroup.add(biceps);
        image1 = R.drawable.pullups1;
        image2 = R.drawable.pullups2;
        Exercises pullups = new Exercises("Pull Ups", bodyweight, "https://musclewiki.com/bodyweight/male/traps_middle/pull-ups", muscleGroup, image1, image2);
        allExercises.add(pullups);
        muscleGroup.removeAll(muscleGroup);

        // OverHead Cable Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.ohcablecurl1;
        image2 = R.drawable.ohcablecurl2;
        Exercises ohcablecurl = new Exercises("Overhead Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/overhead-cable-curl", muscleGroup, image1, image2);
        allExercises.add(ohcablecurl);
        muscleGroup.removeAll(muscleGroup);

        // Cable Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.cablecurl1;
        image2 = R.drawable.cablecurl2;
        Exercises cablecurl = new Exercises("Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/overhead-cable-curl", muscleGroup, image1, image2);
        allExercises.add(cablecurl);
        muscleGroup.removeAll(muscleGroup);

        // Reverse Cable Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.revcablecurl1;
        image2 = R.drawable.revcablecurl2;
        Exercises revcablecurl = new Exercises("Reverse Grip Cable Curl", bodybuilding, "https://www.bodybuilding.com/exercises/reverse-cable-curl", muscleGroup, image1, image2);
        allExercises.add(revcablecurl);
        muscleGroup.removeAll(muscleGroup);

        // EZ-Bar Preacher Curl
        muscleGroup.add(biceps);
        image1 = R.drawable.preachercurl1;
        image2 = R.drawable.preachercurl2;
        Exercises preachercurl = new Exercises("EZ-Bar Preacher Curl", bodybuilding, "https://www.bodybuilding.com/exercises/30-arms-ez-bar-preacher-curl", muscleGroup, image1, image2);
        allExercises.add(preachercurl);
        muscleGroup.removeAll(muscleGroup);

        // Barbell Shrug
        muscleGroup.add(traps);
        image1 = R.drawable.bbshrug1;
        image2 = R.drawable.bbshrug2;
        Exercises bbshrug = new Exercises("Barbell Shrugs", bodybuilding, "https://musclewiki.com/barbell/male/traps/barbell-shrug", muscleGroup, image1, image2);
        allExercises.add(bbshrug);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Shrug
        muscleGroup.add(traps);
        image1 = R.drawable.dbshrug1;
        image2 = R.drawable.dbshrug2;
        Exercises dbshrug = new Exercises("Dumbbell Shrugs", bodybuilding, "https://musclewiki.com/barbell/male/traps/dumbbell-shrug", muscleGroup, image1, image2);
        allExercises.add(dbshrug);
        muscleGroup.removeAll(muscleGroup);

        // Cable Upright Row
        muscleGroup.add(traps);
        muscleGroup.add(shoulders);
        image1 = R.drawable.cableupright1;
        image2 = R.drawable.cableupright2;
        Exercises cableUprightRow = new Exercises("Cable Upright Row", bodybuilding, "https://musclewiki.com/cables/male/traps/cable-upright-row", muscleGroup, image1, image2);
        allExercises.add(cableUprightRow);
        muscleGroup.removeAll(muscleGroup);

        // Inverted Row
        muscleGroup.add(rhomboids);
        muscleGroup.add(lats);
        muscleGroup.add(traps);
        muscleGroup.add(biceps);
        image1 = R.drawable.invertedrow1;
        image2 = R.drawable.invertedrow2;
        Exercises invertedRow = new Exercises("Inverted Row", bodyweight, "https://musclewiki.com/bodyweight/male/traps-middle/inverted-row", muscleGroup, image1, image2);
        allExercises.add(invertedRow);
        muscleGroup.removeAll(muscleGroup);

        // Deadlift
        muscleGroup.add(lowerBack);
        muscleGroup.add(traps);
        muscleGroup.add(glutes);
        image1 = R.drawable.deadlift1;
        image2 = R.drawable.deadlift2;
        Exercises deadlift = new Exercises("Barbell Deadlift", powerlifting, "https://musclewiki.com/barbell/male/traps-middle/barbell-deadlift", muscleGroup, image1, image2);
        allExercises.add(deadlift);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Row
        muscleGroup.add(traps);
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.dumbbellrow1;
        image2 = R.drawable.dumbbellrow2;
        Exercises dumbbellRow = new Exercises("Dumbbell Row", bodybuilding, "https://musclewiki.com/dumbbells/male/traps-middle/dumbbell-row-unilateral", muscleGroup, image1, image2);
        allExercises.add(dumbbellRow);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Rear Delt Row
        muscleGroup.add(rearDelts);
        muscleGroup.add(traps);
        image1 = R.drawable.dumbbellrowrd1;
        image2 = R.drawable.dumbbellrowrd2;
        Exercises dumbbellRDRow = new Exercises("Dumbbell Rear Delt Row", bodybuilding, "https://musclewiki.com/dumbbells/male/traps-middle/dumbbell-row-unilateral", muscleGroup, image1, image2);
        allExercises.add(dumbbellRDRow);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Pullover
        muscleGroup.add(lats);
        image1 = R.drawable.pullover1;
        image2 = R.drawable.pullover2;
        Exercises dumbbellPullover = new Exercises("Dumbbell Pullovers", bodybuilding, "https://musclewiki.com/dumbbells/male/lats/dumbbell-pullover", muscleGroup, image1, image2);
        allExercises.add(dumbbellPullover);
        muscleGroup.removeAll(muscleGroup);

        // Dumbbell Laying Incline Row
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.layingrow1;
        image2 = R.drawable.layingrow2;
        Exercises dumbbellInclineRow = new Exercises("Dumbbell Laying Incline Row", bodybuilding, "https://musclewiki.com/dumbbells/male/lats/dumbbell-laying-incline-row", muscleGroup, image1, image2);
        allExercises.add(dumbbellInclineRow);
        muscleGroup.removeAll(muscleGroup);

        // Bent Over Row
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.bentoverrow1;
        image2 = R.drawable.bentoverrow2;
        Exercises bentOverRow = new Exercises("Barbell Bent Over Row", bodybuilding, "https://musclewiki.com/barbell/male/lats/barbell-bent-over-row", muscleGroup, image1, image2);
        allExercises.add(bentOverRow);
        muscleGroup.removeAll(muscleGroup);

        // Bent Over Row (Pronated)
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.bentoverrow21;
        image2 = R.drawable.bentoverrow22;
        Exercises bentOverRowP = new Exercises("Barbell Bent Over Row (Pronated Grip)", bodybuilding, "https://musclewiki.com/barbell/male/lats/barbell-pronated-row", muscleGroup, image1, image2);
        allExercises.add(bentOverRowP);
        muscleGroup.removeAll(muscleGroup);

        // Lat Pull-down
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.latpulldown1;
        image2 = R.drawable.latpulldown2;
        Exercises latPulldown = new Exercises("Lat Pull-down", bodybuilding, "https://www.bodybuilding.com/exercises/wide-grip-lat-pulldown", muscleGroup, image1, image2);
        allExercises.add(latPulldown);
        muscleGroup.removeAll(muscleGroup);

        // Reverse Grip Lat Pull-down
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.revlatpulldown1;
        image2 = R.drawable.revlatpulldown2;
        Exercises RGlatPulldown = new Exercises("Reverse Grip Lat Pull-down", bodybuilding, "https://www.bodybuilding.com/exercises/wide-grip-lat-pulldown", muscleGroup, image1, image2);
        allExercises.add(RGlatPulldown);
        muscleGroup.removeAll(muscleGroup);

        // Reverse Grip Lat Pull-down
        muscleGroup.add(lats);
        muscleGroup.add(biceps);
        image1 = R.drawable.seatedrow1;
        image2 = R.drawable.seatedrow2;
        Exercises seatedRow = new Exercises("Seated Machine Row", bodybuilding, "https://www.bodybuilding.com/exercises/leverage-iso-row", muscleGroup, image1, image2);
        allExercises.add(seatedRow);
        muscleGroup.removeAll(muscleGroup);

        // T-Bar Row
        muscleGroup.add(rhomboids);
        image1 = R.drawable.tbar1;
        image2 = R.drawable.tbar2;
        Exercises tbarrow = new Exercises("T-Bar Row", bodybuilding, "https://www.bodybuilding.com/exercises/t-bar-row", muscleGroup, image1, image2);
        allExercises.add(tbarrow);
        muscleGroup.removeAll(muscleGroup);

        // Seated Cable Row
        muscleGroup.add(rhomboids);
        image1 = R.drawable.tbar1;
        image2 = R.drawable.tbar2;
        Exercises seatedCableRow = new Exercises("Seated Cable Row", bodybuilding, "https://www.bodybuilding.com/exercises/seated-cable-rows", muscleGroup, image1, image2);
        allExercises.add(seatedCableRow);
        muscleGroup.removeAll(muscleGroup);
















        allExercises.sort(Comparator.comparing(Exercises::getName));
    }
}
