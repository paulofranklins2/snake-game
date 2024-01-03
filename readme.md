# Snake Game

This is a simple Snake Game implemented in Java using the Swing library. The game provides a classic snake gameplay experience, where the player controls a snake that moves around the screen, eats apples, and grows longer. Additionally, there are bonus apples that appear periodically, offering extra points and increasing the snake's length.

## Getting Started

To run the Snake Game, follow these steps:

1. Ensure you have Java installed on your machine.
2. Compile the Java files using a Java compiler:

    ```bash
    javac GamePanel.java GameFrame.java Main.java
    ```

3. Run the game:

    ```bash
    java Main
    ```

## How to Play

- Use the arrow keys (`Up`, `Down`, `Left`, `Right`) to control the snake's movement.
- Eat the red apples to score points and grow longer.
- Bonus apples appear periodically, providing additional points and increasing the snake's length significantly.
- Avoid collisions with the borders of the game window and the snake's own body.
- The game ends when the snake collides with the borders or itself.

## Game Features

- **Scoring**: Each red apple eaten increases the score, and bonus apples provide extra points.
- **Dynamic Difficulty**: The game becomes more challenging as the snake grows longer.
- **Colorful Design**: The game features a colorful design with a green snake, red apples, and pink bonus apples.
- **Game Over Screen**: A Game Over screen displays the final score and bonus apple count when the game ends.

## Game Controls

- `Up Arrow`: Move the snake up.
- `Down Arrow`: Move the snake down.
- `Left Arrow`: Move the snake to the left.
- `Right Arrow`: Move the snake to the right.

## Implementation Details

- The game is implemented using Java and the Swing library.
- The `GamePanel` class handles the game logic, drawing, and user input.
- The `GameFrame` class sets up the game window and adds the `GamePanel`.
- The `Main` class contains the `main` method to start the game.

Feel free to explore and modify the code to enhance or customize the game according to your preferences. Enjoy playing the Snake Game!