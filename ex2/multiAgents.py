# multiAgents.py
# --------------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
  """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
  """


  def getAction(self, gameState):
    """
    You do not need to change this method, but you're welcome to.

    getAction chooses among the best options according to the evaluation function.

    Just like in the previous project, getAction takes a GameState and returns
    some Directions.X for some X in the set {North, South, West, East, Stop}
    """
    # Collect legal moves and successor states
    legalMoves = gameState.getLegalActions()

    # Choose one of the best actions
    scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
    bestScore = max(scores)
    bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
    chosenIndex = random.choice(bestIndices) # Pick randomly among the best

    "Add more of your code here if you want to"

    return legalMoves[chosenIndex]

  def evaluationFunction(self, currentGameState, action):
    """
    Design a better evaluation function here.

    The evaluation function takes in the current and proposed successor
    GameStates (pacman.py) and returns a number, where higher numbers are better.

    The code below extracts some useful information from the state, like the
    remaining food (oldFood) and Pacman position after moving (newPos).
    newScaredTimes holds the number of moves that each ghost will remain
    scared because of Pacman having eaten a power pellet.

    Print out these variables to see what you're getting, then combine them
    to create a masterful evaluation function.
    """
    # Useful information you can extract from a GameState (pacman.py)
    successorGameState = currentGameState.generatePacmanSuccessor(action)
    newPos = successorGameState.getPacmanPosition()
    oldFood = currentGameState.getFood().asList()
    newGhostStates = successorGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

    capsules = currentGameState.getCapsules()
    oldFood += capsules
    ghostDistances = [manhattanDistance(newPos, ghost.getPosition()) for ghost in newGhostStates]
    closestFood = min([manhattanDistance(newPos, food) for food in oldFood])
    minIndex = 0
    for i in range(len(ghostDistances)):
      if ghostDistances[i] < ghostDistances[minIndex]:
        minIndex = i

    time = newScaredTimes[minIndex]
    eval = time + ghostDistances[minIndex]/float(closestFood + 1)

    return eval


    #return successorGameState.getScore()

def scoreEvaluationFunction(currentGameState):
  """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
  """
  ans = currentGameState.getScore()
  print(ans)
  return ans

class MultiAgentSearchAgent(Agent):
  """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
  """

  def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
    self.index = 0 # Pacman is always agent index 0
    self.evaluationFunction = util.lookup(evalFn, globals())
    self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
  """
    Your minimax agent (question 2)
  """

  def getAction(self, gameState):
    """
      Returns the minimax action from the current gameState using self.depth
      and self.evaluationFuncton.

      Here are some method calls that might be useful when implementing minimax.

      gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

      Directions.STOP:
        The stop direction, which is always legal

      gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

      gameState.getNumAgents():
        Returns the total number of agents in the game
    """
    "*** YOUR CODE HERE ***"
    u = float("-inf")
    a = Directions.STOP

    for action in gameState.getLegalActions(0):

      if not action == Directions.STOP:
        eval = self.minValue(gameState.generateSuccessor(0, action), 1, self.depth)
        if eval > u:
          u = eval
          a = action
    return a


  def maxValue(self, gameState, depth):

    if depth == 0 or gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    v = float("-inf")
    for action in gameState.getLegalActions(0):
      if not action == Directions.STOP:
        v = max(v, self.minValue(gameState.generateSuccessor(0, action), 1, depth))
    return v

  def minValue(self, gameState, agentIndex, depth):
    if gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    v = float("inf")

    for action in gameState.getLegalActions(agentIndex):
      if not action == Directions.STOP:
        if agentIndex < (gameState.getNumAgents() - 1):
          v = min(v, self.minValue(gameState.generateSuccessor(agentIndex , action),agentIndex + 1, depth))
        else:
          v = min(v, self.maxValue(gameState.generateSuccessor(agentIndex, action), depth-1))
    return v

class AlphaBetaAgent(MultiAgentSearchAgent):
  """
    Your minimax agent with alpha-beta pruning (question 3)
  """

  def getAction(self, gameState):
    """
      Returns the minimax action using self.depth and self.evaluationFunction
    """
    "*** YOUR CODE HERE ***"
    u = float("-inf")
    a = Directions.STOP
    alpha = float("-inf")
    beta = float("inf")
    for action in gameState.getLegalActions(0):

      if not action == Directions.STOP:
        eval = self.minValue(gameState.generateSuccessor(0, action), 1, self.depth, alpha, beta)
        if eval > u:
          u = eval
          a = action
        if u >= beta:
          return a

    return a


  def maxValue(self, gameState, depth, alpha, beta):

    if depth == 0 or gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    v = float("-inf")
    for action in gameState.getLegalActions(0):
      if not action == Directions.STOP:
        v = max(v, self.minValue(gameState.generateSuccessor(0, action), 1, depth, alpha, beta))
        if v >= beta:
          return v
        alpha = max(alpha, v)
    return v

  def minValue(self, gameState, agentIndex, depth, alpha, beta):
    if gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    v = float("inf")

    for action in gameState.getLegalActions(agentIndex):
      if not action == Directions.STOP:
        if agentIndex < (gameState.getNumAgents() - 1):
          v = min(v, self.minValue(gameState.generateSuccessor(agentIndex , action),agentIndex + 1, depth, alpha, beta))
        else:
          v = min(v, self.maxValue(gameState.generateSuccessor(agentIndex, action), depth-1, alpha, beta))
        if v <= alpha:
          return v
        beta = min(beta, v)
    return v

