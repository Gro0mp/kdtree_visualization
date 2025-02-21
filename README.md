# KD-Tree Simulation

## Overview

This project implements a kd-tree-based spatial data structure and provides a boid simulation that utilizes kd-trees for efficient nearest neighbor searches. The program includes brute-force and kd-tree-based spatial searching and visualization tools.

## Features

- **Boid Simulation**: Simulates flocking behavior with a hawk predator.
- **Kd-Tree and Brute-Force Implementations**: Allows comparison between kd-tree (`KdTreePointST`) and brute-force (`BrutePointST`) spatial searching.
- **Visualization Tools**:
  - `NearestNeighborVisualizer`: Displays the `k` nearest points to the mouse cursor using both kd-tree and brute-force methods.
  - `RangeSearchVisualizer`: Highlights points within a user-selected rectangular region.

## File Descriptions

- \`\`: Runs the boid simulation, where each boid tracks its nearest neighbors.
- \`\`: Implements a brute-force symbol table for 2D spatial searches.
- \`\`: Implements a kd-tree symbol table for efficient spatial searches.
- \`\`: Visualizes nearest neighbor searches using brute-force and kd-tree methods.
- \`\`: Allows users to select a region and visualize range search results.

## Usage

### Running the Boid Simulator


https://github.com/user-attachments/assets/048297e1-4466-4322-9e62-1240ce3b5eba


```sh
java BoidSimulator <mode> <numBoids> <friends>
java BoidSimulator brute 100 5
```

- `<mode>`: Either `brute` (brute-force search) or `kdtree` (kd-tree search).
- `<numBoids>`: Number of boids in the simulation.
- `<friends>`: Number of nearest neighbors each boid tracks.

#### Controls

- `o`: Zoom out
- `i`: Zoom in
- `t`: Track the center of mass
- `h`: Track the hawk
- `m`: Manually control the camera (use arrow keys for movement)

### Running the Nearest Neighbor Visualizer


https://github.com/user-attachments/assets/567e590d-d784-4bdf-9aa3-c801d0e5fbae


```sh
java NearestNeighborVisualizer <filename> <k>
java NearestNeighborVisualizer ./data/circle100.txt 4
```

- `<filename>`: File containing point coordinates.
- `<k>`: Number of nearest neighbors to visualize.

### Running the Range Search Visualizer


https://github.com/user-attachments/assets/ca974bb8-f55e-4bc3-b65d-135b3c748976


```sh
java RangeSearchVisualizer <filename>
java RangeSearchVisualizer ./data/circle10.txt
```

- `<filename>`: File containing point coordinates.
- Drag the mouse to define a search rectangle.

## Dependencies

- Java Standard Library (`stdlib`)
- Data Structures (`dsa` package)

## Credits

- Algorithms Fourth Edition by Robert Sedgewick and Kevin Wayne
