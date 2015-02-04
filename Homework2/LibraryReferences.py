from References import Book, Journal

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

    with open(r'ReferenceInfo', 'r') as file:
        for line in file:
            line = line.strip('\n')
            if not line:
                continue

            if line in ('Journal', 'Book', 'Reference'):
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

def addBook(data):
    data = list(map(lambda x : x.split(': ')[1], data))
    authors = data[1].split(',')
    toAdd = Book(data[1], authors, data[3], data[4])
    print(data)

def addJournal(data):
    data = list(map(lambda x : x.split(': ')[1], data))
    authors = data[1].split(',')
    toAdd = Journal(data[1], authors, data[3], data[4], data[5], data[6], data[7])
    print(data)


if __name__ == '__main__':
    initDict()