class ExpectimaxAgent(MultiAgentSearchAgent):
  """
    Your expectimax agent (question 4)
  """

  def getAction(self, gameState):
    """
      Returns the expectimax action using self.depth and self.evaluationFunction

      All ghosts should be modeled as choosing uniformly at random from their
      legal moves.
    """
    "*** YOUR CODE HERE ***"
    u = float("-inf")
    a = Directions.STOP

    for action in gameState.getLegalActions(0):

      if not action == Directions.STOP:
        eval = self.minValue(gameState.generateSuccessor(0, action), 1, self.depth)
        if eval > u:
          u = eval
          a = action

    return a

  def maxValue(self, gameState, depth):

    if depth == 0 or gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    v = float("-inf")
    for action in gameState.getLegalActions(0):
      if not action == Directions.STOP:
        v = max(v, self.minValue(gameState.generateSuccessor(0, action), 1, depth))
    return v

  def minValue(self, gameState, agentIndex, depth):
    if gameState.isWin() or gameState.isLose():
      return self.evaluationFunction(gameState)

    sum = 0

    for action in gameState.getLegalActions(agentIndex):
      if not action == Directions.STOP:
        if agentIndex < (gameState.getNumAgents() - 1):
          sum += self.minValue(gameState.generateSuccessor(agentIndex , action),agentIndex + 1, depth)
        else:
          sum += self.maxValue(gameState.generateSuccessor(agentIndex, action), depth-1)
    # sum the utility and devide in the legal action minus 1 for stop
    numOfLegalActions = len(gameState.getLegalActions(agentIndex)) - 1
    if numOfLegalActions == 0:
      return sum
    return sum/float(numOfLegalActions)

def betterEvaluationFunction(currentGameState):
  """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
  """
  "*** YOUR CODE HERE ***"
  pos = currentGameState.getPacmanPosition()
  currentScore = scoreEvaluationFunction(currentGameState)

  if currentGameState.isLose():
    return -float("inf")
  elif currentGameState.isWin():
    return float("inf")

  # food distance
  food= currentGameState.getFood().asList()
  foodsLeft = len(food)

  capsules = currentGameState.getCapsules()
  capsulesLeft = len(capsules)

  # adds the capsules to food
  food += capsules
  closestFood = min([manhattanDistance(pos, food) for food in food])



  scaredGhosts = list()
  activeGhosts = list()
  for ghost in currentGameState.getGhostStates():
    if not ghost.scaredTimer:
      activeGhosts.append(ghost)
    else:
      scaredGhosts.append(ghost)


  activeGhostDistances = [manhattanDistance(pos, ghost.getPosition()) for ghost in activeGhosts]
  scaredGhostDistances = [manhattanDistance(pos, ghost.getPosition()) for ghost in scaredGhosts]
  closestActiveGhost = 0
  closestScaredGhost = 0

  if activeGhosts:
    closestActiveGhost = min(activeGhostDistances)
  else:
    closestActiveGhost = float("inf")
  closestActiveGhost = max(closestActiveGhost, 1)

  if scaredGhosts:
    closestScaredGhost = min(scaredGhostDistances)
  else:
    closestScaredGhost = 0


  score = (2 * currentScore)+ (-2 *(1./closestActiveGhost)) + (-1.5 * closestFood) + \
          (-2 * closestScaredGhost) + (-100 * capsulesLeft) + (-4 * foodsLeft)
  return score

# Abbreviation
better = betterEvaluationFunction

class ContestAgent(MultiAgentSearchAgent):
  """
    Your agent for the mini-contest
  """

  def getAction(self, gameState):
    """
      Returns an action.  You can use any method you want and search to any depth you want.
      Just remember that the mini-contest is timed, so you have to trade off speed and computation.

      Ghosts don't behave randomly anymore, but they aren't perfect either -- they'll usually
      just make a beeline straight towards Pacman (or away from him if they're scared!)
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

