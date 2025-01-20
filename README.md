# Cricket Scoring App

## Description

This project is a fully offline, no-login-required cricket scoring application designed for Android. It simulates and tracks every aspect of a cricket match, adhering to the official MCC rules, providing users with an immersive and seamless experience. The app features a robust design that enables the efficient tracking of runs, wickets, overs, and partnerships while supporting multiple innings.

 ## Key Features:
- **Offline Scoring**: No internet connection is required to track and simulate the match.
- **Match Progress Syncing**: Uses File Sharing URIs to seamlessly synchronize match progress across multiple devices, allowing users to pick up right where they left off.
- **Modular Architecture**: Built in Java using Android Studio, the app focuses on scalability, maintainability, and long-term performance.
- **SQLite Database Integration**: Stores match data, player stats, and user preferences offline for quick access and minimal resource usage.
- **User Experience**: Features an intuitive, visually appealing UI built with XML, designed for real-time score updates during matches.
   
## Usage

1. **Start a New Match**  
   Enter the details of the teams, players, and the initial setup.  
   ![Team Setup Screen](screenshots/team_setup.png)

2. **Track Balls and Runs**  
   Input the outcome of each ball (e.g., runs, no-ball, wide, etc.) to update the stats for batsmen and bowlers.  
   ![Scoring Screen](screenshots/scoring_screen.png)

3. **Handle Wickets**  
   Input the type of wicket when a batsman gets out, and the app will update the striker and stats accordingly.  
   ![Wicket Handling Screen](screenshots/wicket_handling.png)

4. **Undo Last Action**  
   Use the undo functionality to revert to the previous state after any ball.  
   ![Undo Action Screen](screenshots/undo_action.png)


## Technologies Used
-  Android Studio
-  Java
-  XML
-  SQLite

## Future Enhancements
- Implementing a graphical user interface (GUI).
- 
- Support for multiple match formats (e.g., Test, ODI, T20).
- Integration with real-time match data.

## Contributors
- **Your Name** - Initial development

