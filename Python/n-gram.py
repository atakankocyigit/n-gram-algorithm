import re,string,itertools,timeit

def createNgram(corpusWords,N):
    print("----------"+ str(N) + "-Gram----------")
    nGramcounts = {}
    startTime = timeit.default_timer()
    for i in range(len(corpusWords)-N+1):
        #create n-gram
        gram = ""
        for j in range(N):
            gram+= corpusWords[i+j] + " " if j != N-1  else corpusWords[i+j]
    
        nGramcounts[gram] = 1 if gram not in nGramcounts else nGramcounts[gram] + 1
    #sorting
    nGramcounts = {k: v for k, v in sorted(nGramcounts.items(), key=lambda item: item[1], reverse = True)}
    
    endTime = timeit.default_timer()
    
    first_100_items = dict(itertools.islice(nGramcounts.items(), 100)) 
    i = 1
    #print first 100 item
    for x in first_100_items:
        print(str(i), "- " , x , " frequency: " , str(first_100_items[x]))
        i+=1
        
    print('Time: ', endTime - startTime,"\n\n") 

#reading files
files = ["BİLİM İŞ BAŞINDA.txt", "BOZKIRDA.txt", "DEĞİŞİM.txt", "DENEMELER.txt", "UNUTULMUŞ DİYARLAR.txt"]
corpus = ""
for file in files:
    with open(file, encoding='UTF-8') as f:
        for line in f:
            corpus += " " + line.strip()

#preprocessing
corpus = re.sub('([' + string.punctuation+ '])', r' \1 ', corpus)
corpus = re.sub('([»,«,’,“,”,—])', r' \1 ', corpus)
corpus = re.sub('\s+',' ',corpus)[1:]

corpus = corpus.replace("İ", "i").replace("Ç","ç").replace("Ü","ü").replace("I", "ı").replace("Ş","ş").lower().replace(". . .", "...").replace(". .", "..").replace("? ..", "?..").replace("! ..", "!..")
corp = corpus.split()

createNgram(corp, 1)
createNgram(corp, 2)
createNgram(corp, 3)
