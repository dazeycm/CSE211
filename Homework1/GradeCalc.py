file = open('studentGrades.txt', 'r')

for line in file:
    parts = line.split()
    name = parts[0]
