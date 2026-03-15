# MineSweeper
The familiar old minesweeper game of windows, for those who remember, I didn't, so I challenged myself to get to know the game and implement my own.

# Minesweeper -- JavaFX Implementation

## Overview

This project is an implementation of the classic **Minesweeper game**
built with **JavaFX**.

The game provides an interactive board where users reveal cells and
attempt to avoid hidden mines. Mines are generated randomly and the game
automatically determines whether the player wins or loses based on their
moves.

The project demonstrates **JavaFX UI development and game logic
implementation in Java**.

------------------------------------------------------------------------

## Features

-   Interactive Minesweeper board built using **JavaFX**
-   **Random mine placement**
-   Game begins on the **first cell click**
-   Automatic **win and lose detection**
-   Clean user interface layout:
    -   **Restart Game** button at the top
    -   **Game board** centered in the window
    -   **Quit button** at the bottom
-   Cells reveal numeric values representing the number of neighboring
    mines

------------------------------------------------------------------------

## Game Flow

1.  The user launches the application.
2.  The user clicks a cell on the board.
3.  Mines are randomly generated across the board.
4.  Each revealed cell shows the number of adjacent mines.
5.  If the user clicks on a mine, the game ends.
6.  If all non-mine cells are revealed, the user wins.
7.  The **Restart Game** button resets the board and starts a new game.

For more info about the game visit https://en.wikipedia.org/wiki/Minesweeper_(video_game)

------------------------------------------------------------------------

## Technologies Used

-   **Java**
-   **JavaFX**
-   **IntelliJ IDEA**

------------------------------------------------------------------------

## Requirements

To run this application you need:

-   **Java Development Kit (JDK) 17 or newer**
-   **JavaFX SDK**
-   **IntelliJ IDEA** (recommended for running the project)

------------------------------------------------------------------------

## Installation

### 1. Install JDK

Download and install a JDK:

https://adoptium.net/

Verify installation:

    java -version

------------------------------------------------------------------------

### 2. Download JavaFX SDK

Download the JavaFX SDK from:

https://openjfx.io/

Extract the SDK to a folder on your computer.

Example:

    C:\javafx-sdk

------------------------------------------------------------------------

### 3. Open the Project

1.  Clone or download the repository.
2.  Open the project in **IntelliJ IDEA**.

------------------------------------------------------------------------

### 4. Configure JavaFX

In IntelliJ:

1.  Go to **File → Project Structure**
2.  Select **Libraries**
3.  Click **+**
4.  Choose the **lib folder** inside the JavaFX SDK
5.  Apply the changes

------------------------------------------------------------------------

## Running the Application

Run the `Main` class in IntelliJ.

The game window will open and display:

-   Restart button
-   Minesweeper board
-   Quit button

