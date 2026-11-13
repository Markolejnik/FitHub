<div align="center">

# FitHub

**Plan your workouts, log your progress, and share your routines with the community.**

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/language-Java-ED8B00?logo=openjdk&logoColor=white)
![Backend](https://img.shields.io/badge/backend-Firebase-FFCA28?logo=firebase&logoColor=black)
![Min SDK](https://img.shields.io/badge/minSdk-26-blue)

</div>

---

## About

FitHub is an Android fitness app that combines three things together in one place:
a **built-in exercise library**, a **workout builder** and a **progress calendar**.
Create your own routines, go through them step by step while you train,
log every set you do, and publish your workouts so other users can find and rate them.

*A high school project - 4th year*

## Features

### Exercise library
- **99 built-in exercises** with two illustrative images, targeted muscles and exercise type
- Filter by **type** (Any / Bodybuilding / Bodyweight) and by **16 muscle groups** (chest, lats, quads, glutes, …)
- Search by name
- Each exercise links to a reference page with form details

### Workout builder
- Compose a custom workout from the library and set **sets and reps** per exercise (or a **time-based** target)
- Your in-progress draft is kept locally, so leaving the screen doesn't lose your progress
- Save a workout **privately** to your account, or **publish** it publicly *(requires a verified e-mail)*

### Community workouts
- Browse published workouts, newest first
- Search workouts and **upvote** the ones you like — each workout shows its vote count
- Open a workout to see its exercises and targeted muscle groups

### Workout viewer
- Step through a workout exercise by exercise (previous / next)
- Built-in **set counter** that tracks how many sets you've completed

### Calendar & progress tracking
- Pick any date and log an exercise
- Track **reps** (or **seconds**), **weight** (or bodyweight), **kg / lb** and optionally add a **note**
- Review and delete past entries for a given day

### Profile & account
- E-mail / password **registration and login** (Firebase Authentication)
- Profile with nickname, join date, **profile picture** and your published workouts
- Settings: sign out, **verify e-mail**, **reset password**, change profile picture, **delete account**

## Tech stack

| Area | Technology |
|---|---|
| Language | Java (JDK 17 for building) |
| Platform | Android — `minSdk 26`, `targetSdk 33` |
| UI | Material Components, ConstraintLayout, RecyclerView, View Binding, custom fonts (Oswald, Days One) |
| Navigation | AndroidX Navigation Component — one `MainActivity` hosting fragments, bottom navigation bar |
| State | `ViewModel` + `LiveData` shared across screens |
| Backend | Firebase **Authentication**, **Cloud Firestore**, **Cloud Storage** |
| Images | Glide |
| JSON | Gson |
| Build | Gradle 8.4, Android Gradle Plugin 8.3.1 |

## Architecture at a glance

```
app/src/main/
├── java/sk/spse/olejnik/fithub/
│   ├── LaunchActivity / MainActivity        # entry point, auth check, shared ViewModels
│   ├── LoginActivity / RegisterActivity     # Firebase email + password auth
│   ├── *Fragment                            # Exercises, Filter, ExerciseDetail, Workouts,
│   │                                        # WorkoutCreation, WorkoutDetail, WorkoutViewer,
│   │                                        # Calendar, CalendarExercise, ProgressEntry,
│   │                                        # Profile, Settings
│   ├── *ViewModel                           # Exercise, Filter, Workout, Progress
│   ├── *Adapter                             # RecyclerView adapters
│   ├── *Data / Exercises / WorkoutExercises # data models
│   └── ExerciseRepository                   # the built-in exercise catalogue
└── res/
    ├── navigation/nav_graph.xml             # all screen-to-screen navigation
    ├── layout/ · drawable/ · mipmap-*/      # UI and artwork
    └── values/ · values-night/              # strings, colours, themes
```

**Data model (Firestore)**

- `users` — one document per account: e-mail, join date, logged **progress** entries, privately saved **workouts**
- `workouts` — publicly published workouts: author, publish timestamp, **votes**, workout content

Profile pictures are stored in Firebase Storage.

## Getting started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (recent version) with **JDK 17**
- An Android device or emulator running **Android 8.0 (API 26)** or newer
- A Firebase project (see below)

### 1. Clone

```bash
git clone https://github.com/Markolejnik/FitHub.git
cd FitHub
```

### 2. Connect your own Firebase project

FitHub needs a Firebase backend to run authentication, workout storage and profile pictures.

1. Create a project in the [Firebase console](https://console.firebase.google.com/).
2. Register an Android app with the package name **`sk.spse.olejnik.fithub`**.
3. Download `google-services.json` and place it in the **`app/`** folder (replace the existing file).
4. Enable the services the app uses:
   - **Authentication → Sign-in method → Email/Password**
   - **Firestore Database** (collections `users` and `workouts` are created by the app)
   - **Storage** (for profile pictures)
5. Set sensible **security rules** for Firestore and Storage so users can only modify their own data.

### 3. Build & run

Open the project in Android Studio, let Gradle sync, then press **Run ▶**.
Or from the command line:

```bash
./gradlew assembleDebug        # Windows: gradlew.bat assembleDebug
```

The first screen redirects you to **Register** — create an account and you're in.

## Author

**Marko Olejník** — [@Markolejnik](https://github.com/Markolejnik)

## Acknowledgements

- Exercise reference links point to [MuscleWiki](https://musclewiki.com/)
- Built with [Firebase](https://firebase.google.com/), [Glide](https://github.com/bumptech/glide) and [Material Components for Android](https://github.com/material-components/material-components-android)