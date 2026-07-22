# Football Franzy

An Android app (Kotlin) for football fans — browse upcoming matches, league tables, and chat with other users. Built on Firebase for authentication/chat and the Everysport API for match data.

## Features

- **Authentication** – Sign up and log in via Firebase Auth
- **Upcoming matches** – Fetched live from the Everysport API, with search/filtering
- **League table** – Current standings for Allsvenskan
- **Group chat** – Chat with other users in real time (Firebase Firestore)
- **Splash screen** – Shown on app startup

## Tech Overview

- **Language:** Kotlin
- **UI:** Fragment-based navigation, View Binding
- **Networking:** Retrofit2 + Gson (against the Everysport API)
- **Backend:** Firebase (Authentication, Firestore, Analytics)
- **Image loading:** Glide
- **Responsive design:** sdp/ssp (Intuit) for screen-size scaling

## Project Structure and Key Files

```
footballfranzy/
├── MainActivity.kt
├── ChatAdapter.kt / ChatMessage.kt
├── splash/
│   └── SplashActivity.kt
├── auth/
│   ├── AuthActivity.kt
│   ├── LoginFragment.kt
│   ├── RegisterFragment.kt
│   └── model/UserRegisterModel.kt
├── home/
│   ├── MainHomeActivity.kt
│   ├── MainHomeFragment.kt
│   ├── WelcomeHomeFragment.kt
│   ├── UpcomingMatchesFragment.kt
│   ├── LeaugeTableFragment.kt
│   └── GroupChatFragment.kt
├── model/
│   ├── ResponseLeagueTable.kt
│   └── UserRegisterModel.kt
└── APIFootball/
    ├── MatchesAPI.kt
    ├── RetrofitClient.kt
    ├── MatchesRepository.kt
    ├── MatchesViewModel.kt
    ├── MatchesAdapter.kt / LeagueTableAdapter.kt
    └── MatchesResponse.kt / Event.kt / Team.kt / League.kt / Arena.kt
```

### `MainActivity.kt`
The app's main activity — the entry point that ties together navigation between the app's core sections.

### `splash/SplashActivity.kt`
Displays a brief splash screen when the app launches, before routing the user to login or the main view.

### Authentication (`auth/`)
- **`AuthActivity.kt`** – Host activity for the login/registration flow; manages navigation between the Login and Register fragments.
- **`LoginFragment.kt`** – UI and logic for logging in via Firebase Authentication (email/password).
- **`RegisterFragment.kt`** – UI and logic for creating a new account via Firebase Authentication.
- **`model/UserRegisterModel.kt`** – Data model representing the information submitted during registration (e.g. name, email).

### Home Views (`home/`)
- **`MainHomeActivity.kt`** – Activity that hosts the main navigation after login (e.g. a bottom navigation or drawer between Matches, League Table, and Chat).
- **`MainHomeFragment.kt` / `WelcomeHomeFragment.kt`** – Landing/welcome views shown when the user enters the app's main section.
- **`UpcomingMatchesFragment.kt`** – Fetches and displays a list of upcoming matches via `MatchesViewModel`, with search/filter functionality (`SearchView`).
- **`LeaugeTableFragment.kt`** – Fetches and displays the current league table (Allsvenskan) by calling `getLeagueStandings` directly via Retrofit, and renders the results in a `RecyclerView` using `LeagueTableAdapter`.
- **`GroupChatFragment.kt`** – The group chat feature; sends and listens for messages in real time via Firebase Firestore.

### Chat
- **`ChatMessage.kt`** – Data model for a single chat message (sender, text, timestamp).
- **`ChatAdapter.kt`** – `RecyclerView` adapter that renders chat messages in the list.

### API Layer (`APIFootball/`)
- **`RetrofitClient.kt`** – Creates and configures the Retrofit instance (base URL for the Everysport API, Gson conversion).
- **`MatchesAPI.kt`** – Defines the actual API endpoints (`@GET` calls) for fetching upcoming matches and league tables.
- **`MatchesRepository.kt`** – Middle layer that calls `MatchesAPI` and returns clean data lists to the ViewModel layer (hides Retrofit details from the rest of the app).
- **`MatchesViewModel.kt`** – Holds match data as `LiveData`, fetches it asynchronously via `MatchesRepository`, and exposes it to `UpcomingMatchesFragment`.
- **`MatchesAdapter.kt`** – `RecyclerView` adapter that displays the list of upcoming matches, including search filtering.
- **`LeagueTableAdapter.kt`** – `RecyclerView` adapter that displays league table rows (team, matches played, wins, points, etc.).
- **`MatchesResponse.kt`, `Event.kt`, `Team.kt`, `League.kt`, `Arena.kt`** – Data models (mapped to the Everysport API's JSON responses) representing a match, a team, a league, and an arena respectively.
- **`ResponseLeagueTable.kt`** (in `model/`) – Data model for the response from the league table endpoint.




## Security
API keys and Firebase configuration are kept out of the source code via `local.properties` and `.gitignore`. Make sure never to commit these files if you fork or contribute to the project.

## Screenshots

| Feature | File |
|---|---|
| Login | `Skärmbild 2026-07-23 010942.png` |
| Register | `Skärmbild 2026-07-23 011104.png` |
| League Table | `Skärmbild 2026-07-23 011422.png` |
| Matches | `Skärmbild 2026-07-23 011445.png` |
| Chat | `Skärmbild 2026-07-23 011508.png` |

![Login](Skärmbild%202026-07-23%20010942.png)
![Register](Skärmbild%202026-07-23%20011104.png)
![League Table](Skärmbild%202026-07-23%20011422.png)
![Matches](Skärmbild%202026-07-23%20011445.png)
![Chat](Skärmbild%202026-07-23%20011508.png)

