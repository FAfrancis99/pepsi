# pepsi
Pepse Game Manager
The Pepse Game Manager is a 2D game engine that initializes and manages a dynamic game world. The game includes features like terrain generation, a day-night cycle, dynamic lighting with a sun and sun halo, and player interaction with the game world.

Table of Contents
Overview
Features
Getting Started
Game Elements
Technologies Used
Acknowledgments
Overview
The Pepse Game Manager creates a procedurally generated 2D game world that includes the following components:

Dynamic terrain that adapts to the game window.
Day and night cycle with smooth transitions.
Sun and sun halo to simulate lighting effects.
Trees and flora to enhance the world.
A player avatar that interacts with the game environment.
A dynamic energy system for the avatar.
The world is procedurally created, ensuring a unique experience on every run.

Features
Sky Rendering: Adds a dynamic sky with clouds.
Terrain Generation: Procedurally generates ground blocks.
Day/Night Cycle:
Smooth transitions to simulate time progression.
A glowing sun with a halo for added visual effect.
Player Avatar:
The player interacts with the terrain and other elements.
Includes an energy indicator for gameplay mechanics.
Tree and Flora System:
Trees grow dynamically within the terrain.
The flora is reactive and interactive with the environment.
Getting Started
Prerequisites
Java Development Kit (JDK) 11 or higher.
A Java IDE like IntelliJ IDEA, Eclipse, or any IDE of your choice.
Running the Game
Clone the repository:
bash
Copy code
git clone https://github.com/yourusername/pepse-game.git
Open the project in your Java IDE.
Run the PepseGameManager class.
Game Elements
1. Sky and Clouds
The Sky is created with a static background to simulate the sky.
Clouds are added to improve visual appeal.
2. Terrain
Procedurally generated terrain blocks are created based on the seed value.
The ground adapts to the game window size.
3. Day-Night Cycle
A smooth day-night transition with a glowing sun and halo.
Night adds a dark overlay to simulate nighttime.
4. Player Avatar
The player starts at ground level.
Includes an energy system visualized with the EnergyIndicator.
5. Trees and Flora
Trees are created dynamically within the terrain bounds.
Flora enhances the aesthetic of the terrain.
Technologies Used
Java: Core language for game logic.
Danogl Framework: A lightweight game framework for managing game objects, rendering, and collisions.
Procedural Generation: Used to generate terrain and flora dynamically.
Randomization: Adds variety to terrain and flora generation.
Acknowledgments
This project uses the Danogl game framework for rendering and managing game elements. It also employs procedural generation techniques for dynamic and interactive game world creation.
![Screenshot (95)](https://github.com/user-attachments/assets/a952dcc9-a8a5-4ee9-8bd0-3eb4b2c8ccd0)
