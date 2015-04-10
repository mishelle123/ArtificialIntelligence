# search.py
# ---------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

"""
In search.py, you will implement generic search algorithms which are called 
by Pacman agents (in searchAgents.py).
"""

import util
import copy

class SearchProblem:
  """
  This class outlines the structure of a search problem, but doesn't implement
  any of the methods (in object-oriented terminology: an abstract class).
  
  You do not need to change anything in this class, ever.
  """
  
  def getStartState(self):
     """
     Returns the start state for the search problem 
     """
     util.raiseNotDefined()
    
  def isGoalState(self, state):
     """
       state: Search state
    
     Returns True if and only if the state is a valid goal state
     """
     util.raiseNotDefined()

  def getSuccessors(self, state):
     """
       state: Search state
     
     For a given state, this should return a list of triples, 
     (successor, action, stepCost), where 'successor' is a
     successor to the current state, 'action' is the action
     required to get there, and 'stepCost' is the incremental 
     cost of expanding to that successor
     """
     util.raiseNotDefined()

  def getCostOfActions(self, actions):
     """
      actions: A list of actions to take
 
     This method returns the total cost of a particular sequence of actions.  The sequence must
     be composed of legal moves
     """
     util.raiseNotDefined()
           

def tinyMazeSearch(problem):
  """
  Returns a sequence of moves that solves tinyMaze.  For any other
  maze, the sequence of moves will be incorrect, so only use this for tinyMaze
  """
  from game import Directions
  s = Directions.SOUTH
  w = Directions.WEST
  return  [s,s,w,s,w,w,s,w]



def genericSearch(problem, fringe, visited, inFringe, moves, funcAddToFringe, heuristic):
  """
  This is a generic function for all the search algorithm
  """

  startState = SearchState((problem.getStartState(), None, None), moves);
  funcAddToFringe(problem, fringe, startState, heuristic)
  inFringe.add(startState.getState())
  while(True):
    if fringe.isEmpty():
      return "Failure"
    currentState = fringe.pop()
    inFringe.remove(currentState.getState())
    if problem.isGoalState(currentState.getState()):
      return currentState.getMoves()
    else:
      visited.add(currentState.getState())
      successors = problem.getSuccessors(currentState.getState())
      movesToThisState = currentState.getMoves()
      for (state, action, stepCost) in successors:
        if not state in visited and not state in inFringe:
          copyMovesToThisState = copy.deepcopy(movesToThisState)
          copyMovesToThisState.append(action)
          newState = SearchState((state,action,stepCost), copyMovesToThisState)
          funcAddToFringe(problem, fringe, newState, heuristic)
          inFringe.add(newState.getState())


class SearchState:
  def __init__(self, stateData, moves):
    self.stateData = stateData
    self.moves = moves

  def getState(self):
    return self.stateData[0]

  def getAction(self):
    return self.stateData[1]

  def getStepCost(self):
    return self.stateData[2]

  def getMoves(self):
    return self.moves

def depthFirstSearch(problem):

  """
  Search the deepest nodes in the search tree first [p 85].

  Your search algorithm needs to return a list of actions that reaches
  the goal.  Make sure to implement a graph search algorithm [Fig. 3.7].

  To get started, you might want to try some of these simple commands to
  understand the search problem that is being passed in:
  """

  fringe = util.Stack()
  visited = set()
  moves =  list()
  inFringe = set()
  return genericSearch(problem, fringe, visited, inFringe, moves, addToDfsBfs, None)

def breadthFirstSearch(problem):
  "Search the shallowest nodes in the search tree first. [p 81]"
  fringe = util.Queue()
  visited = set()
  moves = list()
  inFringe = set()
  return genericSearch(problem, fringe, visited, inFringe, moves, addToDfsBfs, None)
      
def uniformCostSearch(problem):
  "Search the node of least total cost first. "
  fringe = util.PriorityQueue()
  visited = set()
  moves =  list()
  inFringe = set()
  return genericSearch(problem, fringe, visited, inFringe, moves, addToPriorityQueue, None)

def nullHeuristic(state, problem=None):
  """
  A heuristic function estimates the cost from the current state to the nearest
  goal in the provided SearchProblem.  This heuristic is trivial.
  """
  return 0

def aStarSearch(problem, heuristic=nullHeuristic):
  "Search the node that has the lowest combined cost and heuristic first."
  fringe = util.PriorityQueue()
  visited = set()
  moves =  list()
  inFringe = set()
  return genericSearch(problem, fringe, visited, inFringe, moves, addToPriorityQueueAstar, heuristic)

def addToDfsBfs(problem, fringe, state, heuristic):
  fringe.push(state)

def addToPriorityQueue(problem, fringe, state, heuristic):
  fringe.push(state, problem.getCostOfActions(state.getMoves()))

def addToPriorityQueueAstar(problem, fringe, state, heuristic):
  fringe.push(state, problem.getCostOfActions(state.getMoves()) + heuristic(state.getState(), problem))



  
# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch