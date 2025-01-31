KD-Tree Simulation

Overview

This project implements a kd-tree-based spatial data structure and provides a boid simulation that utilizes kd-trees for efficient nearest neighbor searches. The program includes brute-force and kd-tree-based spatial searching and visualization tools.

Features

Boid Simulation: Simulates flocking behavior with a hawk predator.

Kd-Tree and Brute-Force Implementations: Allows comparison between kd-tree (KdTreePointST) and brute-force (BrutePointST) spatial searching.

Visualization Tools:

NearestNeighborVisualizer: Displays the k nearest points to the mouse cursor using both kd-tree and brute-force methods.

RangeSearchVisualizer: Highlights points within a user-selected rectangular region.

File Descriptions

BoidSimulator.java: Runs the boid simulation, where each boid tracks its nearest neighbors.

BrutePointST.java: Implements a brute-force symbol table for 2D spatial searches.

KdTreePointST.java: Implements a kd-tree symbol table for efficient spatial searches.

NearestNeighborVisualizer.java: Visualizes nearest neighbor searches using brute-force and kd-tree methods.

RangeSearchVisualizer.java: Allows users to select a region and visualize range search results.
