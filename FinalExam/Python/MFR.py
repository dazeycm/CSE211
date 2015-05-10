__author__ = 'Craig'

from functools import reduce

nucleotides = 'NGATAACNATGAGTANCGCA'

nucleotides = list(filter(lambda x: x in 'AGTC', nucleotides))
print(nucleotides)

copyNucleo = list(map(lambda x: 'A' if x == 'T' else 'T' if x == 'A' else 'C' if x == 'G' else 'G' if x == 'C' else "Error", nucleotides))
print(copyNucleo)

listC = list(map(lambda x: 1 if x == 'C' else 0, copyNucleo))
listG = list(map(lambda x: 1 if x == 'G' else 0, copyNucleo))
print(listC)
print(listG)

f = lambda x, y: x + y
percC = reduce(f, listC)/len(nucleotides)
percG = reduce(f, listG)/len(nucleotides)

print('{:.3f}'.format(percC))
print('{:.3f}'.format(percG))
