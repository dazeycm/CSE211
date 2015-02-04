def splitAuthors(authors):
    authors = authors.split(',')
    authors[0] = ', '.join((authors[0].split())[::-1])
    return ",".join(authors)

class Book():

    def __init__(self, author, title, publisher, date):
        self.author = author
        self.title = title
        self.publisher = publisher
        self.date = date

    def __str__(self):
        return '%s, %s, %s, %s' % (splitAuthors(self.author), self.title, self.publisher, self.date)

class Journal():

    def __init__(self, author, title, journal, publisher, date, volume, number):
        self.author = author
        self.title = title
        self.journal = journal
        self.publisher = publisher
        self.date = date
        self.volume = volume
        self.number = number

    def __str__(self):
        return '%s, %s, %s, %s:%s(%s), %s' % (splitAuthors(self.author), self.title, self.journal, self.publisher, self.volume, self.number, self.date)


class Conference():

    def __init__(self, author, title, conference, date, location, pages):
        self.author = author
        self.title = title
        self.conference = conference
        self.date = date
        self.location = location
        self.pages = pages

    def __str__(self):
        return '%s, %s, in Proceedings of %s, %s, %s, %s' % (splitAuthors(self.author), self.title, self.conference, self.location, self.date, self.pages)




