# Monster Trading Cards Project
## Tecknical Steps
### Designs
For the design of the project i choose to first of all focus on the server. I created a db package that contains everything database related such as the singleton database
connection class as well as db handlers for each package i would make. Then i added Handler classes for all database retaled functions, to not directly access the database
from outside. So i started to build the project "inside-out" always xreating the database related classes and functions first and the putting it all together in my MethodMnager class,
that allwos me to just call every function that is needeed and handle every directive. I specifically had in mind that if one would want to extend the project, fruther classes
and pathescould be easilly added. So i basically created a rough design idea before starting and implmented it on the fly adding whatever component i needed at that time.
### Failures
1. While creating the project i naturally stumbled arcross some errors abd changed quite a bit. I first was really unsure how to even handle the curl. I googles a lot and searched for the 
answeres i needed. As the moodle course did not really provide any information, getting strated with the project was most liketly the biggest "problem". But after asking friends, 
googeling and sharing knowledge it somehow becaame clearer.
2. What really took me quite some time was the database connection - the curl would at some point just stop and the error "too many clients" would accur.
3. Project Specification
### Solutions
1. My first try was to just get a server running, that acceped the curl file and would create users. Of course this was not at
 all meant to be part of the latter code. I just figured out how it would work using some small external projects, where i simply tried stuff. So then when it comed to actually coding 
 the project it took me way less time than it could have, because i already had some components to check out how specific things work.
2. I fixed the database issue by using a singleton class for the database connection. Doing this, all problems diappeared and i could close he connection as it was supposed to. I also created a new connection valiable in each function, so i would not accidently close them if another thread still used it. That also solved the "connection already closed" issue.
3. The thrid thing that cost me some time was the Project Specification. Many things were not exactly true and i had to ask friends and/or the lector as to they would interpret the things that it said. It wasnt really an "error" but getting it right really cost me some time and thinking, sometimes even more than the actual ltime i spent coding.
## Unit Tests
I generally tried to test as much code as possible, but first of all i tested the battle-logic, the function to get the Deck of cards as card objects and logger. 
Then i continued testing the updating of a crad ownership because that finction is not only frequently used but also critical for the trading and battle to worc correctly. 
After that, i tested the trading functions such as creating a deal, checking existing deals, deleting deals, and trading with yourself to make sure taht the trading really works. 
I also tested the userclass as to weather it could create and login users and get userdata correctly - all of that siginificant to the basic functions of the programm. Last but 
not least i tested the Methodmanager on illegal acceses such as users that are not the admin wanting to create packages, or the username not mathcing the authorization token. 
All of the things mentioned above seemed critical to me, as they all come together to make the project actually work. Without these functions, nothing at all would work, so it is 
essential that these base functions are absolutely reliable.
## Time spent with the Project
Due to starting difficulies i reserved the Poject for when most of the other courses were over so that i really had time to focus on it completely. So I started thinking, planning and 
researching about the project about at the beginning of Jannuary. First i found it difficult to get it, but once i did i discovered that it wasn't so much really. All the coding (excluding 
short other projects to figure specific functionalities out) took me about four to five intense days of work.
