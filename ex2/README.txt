mishellejjgold

*****
Question 1 comments:
We chose to evaluate the action in the following way:

eval = ghost time + (minimal ghost distance)/(closestFood + 1)

we calculated the minimal mannhatan distance between pacman and the ghost,
and between pacman and the food. The ratio will guarantee that if pacman is close to
food the evaluation function will get higher values, and if pacman is close to a ghost
it will get lower values.
We added the time to the evaluation function so it wil takes the capsules.

Question 5 comments:
We chose to evaluate the state in the following way:

eval =  (2 * currentScore)+ (-2 *(1./closestActiveGhost)) + (-1.5 * closestFood) + \
        (-2 * closestScaredGhost) + (-100 * capsulesLeft) + (-4 * foodsLeft)

We considerd the values that affect the evaluation function and chose the weights
of the values in the following way:
1. for values that are better as long as they get higher (score, distance to closest active ghost)
   we chose positive weights or took the ratio.
2. for values that are better as long as they get lower (closest food, food left)
   we chose negative weights.
We played with the weights on the values until we reached a desirable result.
