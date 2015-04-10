"""
import itertools

def createDomainFile(domainFileName, n):
  numbers = list(range(n)) # [0,...,n-1]
  pegs = ['a','b', 'c']
  domainFile = open(domainFileName, 'w') #use domainFile.write(str) to write to domainFile

  "*** YOUR CODE HERE ***"
  domainFile.write('Propositions:\n')
  propositions = list()
  space = " "
  #adds the At propositions
  for i in numbers:
    for peg in pegs:
      domainFile.write(str(i)+"At"+peg + space)
  #adds the clear propositions
  for i in numbers:
    domainFile.write("cl"+ str(i) + space)

  for i in numbers:
    for j in range(i+1,n):
      domainFile.write(str(i) + "On" + str(j) + space)
    domainFile.write(str(i) + "On" + "Floor" + space)


  for peg in pegs:
    domainFile.write(peg + "Empty" + space)

  domainFile.write("\n")

  domainFile.write('Actions:\n')
  #move
  for peg1, peg2 in itertools.product(pegs, pegs) :
    if peg1==peg2:
      continue
    for i in numbers:
      for j in range(i+1, n):
        for k in range(i+1, n):
          if k == j:
            continue
          domainFile.write("Name: ")
          #funciom name
          domainFile.write(str(i)+"On"+str(k)+peg1+peg2+str(j)+"\n")
          domainFile.write("pre: ")
          domainFile.write(str(i)+"On"+str(k)+space)
          domainFile.write("cl"+str(i)+space+"cl"+str(j)+space)
          domainFile.write(str(i)+"At"+peg1+space+str(k)+"At"+peg1+space+str(j)+"At"+peg2+"\n")

          domainFile.write("add: ")
          domainFile.write(str(i)+"On"+str(j)+space)
          domainFile.write("cl"+str(k)+space)
          domainFile.write(str(i)+"At"+peg2+"\n")

          domainFile.write("delete: ")
          domainFile.write(str(i)+"On"+str(k)+space)
          domainFile.write("cl"+str(j)+space)
          domainFile.write(str(i)+"At"+peg1+"\n")

    for i in numbers:
      for j in range(i+1, n):
        domainFile.write("Name: ")
        #funciom name
        domainFile.write(str(i)+"On"+str(j)+peg1+peg2+"Floor"+"\n")
        domainFile.write("pre: ")
        domainFile.write(str(i)+"On"+str(j)+space)
        domainFile.write("cl"+str(i)+space+peg2+"Empty"+space)
        domainFile.write(str(i)+"At"+peg1+space+str(j)+"At"+peg1+"\n")

        domainFile.write("add: ")
        domainFile.write(str(i)+"On"+"Floor"+space)
        domainFile.write("cl"+str(j)+space)
        domainFile.write(str(i)+"At"+peg2+"\n")

        domainFile.write("delete: ")
        domainFile.write(str(i)+"On"+str(j)+space)
        domainFile.write(peg2+"Empty"+space)
        domainFile.write(str(i)+"At"+peg1+"\n")

    for i in numbers:
      for j in range(i+1, n):
        domainFile.write("Name: ")
        #funciom name
        domainFile.write(str(i)+"On"+"Floor"+peg1+peg2+str(j)+"\n")
        domainFile.write("pre: ")
        domainFile.write(str(i)+"On"+"Floor"+space)
        domainFile.write("cl"+str(i)+space+"cl"+str(j)+space)
        domainFile.write(str(i)+"At"+peg1+space+str(j)+"At"+peg2+"\n")

        domainFile.write("add: ")
        domainFile.write(str(i)+"On"+str(j)+space)
        domainFile.write(peg1+"Empty"+space)
        domainFile.write(str(i)+"At"+peg2+"\n")

        domainFile.write("delete: ")
        domainFile.write(str(i)+"On"+"Floor"+space)
        domainFile.write("cl"+str(j)+space)
        domainFile.write(str(i)+"At"+peg1+"\n")

    for i in numbers:
      domainFile.write("Name: ")
      #funciom name
      domainFile.write(str(i)+"On"+"Floor"+peg1+peg2+"Floor"+"\n")
      domainFile.write("pre: ")
      domainFile.write(str(i)+"On"+"Floor"+space)
      domainFile.write("cl"+str(i)+space+peg2+"Empty"+space)
      domainFile.write(str(i)+"At"+peg1+"\n")

      domainFile.write("add: ")
      domainFile.write(peg1+"Empty"+space)
      domainFile.write(str(i)+"At"+peg2+"\n")

      domainFile.write("delete: ")
      domainFile.write(peg2+"Empty"+space)
      domainFile.write(str(i)+"At"+peg1+"\n")


  domainFile.close()
        
  
def createProblemFile(problemFileName, n):
  numbers = list(range(n)) # [0,...,n-1]
  pegs = ['a','b', 'c']
  problemFile = open(problemFileName, 'w') #use problemFile.write(str) to write to problemFile
  "*** YOUR CODE HERE ***"
  space = " "
  problemFile.write("Initial state: ")
  #adds the initial propositions
  for i in numbers:
    problemFile.write(str(i)+"At"+'a' + space)
    if i == n-1:
      problemFile.write(str(i)+"On"+ "Floor" + space)
    else:
      problemFile.write(str(i)+"On"+ str(i+1) + space)

  problemFile.write("cl"+str(0)+ space)
  problemFile.write("b"+"Empty"+ space)
  problemFile.write("c"+"Empty"+ "\n")

  problemFile.write("Goal state: ")
  for i in numbers:
    problemFile.write(str(i)+"At"+'c' + space)
    if i == n-1:
      problemFile.write(str(i)+"On"+ "Floor" + space)
    else:
      problemFile.write(str(i)+"On"+ str(i+1) + space)

  problemFile.write("cl"+str(0)+ space)
  problemFile.write("b"+"Empty"+ space)
  problemFile.write("a"+"Empty"+ "\n")

  problemFile.close()

import sys
if __name__ == '__main__':
  if len(sys.argv) != 2:
    print('Usage: hanoi.py n')
    sys.exit(2)
  
  n = int(float(sys.argv[1])) #number of disks
  domainFileName = 'hanoi' + str(n) + 'Domain.txt'
  problemFileName = 'hanoi' + str(n) + 'Problem.txt'
  
  createDomainFile(domainFileName, n)
  createProblemFile(problemFileName, n)

  """
