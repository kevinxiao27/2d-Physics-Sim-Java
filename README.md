# 2d-Physics-Sim-Java

## What will the application do?

This application will provide a sandbox for users to create objects of different shapes and sizes,
then being able to run a simulation and tweaking the simulation as many times as desired. Ideally
there would be components that would be able to hitch onto each other to create dynamic objects,
or even the ability to add power to parts like wheels.

## Who will use it?

Anyone interested in tinkering with a physics engine and playing around with physics interactions,
and playing around with a fixed set of possible pieces and an indefinite number of possible creations
to complete levels or your own designs.

## Why is this project of interest to you?

Some of my favourite games are heavily physics based and have sandbox functionality. Games like
_Besiege_ or _BeamNG_ were some that led to my interest in this subject. The aspect of these games that
allows a user to test and replicate an indefinite number of designs.
I think my favourite aspect of these games is how very creative and innovative solutions can be made
for a use case, despite the restricted building pieces they have.

# User Stories

## Phase 1

- As a user, I want to be able to add an object to a simulation
- As a user, I want to be able to list all my objects in a simulation
- As a user, I want to be able to change the position of an object in my simulation
- As a user, I want to be able to change the velocity of an object in my simulation
- As a user, I want to be able to remove an object from my simulation

## Phase 2

- As a user, I want to be able to save my simulation to a file if I choose to from a menu
- As a user, I want to be able to load my simulation from a file if I choose to from a menu

## Phase 3 (Supported GUI user stories)

- As a user, I want to add multiple colliders to a simulation
- As a user, I want to be able to edit existing colliders in a simulation
- As a user, I want to be able to remove existing colliders in a simulation
- As a user, I want to be able to tick through a simulation
- As a user, I want to be able to load and save the state of the application
- As a user, I want to be able to visualize the simulation with circles!

## Phase 4: Task 2

Tue Apr 02 23:44:57 PDT 2024 at: Instantiated new Simulation with isPlaying = false

Tue Apr 02 23:44:59 PDT 2024 at: Loaded Simulation

Tue Apr 02 23:44:59 PDT 2024 at: Instantiated new Simulation with isPlaying = false

Tue Apr 02 23:44:59 PDT 2024 at: Successfully added new collider:model.Collider@56f3d603

Tue Apr 02 23:44:59 PDT 2024 at: Successfully added new collider:model.Collider@6f6c7595

Tue Apr 02 23:44:59 PDT 2024 at: Successfully added new collider:model.Collider@2e96a326

Tue Apr 02 23:44:59 PDT 2024 at: Successfully added new collider:model.Collider@1296515b

Tue Apr 02 23:44:59 PDT 2024 at: Successfully added new collider:model.Collider@5a1dcec4

Tue Apr 02 23:45:03 PDT 2024 at: Removed collider: model.Collider@5a1dcec4

Tue Apr 02 23:45:04 PDT 2024 at: Removed collider: model.Collider@1296515b

Tue Apr 02 23:45:14 PDT 2024 at: Successfully added new collider:model.Collider@2da9b46a

Tue Apr 02 23:45:17 PDT 2024 at: Changed position of: model.Collider@2e96a326to x:24.0 to y: 30.0

Tue Apr 02 23:45:17 PDT 2024 at: Changed velocity of: model.Collider@2e96a326to dx:-4.0 to dy: -1.2

Tue Apr 02 23:45:26 PDT 2024 at: Handled bottom boundary collision of: model.Collider@2da9b46a

Tue Apr 02 23:45:26 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:27 PDT 2024 at: Handled bottom boundary collision of: model.Collider@2da9b46a

Tue Apr 02 23:45:27 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:27 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:28 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:28 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:28 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:29 PDT 2024 at: Handled left boundary collision of: model.Collider@2e96a326

Tue Apr 02 23:45:29 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:29 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:30 PDT 2024 at: Handled left boundary collision of: model.Collider@56f3d603

Tue Apr 02 23:45:30 PDT 2024 at: Handled right boundary collision of: model.Collider@2da9b46a

Tue Apr 02 23:45:30 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:30 PDT 2024 at: Handled top boundary collision of: model.Collider@6f6c7595

Tue Apr 02 23:45:30 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:31 PDT 2024 at: Handled left boundary collision of: model.Collider@6f6c7595

Tue Apr 02 23:45:31 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:31 PDT 2024 at: Handled top boundary collision of: model.Collider@2da9b46a

Tue Apr 02 23:45:31 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:32 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:32 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:32 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:33 PDT 2024 at: Changed velocity of: model.Collider@2da9b46ato dx:2.8281739 to dy: -2.0680215

Tue Apr 02 23:45:33 PDT 2024 at: Handled collision of model.Collider@2da9b46a and model.Collider@2e96a326

Tue Apr 02 23:45:33 PDT 2024 at: Ticked Simulation: 1 times

Tue Apr 02 23:45:35 PDT 2024 at: Saved Simulation

## Phase 4: Task 3

If I was given more time to work on this project one of the major changes that I would undertake is a refactoring of
the GUI so that there is less coupling and clearer use of the Single Responsibility Principle in the classes. Currently,
in order for any of the graphical panels to be updated when, for example, a collider is added to the simulation, all
components in the GUI need to be visually updated as well as the actual Simulation class that houses the functionality
of the simulation. This meant that constructors had one reference to a simulation passed down as a parameter which
created significant coupling. One method that I would use to solve this is through the use of an Observer pattern. A
listener would be able to convey to other classes without being directly coupled that a collider was added and update
both the visual components and the actual Simulation without having to pass down a reference in every component.

Although this would not result in any actual changes to the functionality of the project, it would open up the
possibility for new features to be added without having to perform a major refactoring of the GUI.
