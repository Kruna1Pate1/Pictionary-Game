## :magic_wand: Requirement & Assumption for Pictionary Game :scroll:

### Requirements :label:

- Player creates room
- System generate room with gameId (6: alphanumeric)
- Player see list of available rooms with player count
- Game has multiple rounds
- Each room has board and leader-board
- Player can choose word out of 3
- Player can draw, undo and reset on board
- Player guess the word
- Player can see chat guesses with time
- If guess is correct other will just notified
- System maintain state of game
- Correct answer showed at last


### Assumptions :label:

- At time only one player can draw
- First come first serve
- Other will guess the word
- Hint will be length of word
- Each room contain 5 players
- One round time: 90 seconds
- Point for correct guess: (remaining time)
- Word selection time: 15 second
- Correct answer time: 5 second 
- Player can't write in chat after correct guess until next round
