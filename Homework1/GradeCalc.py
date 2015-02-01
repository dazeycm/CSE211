from statistics import *

'''
Craig Dazey
Dr. Kiper
1-30-15
CSE 211
Simple grade calculator
'''

inputFile = open('studentGrades.txt', 'r')
outputFile = open('calculatedGrades.txt', 'w')

for line in inputFile:
    parts = line.split()
    parts = [int(parts[i]) if parts[i].isdigit() else parts[i] for i in range(11)]  #list comprehension to switch elements to ints if they're digits

    name = parts[0]
    quiz =  parts[1:4]
    hw = parts[4:-2]
    exam = parts[-2:]

    quizScore = mean(quiz)
    hwScore = mean(hw)
    examScore = mean(exam)

    finalScore = quizScore * .2 + hwScore * .3 + examScore * .5

    if finalScore >= 90:
        letterGrade = 'A'
    elif finalScore >= 80:
        letterGrade = 'B'
    elif finalScore >= 70:
        letterGrade = 'C'
    elif finalScore >= 60:
        letterGrade = 'D'
    else:
        letterGrade = 'F'

    dontRound = float(str(finalScore)[:4])
    outputFile.write('%20s %5.1f %1s\n' % (name, dontRound, letterGrade))
