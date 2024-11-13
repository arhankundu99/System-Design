# Cricbuzz
## Requirements
- Players can be added / removed with profile. 
- Teams can be added / removed. Players can be assigned to teams.
- Matches can be added at any dates and can be mapped to a tournament.
- Tournament contain matches.
- Venues
- Scorecard
- Commentary (Ball by ball) for a match.
- For each match, each team would announce their playing 11.


## Entities
- `IndividualBattingStats`
```
PlayerId, match_type, runs, average, number of matches, world ranking
```

- `Player`
```
id, name, age, batsman/bowler/wk/allrounder, teams

IndividualBattingStats (For odi, test and t20)

Likewise for bowling / wk
```

- `Team`
```
id, name, players
```

- `Match`
```
id, teams, date, start_time, end_time, team1_playing11, team2_playing11, stadium_id, stadium_details
```

- `Stadum`
```
id, location, capacity
```

- `ODIMatch` extends `Match`, Likewise `T20Match` and `TestMatch`. Use `Factory pattern here`

- `Tournament`
```
id, matches, start_date, end_date
```

- `IndividualBattingScore`
```
player_id, inning_id, match_id, player_name, score, num_sixes, num_fours, dismissed_by, dismissed_by_id, position
```
- `Innings`
```
id, match_id, innings_number, List<IndividualBattingScore>, List<IndividualBowlingScore>
```
- `Scorecard`
```
id, match_id, List<Innings> inningsList
```

- `commentary_message`
```
message_id, message, match_id, timestamp
```
- `Commentary`
```
List<commentary_message> messages
```