mishellejjgold
*****
Question 1 comments:
We Implemented a "SearchState" class which holds the state as triplets
of (state, action, cost) and additional list of moves that led us to
this state.
Our search algorithms uses a single generic search method.

Question 5 comments:
In the corners problem we described the current state by a tuple contains
a position and a list of remaining corners.

Question 6:
Our Heuristic function cornersHeuristic - compute the distances between the
initial position to the closest corner.
Than it changes the start position to be the last corner with the minimal distance
we just found and so on.
It returns the mannhatan distances between all the position in the search.

Question 7:
Our Heuristic function foodHeuristic - compute the distances between the
initial position to the closest food using the kruskal algorithm.
Than it changes the start position to be the nearest food we just found
and so on.
It returns the mannhatan distances between all the position in the search.
In order to Implement kruskal we added additional class - "Union" which
holds the parent of each node.


