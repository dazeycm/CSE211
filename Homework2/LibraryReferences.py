from References import Book, Journal, Conference

'''
Craig Dazey
Dr. Kiper
2-04-15
CSE 211
Practice using a dictionary to hold info on different books/journals
Assignment 2
'''

def initDict():
    global myDict
    myDict = {}
    data = []

    with open(r'Step3Data.txt', 'r') as file:
        for line in file:
            if not line:
                continue
            line = line.strip('\n')

            if line in ('Journal', 'Book', 'Conference'):
                if data:
                    parseData(data)
                data.clear()
                data.append(line)
            else:
                data.append(line)

        parseData(data)

def parseData(data):
    if data[0] == 'Book':
        addBook(data[1:])
    elif data[0] == 'Journal':
        addJournal(data[1:])
    elif data[0] == 'Conference':
        addConference(data[1:])

def addBook(data):
    data = list(map(lambda x : x.split(': ')[1], data))
    toAdd = Book(data[1], data[2], data[3], data[4])
    myDict[data[0]] = toAdd

def addJournal(data):
    data = list(map(lambda x : x.split(': ')[1], data))
    toAdd = Journal(data[1], data[2], data[3], data[4], data[5], data[6], data[7])
    myDict[data[0]] = toAdd

def addConference(data):
    data = list(map(lambda x : x.split(': ')[1], data))
    toAdd = Conference(data[1], data[2], data[3], data[4], data[5], data[6])
    myDict[data[0]] = toAdd



if __name__ == '__main__':
    initDict()
    while('pig' != 'fly'):
        print("List of keys: %s" % (', '.join(list(myDict.keys()))))
        key = input('Enter a key: ')
        try:
            print('%s\t%s\n' % (key, myDict[key]))
        except Exception:
            print('You entered an invalid key!')