from itertools import product

def createDomainFile(domainFileName, n):
  "*** OUR CODE HERE ***"
  numbers = list(range(n)) # [0,...,n-1]

  pegs = ['a','b', 'c']
  numbersAndPegs = numbers + pegs

  domainFile = open(domainFileName, 'w') #use domainFile.write(str) to write to domainFile
  domainFile.write("Propositions:\n")
  disk1OnDisk2 = []
  for disk1 in numbers:
    for disk2 in numbers:
      if disk2 > disk1:
        disk1OnDisk2.append(str(disk1) + 'on' + str(disk2))
    for peg in pegs:
      disk1OnDisk2.append(str(disk1) + 'on' + peg)
  topPegDisk = []
  for peg in pegs:
    for disk in numbers:
      topPegDisk.append('top' + peg + str(disk))
    topPegDisk.append('top' + peg + peg)
  disk1OnDisk2 = ' '.join(disk1OnDisk2)
  topPegDisk = ' '.join(topPegDisk)
  domainFile.write(disk1OnDisk2)
  domainFile.write(' ' + topPegDisk)

  domainFile.write("\nActions:\n")
  for (fromPeg, toPeg) in product(pegs, pegs):
    if fromPeg == toPeg:
      continue
    for (disk1, diskBelow1, disk2) in product(numbers, numbersAndPegs, numbersAndPegs):
      if diskBelow1 == disk2:
        continue
      if disk2 in pegs and disk2 != toPeg:
        continue
      if diskBelow1 in pegs and diskBelow1 != fromPeg:
        continue
      if disk2 not in pegs and disk1 >= disk2:
        continue
      if diskBelow1 not in pegs and disk1 >= diskBelow1:
        continue

      domainFile.write('Name: M' + fromPeg + str(disk1) + '_' + str(diskBelow1) + '_' + toPeg + str(disk2) + '\n')

      pre = [
        'top' + fromPeg + str(disk1),
        'top' + toPeg + str(disk2),
        str(disk1) + 'on' + str(diskBelow1)
      ]
      domainFile.write('pre: ' + ' '.join(pre) + '\n')

      add = [
        'top' + fromPeg + str(diskBelow1),
        'top' + toPeg + str(disk1),
        str(disk1) + 'on' + str(disk2)
      ]
      domainFile.write('add: ' + ' '.join(add) + '\n')

      delete = [
        'top' + fromPeg + str(disk1),
        'top' + toPeg + str(disk2),
        str(disk1) + 'on' + str(diskBelow1)
      ]
      domainFile.write('delete: ' + ' '.join(delete) + '\n')

  domainFile.close()


def createProblemFile(problemFileName, n):
  numbers = list(range(n)) # [0,...,n-1]
  pegs = ['a','b', 'c']
  problemFile = open(problemFileName, 'w') #use problemFile.write(str) to write to problemFile
  "*** OUR CODE HERE ***"
  problemFile.write('Initial state: topa0 topbb topcc')
  for disk in range(n-1):
    problemFile.write(' ' + str(disk) + 'on' + str(disk+1))
  problemFile.write(' ' + str(n-1) + 'ona')
  problemFile.write('\n')

  problemFile.write('Goal state: topaa topbb topc0')
  for disk in range(n-1):
    problemFile.write(' ' + str(disk) + 'on' + str(disk+1))
  problemFile.write(' ' + str(n-1) + 'onc')
  problemFile.write('\n')

  problemFile.close()

import sys
if __name__ == '__main__':
  if len(sys.argv) != 2:
    print('Usage: hanoi.py n')
    sys.exit(2)

  n = int(float(sys.argv[1])) #number of disks
  domainFileName = 'hanoi' + str(n) + 'Domain.txt'
  problemFileName = 'hanoi' + str(n) + 'Problem.txt'

  createDomainFile(domainFileName, n)
  createProblemFile(problemFileName, n)