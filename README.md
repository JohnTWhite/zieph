# zipf


*Under construction, current project*


Zipf's Law is more encompassing then this project could empirically embody. It can be more thoroughly explored  here: https://en.wikipedia.org/wiki/Zipf%27s_law . The scope of this project is to take in natural language and compare it to Zipf Law on a log-log graph. Thus counting all the occurrences of words in any natural language text, like a book, or email, and compare that to what Zipf said it should be. I compare these two on a log-log graph made using swing.

This Java application takes in information through either through a string, or file, and then counts each word in unicode format. Then if that word has not already been inserted into the HashMap made to hold our results, a place is made for that word with a value of 1. If a place already exists in the Hash Map for that word that spot's value is increased by 1 to reflect the use of that word. We then use a comparative function to order the Map, and compare those results to Zipf's on the graph mentioned earlier. 



