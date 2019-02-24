import subprocess
import sys
from itertools import chain, combinations

from multiprocessing import cpu_count, Pool, Manager, Queue

print ('Number of arguments:', len(sys.argv), 'arguments.')
print ('Argument List:', str(sys.argv))

#work for proccess
def workForTwo(input):
    compareNum = input[0]
    A = input[1]
    B = input[2]
    subprocess.check_output("bedtools intersect -a {0} -b {1} > output{2}.bed".format(A, B, compareNum), shell=True)

arguments = sys.argv
K = int(arguments[1])

def workForMultiple(input):
    compareNum = input[0]
    files = input[1]
    command = "bedtools multiinter -i "
    for x in range(len(files)):
        command = command + "{0} ".format(files[x])
    print(command)
    subprocess.check_output(command+"> output{0}.bed".format(compareNum), shell=True)

arguments = sys.argv
K = int(arguments[1])

def powerset(iterable):
    s = list(iterable)  # allows duplicate elements
    return chain.from_iterable(combinations(s, r) for r in range(len(s)+1))

def getCombination(arguments, K):
    stuff = arguments[2:]
    combination = []
    for i, combo in enumerate(powerset(stuff), 1):
        combination.append(list(combo))
    combination.remove([])

    finalcombination = []
    for x in range(len(combination)):
        if (len(combination[x]) <= K & len(combination[x]) < 2):
            continue
        else: finalcombination.append(combination[x])
        #print(len(combo))
        #print('combo #{}: {}'.format(i, combo))
    return finalcombination



def getMultiBedIntersect(arguments):
    combination = getCombination(arguments, K)
    pool = Pool(processes=32)
    processesForTwo = []
    processesForMultiple = []
    #forTwo
    for x in range(len(combination)):
        if(len(combination[x]) == 2):
           processesForTwo.append([x, combination[x][0], combination[x][0]])
        else:
            processesForMultiple.append([x, combination[x]])
    print(processesForMultiple)
    pool.map(workForTwo,processesForTwo)
    pool.close()
    pool.join()

    if(len(processesForMultiple) == 1):
        workForMultiple(processesForMultiple[0])
    else:
        pool = Pool(processes=32)
        pool.map(workForMultiple,processesForMultiple)
        pool.close()
        pool.join()




#TEST run
#print(subprocess.check_output("bedtools intersect -a iCellNeuron_HTTLOC_CAPCxHTT_REP1.bed -b iCellNeuron_HTTLOC_CAPCxHTT_REP2.bed", shell=True).decode("utf-8"))

#Check to see if the input is valid
try:
    k = int(arguments[1])
    if k < 2:
        raise Exception('Invaild K')

except:
    print("error")

getMultiBedIntersect(arguments)
